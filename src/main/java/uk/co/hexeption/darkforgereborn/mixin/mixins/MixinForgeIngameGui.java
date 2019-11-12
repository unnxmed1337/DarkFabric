package uk.co.hexeption.darkforgereborn.mixin.mixins;

import me.zero.alpine.event.EventState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.IngameGui;
import net.minecraftforge.client.ForgeIngameGui;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import uk.co.hexeption.darkforgereborn.DarkForgeReborn;
import uk.co.hexeption.darkforgereborn.event.events.screen.EventRender2D;

/**
 * MixinGuiIngameForge
 *
 * @author Hexeption admin@hexeption.co.uk
 * @since 12/11/2019 - 06:26 pm
 */
@Mixin(ForgeIngameGui.class)
public abstract class MixinForgeIngameGui extends IngameGui {

    public MixinForgeIngameGui(Minecraft mcIn) {
        super(mcIn);
    }

    @Inject(method = "renderGameOverlay", at = @At("HEAD"))
    private void renderGameOverlayPre(float partialTicks, CallbackInfo callbackInfo) {
        DarkForgeReborn.INSTANCE.eventBus.post(new EventRender2D(EventState.PRE, mc.mainWindow.getScaledWidth(), mc.mainWindow.getScaledHeight()));
    }

    @Inject(method = "renderGameOverlay", at = @At("RETURN"))
    private void renderGameOverlayPost(float partialTicks, CallbackInfo callbackInfo) {
        DarkForgeReborn.INSTANCE.eventBus.post(new EventRender2D(EventState.POST, mc.mainWindow.getScaledWidth(), mc.mainWindow.getScaledHeight()));
    }

}
