package uk.co.hexeption.darkforgereborn.mixin.mixins;

import net.minecraft.client.renderer.entity.EntityRendererManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import uk.co.hexeption.darkforgereborn.mixin.imp.IEntityRendererManager;

/**
 * MixinEntityRendererManager
 *
 * @author Hexeption admin@hexeption.co.uk
 * @since 12/11/2019 - 05:12 pm
 */
@Mixin(EntityRendererManager.class)
public class MixinEntityRendererManager implements IEntityRendererManager {

    @Shadow
    private double renderPosX;

    @Shadow
    private double renderPosY;

    @Shadow
    private double renderPosZ;

    @Override
    public double getRenderPosX() {
        return renderPosX;
    }

    @Override
    public double getRenderPosY() {
        return renderPosY;
    }

    @Override
    public double getRenderPosZ() {
        return renderPosZ;
    }
}
