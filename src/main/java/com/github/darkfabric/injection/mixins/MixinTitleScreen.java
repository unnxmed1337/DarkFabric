package com.github.darkfabric.injection.mixins;

import com.github.darkfabric.gui.screen.ScreenMainMenu;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.network.chat.TextComponent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TitleScreen.class)
public abstract class MixinTitleScreen extends Screen {

    protected MixinTitleScreen() {
        super(new TextComponent("This is a Mixin lol."));
    }

    @Inject(method = "init", at = @At(value = "HEAD"))
    public void init(CallbackInfo ci) {
        assert this.minecraft != null;
        this.addButton(new Button(5, 15, 98, 20,
                new TextComponent("Test"),
                (button) -> this.minecraft.setScreen(new ScreenMainMenu())));
    }

    @Inject(method = "render", at = @At(value = "HEAD"))
    public void render(PoseStack poseStack, int i, int j, float f, CallbackInfo ci) {
        minecraft.setScreen(new ScreenMainMenu());
    }

}