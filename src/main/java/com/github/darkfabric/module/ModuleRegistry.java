package com.github.darkfabric.module;

import com.github.darkfabric.base.named.NamedRegistry;
import com.github.darkfabric.util.LogHelper;
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
                AbstractModule abstractModule = (AbstractModule) abstractModuleClass.newInstance();
                getObjects().add(abstractModule);
                LogHelper.info(String.format(" -> added '%s'.", abstractModule.getName()));
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
        });
    }

    public List<AbstractModule> getModulesInType(AbstractModule.Type category) {
        return getObjects().stream().filter(mod -> mod.getCategory() == category).collect(Collectors.toList());
    }

}
