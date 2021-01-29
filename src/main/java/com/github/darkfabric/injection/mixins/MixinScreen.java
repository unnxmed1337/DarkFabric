package com.github.darkfabric.injection.mixins;

import com.github.darkfabric.DarkFabric;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Screen.class)
public abstract class MixinScreen {

    @Shadow
    public abstract Component getTitle();

    @Inject(method = "renderDirtBackground", at = @At(value = "HEAD"), cancellable = true)
    public void renderBackground(int i, CallbackInfo ci) {
        DarkFabric.getInstance().renderShader();
        ci.cancel();
    }

    @Inject(method = "<init>", at = @At(value = "TAIL"))
    protected void Screen(Component component, CallbackInfo ci) {
        updatePresence();
        Minecraft.getInstance().updateTitle();
    }

    @Inject(method = "init(Lnet/minecraft/client/Minecraft;II)V", at = @At(value = "HEAD"))
    public void init(Minecraft client, int width, int height, CallbackInfo ci) {
        updatePresence();
        Minecraft.getInstance().updateTitle();
    }

    @Inject(method = "init()V", at = @At(value = "HEAD"))
    public void init(CallbackInfo ci) {
        updatePresence();
        Minecraft.getInstance().updateTitle();
    }

    /**
     * TODO: fix it lol
     */
    @Unique
    public void updatePresence() {
    }

}