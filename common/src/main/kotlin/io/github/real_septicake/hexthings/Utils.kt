package io.github.real_septicake.hexthings

import at.petrak.hexcasting.api.casting.iota.DoubleIota
import at.petrak.hexcasting.api.casting.iota.Iota
import at.petrak.hexcasting.api.casting.iota.NullIota
import at.petrak.hexcasting.api.casting.mishaps.MishapInvalidIota
import at.petrak.hexcasting.api.casting.mishaps.MishapNotEnoughArgs
import com.mojang.datafixers.util.Either
import io.github.real_septicake.hexthings.casting.iota.DictIota

fun List<Iota>.getDict(idx: Int, argc: Int = 0) : DictIota {
    val x = this.getOrElse(idx) { throw MishapNotEnoughArgs(idx + 1, this.size) }
    if(x !is DictIota)
        throw MishapInvalidIota.ofType(x, if (argc == 0) idx else argc - (idx + 1), "map")
    return x
}

fun List<Iota>.getNumOrNull(idx: Int, argc: Int = 0) : Either<Double, NullIota> {
    val x = this.getOrElse(idx) { throw MishapNotEnoughArgs(idx + 1, this.size) }
    return when(x) {
        is DoubleIota -> Either.left(x.double)
        is NullIota -> Either.right(x)
        else -> throw MishapInvalidIota.ofType(x, if(argc == 0) idx else argc - (idx + 1), "numnull")
    }
}