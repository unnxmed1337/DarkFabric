package com.github.darkfabric.injection.mixins;

import com.github.darkfabric.DarkFabric;
import com.github.darkfabric.injection.interfaces.IMixinMinecraft;
import net.minecraft.client.Minecraft;
import net.minecraft.client.User;
import net.minecraft.client.gui.screens.Screen;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
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
public abstract class MixinMinecraft implements IMixinMinecraft {

    @Shadow
    @Nullable
    public Screen screen;

    @Shadow
    @Final
    private User user;

    @Inject(method = "<init>", at = @At(value = "RETURN"))
    private void startGame(CallbackInfo callbackInfo) {
        DarkFabric.getInstance().initialize();
    }

    @Inject(method = "close", at = @At(value = "HEAD"))
    private void close(CallbackInfo callbackInfo) {
        DarkFabric.getInstance().terminate();
    }

    @Override
    public void setUser(User user) {
        this.user = user;
    }

}