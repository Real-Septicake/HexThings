package io.github.real_septicake.hexthings.fabric

import io.github.real_septicake.hexthings.HexthingsClient
import net.fabricmc.api.ClientModInitializer

object FabricHexthingsClient : ClientModInitializer {
    override fun onInitializeClient() {
        HexthingsClient.init()
    }
}
