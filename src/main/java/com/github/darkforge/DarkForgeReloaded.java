package com.github.darkforge;

import com.github.darkforge.command.CommandRegistry;
import com.github.darkforge.event.EventCaller;
import com.github.darkforge.module.ModuleRegistry;
import com.github.darkforge.util.LogHelper;
import lombok.Getter;
import me.zero.alpine.bus.EventBus;
import me.zero.alpine.bus.EventManager;
import net.minecraftforge.fml.common.Mod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Getter
@Mod("darkforgereborn")
public class DarkForgeReloaded {

    private static final Logger LOGGER = LogManager.getLogger();
    private static final DarkForgeReloaded INSTANCE = new DarkForgeReloaded();

    private final ModuleRegistry moduleRegistry = new ModuleRegistry();
    private final CommandRegistry commandRegistry = new CommandRegistry();
    private final char commandPrefix = '-';

    private final EventBus eventBus = new EventManager();

    public static DarkForgeReloaded getInstance() {
        return INSTANCE;
    }

    public static Logger getLogger() {
        return LOGGER;
    }

    public void initialize() {
        LogHelper.section(String.format("Starting %s", getName()));
        LogHelper.section("Loading Mods");
        moduleRegistry.initialize();
        LogHelper.section("Loading Commands");
        commandRegistry.initialize();
        LogHelper.section("Loading Configs");
        LogHelper.endSection();
        eventBus.subscribe(new EventCaller());
        Runtime.getRuntime().addShutdownHook(new Thread(this::terminate));
    }

    private void terminate() {
        LogHelper.section(String.format("Stopping %s", getName()));

        LogHelper.section("Saving Mods");
        LogHelper.section("Saving Alts");
        LogHelper.section("Saving Friends");

        LogHelper.endSection();
    }

    public String getName() {
        return "DarkForge-reloaded";
    }

}
