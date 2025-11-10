package io.github.real_septicake.hexthings.mixin;

import at.petrak.hexcasting.api.casting.eval.ResolvedPatternType;
import at.petrak.hexcasting.api.casting.eval.vm.CastingImage;
import at.petrak.hexcasting.api.casting.iota.Iota;
import at.petrak.hexcasting.api.casting.iota.NullIota;
import at.petrak.hexcasting.api.casting.iota.PatternIota;
import at.petrak.hexcasting.api.casting.math.HexAngle;
import at.petrak.hexcasting.api.casting.mishaps.Mishap;
import at.petrak.hexcasting.api.casting.mishaps.MishapTooManyCloseParens;
import io.github.real_septicake.hexthings.casting.eval.SpecialPatterns;
import io.github.real_septicake.hexthings.casting.mishaps.MishapNestedUnquote;
import kotlin.Pair;
import net.minecraft.nbt.CompoundTag;
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

    @Inject(method = "handleParentheses(Lat/petrak/hexcasting/api/casting/iota/Iota;)Lkotlin/Pair;", at = @At("HEAD"), cancellable = true, remap = false)
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
                if(image.getUserData().contains("hexthings_prev"))
                    cir.setReturnValue(new Pair<>(new CastingImage(), ResolvedPatternType.INVALID));
                CompoundTag image2 = image.serializeToNbt();
                CompoundTag newUserData = image.getUserData();
                newUserData.put("hexthings_prev", image2);
                CastingImage newImage = image.copy(List.of(new NullIota()), 0, new ArrayList<>(),
                        false, image.getOpsConsumed(), newUserData);
                cir.setReturnValue(new Pair<>(newImage, ResolvedPatternType.EVALUATED));
            }
        }
    }
}
