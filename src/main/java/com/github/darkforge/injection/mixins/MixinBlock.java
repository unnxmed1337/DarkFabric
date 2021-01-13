package com.github.darkforge.injection.mixins;

import com.github.darkforge.DarkForgeReloaded;
import com.github.darkforge.event.impl.render.block.BlockRenderSideEvent;
import me.zero.alpine.event.EventState;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
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

    @Inject(method = "shouldSideBeRendered", at = @At("HEAD"), cancellable = true)
    private static void shouldSideBeRendered(BlockState adjacentState, IBlockReader blockState,
                                             BlockPos blockAccess, Direction pos,
                                             CallbackInfoReturnable<Boolean> callbackInfoReturnable) {
        BlockRenderSideEvent eventBlockRenderSide = new BlockRenderSideEvent(EventState.PRE, adjacentState,
                blockState, blockAccess, pos);
        DarkForgeReloaded.getInstance().getEventBus().post(eventBlockRenderSide);
        if (eventBlockRenderSide.isCancelled())
            callbackInfoReturnable.setReturnValue(false);
        else if (eventBlockRenderSide.isToRender())
            callbackInfoReturnable.setReturnValue(true);
    }

}
