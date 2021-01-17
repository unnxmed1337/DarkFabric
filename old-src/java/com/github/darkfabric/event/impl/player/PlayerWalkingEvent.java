package com.github.darkfabric.event.impl.player;

import me.zero.alpine.event.EventState;
import net.minecraft.client.network.ClientPlayerEntity;

/**
 * EventPlayerWalking
 *
 * @author Hexeption admin@hexeption.co.uk
 * @since 12/11/2019 - 07:03 pm
 */
public class PlayerWalkingEvent extends AbstractEventClientPlayerEntity {
    public PlayerWalkingEvent(EventState eventState, ClientPlayerEntity entity) {
        super(eventState, entity);
    }
}