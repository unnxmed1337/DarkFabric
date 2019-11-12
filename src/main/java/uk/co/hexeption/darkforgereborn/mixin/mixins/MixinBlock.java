package uk.co.hexeption.darkforgereborn.mixin.mixins;

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
import uk.co.hexeption.darkforgereborn.DarkForgeReborn;
import uk.co.hexeption.darkforgereborn.event.events.block.EventBlockRenderSide;

/**
 * MixinBlock
 *
 * @author Hexeption admin@hexeption.co.uk
 * @since 12/11/2019 - 04:29 pm
 */
@Mixin(Block.class)
public class MixinBlock {

    @Inject(method = "shouldSideBeRendered", at = @At("HEAD"), cancellable = true)
    private static void shouldSideBeRendered(BlockState adjacentState, IBlockReader blockState, BlockPos blockAccess, Direction pos, CallbackInfoReturnable<Boolean> callbackInfoReturnable) {
        EventBlockRenderSide eventBlockRenderSide = new EventBlockRenderSide(EventState.PRE, adjacentState, blockState, blockAccess, pos);
        DarkForgeReborn.INSTANCE.eventBus.post(eventBlockRenderSide);
        if (eventBlockRenderSide.isCancelled()) {
            callbackInfoReturnable.setReturnValue(false);
        } else if (eventBlockRenderSide.isToRender()) {
            callbackInfoReturnable.setReturnValue(true);
        }
    }

}
