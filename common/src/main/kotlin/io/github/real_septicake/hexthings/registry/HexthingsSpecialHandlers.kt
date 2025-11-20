package io.github.real_septicake.hexthings.registry

import at.petrak.hexcasting.api.casting.castables.SpecialHandler
import at.petrak.hexcasting.common.lib.HexRegistries
import at.petrak.hexcasting.xplat.IXplatAbstractions
import io.github.real_septicake.hexthings.casting.actions.SpecialHandlerNoOp

object HexthingsSpecialHandlers : HexthingsRegistrar<SpecialHandler.Factory<*>>(
    HexRegistries.SPECIAL_HANDLER,
    { IXplatAbstractions.INSTANCE.specialHandlerRegistry }
) {
    var NO_OP = register("noop") {
        SpecialHandlerNoOp.Factory()
    }
}