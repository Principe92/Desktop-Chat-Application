package model;

import main.Constant;
import type.IChat;
import type.ILogger;
import type.IMessage;

import java.io.File;
import java.io.IOException;
import java.util.Collection;

public class ChatDb implements IChatDb {

    private static IChatDb db;
    private ILogger logger;
    private String messagePath;

    public ChatDb(ILogger logger) {
        this.logger = logger;

        init();
    }

    public static IChatDb Instance(ILogger logger) {
        return db == null ? (db = new ChatDb(logger)) : db;
    }

    private void init() {
        File home = new File(Constant.DEFAULT_PATH);

        if (!home.exists()) {
            if (!home.mkdir()) {
                logger.logInfo("Unable to create home directory");
            }

            File msgPath = new File(String.format("%s/%s", Constant.DEFAULT_PATH, "Chats"));
            if (!msgPath.mkdir()) {
                logger.logInfo("Unable to create msg directory");
            }
        }

        messagePath = String.format("%s/%s", Constant.DEFAULT_PATH, "Chats");
    }

    @Override
    public void createChat(IChat chat) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    IChatFile file = new ChatFile(chat.getCreationDate(), messagePath, logger);
                    file.insertChatInfo(chat);
                } catch (IOException e) {
                    logger.logError(e);
                }
            }
        }).start();
    }

    @Override
    public void deleteChat(IChat activeChat) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    IChatFile file = new ChatFile(activeChat.getCreationDate(), messagePath, logger);
                    if (!file.delete()) {
                        logger.logError(String.format("Unable to delete %s", file.getName()));
                    }
                } catch (IOException e) {
                    logger.logError(e);
                }
            }
        }).start();
    }

    @Override
    public void saveMessage(IChat activeChat, IMessage msg, boolean fromUser) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    if (activeChat != null) {
                        IChatFile file = new ChatFile(activeChat.getCreationDate(), messagePath, logger);
                        file.write(msg, fromUser);
                    }
                } catch (IOException e) {
                    logger.logError(e);
                }
            }
        }).start();
    }

    @Override
    public Collection<IMessage> getMessages(IChat chat) throws IOException {

        IChatFile file = new ChatFile(chat.getCreationDate(), messagePath, logger);
        return file.getMessages();
    }
}
