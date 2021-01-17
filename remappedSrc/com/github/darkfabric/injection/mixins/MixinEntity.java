package com.github.darkfabric.injection.mixins;

import com.github.darkfabric.injection.interfaces.IEntityRendererManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

/**
 * MixinEntityRendererManager
 *
 * @author Hexeption admin@hexeption.co.uk
 * @since 12/11/2019 - 05:12 pm
 */
@Mixin(Entity.class)
public abstract class MixinEntity implements IEntityRendererManager {

    @Shadow
    private Vec3d pos;

    @Override
    public double getPosX() {
        return pos.getX();
    }

    @Override
    public double getPosY() {
        return pos.getY();
    }

    @Override
    public double getPosZ() {
        return pos.getZ();
    }
}
