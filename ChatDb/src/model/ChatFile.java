package model;

import factory.MessageFactory;
import main.Constant;
import main.Util;
import type.IChat;
import type.ILogger;
import type.IMessage;
import type.MessageType;

import java.io.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

/**
 * Created by okori on 26-Apr-17.
 */
public class ChatFile implements IChatFile {

    private final String filePath;
    private final File file;
    private final ILogger logger;
    private final String fileName;

    public ChatFile(Date date, String messagePath, ILogger logger) throws IOException {
        this.logger = logger;
        this.fileName = String.format("Chat_%d.txt", date.getTime());
        this.filePath = String.format("%s/%s", messagePath, fileName);
        this.file = new File(filePath);

        if (!file.exists()) {

            if (!file.createNewFile()) {
                logger.logError(String.format("Unable to create chat file: %s", fileName));
            }
        }

    }

    @Override
    public boolean delete() {
        return file.delete();
    }

    @Override
    public String getName() {
        return fileName;
    }

    @Override
    public void insertChatInfo(IChat chat) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(file, true));
        writer.write(String.format("Title: %s", chat.getChatTitle()));
        writer.newLine();
        writer.write(String.format("IP: %s", chat.getIp()));
        writer.newLine();
        writer.write(String.format("Port: %d", chat.getPort()));
        writer.newLine();
        writer.write(String.format("Date: %d", chat.getCreationDate().getTime()));
        writer.newLine();
        writer.close();
    }

    @Override
    public void write(IMessage msg, boolean fromUser) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(file, true));
        String sender = fromUser ? "_" : msg.getSender();
        writer.write(String.format("%s ~ %d ~ %s", sender, msg.getType().getValue(), new String(msg.getData(), Util.getEncoding())));
        writer.newLine();
        writer.close();
    }

    @Override
    public Collection<IMessage> getMessages() throws IOException {
        Collection<IMessage> messages = new ArrayList<>();

        BufferedReader reader = new BufferedReader(new FileReader(file));
        int skipHeader = 4;
        while (skipHeader-- > 0) reader.readLine();

        String line = reader.readLine();

        while (line != null && !line.isEmpty()) {
            String[] msg = line.split("~");

            IMessage message = MessageFactory.getMessage(MessageType.getType(Integer.parseInt(msg[1].trim())));

            if (message != null) {
                String sender = msg[0].trim().equals("_") ? Constant.EMPTY : msg[0];
                message.setSender(sender.trim());
                message.setData(msg[2].trim());
                messages.add(message);
            }

            line = reader.readLine();
        }

        reader.close();
        return messages;
    }
}
