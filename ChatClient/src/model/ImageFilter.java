package model;

import java.io.File;

import javax.swing.filechooser.FileFilter;

import main.Constant;
import main.Util;

public class ImageFilter extends FileFilter {
	 
    //Accept all directories and all gif, jpg, tiff, or png files.
    public boolean accept(File f) {
        if (f.isDirectory()) {
            return true;
        }
 
        String extension = Util.getExtension(f);
        if (extension != null) {
            if (extension.equals(Constant.tiff) ||
                extension.equals(Constant.tif) ||
                extension.equals(Constant.gif) ||
                extension.equals(Constant.jpeg) ||
                extension.equals(Constant.jpg) ||
                extension.equals(Constant.png)) {
                    return true;
            } else {
                return false;
            }
        }
 
        return false;
    }
 
    //The description of this filter
    public String getDescription() {
        return "Just Images";
    }
}