package uk.co.hexeption.darkforgereborn.mixin;

import me.zero.alpine.event.EventState;
import net.minecraft.client.renderer.chunk.VisGraph;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import uk.co.hexeption.darkforgereborn.DarkForgeReborn;
import uk.co.hexeption.darkforgereborn.event.events.block.EventSetOpaqueCube;

/**
 * MixinVisGraph
 *
 * @author Hexeption admin@hexeption.co.uk
 * @since 12/11/2019 - 03:46 pm
 */
@Mixin(VisGraph.class)
public class MixinVisGraph {

    @Inject(method = "setOpaqueCube", at = @At("HEAD"), cancellable = true)
    private void setOpaqueCube(BlockPos pos, CallbackInfo callbackInfo) {
        EventSetOpaqueCube eventSetOpaqueCube = new EventSetOpaqueCube(EventState.PRE, pos);
        DarkForgeReborn.INSTANCE.eventBus.post(eventSetOpaqueCube);
        if(eventSetOpaqueCube.isCancelled()){
            callbackInfo.cancel();
        }
    }


}
