package com.github.darkforge.event;

import com.github.darkforge.DarkForgeReloaded;
import com.github.darkforge.command.AbstractCommand;
import com.github.darkforge.event.impl.player.PlayerChatsEvent;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listenable;
import me.zero.alpine.listener.Listener;

import java.util.Arrays;

/**
 * EventCaller
 *
 * @author Hexeption admin@hexeption.co.uk
 * @since 12/11/2019 - 11:37 am
 */
public class EventCaller implements Listenable {

    @EventHandler
    private final Listener<PlayerChatsEvent> playerChatsEventListener = new Listener<>(event -> {
        String message = event.getMessage();
        if (!message.startsWith(String.valueOf(DarkForgeReloaded.getInstance().getCommandPrefix())))
            return;
        message = message.replaceAll("^ +| +$|( )+", "$1");
        String[] arguments = message.split(" ");
        for (AbstractCommand abstractCommand : DarkForgeReloaded.getInstance().getCommandRegistry().getObjects()) {
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