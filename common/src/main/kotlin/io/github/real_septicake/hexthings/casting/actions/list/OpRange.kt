package io.github.real_septicake.hexthings.casting.actions.list

import at.petrak.hexcasting.api.casting.asActionResult
import at.petrak.hexcasting.api.casting.castables.ConstMediaAction
import at.petrak.hexcasting.api.casting.eval.CastingEnvironment
import at.petrak.hexcasting.api.casting.getDouble
import at.petrak.hexcasting.api.casting.iota.DoubleIota
import at.petrak.hexcasting.api.casting.iota.Iota
import io.github.real_septicake.hexthings.casting.mishaps.MishapMalformedRange
import io.github.real_septicake.hexthings.getNumOrNull
import kotlin.math.abs

object OpRange : ConstMediaAction {
    override val argc = 3

    override fun execute(args: List<Iota>, env: CastingEnvironment): List<Iota> {
        val start = args.getDouble(0, argc)
        val end = args.getDouble(1, argc)
        val stepIota = args.getNumOrNull(2, argc)
        if(start == end)
            return listOf()
        var step = 0.0
        stepIota.ifLeft {
            step = abs(it) * (if(start < end) 1.0 else -1.0)
        }.ifRight {
            step = if(start < end) 1.0 else -1.0
        }
        if(step == 0.0)
            throw MishapMalformedRange("zero")
        val size = (end - start) / abs(step)
        if(args.size + size > 1023)
            throw MishapMalformedRange("size", size)
        val check = {v: Double -> if(start > end) v > end else v < end }
        val result = mutableListOf<DoubleIota>()
        var current = start
        while(check(current)) {
            result.add(DoubleIota(current))
            current += step
        }
        return result.asActionResult
    }
}