package uk.co.hexeption.darkforgereborn.event.events.block;

import me.zero.alpine.event.EventState;
import me.zero.alpine.event.type.Cancellable;
import net.minecraft.block.BlockState;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;

/**
 * EventBlockRenderSide
 *
 * @author Hexeption admin@hexeption.co.uk
 * @since 12/11/2019 - 04:28 pm
 */
public class EventBlockRenderSide extends Cancellable {

    private final EventState eventState;
    private final BlockState adjacentState;
    private final IBlockReader blockState;
    private final BlockPos blockAccess;
    private final Direction pos;
    private boolean toRender;

    public EventBlockRenderSide(EventState eventState, BlockState adjacentState, IBlockReader blockState, BlockPos blockAccess, Direction pos) {
        this.eventState = eventState;
        this.adjacentState = adjacentState;
        this.blockState = blockState;
        this.blockAccess = blockAccess;
        this.pos = pos;
    }

    public EventState getEventState() {
        return eventState;
    }

    public BlockState getAdjacentState() {
        return adjacentState;
    }

    public IBlockReader getBlockState() {
        return blockState;
    }

    public BlockPos getBlockAccess() {
        return blockAccess;
    }

    public Direction getPos() {
        return pos;
    }

    public boolean isToRender() {
        return toRender;
    }

    public void setToRender(boolean toRender) {
        this.toRender = toRender;
    }
}
