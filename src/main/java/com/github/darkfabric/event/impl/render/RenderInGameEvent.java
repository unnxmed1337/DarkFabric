package com.github.darkfabric.event.impl.render;

import com.github.darkfabric.event.Event;
import lombok.Getter;
import me.zero.alpine.event.EventState;

/**
 * EventRender2D
 *
 * @author Hexeption admin@hexeption.co.uk
 * @since 12/11/2019 - 06:24 pm
 */
@Getter
public class RenderInGameEvent extends Event {
    private final int width, height;

    public RenderInGameEvent(EventState eventState, int width, int height) {
        super(eventState);
        this.width = width;
        this.height = height;
    }
}