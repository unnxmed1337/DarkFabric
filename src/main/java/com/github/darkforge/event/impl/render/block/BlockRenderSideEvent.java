package com.github.darkforge.event.impl.render.block;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
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
@Getter
@RequiredArgsConstructor
public class BlockRenderSideEvent extends Cancellable {
    private final EventState eventState;
    private final BlockState adjacentState;
    private final IBlockReader blockState;
    private final BlockPos blockAccess;
    private final Direction pos;
    @Setter private boolean toRender;
}