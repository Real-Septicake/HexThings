package io.github.real_septicake.hexthings.registry

import at.petrak.hexcasting.api.casting.iota.IotaType
import at.petrak.hexcasting.common.lib.HexRegistries
import at.petrak.hexcasting.common.lib.hex.HexIotaTypes
import io.github.real_septicake.hexthings.casting.iota.DictIota

object HexthingsIotas : HexthingsRegistrar<IotaType<*>>(
    HexRegistries.IOTA_TYPE,
    { HexIotaTypes.REGISTRY }
) {
    val DICT = make("dict", DictIota.TYPE)

    private fun make(name: String, type: IotaType<*>) = register(name) { type }
}