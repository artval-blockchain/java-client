package com.moochain.common.dao;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowListener;

public class Message extends Frame implements ActionListener {
    public Message(String msg){
        setTitle("Message");
        setSize(350, 150);
        setVisible(true);
        setLayout(new FlowLayout());
        Label lblmsg = new Label(msg);
        lblmsg.setFont(new Font(null, 0, 20));
        add(lblmsg);
        Button b = new Button("OK");
        b.setFont(new Font(null, 0, 25));
        add(b);
        b.addActionListener(this);
    }
    @Override public void actionPerformed(ActionEvent evt) {
        removeNotify();
    }
}
