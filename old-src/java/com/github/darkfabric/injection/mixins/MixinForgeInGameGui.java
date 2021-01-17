package com.github.darkfabric.injection.mixins;

import com.github.darkfabric.DarkFabric;
import com.github.darkfabric.event.impl.render.RenderInGameEvent;
import me.zero.alpine.event.EventState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.util.math.MatrixStack;
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
@Mixin(InGameHud.class)
public abstract class MixinForgeInGameGui {

    private final MinecraftClient mc = MinecraftClient.getInstance();

    @Inject(method = "render", at = @At("HEAD"))
    private void renderGameOverlayPre(MatrixStack matrices, float partialTicks, CallbackInfo callbackInfo) {
        DarkFabric.getInstance().getEventBus().post(new RenderInGameEvent(EventState.PRE,
                mc.getWindow().getScaledWidth(), mc.getWindow().getScaledHeight()));
    }

    @Inject(method = "render", at = @At("RETURN"))
    private void renderGameOverlayPost(MatrixStack matrices, float partialTicks, CallbackInfo callbackInfo) {
        DarkFabric.getInstance().getEventBus().post(new RenderInGameEvent(EventState.POST,
                mc.getWindow().getScaledWidth(), mc.getWindow().getScaledHeight()));
    }

}
