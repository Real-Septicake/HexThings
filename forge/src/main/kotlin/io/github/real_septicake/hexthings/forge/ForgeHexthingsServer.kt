package io.github.real_septicake.hexthings.forge

import io.github.real_septicake.hexthings.Hexthings
import net.minecraftforge.fml.event.lifecycle.FMLDedicatedServerSetupEvent

object ForgeHexthingsServer {
    @Suppress("UNUSED_PARAMETER")
    fun init(event: FMLDedicatedServerSetupEvent) {
        Hexthings.initServer()
    }
}
