package uk.co.hexeption.darkforgereborn.event.events.chat;

import me.zero.alpine.event.type.Cancellable;

/**
 * EventSendChatMessage
 *
 * @author Hexeption admin@hexeption.co.uk
 * @since 13/11/2019 - 05:23 pm
 */
public class EventSendChatMessage extends Cancellable {

    private String message;

    public EventSendChatMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
