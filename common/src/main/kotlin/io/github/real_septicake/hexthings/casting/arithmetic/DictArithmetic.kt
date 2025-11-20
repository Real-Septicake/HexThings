package io.github.real_septicake.hexthings.casting.arithmetic

import at.petrak.hexcasting.api.casting.arithmetic.Arithmetic
import at.petrak.hexcasting.api.casting.arithmetic.operator.Operator
import at.petrak.hexcasting.api.casting.arithmetic.operator.OperatorBasic
import at.petrak.hexcasting.api.casting.arithmetic.predicates.IotaMultiPredicate
import at.petrak.hexcasting.api.casting.arithmetic.predicates.IotaPredicate
import at.petrak.hexcasting.api.casting.eval.CastingEnvironment
import at.petrak.hexcasting.api.casting.iota.Iota
import at.petrak.hexcasting.api.casting.iota.NullIota
import at.petrak.hexcasting.api.casting.math.HexPattern
import io.github.real_septicake.hexthings.casting.iota.DictIota

object DictArithmetic : Arithmetic {
    val impls = HashMap<HexPattern, Operator>()

    init {
        impls[Arithmetic.REPLACE] = mapKeyValToIota { mapIota, iota, iota2 -> mapIota[iota] = iota2; mapIota }
        impls[Arithmetic.REMOVE] = mapKeyToIotas { mapIota, iota -> listOf(mapIota, mapIota.map.remove(iota) ?: NullIota()) }
        impls[Arithmetic.INDEX_OF] = mapKeyToIotas { mapIota, iota -> listOf(mapIota[iota] ?: NullIota()) }
    }

    override fun arithName() = "map_arithmetic"

    override fun opTypes() = impls.keys

    override fun getOperator(pattern: HexPattern?) = impls[pattern]

    private fun mapKeyValToIota(op: (DictIota, Iota, Iota) -> Iota) = object : OperatorBasic (
        3,
        IotaMultiPredicate.triple(IotaPredicate.ofType(DictIota.TYPE), IotaPredicate.TRUE, IotaPredicate.TRUE)
    ) {
        override fun apply(iotas: Iterable<Iota>, env: CastingEnvironment): Iterable<Iota> {
            val it = iotas.iterator()
            return listOf(op(it.next() as DictIota, it.next(), it.next()))
        }
    }

    private fun mapKeyToIotas(op: (DictIota, Iota) -> Iterable<Iota>) = object : OperatorBasic (
        2,
        IotaMultiPredicate.pair(IotaPredicate.ofType(DictIota.TYPE), IotaPredicate.TRUE)
    ) {
        override fun apply(iotas: Iterable<Iota>, env: CastingEnvironment): Iterable<Iota> {
            val it = iotas.iterator()
            return op(it.next() as DictIota, it.next())
        }

    }
}