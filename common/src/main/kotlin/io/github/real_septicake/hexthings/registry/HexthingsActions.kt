@file:Suppress("unused")

package io.github.real_septicake.hexthings.registry

import at.petrak.hexcasting.api.casting.ActionRegistryEntry
import at.petrak.hexcasting.api.casting.castables.Action
import at.petrak.hexcasting.api.casting.math.HexDir
import at.petrak.hexcasting.api.casting.math.HexPattern
import at.petrak.hexcasting.common.lib.HexRegistries
import at.petrak.hexcasting.common.lib.hex.HexActions
import io.github.real_septicake.hexthings.casting.actions.*
import io.github.real_septicake.hexthings.casting.actions.dict.OpDictEntries
import io.github.real_septicake.hexthings.casting.actions.dict.OpDictKeys
import io.github.real_septicake.hexthings.casting.actions.dict.OpDictVals
import io.github.real_septicake.hexthings.casting.actions.dict.OpEmptyDict
import io.github.real_septicake.hexthings.casting.actions.list.OpFlatInsert
import io.github.real_septicake.hexthings.casting.actions.list.OpRange
import io.github.real_septicake.hexthings.casting.actions.meta.OpResumeEscape
import io.github.real_septicake.hexthings.casting.actions.list.uiua.OpUiuaDrop
import io.github.real_septicake.hexthings.casting.actions.list.uiua.OpUiuaRotate
import io.github.real_septicake.hexthings.casting.actions.list.uiua.OpUiuaTake
import io.github.real_septicake.hexthings.casting.actions.list.uiua.OpUiuaWhere

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
    val DICT_ENTS = make("dict_ents", HexDir.SOUTH_WEST, "dqwaeawqd") { OpDictEntries }

    val UIUA_TAKE = make("uiua_take", HexDir.NORTH_WEST, "qaeaqwd") { OpUiuaTake }
    val UIUA_DROP = make("uiua_drop", HexDir.NORTH_WEST, "qaeaqda") { OpUiuaDrop }
    val UIUA_ROTATE = make("uiua_rotate", HexDir.NORTH_WEST, "qaeaqweeee") { OpUiuaRotate }
    val UIUA_WHERE = make("uiua_where", HexDir.NORTH_WEST, "qaeaqeaa") { OpUiuaWhere }
    val MAKE_RANGE = make("make_range", HexDir.NORTH_WEST, "qqqqqeweaqa") { OpRange }

    private fun make(name: String, startDir: HexDir, signature: String, getAction: () -> Action) = register(name) {
        ActionRegistryEntry(HexPattern.fromAngles(signature, startDir), getAction())
    }
}
