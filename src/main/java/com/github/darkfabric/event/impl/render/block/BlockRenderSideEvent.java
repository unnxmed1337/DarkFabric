package com.github.darkfabric.event.impl.render.block;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import me.zero.alpine.event.EventState;
import me.zero.alpine.event.type.Cancellable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;

/**
 * EventBlockRenderSide
 *
 * @author Hexeption admin@hexeption.co.uk
 * @since 12/11/2019 - 04:28 pm
 */
@Getter
@RequiredArgsConstructor
public class BlockRenderSideEvent extends Cancellable {
    private final EventState eventState;
    private final BlockState adjacentState;
    private final BlockGetter blockState;
    private final BlockPos blockAccess;
    private final Direction pos;
    @Setter
    private boolean toRender;
}