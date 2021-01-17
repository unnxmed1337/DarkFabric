package com.github.darkfabric;

import com.github.darkfabric.command.CommandRegistry;
import com.github.darkfabric.event.EventCaller;
import com.github.darkfabric.module.ModuleRegistry;
import com.github.darkfabric.util.LogHelper;
import lombok.Getter;
import me.zero.alpine.bus.EventBus;
import me.zero.alpine.bus.EventManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Getter
public class DarkFabric {

    private static final Logger LOGGER = LogManager.getLogger();
    private static final DarkFabric INSTANCE = new DarkFabric();

    private final ModuleRegistry moduleRegistry = new ModuleRegistry();
    private final CommandRegistry commandRegistry = new CommandRegistry();
    private final char commandPrefix = '-';

    private final EventBus eventBus = new EventManager();

    public static DarkFabric getInstance() {
        return INSTANCE;
    }

    public static Logger getLogger() {
        return LOGGER;
    }

    public void initialize() {
        Runtime.getRuntime().addShutdownHook(new Thread(this::terminate));
        LogHelper.section(String.format("Starting %s", getName()));
        LogHelper.section("Loading Mods");
        moduleRegistry.initialize();
        LogHelper.section("Loading Commands");
        commandRegistry.initialize();
        LogHelper.section("Loading Configs");
        LogHelper.endSection();
        eventBus.subscribe(new EventCaller());
    }

    private void terminate() {
        LogHelper.section(String.format("Stopping %s", getName()));

        LogHelper.section("Saving Mods");
        LogHelper.section("Saving Alts");
        LogHelper.section("Saving Friends");

        LogHelper.endSection();
    }

    public String getName() {
        return "DarkFabric";
    }

}