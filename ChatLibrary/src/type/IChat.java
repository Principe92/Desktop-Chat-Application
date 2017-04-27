package type;

import java.io.IOException;
import java.util.Date;

/**
 * Created by okori on 06-Apr-17.
 */
public interface IChat {
    void close() throws IOException;

    void sendToUsers(IMessage msg) throws IOException;

    Integer getChatId();

    boolean start(String[] args) throws IOException;

    int getGuiId();

    void setGuiId(int guiId);

    String getChatTitle();

    void setChatTitle(String title);

    int getPort();

    Date getCreationDate();

    String getIp();
}
