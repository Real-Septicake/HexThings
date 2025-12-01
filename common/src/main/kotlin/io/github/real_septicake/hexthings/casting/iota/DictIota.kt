package io.github.real_septicake.hexthings.casting.iota

import at.petrak.hexcasting.api.casting.iota.Iota
import at.petrak.hexcasting.api.casting.iota.IotaType
import at.petrak.hexcasting.api.utils.asCompound
import net.minecraft.ChatFormatting
import net.minecraft.nbt.CompoundTag
import net.minecraft.nbt.Tag
import net.minecraft.nbt.TagParser.parseTag
import net.minecraft.network.chat.Component
import net.minecraft.server.level.ServerLevel
import kotlin.math.max

class DictIota(val map: HashMap<String, Iota>, val deserial: MutableMap<String, Iota>) : Iota(TYPE, map) {
    var depth = 0
    override fun isTruthy() = map.isNotEmpty()

    init {
        for(v in map.values) {
            depth = max(v.depth(), depth)
        }
    }

    override fun toleratesOther(that: Iota?): Boolean {
        return typesMatch(this, that)
                && that is DictIota
                && this.map === that.map
    }

    override fun serialize(): Tag {
        val tag = CompoundTag()
        map.onEachIndexed { i, entry ->
            val e = CompoundTag()
            e.putString("key", entry.key)
            e.put("value", IotaType.serialize(entry.value))
            tag.put(i.toString(), e)
        }
        return tag
    }

    override fun subIotas(): MutableIterable<Iota> {
        return (map.values + deserial.values).toMutableList()
    }

    override fun size() = map.size * 2

    operator fun get(key: Iota) = map[IotaType.serialize(key).toString()]
    operator fun set(key: Iota, value: Iota) {
        map[IotaType.serialize(key).toString()] = value
        deserial[IotaType.serialize(key).toString()] = key
        depth = max(depth, value.depth())
    }

    companion object {
        val TYPE: IotaType<DictIota> = object : IotaType<DictIota>() {
            override fun deserialize(tag: Tag, world: ServerLevel) = DictIota.deserialize(tag, world)

            override fun display(tag: Tag): Component = DictIota.display(tag.asCompound)

            override fun color(): Int {
                return 0x1bd6cf
            }
        }

        fun deserialize(tag: Tag, world: ServerLevel): DictIota {
            val ctag = tag.asCompound
            var idx = 0
            val map = HashMap<String, Iota>()
            val toIota = HashMap<String, Iota>()
            while(ctag.contains(idx.toString())) {
                val entry = ctag[idx.toString()]!!.asCompound
                val key = entry["key"]!!.asString
                val value = IotaType.deserialize(entry["value"]?.asCompound, world)

                map[key] = value
                toIota[key] = IotaType.deserialize(parseTag(key).asCompound, world)
                idx++
            }
            return DictIota(map, toIota)
        }

        fun display(tag: CompoundTag): Component {
            val comp = Component.literal("{")
                .withStyle(ChatFormatting.DARK_AQUA)
            var idx = 0
            while(tag.contains(idx.toString())) {
                val entry = tag[idx.toString()]!!.asCompound
                idx++
                comp.append(IotaType.getDisplay(parseTag(entry["key"]!!.asString).asCompound))
                    .append(Component.literal(" : ").withStyle(ChatFormatting.DARK_AQUA))
                    .append(IotaType.getDisplay(entry["value"]!!.asCompound))
                if(tag.contains(idx.toString()))
                    comp.append(Component.literal(", ").withStyle(ChatFormatting.DARK_AQUA))
            }
            comp.append("}").withStyle(ChatFormatting.DARK_AQUA)
            return comp
        }
    }
}