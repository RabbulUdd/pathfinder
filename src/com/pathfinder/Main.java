package com.pathfinder;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeListener;

public class Main {

    public static void main(String[] args) {
        JFrame frame = new JFrame();

        // Variables Used
        int size = 9;
        int windowHeight = 600;
        int windowWidth = 600;

        addGrids(frame, size);

        frame.setSize(windowWidth, windowHeight);
        frame.setLayout(new GridLayout(size, size));

        frame.show();
    }

    private static void addGrids(JFrame frame, int size) {
        for (int i = 0; i < size; i++) {
            for (int j =0; j < size; j++) {
                GridButton b = addButton(Integer.toString(i) + Integer.toString(j));

                frame.add(b);
            }
        }
    }

    private static GridButton addButton(String text) {
        GridButton button = new GridButton(text);

        button.setForeground(new Color(0, 135, 200).brighter());
        button.setHorizontalTextPosition(SwingConstants.CENTER);
        button.setBorder(null);
        button.setBackground(new Color(3, 59, 90));
        button.setHoverBackgroundColor(new Color(3, 59, 90).brighter());
        button.setPressedBackgroundColor(Color.PINK);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                button.setBackground(Color.PINK);
            }
        });

        return  button;
    }
}
