package type;

import java.awt.*;

public interface IMessage {

    void setData(String text);

    boolean IsType(String text);

    byte[] getData();

    void setData(byte[] data);

    MessageType getType();

    Component getMessagePanel(float alignment, Color color);
}
 