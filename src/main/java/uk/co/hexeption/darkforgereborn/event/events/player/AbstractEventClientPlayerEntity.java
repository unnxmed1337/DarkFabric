package uk.co.hexeption.darkforgereborn.event.events.player;

import me.zero.alpine.event.EventState;
import me.zero.alpine.event.type.Cancellable;
import net.minecraft.client.entity.player.ClientPlayerEntity;

/**
 * AbstractEventClientPlayerEntity
 *
 * @author Hexeption admin@hexeption.co.uk
 * @since 12/11/2019 - 07:03 pm
 */
public abstract class AbstractEventClientPlayerEntity extends Cancellable {

    private EventState eventState;
    protected ClientPlayerEntity entity;

    public AbstractEventClientPlayerEntity(EventState eventState, ClientPlayerEntity entity) {
        this.eventState = eventState;
        this.entity = entity;
    }

    public EventState getEventState() {
        return eventState;
    }

    public ClientPlayerEntity getEntity() {
        return entity;
    }


}
