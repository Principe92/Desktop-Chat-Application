package model;

import main.Util;
import type.IImageHandler;
import type.IMessage;
import type.MessageType;

import java.io.File;

public class ImageMessage extends TextMessage implements IMessage {
    private byte[] image;
    private IImageHandler imageHandler;
    private String path;

    public ImageMessage(IImageHandler imageHandler) {
        this.imageHandler = imageHandler;
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
}
