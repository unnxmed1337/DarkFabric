package com.github.darkfabric;

import com.github.darkfabric.addon.AbstractAddon;
import com.github.darkfabric.addon.AddonRegistry;
import com.github.darkfabric.command.CommandRegistry;
import com.github.darkfabric.config.ConfigRegistry;
import com.github.darkfabric.discord.PresenceRegistry;
import com.github.darkfabric.event.EventCaller;
import com.github.darkfabric.module.ModuleRegistry;
import com.github.darkfabric.util.LogHelper;
import com.mojang.blaze3d.platform.GlStateManager;
import lombok.Getter;
import me.zero.alpine.bus.EventBus;
import me.zero.alpine.bus.EventManager;
import net.minecraft.client.Minecraft;
import net.superblaubeere27.clientbase.utils.ShaderRenderer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

@Getter
public class DarkFabric {

    private static final Logger LOGGER = LogManager.getLogger();
    private static final DarkFabric INSTANCE = new DarkFabric();
    private final char commandPrefix = '-';

    private final PresenceRegistry presenceRegistry = new PresenceRegistry();
    private final ConfigRegistry configRegistry = new ConfigRegistry();
    private final AddonRegistry addonRegistry = new AddonRegistry();
    private final ModuleRegistry moduleRegistry = new ModuleRegistry();
    private final CommandRegistry commandRegistry = new CommandRegistry();

    private final EventBus eventBus = new EventManager();
    private ShaderRenderer shaderRenderer;
    private long initTime = System.currentTimeMillis();

    public static DarkFabric getInstance() {
        return INSTANCE;
    }

    public static Logger getLogger() {
        return LOGGER;
    }

    public void renderShader() {
        GL11.glPushMatrix();
        GlStateManager._enableAlphaTest();
        GlStateManager._disableCull();
        shaderRenderer.useShader(Minecraft.getInstance().getWindow().getWidth(),
                Minecraft.getInstance().getWindow().getHeight(), (float) Minecraft.getInstance().mouseHandler.xpos(),
                (float) Minecraft.getInstance().mouseHandler.ypos(),
                (System.currentTimeMillis() - initTime) / 1000.f);

        GL11.glBegin(GL11.GL_QUADS);

        GL11.glVertex2f(-1f, -1f);
        GL11.glVertex2f(-1f, 1f);
        GL11.glVertex2f(1f, 1f);
        GL11.glVertex2f(1f, -1f);

        GL11.glEnd();
        GL20.glUseProgram(0);
        GL11.glPopMatrix();

        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
    }

    public void preInitialize() {
        LogHelper.introduce();
        LogHelper.section(String.format("Pre-Initializing %s", getName()));
        LogHelper.section("loading configs");
        configRegistry.loadAll();

        LogHelper.section("loading d-rpc's");
        presenceRegistry.initialize();
    }

    public void initialize() {
        addonRegistry.getObjects().forEach(AbstractAddon::preInit);
        LogHelper.section(String.format("Initializing %s", getName()));
        LogHelper.section("loading shaders");
        try {
            shaderRenderer = new ShaderRenderer("default");
        } catch (Exception e) {
            throw new IllegalStateException("Failed to load background shader", e);
        }
        initTime = System.currentTimeMillis();

        LogHelper.section("loading modules");
        moduleRegistry.initialize();

        LogHelper.section("loading commands");
        commandRegistry.initialize();

        LogHelper.section("loading addons");
        addonRegistry.initialize();
        addonRegistry.getObjects().forEach(AbstractAddon::init);

        LogHelper.section(String.format("Done with %s", getName()));
        eventBus.subscribe(new EventCaller());
        addonRegistry.getObjects().forEach(AbstractAddon::postInit);
    }

    public void terminate() {
        LogHelper.section(String.format("Terminating %s", getName()));
        LogHelper.section("saving all configs");
        configRegistry.saveAll();
        LogHelper.section("terminating discord-rpc");
        presenceRegistry.terminate();
        LogHelper.section(String.format("Done with %s", getName()));
    }

    public String getName() {
        return "DarkFabric";
    }

}