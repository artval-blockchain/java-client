package com.moochain.common.dao;

import java.awt.event.*;

public class WListener implements WindowListener {
    @Override public void windowClosing(WindowEvent evt) {
        System.exit(0);
    }
    @Override public void windowOpened(WindowEvent evt) {}
    @Override public void windowClosed(WindowEvent evt) {}
    @Override public void windowIconified(WindowEvent evt) {}
    @Override public void windowDeiconified(WindowEvent evt) {}
    @Override public void windowActivated(WindowEvent evt) {}
    @Override public void windowDeactivated(WindowEvent evt) {}
}
