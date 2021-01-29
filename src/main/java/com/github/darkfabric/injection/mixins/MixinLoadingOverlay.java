package com.github.darkfabric.injection.mixins;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.screens.LoadingOverlay;
import net.minecraft.network.protocol.game.ClientboundLevelParticlesPacket;
import net.minecraft.util.Mth;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.awt.*;

@Mixin(LoadingOverlay.class)
public abstract class MixinLoadingOverlay {

    @Mutable
    @Shadow
    @Final
    private static final int BRAND_BACKGROUND, BRAND_BACKGROUND_NO_ALPHA;

    static {
        BRAND_BACKGROUND = Color.black.hashCode();
        BRAND_BACKGROUND_NO_ALPHA = BRAND_BACKGROUND & 16777215;
    }

    @Shadow
    private float currentProgress;

    @Inject(method = "render", at = @At(value = "INVOKE",
            target = "Lcom/mojang/blaze3d/systems/RenderSystem;color4f(FFFF)V", shift = At.Shift.AFTER))
    public void render(PoseStack poseStack, int i, int j, float f, CallbackInfo ci) {
        GL11.glColor4f(135.f / 255.f, 0, 135.f / 255.f, 1.0f);
    }

    @Inject(method = "drawProgressBar", at = @At(value = "HEAD"), cancellable = true)
    public void drawProgressBar(PoseStack poseStack, int i, int j, int k, int l, float f, CallbackInfo ci) {
        int o = new Color(135.f / 255.f, 0, 135.f / 255.f, 1.0f).hashCode();
        Gui.fill(poseStack, i + 1, j, k - 1, j + 1, o);
        Gui.fill(poseStack, i + 1, l, k - 1, l - 1, o);
        Gui.fill(poseStack, i, j, i + 1, l, o);
        Gui.fill(poseStack, k, j, k - 1, l, o);
        Gui.fill(poseStack, i + 2, j + 2, i + Mth.ceil((float) (k - i - 2) * currentProgress),
                l - 2, o);
        ci.cancel();
    }

}