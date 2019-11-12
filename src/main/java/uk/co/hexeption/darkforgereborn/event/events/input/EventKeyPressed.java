package uk.co.hexeption.darkforgereborn.event.events.input;

import me.zero.alpine.event.EventState;
import uk.co.hexeption.darkforgereborn.event.Event;

/**
 * EventKeyPressed
 *
 * @author Hexeption admin@hexeption.co.uk
 * @since 12/11/2019 - 12:11 pm
 */
public class EventKeyPressed extends Event {

    private int key;
    private int scancode;

    public EventKeyPressed(EventState eventState, int key, int scancode) {
        super(eventState);
        this.key = key;
        this.scancode = scancode;
    }

    public int getKey() {
        return key;
    }

    public int getScancode() {
        return scancode;
    }
}
