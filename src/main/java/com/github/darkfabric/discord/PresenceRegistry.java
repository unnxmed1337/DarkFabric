package com.github.darkfabric.discord;

import com.github.darkfabric.DarkFabric;
import com.github.darkfabric.base.named.NamedRegistry;
import com.github.darkfabric.config.impl.Default;
import com.github.darkfabric.util.LogHelper;
import lombok.Getter;
import lombok.Setter;
import net.arikia.dev.drpc.DiscordEventHandlers;
import net.arikia.dev.drpc.DiscordRPC;
import org.reflections.Reflections;
import org.simpleyaml.configuration.file.YamlFile;

import java.util.Arrays;

@Setter
@Getter
public class PresenceRegistry extends NamedRegistry<Presence> {

    private boolean running = true;
    private long created = 0;

    public void terminate() {
        setRunning(false);
        DiscordRPC.discordShutdown();
    }

    public void restartRpc() {
        new Thread(() -> {
            try {
                DarkFabric.getInstance().getPresenceRegistry().terminate();
                Thread.sleep(1000);
                DarkFabric.getInstance().getPresenceRegistry().initialize();
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }).start();
    }

    public void initialize() {
        setRunning(true);
        clearList();
        YamlFile yamlFile = com.github.darkfabric.DarkFabric.getInstance()
                .getConfigRegistry().getByClass(Default.class)
                .getYamlFile();
        registerAllPresences();
        if (yamlFile.get("discordrpc.current") == null) yamlFile.set("discordrpc.current",
                getByClass(com.github.darkfabric.discord.impl.DarkFabric.class).getName());
        Presence selectedPresence = getByName(yamlFile.getString("discordrpc.current"), true);
        System.out.println("id: " + selectedPresence.getAppId());
        System.out.println("name: " + selectedPresence.getName());
        System.out.println("bImage-name: " + selectedPresence.getBigImageName());
        System.out.println("bImage-text: " + selectedPresence.getBigImageText());
        System.out.println("bSmall-name: " + selectedPresence.getSmallImageName());
        System.out.println("bSmall-text: " + selectedPresence.getSmallImageText());
        System.out.println("details-1: " + selectedPresence.getDetailsOne());
        System.out.println("details-2: " + selectedPresence.getDetailsTwo());
        try {
            created = System.currentTimeMillis();
            DiscordEventHandlers discordEventHandlers = new DiscordEventHandlers.Builder().setReadyEventHandler(
                    user -> update(selectedPresence)).build();
            DiscordRPC.discordInitialize(selectedPresence.getAppId(),
                    discordEventHandlers, true);
            new Thread("Discord Rich Presence Callback") {
                @Override
                public void run() {
                    while (isRunning())
                        DiscordRPC.discordRunCallbacks();
                }
            }.start();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    private void registerAllPresences() {
        Reflections reflections = new Reflections(Presence.class.getPackage().getName());
        reflections.getTypesAnnotatedWith(Presence.Info.class).forEach(abstractModuleClass -> {
            try {
                Presence abstractPresence = (Presence) abstractModuleClass.newInstance();
                register(abstractPresence);
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
        });
    }

    private void update(Presence abstractPresence) {
        net.arikia.dev.drpc.DiscordRichPresence.Builder discordRichPresenceBuilder
                = new net.arikia.dev.drpc.DiscordRichPresence.Builder(abstractPresence.getDetailsTwo());
        discordRichPresenceBuilder.setSmallImage(abstractPresence.getSmallImageName(),
                abstractPresence.getSmallImageText());
        discordRichPresenceBuilder.setBigImage(abstractPresence.getBigImageName(),
                abstractPresence.getBigImageText());
        discordRichPresenceBuilder.setDetails(abstractPresence.getDetailsOne());
        discordRichPresenceBuilder.setStartTimestamps(created);
        DiscordRPC.discordUpdatePresence(discordRichPresenceBuilder.build());
    }

    @Override
    public void register(Presence... abstractPresence) {
        Arrays.stream(abstractPresence).forEach(command -> LogHelper.info(String.format(" -> registered '%s'",
                command.getName())));
        super.register(abstractPresence);
    }

    @Override
    public void unregister(Presence... abstractPresence) {
        Arrays.stream(abstractPresence).forEach(command -> LogHelper.info(String.format(" -> unregistered '%s'",
                command.getName())));
        super.register(abstractPresence);
    }

}