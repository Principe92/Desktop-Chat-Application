package type;

import java.io.IOException;

public interface ILogger {

	void logError(IOException e);

	void logInfo(String msg);

    void logError(String msg);
}
