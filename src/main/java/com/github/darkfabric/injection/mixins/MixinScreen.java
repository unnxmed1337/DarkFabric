package com.github.darkfabric.injection.mixins;

import com.github.darkfabric.DarkFabric;
import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Screen.class)
public abstract class MixinScreen {

    @Inject(method = "renderDirtBackground", at = @At(value = "HEAD"), cancellable = true)
    public void renderBackground(int i, CallbackInfo ci) {
        GlStateManager._enableAlphaTest();
        GlStateManager._disableCull();
        DarkFabric.getInstance().getShaderRenderer().useShader(Minecraft.getInstance().getWindow().getWidth(),
                Minecraft.getInstance().getWindow().getHeight(), (float) Minecraft.getInstance().mouseHandler.xpos(),
                (float) Minecraft.getInstance().mouseHandler.ypos(),
                (System.currentTimeMillis() - DarkFabric.getInstance().getInitTime()) / 1000f);

        GL11.glBegin(GL11.GL_QUADS);

        GL11.glVertex2f(-1f, -1f);
        GL11.glVertex2f(-1f, 1f);
        GL11.glVertex2f(1f, 1f);
        GL11.glVertex2f(1f, -1f);

        GL11.glEnd();

        GL20.glUseProgram(0);

        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        ci.cancel();
    }

}