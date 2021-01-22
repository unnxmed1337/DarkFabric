package com.github.darkfabric.module;

import com.github.darkfabric.base.named.NamedRegistry;
import com.github.darkfabric.command.AbstractCommand;
import com.github.darkfabric.util.LogHelper;
import org.reflections.Reflections;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ModuleRegistry extends NamedRegistry<AbstractModule> {

    public void initialize() {
        registerAllCommands();
    }

    public void registerAllCommands() {
        Reflections reflections = new Reflections(AbstractModule.class.getPackage().getName());
        reflections.getTypesAnnotatedWith(AbstractModule.Info.class).forEach(abstractModuleClass -> {
            try {
                AbstractModule abstractModule = (AbstractModule) abstractModuleClass.newInstance();
                register(abstractModule);
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
        });
    }

    public List<AbstractModule> getModulesInType(AbstractModule.Type category) {
        return getObjects().stream().filter(mod -> mod.getCategory() == category).collect(Collectors.toList());
    }

    @Override
    public void register(AbstractModule... abstractModules) {
        Arrays.stream(abstractModules).forEach(command -> LogHelper.info(String.format(" -> registered '%s'",
                command.getName())));
        super.register(abstractModules);
    }

    @Override
    public void unregister(AbstractModule... abstractModules) {
        Arrays.stream(abstractModules).forEach(command -> LogHelper.info(String.format(" -> unregistered '%s'",
                command.getName())));
        super.register(abstractModules);
    }

}
