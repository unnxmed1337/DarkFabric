package com.github.darkforge.injection.mixins;

import com.github.darkforge.injection.interfaces.IPlayerController;
import net.minecraft.client.multiplayer.PlayerController;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

/**
 * MixinPlayerController
 *
 * @author Hexeption admin@hexeption.co.uk
 * @since 12/11/2019 - 05:19 pm
 */
@Mixin(PlayerController.class)
public abstract class MixinPlayerController implements IPlayerController {

    @Shadow
    private float curBlockDamageMP;

    @Override
    public float getCurrentBlockDamage() {
        return curBlockDamageMP;
    }
}
