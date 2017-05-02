package main;

import java.awt.*;
import java.io.File;
import java.nio.charset.Charset;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Util {

    public static boolean isNullOrEmpty(String a) {
        return a == null || a.isEmpty();
    }

    public static Charset getEncoding() {
        return Charset.forName("UTF-8");
    }

    public static String getExtension(File f) {
        String ext = null;
        String s = f.getName();
        int i = s.lastIndexOf('.');

        if (i > 0 && i < s.length() - 1) {
            ext = s.substring(i + 1).toLowerCase();
        }
        return ext;
    }

    public static GridBagConstraints getNoFill(int alignment) {
        GridBagConstraints gs = new GridBagConstraints();
        gs.anchor = alignment;
        gs.fill = GridBagConstraints.NONE;

        return gs;
    }

    public static String fillIconPath(String file) {
        return String.format("/icons/%s", file);
    }
    
    public static boolean hasUpperCase(String str) {
    	boolean b = !str.equals(str.toLowerCase());
    	return b;
    }
    
    public static boolean hasSpecialChar(String str){
    	Pattern p = Pattern.compile("[^a-z0-9 ]", Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(str);
        boolean b = (m.find())? true : false;
        return b;
    }
    
}
