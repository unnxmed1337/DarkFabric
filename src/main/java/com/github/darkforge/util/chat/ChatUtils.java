package com.github.darkforge.util.chat;

import com.github.darkforge.DarkForgeReloaded;
import net.minecraft.client.Minecraft;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.Style;

import java.util.Optional;

public class ChatUtils {

    private static final String PREFIX = ChatColors.GRAY + "\u2981\u25cf "
            + ChatColors.BLUE + DarkForgeReloaded.getInstance().getName() + " " + ChatColors.GRAY + "\u00bb ";

    public static void sendMessageToPlayer(String message, boolean prefix, Style chatStyle) {
        ITextComponent textComponent = new StringTextComponent(prefix ? PREFIX + message : message);
        textComponent.func_230534_b_((p_accept_1_, p_accept_2_) -> Optional.empty(), chatStyle);
        assert Minecraft.getInstance().player != null;
        Minecraft.getInstance().player.sendMessage(textComponent,
                Minecraft.getInstance().getSession().getProfile().getId());
    }

    public static void sendMessageToPlayerSimple(String message, boolean prefix) {
        assert Minecraft.getInstance().player != null;
        Minecraft.getInstance().player.sendChatMessage(prefix ? PREFIX + message : message);
    }

    public void sendMessageAsPlayer(String message) {
        assert Minecraft.getInstance().player != null;
        Minecraft.getInstance().player.sendChatMessage(message);
    }

}
