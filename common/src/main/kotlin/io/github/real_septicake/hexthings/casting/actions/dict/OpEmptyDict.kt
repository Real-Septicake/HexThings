package io.github.real_septicake.hexthings.casting.actions.dict

import at.petrak.hexcasting.api.casting.castables.ConstMediaAction
import at.petrak.hexcasting.api.casting.eval.CastingEnvironment
import at.petrak.hexcasting.api.casting.iota.Iota
import io.github.real_septicake.hexthings.casting.iota.DictIota

object OpEmptyDict : ConstMediaAction {
    override val argc = 0

    override fun execute(args: List<Iota>, env: CastingEnvironment): List<Iota> {
        return listOf(DictIota(HashMap(), HashMap()))
    }
}