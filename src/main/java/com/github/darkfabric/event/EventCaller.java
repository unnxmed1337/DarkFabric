package com.github.darkfabric.event;

import com.github.darkfabric.DarkFabric;
import com.github.darkfabric.command.AbstractCommand;
import com.github.darkfabric.event.impl.player.PlayerChatsEvent;
import com.github.darkfabric.event.impl.player.PlayerKeyPressEvent;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listenable;
import me.zero.alpine.listener.Listener;
import net.minecraft.client.Minecraft;
import org.lwjgl.glfw.GLFW;

import java.util.Arrays;

/**
 * EventCaller
 *
 * @author Hexeption admin@hexeption.co.uk
 * @since 12/11/2019 - 11:37 am
 */
public class EventCaller implements Listenable {

    @EventHandler
    private final Listener<PlayerKeyPressEvent> playerKeyPressEventListener = new Listener<>(event -> {
        if (event.getKey() == GLFW.GLFW_KEY_L) System.out.println(Minecraft.getInstance().screen);
    });

    @EventHandler
    private final Listener<PlayerChatsEvent> playerChatsEventListener = new Listener<>(event -> {
        String message = event.getMessage();
        if (!message.startsWith(String.valueOf(DarkFabric.getInstance().getCommandPrefix())))
            return;
        System.out.println("ok");
        message = message.replaceAll("^ +| +$|( )+", "$1");
        String[] arguments = message.split(" ");
        for (AbstractCommand abstractCommand : DarkFabric.getInstance().getCommandRegistry().getObjects()) {
            if (abstractCommand.getAliases().contains(arguments[0].substring(1))) {
                try {
                    if (abstractCommand.execute(Arrays.copyOfRange(arguments, 1, arguments.length))) {
                        event.cancel();
                        return;
                    }
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        }
    });

}