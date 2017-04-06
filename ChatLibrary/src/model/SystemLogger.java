package model;

import type.ILogger;

import java.io.IOException;

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
