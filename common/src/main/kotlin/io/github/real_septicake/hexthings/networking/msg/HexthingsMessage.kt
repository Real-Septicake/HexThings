package io.github.real_septicake.hexthings.networking.msg

import dev.architectury.networking.NetworkChannel
import dev.architectury.networking.NetworkManager.PacketContext
import io.github.real_septicake.hexthings.Hexthings
import io.github.real_septicake.hexthings.networking.HexthingsNetworking
import io.github.real_septicake.hexthings.networking.handler.applyOnClient
import io.github.real_septicake.hexthings.networking.handler.applyOnServer
import net.fabricmc.api.EnvType
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.server.level.ServerPlayer
import java.util.function.Supplier

sealed interface HexthingsMessage

sealed interface HexthingsMessageC2S : HexthingsMessage {
    fun sendToServer() {
        HexthingsNetworking.CHANNEL.sendToServer(this)
    }
}

sealed interface HexthingsMessageS2C : HexthingsMessage {
    fun sendToPlayer(player: ServerPlayer) {
        HexthingsNetworking.CHANNEL.sendToPlayer(player, this)
    }

    fun sendToPlayers(players: Iterable<ServerPlayer>) {
        HexthingsNetworking.CHANNEL.sendToPlayers(players, this)
    }
}

sealed interface HexthingsMessageCompanion<T : HexthingsMessage> {
    val type: Class<T>

    fun decode(buf: FriendlyByteBuf): T

    fun T.encode(buf: FriendlyByteBuf)

    fun apply(msg: T, supplier: Supplier<PacketContext>) {
        val ctx = supplier.get()
        when (ctx.env) {
            EnvType.SERVER, null -> {
                Hexthings.LOGGER.debug("Server received packet from {}: {}", ctx.player.name.string, this)
                when (msg) {
                    is HexthingsMessageC2S -> msg.applyOnServer(ctx)
                    else -> Hexthings.LOGGER.warn("Message not handled on server: {}", msg::class)
                }
            }
            EnvType.CLIENT -> {
                Hexthings.LOGGER.debug("Client received packet: {}", this)
                when (msg) {
                    is HexthingsMessageS2C -> msg.applyOnClient(ctx)
                    else -> Hexthings.LOGGER.warn("Message not handled on client: {}", msg::class)
                }
            }
        }
    }

    fun register(channel: NetworkChannel) {
        channel.register(type, { msg, buf -> msg.encode(buf) }, ::decode, ::apply)
    }
}
