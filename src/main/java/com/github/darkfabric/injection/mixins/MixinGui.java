package com.github.darkfabric.injection.mixins;

import com.github.darkfabric.DarkFabric;
import com.github.darkfabric.event.impl.render.RenderInGameEvent;
import com.mojang.blaze3d.vertex.PoseStack;
import me.zero.alpine.event.EventState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.screens.LanguageSelectScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * MixinGuiIngameForge
 *
 * @author Hexeption admin@hexeption.co.uk
 * @since 12/11/2019 - 06:26 pm
 */
@Mixin(Gui.class)
public abstract class MixinGui {

    private final Minecraft mc = Minecraft.getInstance();

    @Inject(method = "render", at = @At("HEAD"))
    private void renderGameOverlayPre(PoseStack matrices, float partialTicks, CallbackInfo callbackInfo) {
        DarkFabric.getInstance().getEventBus().post(new RenderInGameEvent(EventState.PRE,
                mc.getWindow().getGuiScaledWidth(), mc.getWindow().getGuiScaledHeight()));
    }

    @Inject(method = "render", at = @At("RETURN"))
    private void renderGameOverlayPost(PoseStack matrices, float partialTicks, CallbackInfo callbackInfo) {
        DarkFabric.getInstance().getEventBus().post(new RenderInGameEvent(EventState.POST,
                mc.getWindow().getGuiScaledWidth(), mc.getWindow().getGuiScaledHeight()));
    }

}
