package io.github.real_septicake.hexthings.fabric

import io.github.real_septicake.hexthings.Hexthings
import net.fabricmc.api.ModInitializer

object FabricHexthings : ModInitializer {
    override fun onInitialize() {
        Hexthings.init()
    }
}
