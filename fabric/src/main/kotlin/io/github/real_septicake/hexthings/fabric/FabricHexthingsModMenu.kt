package io.github.real_septicake.hexthings.fabric

import com.terraformersmc.modmenu.api.ConfigScreenFactory
import com.terraformersmc.modmenu.api.ModMenuApi
import io.github.real_septicake.hexthings.HexthingsClient

object FabricHexthingsModMenu : ModMenuApi {
    override fun getModConfigScreenFactory() = ConfigScreenFactory(HexthingsClient::getConfigScreen)
}
