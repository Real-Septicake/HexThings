package io.github.real_septicake.hexthings.mixin;

import at.petrak.hexcasting.api.casting.eval.ExecutionClientView;
import at.petrak.hexcasting.api.casting.eval.vm.CastingImage;
import at.petrak.hexcasting.api.casting.iota.Iota;
import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.ref.LocalBooleanRef;
import io.github.real_septicake.hexthings.mixin_interface.ECVMixinInterface;
import net.minecraft.server.level.ServerLevel;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

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
    private void modifyStackClear(List<? extends Iota> iotas, ServerLevel world, CallbackInfoReturnable<ExecutionClientView> cir, @Local(name = "isStackClear") LocalBooleanRef isStackClear) {
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
}
