package io.github.real_septicake.hexthings.fabric.datagen

import io.github.real_septicake.hexthings.datagen.HexthingsActionTags
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator

object FabricHexthingsDatagen : DataGeneratorEntrypoint {
    override fun onInitializeDataGenerator(gen: FabricDataGenerator) {
        val pack = gen.createPack()

        pack.addProvider(::HexthingsActionTags)
    }
}
