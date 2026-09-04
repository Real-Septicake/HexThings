package io.github.real_septicake.hexthings

import net.minecraft.resources.ResourceLocation
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import io.github.real_septicake.hexthings.registry.HexthingsActions
import io.github.real_septicake.hexthings.registry.HexthingsArithmetics
import io.github.real_septicake.hexthings.registry.HexthingsIotas
import io.github.real_septicake.hexthings.registry.HexthingsSpecialHandlers

object Hexthings {
    const val MODID = "hexthings"

    @JvmField
    val LOGGER: Logger = LogManager.getLogger(MODID)

    @JvmStatic
    fun id(path: String) = ResourceLocation(MODID, path)

    fun init() {
        initRegistries(
            HexthingsActions,
            HexthingsSpecialHandlers,
            HexthingsIotas,
            HexthingsArithmetics
        )
    }

    fun initServer() {
    }
}
