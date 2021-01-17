package com.github.darkfabric.injection.mixins;

import com.github.darkfabric.DarkFabric;
import com.github.darkfabric.event.impl.render.block.BlockRenderSideEvent;
import me.zero.alpine.event.EventState;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * MixinBlock
 *
 * @author Hexeption admin@hexeption.co.uk
 * @since 12/11/2019 - 04:29 pm
 */
@Mixin(Block.class)
public abstract class MixinBlock {

    @Inject(method = "shouldDrawSide", at = @At("HEAD"), cancellable = true)
    private static void shouldSideBeRendered(BlockState state, BlockView world, BlockPos pos, Direction facing,
                                             CallbackInfoReturnable<Boolean> cir) {
        BlockRenderSideEvent eventBlockRenderSide = new BlockRenderSideEvent(EventState.PRE, state,
                world, pos, facing);
        DarkFabric.getInstance().getEventBus().post(eventBlockRenderSide);
        if (eventBlockRenderSide.isCancelled())
            cir.setReturnValue(false);
        else if (eventBlockRenderSide.isToRender())
            cir.setReturnValue(true);
    }

}
