package io.github.real_septicake.hexthings.casting.actions

import at.petrak.hexcasting.api.casting.SpellList
import at.petrak.hexcasting.api.casting.asActionResult
import at.petrak.hexcasting.api.casting.eval.CastingEnvironment
import at.petrak.hexcasting.api.casting.iota.Iota
import at.petrak.hexcasting.api.casting.castables.ConstMediaAction
import at.petrak.hexcasting.api.casting.getList
import at.petrak.hexcasting.api.casting.getPositiveIntUnder

object OpFlatInsert : ConstMediaAction {
    override val argc = 3

    override fun execute(args: List<Iota>, env: CastingEnvironment): List<Iota> {
        val list = args.getList(0, argc).toMutableList()
        val idx = args.getPositiveIntUnder(1, list.size, argc)
        val iotas = args.getList(2, argc).toMutableList()
        list.removeAt(idx)
        while(iotas.isNotEmpty()) {
            val iota = iotas.removeLast()
            list.add(idx, iota)
        }
        return SpellList.LList(list).asActionResult
    }
}
