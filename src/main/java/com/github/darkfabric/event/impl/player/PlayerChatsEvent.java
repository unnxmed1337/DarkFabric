package com.github.darkfabric.event.impl.player;

import lombok.AllArgsConstructor;
import lombok.Getter;
import me.zero.alpine.event.type.Cancellable;

/**
 * EventSendChatMessage
 *
 * @author Hexeption admin@hexeption.co.uk
 * @since 13/11/2019 - 05:23 pm
 */
@Getter
@AllArgsConstructor
public class PlayerChatsEvent extends Cancellable {
    private final String message;
}