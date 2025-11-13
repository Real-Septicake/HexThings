package io.github.real_septicake.hexthings.mixin;

import at.petrak.hexcasting.api.casting.eval.ExecutionClientView;
import at.petrak.hexcasting.common.msgs.MsgNewSpellPatternS2C;
import io.github.real_septicake.hexthings.mxin_interface.ECVMixinInterface;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.FriendlyByteBuf;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(at.petrak.hexcasting.common.msgs.MsgNewSpellPatternS2C.class)
public class MsgNewSpellPatternS2CMixin {
    @Final
    @Shadow(remap = false)
    private ExecutionClientView info;

    @Unique
    private static int depth;

    @Inject(
            method = "deserialize",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/network/FriendlyByteBuf;readInt()I",
                    shift = At.Shift.AFTER,
                    remap = false
            ),
            remap = false
    )
    private static void readDepth(ByteBuf buffer, CallbackInfoReturnable<MsgNewSpellPatternS2C> cir) {
        depth = buffer.readInt();
    }

    @Inject(
            method = "deserialize",
            at = @At(value = "RETURN"),
            remap = false,
            cancellable = true
    )
    private static void alterReturn(ByteBuf buffer, CallbackInfoReturnable<MsgNewSpellPatternS2C> cir) {
        MsgNewSpellPatternS2C m = cir.getReturnValue();
        ExecutionClientView ecv = m.info();
        ((ECVMixinInterface) (Object) ecv).hexThings$setDepth(depth);
        cir.setReturnValue(new MsgNewSpellPatternS2C(ecv, m.index()));
    }

    @Inject(
            method = "serialize",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/network/FriendlyByteBuf;writeInt(I)Lio/netty/buffer/ByteBuf;",
                    shift = At.Shift.AFTER,
                    remap = false
            ),
            remap = false
    )
    private void writeDepth(FriendlyByteBuf buf, CallbackInfo ci) {
        buf.writeInt(((ECVMixinInterface) (Object) info).hexThings$getDepth());
    }
}
