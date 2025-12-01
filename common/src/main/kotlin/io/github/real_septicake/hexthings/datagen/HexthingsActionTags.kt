package io.github.real_septicake.hexthings.datagen

import at.petrak.hexcasting.api.casting.ActionRegistryEntry
import at.petrak.hexcasting.common.lib.HexRegistries
import net.minecraft.core.HolderLookup
import net.minecraft.data.PackOutput
import net.minecraft.data.tags.TagsProvider
import java.util.concurrent.CompletableFuture

class HexthingsActionTags(
    output: PackOutput,
    provider: CompletableFuture<HolderLookup.Provider>,
) : TagsProvider<ActionRegistryEntry>(output, HexRegistries.ACTION, provider) {
    override fun addTags(provider: HolderLookup.Provider) {
    }
}
