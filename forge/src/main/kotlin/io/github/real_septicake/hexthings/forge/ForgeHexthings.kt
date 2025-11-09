package io.github.real_septicake.hexthings.forge

import dev.architectury.platform.forge.EventBuses
import io.github.real_septicake.hexthings.Hexthings
import io.github.real_septicake.hexthings.forge.datagen.ForgeHexthingsDatagen
import net.minecraftforge.fml.common.Mod
import thedarkcolour.kotlinforforge.forge.MOD_BUS

@Mod(Hexthings.MODID)
class ForgeHexthings {
    init {
        MOD_BUS.apply {
            EventBuses.registerModEventBus(Hexthings.MODID, this)
            addListener(ForgeHexthingsClient::init)
            addListener(ForgeHexthingsDatagen::init)
            addListener(ForgeHexthingsServer::init)
        }
        Hexthings.init()
    }
}
