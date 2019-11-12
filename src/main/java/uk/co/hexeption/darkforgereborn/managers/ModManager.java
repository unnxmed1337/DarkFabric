package uk.co.hexeption.darkforgereborn.managers;

import com.google.common.collect.Lists;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.codehaus.plexus.util.StringUtils;
import org.lwjgl.glfw.GLFW;
import org.reflections.Reflections;
import uk.co.hexeption.darkforgereborn.mod.Category;
import uk.co.hexeption.darkforgereborn.mod.Mod;
import uk.co.hexeption.darkforgereborn.mod.Mod.Enabled;
import uk.co.hexeption.darkforgereborn.util.LogHelper;

/**
 * ModManager
 *
 * @author Hexeption admin@hexeption.co.uk
 * @since 09/11/2019 - 05:26 am
 */
public class ModManager {

    private final List<Mod> mods = Lists.newCopyOnWriteArrayList();

    public void init() {
        initMods();
        LogHelper.info(String.format("%s mods loaded!", mods.size()));
        mods.forEach(mod -> LogHelper.info(String.format("%s [%s] loaded", mod.getName(), StringUtils.capitalise(GLFW.glfwGetKeyName(mod.getBind(), 0)))));
    }

    public void addMod(Mod... mods) {
        this.mods.addAll(Arrays.asList(mods));
    }

    public <T extends Mod> Mod getModByClass(final Class<T> clazz) {
        return mods.stream().filter(mod -> mod.getClass().equals(clazz)).findFirst().map(clazz::cast).orElse(null);
    }

    public Mod getModByName(final String modName) {
        return mods.stream().filter(mod -> mod.getName().equalsIgnoreCase(modName)).findFirst().orElse(null);
    }

    public List<Mod> getMods() {
        return mods;
    }

    private void initMods() {
        Reflections reflections = new Reflections(Mod.class.getPackage().getName());

        reflections.getTypesAnnotatedWith(Mod.ModInfo.class).forEach(aClass -> {
            try {
                Mod mod = (Mod) aClass.newInstance();
                if (aClass.isAnnotationPresent(Enabled.class)) {
                    mods.add(mod);
                    mod.toggle();
                } else {
                    mods.add(mod);
                }
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
        });
    }

    public List<Mod> getModsInCategory(Category category) {
        return mods.stream().filter(mod -> mod.getCategory() == category).collect(Collectors.toList());
    }

}
