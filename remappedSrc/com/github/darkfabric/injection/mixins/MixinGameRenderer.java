package com.github.darkfabric.injection.mixins;

import com.github.darkfabric.DarkFabric;
import com.github.darkfabric.event.impl.render.RenderWorldEvent;
import me.zero.alpine.event.EventState;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
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
            target = "Lnet/minecraft/client/render/GameRenderer"
                    + ";renderHand(Lnet/minecraft/client/util/math/MatrixStack"
                    + ";Lnet/minecraft/client/render/Camera;F)V", shift = Shift.BEFORE))
    private void renderWorldPre(float tickDelta, long limitTime, MatrixStack matrix, CallbackInfo ci) {
        DarkFabric.getInstance().getEventBus().post(new RenderWorldEvent(EventState.PRE));
    }

    @Inject(method = "renderWorld", at = @At(value = "FIELD",
            target = "Lnet/minecraft/client/render/GameRenderer"
                    + ";renderHand(Lnet/minecraft/client/util/math/MatrixStack"
                    + ";Lnet/minecraft/client/render/Camera;F)V", shift = Shift.AFTER))
    private void renderWorldPost(float partialTicks, long finishTimeNano,
                                 MatrixStack matrixStackIn, CallbackInfo ci) {
        DarkFabric.getInstance().getEventBus().post(new RenderWorldEvent(EventState.POST));
    }
}
