package listener;

import model.User;

/**
 * Created by okori on 29-Apr-17.
 */
public interface AccountListener {
    void loginAccepted(User user);
}
