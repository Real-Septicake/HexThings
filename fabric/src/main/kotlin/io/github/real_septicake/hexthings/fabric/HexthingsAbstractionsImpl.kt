@file:JvmName("HexthingsAbstractionsImpl")

package io.github.real_septicake.hexthings.fabric

import io.github.real_septicake.hexthings.registry.HexthingsRegistrar
import net.minecraft.core.Registry

fun <T : Any> initRegistry(registrar: HexthingsRegistrar<T>) {
    val registry = registrar.registry
    registrar.init { id, value -> Registry.register(registry, id, value) }
}
