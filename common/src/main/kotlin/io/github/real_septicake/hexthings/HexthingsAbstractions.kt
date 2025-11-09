@file:JvmName("HexthingsAbstractions")

package io.github.real_septicake.hexthings

import dev.architectury.injectables.annotations.ExpectPlatform
import io.github.real_septicake.hexthings.registry.HexthingsRegistrar

fun initRegistries(vararg registries: HexthingsRegistrar<*>) {
    for (registry in registries) {
        initRegistry(registry)
    }
}

@ExpectPlatform
fun <T : Any> initRegistry(registrar: HexthingsRegistrar<T>) {
    throw AssertionError()
}
