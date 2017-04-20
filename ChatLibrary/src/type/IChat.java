package type;

import java.awt.*;
import java.io.IOException;

/**
 * Created by okori on 06-Apr-17.
 */
public interface IChat {
    void close() throws IOException;

    void sendToUsers(IMessage msg) throws IOException;

    Integer getChatId();

    boolean start(String[] args) throws IOException;

    void setGuiPosition(Point point);

    Point getPosition();

    String getChatTitle();

    void setChatTitle(String title);

    int getPort();
}
