package model;

import type.MessageType;

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
