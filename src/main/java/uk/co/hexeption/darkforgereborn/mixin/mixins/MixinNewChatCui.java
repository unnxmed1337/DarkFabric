package uk.co.hexeption.darkforgereborn.mixin.mixins;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.NewChatGui;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import uk.co.hexeption.darkforgereborn.DarkForgeReborn;
import uk.co.hexeption.darkforgereborn.mod.mods.misc.CustomChat;

/**
 * MixinNewChatCui
 *
 * @author Hexeption admin@hexeption.co.uk
 * @since 13/11/2019 - 06:23 pm
 */
@Mixin(NewChatGui.class)
public abstract class MixinNewChatCui {

    @Redirect(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/FontRenderer;drawStringWithShadow(Ljava/lang/String;FFI)I"))
    public int drawStringWithShadow(FontRenderer fontRenderer, String text, float x, float y, int color) {

        if (DarkForgeReborn.INSTANCE.modManager.getModByClass(CustomChat.class).getState()) {
            DarkForgeReborn.INSTANCE.fontManager.chat.drawStringWithShadow(text, (int) x, (int) y - 3, color);
        } else {
            fontRenderer.drawStringWithShadow(text, x, y, color);
        }
        return -1;
    }

}
