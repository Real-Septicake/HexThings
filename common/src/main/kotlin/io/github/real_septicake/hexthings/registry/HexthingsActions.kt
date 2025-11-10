package io.github.real_septicake.hexthings.registry

import at.petrak.hexcasting.api.casting.ActionRegistryEntry
import at.petrak.hexcasting.api.casting.castables.Action
import at.petrak.hexcasting.api.casting.math.HexDir
import at.petrak.hexcasting.api.casting.math.HexPattern
import at.petrak.hexcasting.common.lib.HexRegistries
import at.petrak.hexcasting.common.lib.hex.HexActions
import io.github.real_septicake.hexthings.casting.actions.spells.OpFlatInsert
import io.github.real_septicake.hexthings.casting.actions.spells.meta.OpResumeEscape

object HexthingsActions : HexthingsRegistrar<ActionRegistryEntry>(
    HexRegistries.ACTION,
    { HexActions.REGISTRY },
) {
    val FLAT_IN = make("insert", HexDir.NORTH_WEST, "wqwaeawqw", OpFlatInsert)

    val ESCAPE_RESUME = make("escresume", HexDir.NORTH_WEST, "deee", OpResumeEscape)

    private fun make(name: String, startDir: HexDir, signature: String, action: Action) =
        make(name, startDir, signature) { action }

    private fun make(name: String, startDir: HexDir, signature: String, getAction: () -> Action) = register(name) {
        ActionRegistryEntry(HexPattern.fromAngles(signature, startDir), getAction())
    }
}
