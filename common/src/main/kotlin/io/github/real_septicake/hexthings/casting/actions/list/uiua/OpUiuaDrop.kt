package io.github.real_septicake.hexthings.casting.actions.list.uiua

import at.petrak.hexcasting.api.casting.castables.ConstMediaAction
import at.petrak.hexcasting.api.casting.eval.CastingEnvironment
import at.petrak.hexcasting.api.casting.getList
import at.petrak.hexcasting.api.casting.getLongOrList
import at.petrak.hexcasting.api.casting.iota.DoubleIota
import at.petrak.hexcasting.api.casting.iota.Iota
import at.petrak.hexcasting.api.casting.iota.ListIota
import at.petrak.hexcasting.api.casting.mishaps.MishapInvalidIota
import kotlin.math.absoluteValue
import kotlin.math.roundToInt

object OpUiuaDrop : ConstMediaAction {
    override val argc = 2

    override fun execute(args: List<Iota>, env: CastingEnvironment): List<Iota> {
        val l = args.getList(0, argc)
        val list = l.toList()
        val ret = mutableListOf<Iota>()
        val idx = args.getLongOrList(1, argc)
        idx.ifLeft {
            if(it >= 0)
                ret.addAll(list.drop(it.toInt()))
            else
                ret.addAll(list.dropLast(it.toInt().absoluteValue))
        }.ifRight {
            val indices = it.toSet().map { v ->
                if(v is DoubleIota)
                    v.double.roundToInt()
                else
                    throw MishapInvalidIota.ofType(ListIota(it), 0, "int_list")
            }
            list.forEachIndexed { index, iota ->
                if(index !in indices)
                    ret += iota
            }
//            ret.addAll(list)
//            for(i in it) {
//                if(i is DoubleIota) {
//                    val rounded = i.double.roundToInt()
//                    val max = it.size()
//                    if(rounded < 0 || rounded >= max)
//                        throw MishapInvalidIota.of(i, 0, "int.positive.less", max)
//                    ret.removeAt(rounded)
//                } else {
//                    throw MishapInvalidIota.ofType(ListIota(it), 0, "int_list")
//                }
//            }
        }
        return listOf(ListIota(ret))
    }
}