package com.github.darkforge.event.impl.render.block;

import lombok.AllArgsConstructor;
import lombok.Getter;
import me.zero.alpine.event.EventState;
import me.zero.alpine.event.type.Cancellable;
import net.minecraft.util.math.BlockPos;

/**
 * EventSetOpaqueCube
 *
 * @author Hexeption admin@hexeption.co.uk
 * @since 12/11/2019 - 04:25 pm
 */
@Getter
@AllArgsConstructor
public class SetOpaqueCubeEvent extends Cancellable {
    private final EventState eventState;
    private final BlockPos blockPos;
}