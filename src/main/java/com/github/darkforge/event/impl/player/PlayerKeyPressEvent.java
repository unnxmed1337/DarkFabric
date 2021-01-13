package com.github.darkforge.event.impl.player;

import com.github.darkforge.event.Event;
import lombok.Getter;
import me.zero.alpine.event.EventState;

/**
 * EventKeyPressed
 *
 * @author Hexeption admin@hexeption.co.uk
 * @since 12/11/2019 - 12:11 pm
 */
@Getter
public class PlayerKeyPressEvent extends Event {
    private final int key;
    private final int scancode;

    public PlayerKeyPressEvent(EventState eventState, int key, int scancode) {
        super(eventState);
        this.key = key;
        this.scancode = scancode;
    }
}