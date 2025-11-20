package io.github.real_septicake.hexthings.casting.actions

import at.petrak.hexcasting.api.casting.castables.ConstMediaAction
import at.petrak.hexcasting.api.casting.eval.CastingEnvironment
import at.petrak.hexcasting.api.casting.iota.Iota
import at.petrak.hexcasting.api.casting.iota.ListIota
import at.petrak.hexcasting.api.casting.mishaps.MishapInvalidIota
import at.petrak.hexcasting.api.casting.mishaps.MishapNotEnoughArgs
import io.github.real_septicake.hexthings.casting.iota.DictIota

object OpDictVals : ConstMediaAction {
    override val argc = 1

    override fun execute(args: List<Iota>, env: CastingEnvironment): List<Iota> {
        val map = args.getOrElse(0) { throw MishapNotEnoughArgs(1, 0) }
        if(map !is DictIota)
            throw MishapInvalidIota.ofType(map, 0, "map")
        return listOf(ListIota(ArrayList(map.map.values)))
    }
}