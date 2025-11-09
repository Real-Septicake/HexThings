package io.github.real_septicake.hexthings.networking.handler

import dev.architectury.networking.NetworkManager.PacketContext
import io.github.real_septicake.hexthings.config.HexthingsServerConfig
import io.github.real_septicake.hexthings.networking.msg.*

fun HexthingsMessageS2C.applyOnClient(ctx: PacketContext) = ctx.queue {
    when (this) {
        is MsgSyncConfigS2C -> {
            HexthingsServerConfig.onSyncConfig(serverConfig)
        }

        // add more client-side message handlers here
    }
}
