package com.github.darkfabric.module;

import com.github.darkfabric.DarkFabric;
import com.github.darkfabric.base.interfaces.INameable;
import com.github.darkfabric.module.options.Option;
import com.github.darkfabric.module.options.ValueChoice;
import com.github.darkfabric.util.LogHelper;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import joptsimple.internal.Strings;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import me.zero.alpine.listener.Listenable;
import net.minecraft.client.Minecraft;

import java.awt.*;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Mod
 *
 * @author Hexeption admin@hexeption.co.uk
 * @since 12/11/2019 - 11:47 am
 */
@Setter
@Getter
public abstract class AbstractModule implements INameable, Listenable {
    public final Minecraft mc = Minecraft.getInstance();

    public Map<String, Option> options = Collections.synchronizedMap(Maps.newLinkedHashMap());
    private String name = getClass().getAnnotation(Info.class).name();
    private String description = getClass().getAnnotation(Info.class).description();
    private Type category = getClass().getAnnotation(Info.class).type();
    private boolean enabled;

    public AbstractModule() {
        initCommands(name.toLowerCase().replaceAll(" ", ""));
    }

    protected void initCommands(String base) {
        final List<String> usages = Collections.synchronizedList(Lists.newLinkedList());
        usages.add(base);
        for (String key : options.keySet()) {
            Option option = Option.get(options, key);
            for (String key2 : option.options.keySet()) {
                Option option2 = Option.get(options, key, key2);
                usages.add(String.format("%s %s %s %s",
                        name.replaceAll(" ", ""),
                        option.name.replaceAll(" ", ""),
                        option2.name.replaceAll(" ", ""),
                        option2.type == Option.Type.CHOICE ? String.format(option2.type.usage,
                                Strings.join(((ValueChoice) option2.value).list, "|"))
                                : option2.type.usage).toLowerCase());
            }
            usages.add(String.format("%s %s %s", name.replaceAll(" ",
                    ""), option.name.replaceAll(" ", ""),
                    option.type == Option.Type.CHOICE ? String.format(option.type.usage,
                            Strings.join(((ValueChoice) option.value).list, "|"))
                            : option.type.usage).toLowerCase());
        }
    }

    public void toggle(boolean state) {
        if (state) {
            LogHelper.info(String.format("Enabling %s", name));
            this.enabled = true;
            try {
                onEnable();
                DarkFabric.getInstance().getEventBus().subscribe(this);
            } catch (Exception ignored) {
            }
        } else {
            LogHelper.info(String.format("Disabling %s", name));
            this.enabled = false;
            try {
                onDisable();
                DarkFabric.getInstance().getEventBus().unsubscribe(this);
            } catch (Exception ignored) {
            }
        }
    }

    public void toggle() {
        toggle(!this.enabled);
    }

    public abstract void onDisable();

    public abstract void onEnable();

    /**
     * Category
     *
     * @author Hexeption admin@hexeption.co.uk
     * @since 09/11/2019 - 03:30 am
     */
    @Getter
    @AllArgsConstructor
    public enum Type {
        COMBAT("Combat", "Combat related mods.", Color.white),
        EXPLOIT("Exploit", "Modules that interact with the server, the client or both.", Color.white),
        MOVEMENT("Movement", "Modules that interact with the movement of the player.", Color.white),
        VISUAL("Visual", "Modules for rendering.", Color.white),
        WORLD("World", "Any mod that has to do with the world", Color.white),
        NONE("", "", Color.white);
        private final String name;
        private final String description;
        private final Color color;
    }

    @Retention(RetentionPolicy.RUNTIME)
    public @interface Info {
        String name();

        String description();

        Type type();
    }

}