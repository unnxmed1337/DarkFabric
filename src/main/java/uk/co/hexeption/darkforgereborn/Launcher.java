package uk.co.hexeption.darkforgereborn;

import org.spongepowered.asm.launch.MixinBootstrap;
import org.spongepowered.asm.mixin.Mixins;
import org.spongepowered.asm.mixin.connect.IMixinConnector;

/**
 * Launcher
 *
 * @author Hexeption admin@hexeption.co.uk
 * @since 10/11/2019 - 12:30 pm
 */
public class Launcher implements IMixinConnector {

    @Override
    public void connect() {
        Mixins.addConfiguration("mixins.darkforge.json");
}
}
