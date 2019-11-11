package uk.co.hexeption.darkforgereborn.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.MainMenuScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * MixinMainMenu
 *
 * @author Hexeption admin@hexeption.co.uk
 * @since 11/11/2019 - 02:11 pm
 */
@Mixin(MainMenuScreen.class)
public class MixinMainMenu {

    @Inject(method = "render", at = @At("RETURN"))
    private void render(CallbackInfo callbackInfo) {
        Minecraft.getInstance().fontRenderer.drawStringWithShadow("Darkforge Mixin Test", 20, 20, -1);
    }

}
