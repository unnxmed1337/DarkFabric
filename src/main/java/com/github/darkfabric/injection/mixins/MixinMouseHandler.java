package com.github.darkfabric.injection.mixins;

import net.minecraft.client.MouseHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MouseHandler.class)
public abstract class MixinMouseHandler {

    @Shadow
    private boolean isRightPressed;

    @Shadow
    private boolean isMiddlePressed;

    @Shadow
    private boolean isLeftPressed;

    @Inject(method = "onPress", at = @At(value = "HEAD"))
    public void onPress(long l, int i, int j, int k, CallbackInfo ci) {
        boolean bl = j == 1;
        if (i == 0)
            this.isLeftPressed = bl;
        else if (i == 2)
            this.isMiddlePressed = bl;
        else if (i == 1)
            this.isRightPressed = bl;
    }

}