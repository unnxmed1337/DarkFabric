package com.github.darkfabric.command;

import com.github.darkfabric.base.named.NamedRegistry;
import com.github.darkfabric.util.LogHelper;
import org.reflections.Reflections;

import java.util.Arrays;
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
        reflections.getTypesAnnotatedWith(AbstractCommand.Info.class).forEach(abstractCommandClass -> {
            try {
                AbstractCommand abstractCommand = (AbstractCommand) abstractCommandClass.newInstance();
                register(abstractCommand);
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public void register(AbstractCommand... abstractCommand) {
        Arrays.stream(abstractCommand).forEach(command -> LogHelper.info(String.format(" -> registered '%s'",
                command.getAliases())));
        super.register(abstractCommand);
    }

    @Override
    public void unregister(AbstractCommand... abstractCommand) {
        Arrays.stream(abstractCommand).forEach(command -> LogHelper.info(String.format(" -> unregistered '%s'",
                command.getAliases())));
        super.register(abstractCommand);
    }

}