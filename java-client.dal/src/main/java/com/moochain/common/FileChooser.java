package com.moochain.common;

import javax.swing.*;
import java.io.File;

public class FileChooser extends JPanel {
    static private String newline = "\n";
    private JTextArea log;
    private JFileChooser fc;
    private File img;

    public FileChooser() { }

    public File getFile() {
        if (fc == null) {
            fc = new JFileChooser();
            fc.addChoosableFileFilter(new ImageFilter());
            fc.setAcceptAllFileFilterUsed(false);
            fc.setAccessory(new ImagePreview(fc));
        }
        int returnVal = fc.showDialog(FileChooser.this, "Select");
        img=fc.getSelectedFile();
        return img;
    }
}
