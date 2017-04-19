package listener;

import java.awt.*;

/**
 * Created by okori on 06-Apr-17.
 */
public interface ChatListPanelListener {
    void joinChatRoom();

    void createChatRoom();

    void loadChat(Point point);
}
