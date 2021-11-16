package com.pathfinder;

import javafx.util.Pair;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class Window extends JFrame {

    private GridSquare startSquare;
    private GridSquare endSquare;
    private ArrayList<GridSquare> wallSquares = new ArrayList<>();
    private ArrayList<GridSquare> allSquares = new ArrayList<>();

    private Color primaryColour = new Color(3, 59, 90);

    private JButton startButton;
    private JButton coordinateButton;
    private JButton resetButton;

    public Window() {}

    public Window(int size, int windowHeight, int windowWidth) {
        this.add(addGrids(size), BorderLayout.CENTER);
        this.add(addSidePane(), BorderLayout.WEST);

        this.setSize(windowWidth, windowHeight);
    }

    private JPanel addGrids(int size) {
        JPanel frame = new JPanel();

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                Pair<Integer, Integer> coordinate = new Pair<>(j, i);
                String coordinateText = String.valueOf(coordinate.getKey()) + String.valueOf(coordinate.getValue());

                GridSquare b = addButton(coordinateText);
//                GridSquare b = addButton(String.valueOf(i) + String.valueOf(j));
                b.setCoordinate(coordinate);
                allSquares.add(b);

                frame.add(b);
            }
        }

        frame.setLayout(new GridLayout(size, size));

        return frame;
    }

    @NotNull
    private GridSquare addButton(String text) {
        GridSquare button = new GridSquare(text);

        button.setForeground(new Color(0, 135, 200).brighter());
        button.setHorizontalTextPosition(SwingConstants.CENTER);
        button.setBorder(null);
        button.setBackground(primaryColour);
        button.setHoverBackgroundColor(new Color(3, 59, 90).brighter());
        button.setPressedBackgroundColor(Color.PINK);

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                setSquare(button);
            }
        });
//        button.addMouseListener(new MouseAdapter() {
//            @Override
//            public void mouseClicked(MouseEvent e) {
//                if (e.getButton() == MouseEvent.BUTTON3) {
//                    removeSquare(button);
//                }
//            }
//        });

        return  button;
    }


    private void setSquare(@NotNull GridSquare square) {
        if (!square.isSet()) {
            square.setSet();

            if (startSquare == null) {
                startSquare = square;
                setStart(square);
            } else if (endSquare == null) {
                endSquare = square;
                setEnd(square);
            } else if (!wallSquares.contains(square)){
                wallSquares.add(square);
                square.setWall();
                setWall(square);
            }
        }
    }

    public void removeSquare(@NotNull GridSquare square) {
        if (square.isSet()) {
            if (startSquare == square) {
                startSquare = null;
            } else if (endSquare == square) {
                endSquare = null;
            } else if (wallSquares.contains(square)) {
                wallSquares.remove(square);
                square.setWall();
            }
            square.setSet();
        }
        setNull(square);
    }

    private void setStart(@NotNull GridSquare square) {
        square.setBackground(Color.GREEN);
    }

    private void setEnd(@NotNull GridSquare square) {
        square.setBackground(Color.MAGENTA);
    }

    private void setWall(@NotNull GridSquare square) {
        square.setBackground(Color.BLACK);
    }

    private void setNull(@NotNull GridSquare square) {
        square.setBackground(primaryColour);
    }

    private JPanel addSidePane() {
        JPanel sidePane = new JPanel();
        sidePane.setLayout(new GridLayout(20, 1));

        startButton = makeStartButton();
        coordinateButton = makeCoordinateButton();
        resetButton = makeResetButton();

        sidePane.add(coordinateButton);
        sidePane.add(startButton);
        sidePane.add(resetButton);

        return sidePane;
    }

    private JButton makeStartButton() {
        JButton startButton = new JButton();

        startButton.setText("Start");

        return startButton;
    }

    private JButton makeCoordinateButton() {
        JButton coordinateButton = new JButton();

        coordinateButton.setText("Show Coordinates");

        coordinateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String x = "";

                if (!roadSet()) {
                    x = setRoadPrompt();
                } else {
                    x = "Start Coordinate : " + startSquare.getCoordinate() + " | End Coordinate : " + endSquare.getCoordinate();
                }

                if (wallSquares.size() > 0) {
                    x += " | Wall Coordinates : ";
                }

                for (int i = 0; i < wallSquares.size(); i++) {
                    x = x + " " + wallSquares.get(i).getCoordinate();
                }

                System.out.println(x);
            }
        });

        return coordinateButton;
    }

    private JButton makeResetButton() {
        JButton resetButton = new JButton();

        resetButton.setText("Reset");

        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                reset();
            }
        });

        return resetButton;
    }

    private void reset() {
        for (GridSquare i: allSquares) {
            removeSquare(i);
        }

        System.out.println(startSquare + " " + endSquare);

        System.out.println("-------------------------------------------------------------------------------");
        System.out.println("Board Reset");
        System.out.println("-------------------------------------------------------------------------------");
        System.out.println();
    }

    public boolean roadSet() {
        return (startSquare != null && endSquare != null);
    }

    public String setRoadPrompt() {
        return "Please select the start and end square";
    }

//    Getters

    public JButton getStartButton() {
        return startButton;
    }

    public JButton getCoordinateButton() {
        return coordinateButton;
    }

    public JButton getResetButton() {
        return resetButton;
    }

    public GridSquare getStartSquare() {
        return startSquare;
    }

    public GridSquare getEndSquare() {
        return endSquare;
    }

    public ArrayList<GridSquare> getWallSquares() {
        return wallSquares;
    }

    public ArrayList<GridSquare> getAllSquares() {
        return allSquares;
    }
}
