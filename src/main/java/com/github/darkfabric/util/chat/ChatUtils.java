package com.github.darkfabric.util.chat;

import com.github.darkfabric.DarkFabric;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextComponent;

public class ChatUtils {

    private static final Minecraft MC = Minecraft.getInstance();

    private static final String PREFIX = ChatColors.GRAY + "\u2981\u25cf "
            + ChatColors.BLUE + DarkFabric.getInstance().getName() + " " + ChatColors.GRAY + "\u00bb ";

    public static void sendMessageToPlayer(String message, boolean prefix, Style chatStyle) {
        TextComponent textComponent = new TextComponent(prefix ? PREFIX + message : message);
        textComponent.setStyle(chatStyle);
        assert MC.player != null;
        MC.player.displayClientMessage(textComponent, false);
    }

    public static void sendMessageToPlayerSimple(String message, boolean prefix) {
        assert MC.player != null;
        MC.player.displayClientMessage(Component.nullToEmpty(prefix ? PREFIX + message : message),
                false);
    }

    public static void sendMessageToPlayerActionbar(String message, boolean prefix) {
        assert MC.player != null;
        MC.player.displayClientMessage(Component.nullToEmpty(prefix ? PREFIX + message : message),
                true);
    }

    public void sendMessageAsPlayer(String message) {
        assert MC.player != null;
        MC.player.sendMessage(Component.nullToEmpty(message), MC.player.getUUID());
    }

}
