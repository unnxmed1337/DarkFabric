package com.github.darkfabric.injection.mixins;

import com.github.darkfabric.DarkFabric;
import com.github.darkfabric.event.impl.player.PlayerKeyPressEvent;
import me.zero.alpine.event.EventState;
import net.minecraft.client.KeyboardHandler;
import net.minecraft.client.player.KeyboardInput;
import org.lwjgl.glfw.GLFW;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * MixinKeyboardListener
 *
 * @author Hexeption admin@hexeption.co.uk
 * @since 12/11/2019 - 12:13 pm
 */
@Mixin(KeyboardHandler.class)
public abstract class MixinKeyboardListener {

    @Inject(method = "keyPress", at = @At("HEAD"))
    public void onKeyEvent(long windowPointer, int key, int scanCode,
                           int action, int modifiers, CallbackInfo callbackInfo) {
        if (action == GLFW.GLFW_PRESS)
            DarkFabric.getInstance().getEventBus().post(
                    new PlayerKeyPressEvent(EventState.POST, key, scanCode));
    }

}
