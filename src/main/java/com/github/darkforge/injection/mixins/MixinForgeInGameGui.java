package com.github.darkforge.injection.mixins;

import com.github.darkforge.DarkForgeReloaded;
import com.github.darkforge.event.impl.render.RenderInGameEvent;
import com.mojang.blaze3d.matrix.MatrixStack;
import me.zero.alpine.event.EventState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.IngameGui;
import net.minecraftforge.client.gui.ForgeIngameGui;
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
@Mixin(ForgeIngameGui.class)
public abstract class MixinForgeInGameGui extends IngameGui {

    public MixinForgeInGameGui(Minecraft mcIn) {
        super(mcIn);
    }

    @Inject(method = "renderIngameGui", at = @At("HEAD"))
    private void renderGameOverlayPre(MatrixStack matrices, float partialTicks, CallbackInfo callbackInfo) {
        DarkForgeReloaded.getInstance().getEventBus().post(new RenderInGameEvent(EventState.PRE,
                mc.getMainWindow().getScaledWidth(), mc.getMainWindow().getScaledHeight()));
    }

    @Inject(method = "renderIngameGui", at = @At("RETURN"))
    private void renderGameOverlayPost(MatrixStack matrices, float partialTicks, CallbackInfo callbackInfo) {
        DarkForgeReloaded.getInstance().getEventBus().post(new RenderInGameEvent(EventState.POST,
                mc.getMainWindow().getScaledWidth(), mc.getMainWindow().getScaledHeight()));
    }

}
