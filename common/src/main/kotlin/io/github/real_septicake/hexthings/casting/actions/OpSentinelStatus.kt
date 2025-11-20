package io.github.real_septicake.hexthings.casting.actions

import at.petrak.hexcasting.api.casting.castables.ConstMediaAction
import at.petrak.hexcasting.api.casting.eval.CastingEnvironment
import at.petrak.hexcasting.api.casting.iota.BooleanIota
import at.petrak.hexcasting.api.casting.iota.Iota
import at.petrak.hexcasting.api.casting.iota.NullIota
import at.petrak.hexcasting.api.casting.mishaps.MishapBadCaster
import at.petrak.hexcasting.xplat.IXplatAbstractions
import net.minecraft.server.level.ServerPlayer

object OpSentinelStatus : ConstMediaAction {
    override val argc: Int = 0

    override fun execute(args: List<Iota>, env: CastingEnvironment): List<Iota> {
        if(env.castingEntity !is ServerPlayer)
            throw MishapBadCaster()
        val s = IXplatAbstractions.INSTANCE.getSentinel(env.castingEntity as ServerPlayer) ?: return listOf(NullIota())
        return if(s.extendsRange)
            listOf(BooleanIota(true))
        else
            listOf(BooleanIota(false))
    }
}