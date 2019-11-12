package uk.co.hexeption.darkforgereborn.mixin.mixins;

import net.minecraft.client.multiplayer.PlayerController;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import uk.co.hexeption.darkforgereborn.mixin.imp.IPlayerController;

/**
 * MixinPlayerController
 *
 * @author Hexeption admin@hexeption.co.uk
 * @since 12/11/2019 - 05:19 pm
 */
@Mixin(PlayerController.class)
public class MixinPlayerController implements IPlayerController {


    @Shadow
    private float curBlockDamageMP;

    @Override
    public float getCurrentBlockDamage() {
        return curBlockDamageMP;
    }
}
