package com.github.darkfabric.addon;

import com.github.darkfabric.DarkFabric;
import com.github.darkfabric.base.interfaces.INameable;
import com.github.darkfabric.base.named.NamedRegistry;
import lombok.Getter;
import net.minecraft.client.Minecraft;

import java.io.File;

@Getter
public class AddonRegistry extends NamedRegistry<AbstractAddon> {

    private File directory;

    public void initialize() {
        directory = new File(Minecraft.getInstance().gameDirectory.getAbsolutePath() + "/"
                + DarkFabric.getInstance().getName().toLowerCase(), "addons");
        getObjects().forEach(AbstractAddon::init);
        if (!directory.exists())
            directory.mkdirs();
        for (File file : directory.listFiles())
            if (file.isFile()) {
                AbstractAddon addon = AbstractAddon.Provider.load(file);
                register(addon);
            }
    }

}