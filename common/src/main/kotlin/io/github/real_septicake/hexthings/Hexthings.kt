package io.github.real_septicake.hexthings

import net.minecraft.resources.ResourceLocation
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import io.github.real_septicake.hexthings.config.HexthingsServerConfig
import io.github.real_septicake.hexthings.networking.HexthingsNetworking
import io.github.real_septicake.hexthings.registry.HexthingsActions
import io.github.real_septicake.hexthings.registry.HexthingsSpecialHandlers

object Hexthings {
    const val MODID = "hexthings"

    @JvmField
    val LOGGER: Logger = LogManager.getLogger(MODID)

    @JvmStatic
    fun id(path: String) = ResourceLocation(MODID, path)

    fun init() {
        HexthingsServerConfig.init()
        initRegistries(
            HexthingsActions,
            HexthingsSpecialHandlers
        )
        HexthingsNetworking.init()
    }

    fun initServer() {
        HexthingsServerConfig.initServer()
    }
}
