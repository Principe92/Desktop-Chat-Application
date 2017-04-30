package model;

import type.IMessage;

import java.io.IOException;

public interface IClient {

    void sendToSocket(IMessage message) throws IOException;

    void setUp(String title) throws IOException;

    int getChatId();

    String getUserName();

    void start();

    void close() throws IOException;
}
