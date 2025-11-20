@file:Suppress("unused")

package io.github.real_septicake.hexthings.registry

import at.petrak.hexcasting.api.casting.ActionRegistryEntry
import at.petrak.hexcasting.api.casting.castables.Action
import at.petrak.hexcasting.api.casting.math.HexDir
import at.petrak.hexcasting.api.casting.math.HexPattern
import at.petrak.hexcasting.common.lib.HexRegistries
import at.petrak.hexcasting.common.lib.hex.HexActions
import io.github.real_septicake.hexthings.casting.actions.*
import io.github.real_septicake.hexthings.casting.actions.meta.OpResumeEscape

object HexthingsActions : HexthingsRegistrar<ActionRegistryEntry>(
    HexRegistries.ACTION,
    { HexActions.REGISTRY },
) {
    val FLAT_IN = make("insert", HexDir.NORTH_WEST, "wqwaeawqw") { OpFlatInsert }

    val ESCAPE_RESUME = make("requote", HexDir.NORTH_WEST, "deee") { OpResumeEscape }

    val IS_GREATER_SENT = make("isgreatersent", HexDir.EAST, "waeawaeqqq") { OpSentinelStatus }

    val EMPTY_DICT = make("empty_dict", HexDir.NORTH_WEST, "eaaea") { OpEmptyDict }
    val DICT_KEYS = make("dict_keys", HexDir.SOUTH_WEST, "dqwaeawq") { OpDictKeys }
    val DICT_VALS = make("dict_vals", HexDir.NORTH_WEST, "wqwaeawq") { OpDictVals }

    private fun make(name: String, startDir: HexDir, signature: String, getAction: () -> Action) = register(name) {
        ActionRegistryEntry(HexPattern.fromAngles(signature, startDir), getAction())
    }
}
