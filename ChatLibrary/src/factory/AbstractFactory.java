package factory;

import model.ILogger;
import model.SystemLogger;

public class AbstractFactory {

	public static ILogger getLogger(){
		return new SystemLogger();
	}
}
