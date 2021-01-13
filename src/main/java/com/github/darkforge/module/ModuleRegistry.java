package com.github.darkforge.module;

import com.github.darkforge.base.named.NamedRegistry;
import org.reflections.Reflections;

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
                getObjects().add((AbstractModule) abstractModuleClass.newInstance());
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
        });
    }

    public List<AbstractModule> getModulesInType(AbstractModule.Type category) {
        return getObjects().stream().filter(mod -> mod.getCategory() == category).collect(Collectors.toList());
    }

}
