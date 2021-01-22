package com.github.darkfabric.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import me.zero.alpine.event.EventState;

/**
 * Event
 *
 * @author Hexeption admin@hexeption.co.uk
 * @since 12/11/2019 - 11:38 am
 */
@Getter
@Setter
@AllArgsConstructor
public class Event {
    private EventState eventState;
}