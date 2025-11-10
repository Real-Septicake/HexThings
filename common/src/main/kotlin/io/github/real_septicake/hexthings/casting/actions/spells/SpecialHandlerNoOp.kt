package io.github.real_septicake.hexthings.casting.actions.spells

import at.petrak.hexcasting.api.casting.castables.Action
import at.petrak.hexcasting.api.casting.castables.ConstMediaAction
import at.petrak.hexcasting.api.casting.castables.SpecialHandler
import at.petrak.hexcasting.api.casting.eval.CastingEnvironment
import at.petrak.hexcasting.api.casting.iota.Iota
import at.petrak.hexcasting.api.casting.math.HexPattern
import at.petrak.hexcasting.api.utils.lightPurple
import net.minecraft.network.chat.Component

class SpecialHandlerNoOp : SpecialHandler {
    override fun act(): Action {
        return object : ConstMediaAction {
            override val argc: Int
                get() = 0

            override fun execute(args: List<Iota>, env: CastingEnvironment): List<Iota> {
                return listOf()
            }
        }
    }

    override fun getName(): Component {
        return "NoOp".lightPurple
    }

    class Factory : SpecialHandler.Factory<SpecialHandlerNoOp> {
        override fun tryMatch(pattern: HexPattern, env: CastingEnvironment): SpecialHandlerNoOp? {
            val sig = pattern.anglesSignature()
            if(sig.startsWith("dade"))
                return SpecialHandlerNoOp()
            return null
        }

    }
}