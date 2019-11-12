package uk.co.hexeption.darkforgereborn.event.events.player;

import me.zero.alpine.event.EventState;
import net.minecraft.client.entity.player.ClientPlayerEntity;

/**
 * EventPlayerWalking
 *
 * @author Hexeption admin@hexeption.co.uk
 * @since 12/11/2019 - 07:03 pm
 */
public class EventPlayerWalking extends AbstractEventClientPlayerEntity {

    public EventPlayerWalking(EventState eventState, ClientPlayerEntity entity) {
        super(eventState, entity);
    }
}
