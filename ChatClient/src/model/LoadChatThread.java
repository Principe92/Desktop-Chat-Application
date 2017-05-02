package model;

import gui.gui;
import type.IChat;
import type.ILogger;
import type.IMessage;

import java.io.IOException;
import java.util.Collection;

public class LoadChatThread extends Thread {

    private final IChatManager chatManager;
    private final IChatDb db;
    private final gui gui;
    private final int point;
    private boolean cancel;
    private ILogger logger;

    public LoadChatThread(IChatManager chatManager, IChatDb db, gui gui, int guiId, ILogger logger) {

        this.chatManager = chatManager;
        this.db = db;
        this.gui = gui;
        this.point = guiId;
        this.logger = logger;
    }

    @Override
    public void run() {
        IChat chat = chatManager.getChat(point);

        if (chat != null) {
            gui.clearMessageWindow();
            gui.setActive(chat);

            try {
                Collection<IMessage> messages = db.getMessages(chat);

                if (!cancel) {
                    for (IMessage msg :
                            messages) {
                        gui.displayMessage(msg, msg.getSender().isEmpty());
                    }
                }
            } catch (IOException e) {
                logger.logError(e);
            }
        }
    }

    public void cancel() {
        this.cancel = true;
    }
}
