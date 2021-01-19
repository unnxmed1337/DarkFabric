package com.github.darkfabric.config;

import com.github.darkfabric.base.Registry;
import com.github.darkfabric.base.named.NamedRegistry;
import com.github.darkfabric.command.AbstractCommand;
import com.github.darkfabric.util.LogHelper;
import org.reflections.Reflections;

import java.util.Arrays;
import java.util.concurrent.CompletableFuture;

public class ConfigRegistry extends NamedRegistry<AbstractConfig> {

    public void loadAll() {
        registerAllConfigs();
    }

    public void reloadAll() {
        CompletableFuture.runAsync(() -> {
            saveAll();
            try {
                this.clearList();
                Thread.sleep(750);
                registerAllConfigs();
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        });
    }

    public void saveAll() {
        for (AbstractConfig abstractConfig : getObjects()) {
            LogHelper.info(String.format(" -> saving '%s'...", abstractConfig.getName()));
            abstractConfig.save();
        }
    }

    public void registerAllConfigs() {
        Reflections reflections = new Reflections(AbstractConfig.class.getPackage().getName());
        reflections.getTypesAnnotatedWith(AbstractConfig.Info.class).forEach(abstractConfigClass -> {
            try {
                AbstractConfig abstractConfig = (AbstractConfig) abstractConfigClass.newInstance();
                System.out.println(abstractConfig.getName());
                abstractConfig.initialize();
                getObjects().add(abstractConfig);
                LogHelper.info(String.format(" -> loaded '%s'.", abstractConfig.getName()));
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public void register(AbstractConfig... abstractCommand) {
        Arrays.stream(abstractCommand).forEach(command -> LogHelper.info(String.format(" -> registered '%s'",
                command.getName())));
        super.register(abstractCommand);
    }

    @Override
    public void unregister(AbstractConfig... abstractCommand) {
        Arrays.stream(abstractCommand).forEach(command -> LogHelper.info(String.format(" -> unregistered '%s'",
                command.getName())));
        super.register(abstractCommand);
    }

}