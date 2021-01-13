package com.github.darkforge.event.impl.player;

import lombok.AllArgsConstructor;
import lombok.Getter;
import me.zero.alpine.event.EventState;
import me.zero.alpine.event.type.Cancellable;
import net.minecraft.client.entity.player.ClientPlayerEntity;

/**
 * AbstractEventClientPlayerEntity
 *
 * @author Hexeption admin@hexeption.co.uk
 * @since 12/11/2019 - 07:03 pm
 */
@Getter
@AllArgsConstructor
public abstract class AbstractEventClientPlayerEntity extends Cancellable {
    private final EventState eventState;
    protected ClientPlayerEntity entity;
}