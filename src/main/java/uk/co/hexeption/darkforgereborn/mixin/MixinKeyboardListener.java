package uk.co.hexeption.darkforgereborn.mixin;

import me.zero.alpine.event.EventState;
import net.minecraft.client.KeyboardListener;
import org.lwjgl.glfw.GLFW;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import uk.co.hexeption.darkforgereborn.DarkForgeReborn;
import uk.co.hexeption.darkforgereborn.event.events.input.EventKeyPressed;

/**
 * MixinKeyboardListener
 *
 * @author Hexeption admin@hexeption.co.uk
 * @since 12/11/2019 - 12:13 pm
 */
@Mixin(KeyboardListener.class)
public class MixinKeyboardListener {

    @Inject(method = "onKeyEvent", at = @At("HEAD"))
    public void onKeyEvent(long windowPointer, int key, int scanCode, int action, int modifiers, CallbackInfo callbackInfo) {
        if (action == GLFW.GLFW_PRESS) {
            DarkForgeReborn.INSTANCE.eventBus.post(new EventKeyPressed(EventState.POST, key, scanCode));
        }
    }

}
