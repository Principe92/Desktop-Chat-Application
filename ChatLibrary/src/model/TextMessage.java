package model;

import factory.RoundedBorder;
import main.Constant;
import main.Util;
import type.IMessage;
import type.MessageType;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.File;

public class TextMessage implements IMessage {
    private String data;

    public TextMessage() {

    }

    public TextMessage(String text) {
        setData(text);
    }

    @Override
    public void setData(String text) {
        this.data = text;

    }

    @Override
    public boolean IsType(String text) {
        return !(new File(text)).exists();
    }

    @Override
    public byte[] getData() {
        return data != null ? data.getBytes(Util.getEncoding()) : null;
    }

    @Override
    public void setData(byte[] data) {
        this.data = new String(data, Util.getEncoding());
    }

    @Override
    public MessageType getType() {
        return MessageType.TEXT;
    }

    @Override
    public Component getMessagePanel(Color color) {
        JLabel label = new JLabel(this.data);
        label.setBorder(new CompoundBorder(new RoundedBorder(10), new EmptyBorder(Constant.MSG_PADDING, Constant.MSG_PADDING, Constant.MSG_PADDING, Constant.MSG_PADDING)));
        label.setBackground(color);
        label.setOpaque(true);
        return label;
    }
}
