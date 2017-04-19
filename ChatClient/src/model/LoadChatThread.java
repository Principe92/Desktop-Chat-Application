package model;

import gui.gui;
import type.IChat;
import type.IMessage;

import java.awt.*;
import java.util.Collection;

/**
 * Created by okori on 19-Apr-17.
 */
public class LoadChatThread extends Thread {

    private final IChatManager chatManager;
    private final IChatDb db;
    private final gui gui;
    private final Point point;
    private boolean cancel;

    public LoadChatThread(IChatManager chatManager, IChatDb db, gui gui, Point point) {

        this.chatManager = chatManager;
        this.db = db;
        this.gui = gui;
        this.point = point;
    }

    @Override
    public void run() {
        IChat chat = chatManager.getChat(point);

        if (chat != null) {
            gui.clearMessageWindow();
            Collection<IMessage> messages = db.getMessages(chat);

            if (!cancel) {
                for (IMessage msg :
                        messages) {
                    gui.displayMessage(msg);
                }
            }
        }
    }

    public void cancel() {
        this.cancel = true;
    }
}
