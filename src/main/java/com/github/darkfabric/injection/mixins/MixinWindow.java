package com.github.darkfabric.injection.mixins;

import com.github.darkfabric.DarkFabric;
import com.mojang.blaze3d.platform.Window;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GLCapabilities;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.io.InputStream;
import java.util.Locale;

import static org.lwjgl.glfw.GLFW.glfwSwapBuffers;
import static org.lwjgl.opengl.GL11.*;

@Mixin(Window.class)
public abstract class MixinWindow {

    @Shadow
    @Final
    private long window;

    @Redirect(method = "<init>",
            at = @At(value = "INVOKE", target = "Lorg/lwjgl/opengl/GL;createCapabilities()Lorg/lwjgl/opengl/GLCapabilities;", remap = false))
    private GLCapabilities onCreateCapabilities() {
        glfwSwapBuffers(window);
        final GLCapabilities result = GL.createCapabilities();

        glClearColor(0, 0, 0, 1);
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
        glfwSwapBuffers(window);

        return result;
    }

    @Inject(method = "setIcon", at = @At(value = "HEAD"))
    public void setIcon(InputStream inputStream, InputStream inputStream2, CallbackInfo ci) {
        try {
            inputStream = Minecraft.getInstance().getClientPackSource().getVanillaPack()
                    .getResource(PackType.CLIENT_RESOURCES,
                            new ResourceLocation(DarkFabric.getInstance().getName().toLowerCase()
                                    + ":icons/darkfabric-16.png"));
            inputStream2 = Minecraft.getInstance().getClientPackSource().getVanillaPack()
                    .getResource(PackType.CLIENT_RESOURCES,
                            new ResourceLocation(DarkFabric.getInstance().getName().toLowerCase()
                                    + ":icons/darkfabric-32.png"));
        } catch (Exception ignored) {
            ignored.printStackTrace();
        }
    }

}
