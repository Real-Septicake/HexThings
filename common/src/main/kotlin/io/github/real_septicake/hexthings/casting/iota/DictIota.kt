package io.github.real_septicake.hexthings.casting.iota

import at.petrak.hexcasting.api.casting.iota.Iota
import at.petrak.hexcasting.api.casting.iota.IotaType
import at.petrak.hexcasting.api.utils.asCompound
import net.minecraft.ChatFormatting
import net.minecraft.nbt.CompoundTag
import net.minecraft.nbt.Tag
import net.minecraft.network.chat.Component
import net.minecraft.server.level.ServerLevel

class DictIota(val map: HashMap<Iota, Iota>) : Iota(TYPE, map) {
    override fun isTruthy() = map.isNotEmpty()

    override fun toleratesOther(that: Iota?): Boolean {
        return typesMatch(this, that)
                && that is DictIota
                && this.map === that.map
    }

    override fun serialize(): Tag {
        val tag = CompoundTag()
        map.onEachIndexed { i, entry ->
            val e = CompoundTag()
            e.put("key", IotaType.serialize(entry.key))
            e.put("value", IotaType.serialize(entry.value))
            tag.put(i.toString(), e)
        }
        return tag
    }

    operator fun get(key: Iota) = map[key]
    operator fun set(key: Iota, value: Iota) { map[key] = value }

    companion object {
        val TYPE: IotaType<DictIota> = object : IotaType<DictIota>() {
            override fun deserialize(tag: Tag, world: ServerLevel) = DictIota.deserialize(tag, world)

            override fun display(tag: Tag): Component = display(tag.asCompound.size())

            override fun color(): Int {
                return 0x1bd6cf
            }
        }

        fun deserialize(tag: Tag, world: ServerLevel): DictIota {
            val ctag = tag.asCompound
            var idx = 0
            val map = HashMap<Iota, Iota>()
            while(ctag.contains(idx.toString())) {
                val entry = ctag[idx.toString()]!!.asCompound
                val key = IotaType.deserialize(entry["key"]?.asCompound, world)
                val value = IotaType.deserialize(entry["value"]?.asCompound, world)
                map[key] = value
                idx++
            }
            return DictIota(map)
        }

        fun display(size: Int): Component {
            return Component.translatable("hexthings.tooltip.dict")
                .append(" { size: $size }")
                .withStyle(ChatFormatting.DARK_AQUA)
        }
    }
}