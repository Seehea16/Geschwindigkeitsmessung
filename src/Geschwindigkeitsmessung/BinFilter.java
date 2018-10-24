package Geschwindigkeitsmessung;

import java.io.File;
import javax.swing.filechooser.FileFilter;

public class BinFilter extends FileFilter {
    @Override
    public boolean accept(File f) {
        String extension = null;
        if(f.isDirectory()) {
            return true;
        }
        String s = f.getName();
        int i = s.lastIndexOf(".");
        if(i > 0 && i < s.length() - 1) {
            extension = s.substring(i + 1).toLowerCase();
        }
        return (extension != null && extension.equals("bin"));
    }

    @Override
    public String getDescription() {
        return "Binary Files (*.bin)";
    }
}