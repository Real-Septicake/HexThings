package io.github.real_septicake.hexthings.casting.actions.meta

import at.petrak.hexcasting.api.casting.castables.Action
import at.petrak.hexcasting.api.casting.eval.CastingEnvironment
import at.petrak.hexcasting.api.casting.eval.OperationResult
import at.petrak.hexcasting.api.casting.eval.ParenthesizedOperationResult
import at.petrak.hexcasting.api.casting.eval.ResolvedPatternType
import at.petrak.hexcasting.api.casting.eval.vm.CastingImage
import at.petrak.hexcasting.api.casting.eval.vm.SpellContinuation
import at.petrak.hexcasting.api.casting.iota.Iota
import at.petrak.hexcasting.api.casting.mishaps.MishapNeedsParens
import at.petrak.hexcasting.common.lib.hex.HexEvalSounds

object OpStopEscape : Action {
    override fun operate(
        env: CastingEnvironment,
        image: CastingImage,
        continuation: SpellContinuation
    ): OperationResult {
        throw MishapNeedsParens()
    }

    override fun operateInParens(
        env: CastingEnvironment,
        image: CastingImage,
        continuation: SpellContinuation,
        thisIota: Iota
    ): ParenthesizedOperationResult {
        val compound = image.serializeToNbt()
        val newData = image.userData.copy()
        newData.put("hexthings_prev", compound)
        newData.putInt("hexthings_depth", newData.getInt("hexthings_depth") + 1)
        val image = CastingImage().copy(userData = newData)
        return ParenthesizedOperationResult(image, emptyList(), SpellContinuation.Done, HexEvalSounds.NORMAL_EXECUTE, ResolvedPatternType.EVALUATED)
    }
}