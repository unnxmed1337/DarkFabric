package com.github.darkfabric.command;

import com.github.darkfabric.base.interfaces.INameable;
import com.github.darkfabric.util.LogHelper;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.client.Minecraft;
import org.reflections.Reflections;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Arrays;
import java.util.List;

/**
 * Command
 *
 * @author Hexeption admin@hexeption.co.uk
 * @since 13/11/2019 - 05:00 pm
 */
@Setter
@Getter
public abstract class AbstractCommand implements INameable {
    public final Minecraft mc = Minecraft.getInstance();
    private List<String> aliases = Arrays.asList(getClass().getAnnotation(Info.class).aliases().clone());

    private String help = getClass().getAnnotation(Info.class).help(),
            description = getClass().getAnnotation(Info.class).description();

    public abstract boolean execute(String[] args) throws Exception;

    @Override
    public String getName() {
        return aliases.get(0);
    }

    @Retention(RetentionPolicy.RUNTIME)
    public @interface Info {
        String[] aliases();

        String help();

        String description();
    }
}