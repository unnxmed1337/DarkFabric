package com.github.darkforge.injection.mixins;

import com.github.darkforge.DarkForgeReloaded;
import com.github.darkforge.event.impl.render.RenderWorldEvent;
import com.mojang.blaze3d.matrix.MatrixStack;
import me.zero.alpine.event.EventState;
import net.minecraft.client.renderer.GameRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.At.Shift;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * MixinGameRenderer
 *
 * @author Hexeption admin@hexeption.co.uk
 * @since 12/11/2019 - 04:56 pm
 */
@Mixin(GameRenderer.class)
public abstract class MixinGameRenderer {

    @Inject(method = "renderWorld", at = @At(value = "FIELD",
            target = "Lnet/minecraft/client/renderer/GameRenderer;renderHand:Z", shift = Shift.BEFORE))
    private void renderWorldPre(float partialTicks, long finishTimeNano,
                                MatrixStack matrixStackIn, CallbackInfo ci) {
        DarkForgeReloaded.getInstance().getEventBus().post(new RenderWorldEvent(EventState.PRE));
    }

    @Inject(method = "renderWorld", at = @At(value = "FIELD",
            target = "Lnet/minecraft/client/renderer/GameRenderer;renderHand:Z", shift = Shift.AFTER))
    private void renderWorldPost(float partialTicks, long finishTimeNano,
                                 MatrixStack matrixStackIn, CallbackInfo ci) {
        DarkForgeReloaded.getInstance().getEventBus().post(new RenderWorldEvent(EventState.POST));
    }
}
