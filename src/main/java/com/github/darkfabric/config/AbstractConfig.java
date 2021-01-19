package com.github.darkfabric.config;

import com.github.darkfabric.DarkFabric;
import com.github.darkfabric.addon.AbstractAddon;
import com.github.darkfabric.base.interfaces.INameable;
import com.github.darkfabric.command.AbstractCommand;
import com.github.darkfabric.util.LogHelper;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import net.minecraft.client.Minecraft;
import org.reflections.Reflections;
import org.simpleyaml.configuration.file.YamlFile;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.List;

@Setter
@Getter
public abstract class AbstractConfig implements INameable {

    private File folder, config;
    private String name = getClass().getAnnotation(AbstractConfig.Info.class).name();
    private YamlFile yamlFile;

    public void initialize() {
        folder = new File(Minecraft.getInstance().gameDirectory.getAbsolutePath().replaceAll("\\.", "") + "/"
                + DarkFabric.getInstance().getName().toLowerCase(), "configs");
        config = new File(folder.getAbsolutePath() + "/" + getName() + ".yml");
        yamlFile = new YamlFile(config);
        load();
    }

    public void save() {
        try {
            onSavePre();
            yamlFile.save();
            onSave();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    private void load() {
        onLoadPre();
        try {
            if (!folder.exists())
                folder.mkdirs();
            if (!config.exists())
                config.createNewFile();
            System.out.println(config.getAbsolutePath());
            yamlFile.load(config);
            onLoad();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public abstract void onLoadPre();

    public abstract void onLoad();

    public abstract void onLoadPost();

    public abstract void onSavePre();

    public abstract void onSave();

    @Retention(RetentionPolicy.RUNTIME)
    public @interface Info {
        String name();
    }

}