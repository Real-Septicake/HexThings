package io.github.real_septicake.hexthings.mixin;

import at.petrak.hexcasting.api.casting.eval.ExecutionClientView;
import at.petrak.hexcasting.api.casting.eval.ResolvedPatternType;
import at.petrak.hexcasting.api.casting.eval.vm.CastingImage;
import at.petrak.hexcasting.api.casting.iota.Iota;
import at.petrak.hexcasting.api.casting.iota.PatternIota;
import at.petrak.hexcasting.api.casting.math.HexAngle;
import at.petrak.hexcasting.api.casting.mishaps.MishapTooManyCloseParens;
import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.ref.LocalBooleanRef;
import io.github.real_septicake.hexthings.casting.eval.SpecialPatterns;
import io.github.real_septicake.hexthings.mxin_interface.ECVMixinInterface;
import kotlin.Pair;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.ArrayList;
import java.util.List;

@Mixin(at.petrak.hexcasting.api.casting.eval.vm.CastingVM.class)
public abstract class CastingVMMixin {
    @Shadow(remap = false)
    private CastingImage image;

    @Inject(
            method = "queueExecuteAndWrapIotas",
            at = @At(
                    value = "INVOKE",
                    target = "Lat/petrak/hexcasting/api/casting/eval/CastingEnvironment;postCast(Lat/petrak/hexcasting/api/casting/eval/vm/CastingImage;)V",
                    shift = At.Shift.AFTER,
                    remap = false
            ),
            remap = false
    )
    private void modifyStackClear(List<? extends Iota> iotas, ServerLevel world, CallbackInfoReturnable<ExecutionClientView> cir, @Local LocalBooleanRef isStackClear) {
        isStackClear.set(isStackClear.get() && !image.getUserData().contains("hexthings_prev"));
    }

    @Inject(
            method = "queueExecuteAndWrapIotas",
            at = @At(value = "RETURN"),
            remap = false
    )
    private void modifyQueueExecuteAndWrapIotaReturn(List<? extends Iota> iotas, ServerLevel world, CallbackInfoReturnable<ExecutionClientView> cir) {
        ((ECVMixinInterface) (Object) cir.getReturnValue()).hexThings$setDepth(image.getUserData().getInt("hexthings_depth"));
    }

    @Inject(method = "handleParentheses", at = @At("HEAD"), cancellable = true, remap = false)
    private void handleParentheses(Iota iota, CallbackInfoReturnable<Pair<CastingImage, ResolvedPatternType>> cir) throws MishapTooManyCloseParens {
        List<HexAngle> sig = null;
        if(iota instanceof PatternIota p) {
            sig = p.getPattern().getAngles();
        }

        int displayDepth = this.image.getParenCount();
        if(!image.getEscapeNext() && sig != null) {
            if(sig.equals(SpecialPatterns.INSTANCE.getESCAPE_STOP().getAngles())) {
                if(displayDepth == 0)
                    throw new MishapTooManyCloseParens();
                CompoundTag image2 = image.serializeToNbt();
                CompoundTag newUserData = image.getUserData().copy();
                newUserData.put("hexthings_prev", image2);
                newUserData.putInt("hexthings_depth", image2.getInt("hexthings_depth") + 1);
                CastingImage newImage = image.copy(new ArrayList<>(), 0, new ArrayList<>(),
                        false, image.getOpsConsumed(), newUserData);
                cir.setReturnValue(new Pair<>(newImage, ResolvedPatternType.EVALUATED));
                cir.cancel();
            }
        }
    }
}
