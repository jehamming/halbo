package com.hamming.halbo.client.viewer;

import com.hamming.halbo.client.controllers.MoveController;
import com.hamming.halbo.client.interfaces.Viewer;
import com.hamming.halbo.model.dto.UserLocationDto;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class DummyVirtualDisplay extends JFrame implements Viewer  {

    private JLabel lblMessage;
    private JLabel lblLocation;
    private JLabel lblDetails;
    private JTextArea taOther;

    public DummyVirtualDisplay() {
        init();
    }

    public void init() {        //Create and set up the window.
        setTitle("DummyVirtualWindow");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //Display the window.

        lblMessage = new JLabel();
        lblLocation = new JLabel();
        lblDetails = new JLabel();
        taOther = new JTextArea();
        taOther.setFocusable(false);
        taOther.setRows(5);
        taOther.setEditable(false);

        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        p.add(lblMessage);
        p.add(lblLocation);
        p.add(lblDetails);
        p.add(taOther);

        getContentPane().add(p);

        pack();
        setVisible(true);
        setSize(600, 500);
        setLocation(350, 50);
    }

    private void addOtherUserText(String str) {
        String txt = taOther.getText();
        txt = txt.concat(str + "\n");
        taOther.setText(txt);
    }

    @Override
    public void setLocation(double x, double y, double z, double viewDirection) {
        String details = "X:" + x+  ", Y:" + y + ", Z:" + z;
        lblDetails.setText(details);
    }

    @Override
    public void setLocation(String userId, String name, double x, double y, double z, double viewDirection) {
        String details = name + "("+userId+")-X:" + x+  ", Y:" + y + ", Z:" + z;
        addOtherUserText(details);
    }

    @Override
    public void setBaseplate(String name, int width, int length) {
        setTitle(name + "("+width+","+length+')');
    }

    @Override
    public void resetView() {
        lblMessage.setText("");
        lblLocation.setText("");
        lblDetails.setText("");
        setTitle("UNKNOWN");
    }
}
