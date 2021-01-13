package com.github.darkforge.command;

import com.github.darkforge.base.named.NamedRegistry;
import com.github.darkforge.util.LogHelper;
import org.reflections.Reflections;

import java.util.stream.Collectors;

public class CommandRegistry extends NamedRegistry<AbstractCommand> {

    public void initialize() {
        registerAllCommands();
    }

    public AbstractCommand getCommandByAlias(String alias) {
        return getObjects().stream().filter(command ->
                command.getAliases().stream().map(String::toLowerCase).collect(Collectors.toList())
                        .contains(alias.toLowerCase())).findFirst().orElse(null);
    }

    public void registerAllCommands() {
        Reflections reflections = new Reflections(AbstractCommand.class.getPackage().getName());
        reflections.getTypesAnnotatedWith(AbstractCommand.Info.class).forEach(aClass -> {
            try {
                AbstractCommand command = (AbstractCommand) aClass.newInstance();
                getObjects().add(command);
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public void register(AbstractCommand abstractCommand) {
        System.out.println(" -> registered '" + abstractCommand.getAliases() + "'");
        super.register(abstractCommand);
    }

    @Override
    public void unregister(AbstractCommand abstractCommand) {
        System.out.println(" -> unregistered '" + abstractCommand.getAliases() + "'");
        super.register(abstractCommand);
    }

}