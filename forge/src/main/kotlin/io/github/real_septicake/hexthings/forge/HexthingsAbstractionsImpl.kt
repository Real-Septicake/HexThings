@file:JvmName("HexthingsAbstractionsImpl")

package io.github.real_septicake.hexthings.forge

import io.github.real_septicake.hexthings.registry.HexthingsRegistrar
import net.minecraftforge.registries.RegisterEvent
import thedarkcolour.kotlinforforge.forge.MOD_BUS

fun <T : Any> initRegistry(registrar: HexthingsRegistrar<T>) {
    MOD_BUS.addListener { event: RegisterEvent ->
        event.register(registrar.registryKey) { helper ->
            registrar.init(helper::register)
        }
    }
}
