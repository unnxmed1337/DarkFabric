package uk.co.hexeption.darkforgereborn.mixin.mixins;

import me.zero.alpine.event.EventState;
import net.minecraft.client.renderer.GameRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.At.Shift;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import uk.co.hexeption.darkforgereborn.DarkForgeReborn;
import uk.co.hexeption.darkforgereborn.event.events.world.EventRenderWorld;

/**
 * MixinGameRenderer
 *
 * @author Hexeption admin@hexeption.co.uk
 * @since 12/11/2019 - 04:56 pm
 */
@Mixin(GameRenderer.class)
public class MixinGameRenderer {

    @Inject(method = "updateCameraAndRender(FJ)V", at = @At(value = "FIELD", target = "Lnet/minecraft/client/renderer/GameRenderer;renderHand:Z", shift = Shift.BEFORE))
    private void updateCameraAndRenderPre(float partialTicks, long nanoTime, CallbackInfo callbackInfo) {
        DarkForgeReborn.INSTANCE.eventBus.post(new EventRenderWorld(EventState.PRE));
    }

    @Inject(method = "updateCameraAndRender(FJ)V", at = @At(value = "FIELD", target = "Lnet/minecraft/client/renderer/GameRenderer;renderHand:Z", shift = Shift.AFTER))
    private void updateCameraAndRenderPost(float partialTicks, long nanoTime, CallbackInfo callbackInfo) {
        DarkForgeReborn.INSTANCE.eventBus.post(new EventRenderWorld(EventState.POST));
    }
}
