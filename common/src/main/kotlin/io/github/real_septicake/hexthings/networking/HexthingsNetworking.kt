package io.github.real_septicake.hexthings.networking

import dev.architectury.networking.NetworkChannel
import io.github.real_septicake.hexthings.Hexthings
import io.github.real_septicake.hexthings.networking.msg.HexthingsMessageCompanion

object HexthingsNetworking {
    val CHANNEL: NetworkChannel = NetworkChannel.create(Hexthings.id("networking_channel"))

    fun init() {
        for (subclass in HexthingsMessageCompanion::class.sealedSubclasses) {
            subclass.objectInstance?.register(CHANNEL)
        }
    }
}
