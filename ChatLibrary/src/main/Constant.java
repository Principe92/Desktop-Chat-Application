package main;

import javax.swing.*;
import java.awt.*;

public class Constant {
    public static final int BUFFER_SIZE = 4096;
    public static final String DELIMITER = "-";

    public final static String jpeg = "jpeg";
    public final static String jpg = "jpg";
    public final static String gif = "gif";
    public final static String tiff = "tiff";
    public final static String tif = "tif";
    public final static String png = "png";
    public static final String EMPTY = "";
    public static final int MIN_HEIGHT = 800;
    public static final int MIN_WIDTH = 1000;
    public static final int MIN_CHAR_WIDTH = 30;
    public static final int MIN_CHAR_HEIGHT = 100;
    public static final int MAG_2 = 2;
    public static final int MSG_PADDING = 8;
    public static final int MAG_16 = 16;
    public static final Color MSG_BG = new Color(244, 244, 244);
    public static final Color USER_BG = new Color(255, 255, 255);
    public static final Color OTHERS_BG = new Color(102, 187, 106);
    public static final String DOCK_EAST = "dock east";
    public static final String DOCK_WEST = "dock west";
    public static final String SERVER_ERROR = "Chat room has closed";
    public static final Color CHAT_BG = Color.WHITE;
    public static final Color MENU_BG = new Color(229, 72, 25);
    public static final int MAG_32 = 32;
    public static final Color CHAT_LIST_BG = new Color(255, 162, 112);
    public static final int MAG_24 = 24;
    public static final int HANDSHAKE_MSG_SIZE = 3;
    public static final int ROUNDED_CORNER_RADIUS = 4;
    public static final String MSG_FORMAT = "<html><span style=\"font-weight: bold\">%s</span><br>%s</html>";

    public static final String DEFAULT_PATH = String.format("%s/%s",
            new JFileChooser().getFileSystemView().getDefaultDirectory().toString(), "ChatAppHome");
    public static final Color ACTIVE_CHAT = new Color(229, 72, 112);
}
