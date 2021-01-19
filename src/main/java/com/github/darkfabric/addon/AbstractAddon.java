package com.github.darkfabric.addon;

import com.github.darkfabric.DarkFabric;
import com.github.darkfabric.base.interfaces.INameable;
import lombok.Getter;
import lombok.SneakyThrows;
import org.simpleyaml.configuration.file.YamlFile;

import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

@Getter
public abstract class AbstractAddon implements INameable {

    private File dataFolder;
    private Description description;

    public final AbstractAddon setup(Description description) {
        this.description = description;
        dataFolder = new File(DarkFabric.getInstance().getAddonRegistry().getDirectory(), description.getName());
        preInit();
        return this;
    }

    public abstract void preInit();
    public abstract void init();
    public abstract void postInit();

    @Override
    public String getName() {
        return null;
    }

    public static class Provider {

        protected static ParallelClassLoader parallelClassLoader;

        @SneakyThrows
        public static AbstractAddon load(File file) {
            if (file == null || !file.getAbsolutePath().endsWith(".jar"))
                return null;

            JarFile jarFile = new JarFile(file);
            JarEntry jarEntry = jarFile.getJarEntry("addon.yml");
            Description description = new Description(jarFile.getInputStream(jarEntry));
            jarFile.close();

            parallelClassLoader = new ParallelClassLoader(AbstractAddon.class.getClassLoader(), file.toURI().toURL());
            Class<? extends AbstractAddon> clazz = Class.forName(description.main,
                    true, parallelClassLoader).asSubclass(AbstractAddon.class);
            parallelClassLoader.close();

            return clazz.newInstance().setup(description);
        }

        @SneakyThrows
        public static AbstractAddon debug() {
            Description description = new Description(AbstractAddon.class.getResourceAsStream("/addon.yml"));
            Class<? extends AbstractAddon> clazz = Class.forName(description.main).asSubclass(AbstractAddon.class);
            return clazz.newInstance().setup(description);
        }
    }

    @Getter
    public static class Description {

        private final String name;
        private final String version;
        private final String main;

        @SneakyThrows
        public Description(InputStream inputStream) {
            YamlFile yamlFile = new YamlFile();
            yamlFile.load(new InputStreamReader(inputStream));

            name = yamlFile.getString("name");
            version = yamlFile.getString("version");
            main = yamlFile.getString("main");
        }

    }

}