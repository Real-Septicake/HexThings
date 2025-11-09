package io.github.real_septicake.hexthings

import io.github.real_septicake.hexthings.config.HexthingsClientConfig
import me.shedaniel.autoconfig.AutoConfig
import net.minecraft.client.gui.screens.Screen

object HexthingsClient {
    fun init() {
        HexthingsClientConfig.init()
    }

    fun getConfigScreen(parent: Screen): Screen {
        return AutoConfig.getConfigScreen(HexthingsClientConfig.GlobalConfig::class.java, parent).get()
    }
}
