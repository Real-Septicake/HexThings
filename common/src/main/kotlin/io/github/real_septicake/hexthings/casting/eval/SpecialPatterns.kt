package io.github.real_septicake.hexthings.casting.eval

import at.petrak.hexcasting.api.casting.math.HexDir
import at.petrak.hexcasting.api.casting.math.HexPattern

object SpecialPatterns {
    val ESCAPE_STOP = HexPattern.fromAngles("aqqq", HexDir.NORTH_EAST)
}