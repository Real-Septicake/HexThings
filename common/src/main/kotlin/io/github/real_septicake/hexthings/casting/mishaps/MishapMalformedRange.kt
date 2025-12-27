package io.github.real_septicake.hexthings.casting.mishaps

import at.petrak.hexcasting.api.casting.eval.CastingEnvironment
import at.petrak.hexcasting.api.casting.iota.GarbageIota
import at.petrak.hexcasting.api.casting.iota.Iota
import at.petrak.hexcasting.api.casting.iota.ListIota
import at.petrak.hexcasting.api.casting.mishaps.Mishap
import net.minecraft.world.item.DyeColor

class MishapMalformedRange(val error: String, vararg v: Any) : Mishap() {
    val args = v

    override fun accentColor(
        ctx: CastingEnvironment,
        errorCtx: Context
    ) = dyeColor(DyeColor.RED)

    override fun errorMessage(
        ctx: CastingEnvironment,
        errorCtx: Context
    ) = error("malformed_range.$error", *args)

    override fun execute(env: CastingEnvironment, errorCtx: Context, stack: MutableList<Iota>) {
        repeat(3) { stack.removeLast() } // only used for OpRange, fine to assume
        stack.add(ListIota(listOf(GarbageIota())))
    }
}