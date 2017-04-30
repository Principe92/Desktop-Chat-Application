package model;

import type.MessageType;

/**
 * Created by okori on 29-Apr-17.
 */
public class QuitMessage extends TextMessage {

    public QuitMessage(String text) {
        super(text);
    }

    public QuitMessage() {
        super();
    }

    @Override
    public MessageType getType() {
        return MessageType.QUIT;
    }
}
