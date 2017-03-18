package factory;

import model.SocketProtocol;
import model.SystemLogger;
import type.ILogger;
import type.ISocketProtocol;

public class AbstractFactory {

	public static ILogger getLogger(){
		return new SystemLogger();
	}

	public static ISocketProtocol getProtocol() {
		return new SocketProtocol();
	}
}
