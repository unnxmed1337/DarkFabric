package com.github.darkfabric.injection.mixins;

import net.minecraft.core.Registry;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.game.ClientboundLevelParticlesPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientboundLevelParticlesPacket.class)
public abstract class MixinClientboundLevelParticlesPacket {

    @Shadow
    private boolean overrideLimiter;

    @Shadow
    private double x, y, z;

    @Shadow
    private float xDist, yDist, zDist;

    @Shadow
    private float maxSpeed;

    @Shadow
    private int count;

    @Shadow
    private ParticleOptions particle;

    @Shadow
    protected abstract <T extends ParticleOptions> T readParticle(FriendlyByteBuf friendlyByteBuf, ParticleType<T> particleType);

    /**
     * @reason Fixed the particle Crash.
     */
    @Inject(method = "read", at = @At(value = "HEAD"), cancellable = true)
    public void read(FriendlyByteBuf friendlyByteBuf, CallbackInfo ci) {
        ParticleType<?> particleType = Registry.PARTICLE_TYPE.byId(friendlyByteBuf.readInt());
        if (particleType == null)
            particleType = ParticleTypes.BARRIER;

        overrideLimiter = friendlyByteBuf.readBoolean();
        x = friendlyByteBuf.readDouble();
        y = friendlyByteBuf.readDouble();
        z = friendlyByteBuf.readDouble();
        xDist = friendlyByteBuf.readFloat();
        yDist = friendlyByteBuf.readFloat();
        zDist = friendlyByteBuf.readFloat();
        maxSpeed = friendlyByteBuf.readFloat();
        count = Math.min(120, friendlyByteBuf.readInt());
        particle = readParticle(friendlyByteBuf, (ParticleType) particleType);
        ci.cancel();
    }

}