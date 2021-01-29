package com.github.darkfabric.injection.mixins;

import com.github.darkfabric.injection.interfaces.IEntityRendererManager;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

/**
 * MixinEntityRendererManager
 *
 * @author Hexeption admin@hexeption.co.uk
 * @since 12/11/2019 - 05:12 pm
 */
@Mixin(Entity.class)
public abstract class MixinEntity implements IEntityRendererManager {

    @Shadow
    private Vec3 position;

    @Unique
    @Override
    public double getPosX() {
        return position.x;
    }

    @Unique
    @Override
    public double getPosY() {
        return position.y;
    }

    @Unique
    @Override
    public double getPosZ() {
        return position.z;
    }

}