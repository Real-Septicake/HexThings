package io.github.real_septicake.hexthings.mixin;

import at.petrak.hexcasting.api.casting.eval.ExecutionClientView;
import at.petrak.hexcasting.client.gui.GuiSpellcasting;
import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.blaze3d.vertex.PoseStack;
import io.github.real_septicake.hexthings.mixin_interface.ECVMixinInterface;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GuiSpellcasting.class)
public class GuiSpellcastingMixin {
    @Unique
    private int hexThings$depth;

    @Inject(
            method = "recvServerUpdate",
            at = @At(value = "TAIL"),
            remap = false
    )
    public void getDepth(ExecutionClientView info, int index, CallbackInfo ci) {
        this.hexThings$depth = ((ECVMixinInterface) (Object) info).hexThings$getDepth();
    }

    @Inject(method = "render", at = @At(value = "TAIL"))
    public void render(GuiGraphics graphics, int pMouseX, int pMouseY, float pPartialTick, CallbackInfo ci, @Local Font font, @Local PoseStack ps) {
        if(hexThings$depth > 0) {
            ps.pushPose();
            String label = "Introjection Depth: " + hexThings$depth;
            int width = font.width(label);
            graphics.drawCenteredString(font, label, (((GuiSpellcasting)(Object)this).width/2), 10, 0xffffff);
            GuiSpellcasting.Companion.drawBox(ps, (((GuiSpellcasting)(Object)this).width/2.0f) - ((float) width /2) - 4, 6, width + 7, 16, 2.5f);
            ps.popPose();
        }
    }
}
