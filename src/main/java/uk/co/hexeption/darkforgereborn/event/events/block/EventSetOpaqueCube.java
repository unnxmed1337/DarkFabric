package uk.co.hexeption.darkforgereborn.event.events.block;

import me.zero.alpine.event.EventState;
import me.zero.alpine.event.type.Cancellable;
import net.minecraft.util.math.BlockPos;

/**
 * EventSetOpaqueCube
 *
 * @author Hexeption admin@hexeption.co.uk
 * @since 12/11/2019 - 04:25 pm
 */
public class EventSetOpaqueCube extends Cancellable {

    private final EventState eventState;
    private final BlockPos blockPos;

    public EventSetOpaqueCube(EventState eventState, BlockPos blockPos) {
        this.eventState = eventState;
        this.blockPos = blockPos;
    }

    public EventState getEventState() {
        return eventState;
    }

    public BlockPos getBlockPos() {
        return blockPos;
    }
}
