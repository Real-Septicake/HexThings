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
        val list = args.getList(0, argc)
        val idx = args.getPositiveIntUnder(1, list.size(), argc)
        val iotas = args.getList(2, argc)
        val mList = list.toMutableList()
        val mIotas = iotas.toMutableList()
        mList.removeAt(idx)
        while(mIotas.isNotEmpty()) {
            val iota = mIotas.removeLast()
            mList.add(idx, iota)
        }
        return SpellList.LList(mList).asActionResult
    }
}
