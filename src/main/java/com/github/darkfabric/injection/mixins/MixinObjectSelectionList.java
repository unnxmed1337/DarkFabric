package com.github.darkfabric.injection.mixins;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.components.AbstractSelectionList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AbstractSelectionList.class)
public abstract class MixinObjectSelectionList {

    @Shadow private boolean renderBackground;

    @Inject(method = "render", at = @At(value = "FIELD",
            target = "Lnet/minecraft/client/gui/components/AbstractSelectionList;getRowLeft()I", shift = At.Shift.BEFORE))
    public void render(PoseStack poseStack, int i, int j, float f, CallbackInfo ci) {
        if (renderBackground) {
        }
    }

}