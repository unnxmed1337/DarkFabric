package com.github.darkfabric.injection.mixins;

import com.github.darkfabric.DarkFabric;
import com.github.darkfabric.event.impl.render.block.BlockRenderSideEvent;
import me.zero.alpine.event.EventState;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
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

    @Inject(method = "shouldRenderFace", at = @At("HEAD"), cancellable = true)
    private static void shouldSideBeRendered(BlockState state, BlockGetter world, BlockPos pos, Direction facing,
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
