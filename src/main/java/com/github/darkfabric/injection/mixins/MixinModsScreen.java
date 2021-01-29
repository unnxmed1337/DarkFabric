package com.github.darkfabric.injection.mixins;

import com.mojang.blaze3d.vertex.PoseStack;
import io.github.prospector.modmenu.gui.ModsScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.TextComponent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ModsScreen.class)
public abstract class MixinModsScreen extends Screen {

    protected MixinModsScreen() {
        super(new TextComponent("It's a Mixin. lol."));
    }

    @Inject(method = "renderBackground", at = @At(value = "HEAD"), cancellable = true)
    public void renderBackground(PoseStack poseStack, CallbackInfo ci) {
        super.renderBackground(poseStack);
        ci.cancel();
    }

}