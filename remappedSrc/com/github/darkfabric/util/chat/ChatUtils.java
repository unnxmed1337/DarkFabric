package com.github.darkfabric.util.chat;

import com.github.darkfabric.DarkFabric;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Style;
import net.minecraft.text.Text;

public class ChatUtils {

    private static final MinecraftClient MC = MinecraftClient.getInstance();

    private static final String PREFIX = ChatColors.GRAY + "\u2981\u25cf "
            + ChatColors.BLUE + DarkFabric.getInstance().getName() + " " + ChatColors.GRAY + "\u00bb ";

    public static void sendMessageToPlayer(String message, boolean prefix, Style chatStyle) {
        LiteralText textComponent = new LiteralText(prefix ? PREFIX + message : message);
        textComponent.setStyle(chatStyle);
        assert MC.player != null;
        MC.player.sendMessage(textComponent, false);
    }

    public static void sendMessageToPlayerSimple(String message, boolean prefix) {
        assert MC.player != null;
        MC.player.sendMessage(Text.of(prefix ? PREFIX + message : message), false);
    }

    public static void sendMessageToPlayerActionbar(String message, boolean prefix) {
        assert MC.player != null;
        MC.player.sendMessage(Text.of(prefix ? PREFIX + message : message), true);
    }

    public void sendMessageAsPlayer(String message) {
        assert MC.player != null;
        MC.player.sendChatMessage(message);
    }

}
