package model;

import factory.RoundedBorder;
import main.Constant;
import main.Util;
import type.IImageHandler;
import type.IMessage;
import type.MessageType;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.File;

public class ImageMessage extends TextMessage implements IMessage {
    private byte[] image;
    private IImageHandler imageHandler;
    private String path;
    private String sender;

    public ImageMessage(IImageHandler imageHandler) {
        this.imageHandler = imageHandler;
        this.sender = Constant.EMPTY;
    }

    @Override
    public void setData(String text) {
        if (IsType(text)) {

            if (imageHandler.loadImage(text)) {
                image = imageHandler.getImageAsBytes();
            }

            this.path = text;

        }
    }

    @Override
    public boolean IsType(String text) {
        return new File(text).exists();
    }

    @Override
    public byte[] getData() {
        //return image;
        return this.path.getBytes(Util.getEncoding());
    }

    @Override
    public void setData(byte[] data) {
        //this.image = image;
        this.path = new String(data, Util.getEncoding());
    }

    @Override
    public MessageType getType() {
        return MessageType.IMAGE;
    }

    @Override
    public Component getMessagePanel(Color color) {
        JLabel label = new JLabel(this.path);
        label.setBorder(new CompoundBorder(new RoundedBorder(10), new EmptyBorder(Constant.MSG_PADDING, Constant.MSG_PADDING, Constant.MSG_PADDING, Constant.MSG_PADDING)));
        label.setBackground(color);
        label.setOpaque(true);
        return label;
    }

    @Override
    public String getSender() {
        return this.sender;
    }

    @Override
    public void setSender(String name) {
        this.sender = name;
    }
}
