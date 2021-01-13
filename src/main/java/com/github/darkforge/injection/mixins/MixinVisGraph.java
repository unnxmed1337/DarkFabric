package com.github.darkforge.injection.mixins;

import com.github.darkforge.DarkForgeReloaded;
import com.github.darkforge.event.impl.render.block.SetOpaqueCubeEvent;
import me.zero.alpine.event.EventState;
import net.minecraft.client.renderer.chunk.VisGraph;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * MixinVisGraph
 *
 * @author Hexeption admin@hexeption.co.uk
 * @since 12/11/2019 - 03:46 pm
 */
@Mixin(VisGraph.class)
public abstract class MixinVisGraph {

    @Inject(method = "setOpaqueCube", at = @At("HEAD"), cancellable = true)
    private void setOpaqueCube(BlockPos pos, CallbackInfo callbackInfo) {
        SetOpaqueCubeEvent eventSetOpaqueCube = new SetOpaqueCubeEvent(EventState.PRE, pos);
        DarkForgeReloaded.getInstance().getEventBus().post(eventSetOpaqueCube);
        if (eventSetOpaqueCube.isCancelled())
            callbackInfo.cancel();
    }

}