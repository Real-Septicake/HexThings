package io.github.real_septicake.hexthings.fabric

import io.github.real_septicake.hexthings.Hexthings
import net.fabricmc.api.DedicatedServerModInitializer

object FabricHexthingsServer : DedicatedServerModInitializer {
    override fun onInitializeServer() {
        Hexthings.initServer()
    }
}
