package com.github.darkfabric.injection.mixins;

import com.github.darkfabric.DarkFabric;
import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * MixinMinecraft
 *
 * @author Hexeption admin@hexeption.co.uk
 * @since 10/11/2019 - 12:44 pm
 */
@Mixin(Minecraft.class)
public abstract class MixinMinecraft {

    @Inject(method = "<init>", at = @At(value = "RETURN"))
    private void startGame(CallbackInfo callbackInfo) {
        DarkFabric.getInstance().initialize();
    }

}