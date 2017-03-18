package model;

import java.io.IOException;

import type.ILogger;

public class SystemLogger implements ILogger {

	@Override
	public void logError(IOException e) {
		e.printStackTrace();
		
	}

	@Override
	public void logInfo(String msg) {
		System.out.println(msg);
		
	}

}
