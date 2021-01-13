package com.github.darkforge.injection.mixins;

import net.minecraft.entity.Entity;
import net.minecraft.util.math.vector.Vector3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import com.github.darkforge.injection.interfaces.IEntityRendererManager;

/**
 * MixinEntityRendererManager
 *
 * @author Hexeption admin@hexeption.co.uk
 * @since 12/11/2019 - 05:12 pm
 */
@Mixin(Entity.class)
public abstract class MixinEntity implements IEntityRendererManager {

    @Shadow
    private Vector3d positionVec;

    @Override
    public double getPosX() {
        return positionVec.getX();
    }

    @Override
    public double getPosY() {
        return positionVec.getY();
    }

    @Override
    public double getPosZ() {
        return positionVec.getZ();
    }
}
