package uk.co.hexeption.darkforgereborn.managers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listenable;
import me.zero.alpine.listener.Listener;
import org.reflections.Reflections;
import uk.co.hexeption.darkforgereborn.DarkForgeReborn;
import uk.co.hexeption.darkforgereborn.command.Command;
import uk.co.hexeption.darkforgereborn.command.Command.CMDInfo;
import uk.co.hexeption.darkforgereborn.event.events.chat.EventSendChatMessage;
import uk.co.hexeption.darkforgereborn.util.LogHelper;

/**
 * CommandManager
 *
 * @author Hexeption admin@hexeption.co.uk
 * @since 13/11/2019 - 05:06 pm
 */
public class CommandManager implements Listenable {

    private final Pattern patten = Pattern.compile("([^\"']\\s*|\".+?\"|'.+?')\\s*");

    private final List<Command> commands = new ArrayList<>();

    public void init() {
        initCommands();
        LogHelper.info(String.format("%s commands loaded!", commands.size()));
        commands.forEach(mod -> LogHelper.info(String.format("%s (%s)[%s] loaded", Arrays.toString(mod.getName()), mod.getDescription(), mod.getHelp())));
    }

    private void initCommands() {
        Reflections reflections = new Reflections(Command.class.getPackage().getName());

        reflections.getTypesAnnotatedWith(CMDInfo.class).forEach(aClass -> {
            try {
                Command command = (Command) aClass.newInstance();
                commands.add(command);
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
        });
    }

    public List<Command> getCommands() {
        return commands;
    }

    public boolean executeCommand(String message) {
        String name = message.contains(" ") ? message.split(" ")[0] : message;
        commands.forEach(command -> {
            for (String alias : command.getName()) {
                if (alias.equalsIgnoreCase(name)) {
                    if (message.contains(" ")) {
                        if (message.split(" ")[1].equalsIgnoreCase("aliases")) {
                            listAllNames(command);
                        }
                    }
                    tryCommand(command, message);
                }
            }
        });
        return false;
    }

    private void tryCommand(Command command, String message) {
        try {
            String input = message.substring(1);
            String[] args;

            if (input.contains(" ")) {
                args = input.substring(input.indexOf(" ") + 1).split(" ");
            } else {
                args = new String[0];
            }

            command.execute(input, args);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void listAllNames(Command command) {
        String namesList = "Avaliable names: ";
        String[] name = command.getName();
        for (int i = 0; i < name.length; i++) {
            String names = name[i];
            namesList += names + (i != name.length ? "," : "");
            LogHelper.info(namesList);
        }
    }

    @EventHandler
    private Listener<EventSendChatMessage> eventSendChatMessageListener = new Listener<>(event -> {
        if (event.getMessage().startsWith(DarkForgeReborn.INSTANCE.commandPrefix)) {
            DarkForgeReborn.INSTANCE.commandManager.executeCommand(event.getMessage().substring(DarkForgeReborn.INSTANCE.commandPrefix.length()));
            event.cancel();
        }
    });
}
