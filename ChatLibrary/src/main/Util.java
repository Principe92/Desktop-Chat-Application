package main;

import java.nio.charset.Charset;

public class Util {

	public static boolean isNullOrEmpty(String a){
		return a == null || a.isEmpty();
	}

	public static Charset getEncoding() {
		return Charset.forName("UTF-8");
	}
}
