package io.github.real_septicake.hexthings.casting.actions.spells.meta

import at.petrak.hexcasting.api.casting.castables.Action
import at.petrak.hexcasting.api.casting.eval.CastingEnvironment
import at.petrak.hexcasting.api.casting.eval.OperationResult
import at.petrak.hexcasting.api.casting.eval.SpecialPatterns
import at.petrak.hexcasting.api.casting.eval.vm.CastingImage
import at.petrak.hexcasting.api.casting.eval.vm.SpellContinuation
import at.petrak.hexcasting.api.casting.iota.NullIota
import at.petrak.hexcasting.api.casting.iota.PatternIota
import at.petrak.hexcasting.common.lib.hex.HexEvalSounds

object OpResumeEscape : Action {
    override fun operate(
        env: CastingEnvironment,
        image: CastingImage,
        continuation: SpellContinuation
    ): OperationResult {
        val stack = image.stack.toMutableList()
        if(stack.first() is NullIota)
            stack.removeFirst()
        val prev = image.userData.getCompound("hexthings_prev")
        val prevImage = CastingImage.loadFromNbt(prev, env.world)
        val newParens = prevImage.parenthesized.toMutableList()
        newParens.add(
            CastingImage.ParenthesizedIota(PatternIota(SpecialPatterns.INTROSPECTION), false)
        )
        newParens.addAll(stack.map { CastingImage.ParenthesizedIota(it, false) })
        newParens.add(
            CastingImage.ParenthesizedIota(PatternIota(SpecialPatterns.RETROSPECTION), false)
        )
        val newPrevImage = prevImage.copy(parenthesized = newParens, opsConsumed = prevImage.opsConsumed + image.opsConsumed)
        return OperationResult(newPrevImage, listOf(), continuation, HexEvalSounds.MUTE)
    }
}