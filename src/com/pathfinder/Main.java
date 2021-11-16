package com.pathfinder;

import javafx.util.Pair;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

//import java.awt.desktop.SystemSleepEvent;

public class Main {

    private static boolean debugStop = false;

    private static ArrayList<GridSquare> allSquares = new ArrayList<>();

    private static GridSquare startSquare;
    private static GridSquare endSquare;
    private static ArrayList<GridSquare> wallSquares;

    public static void main(String[] args) {
        // Variables Used
        final int[] size = new int[1];
        int windowHeight = 600;
        int windowWidth = 600;

        JFrame inputWindow = new JFrame("Enter Grid Size");

        JTextField sizeInput = new JTextField();
        sizeInput.setBounds(10, 10, 150,20);

        JButton submitButton = new JButton("Submit");
        submitButton.setBounds(180, 10, 100, 20);
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String sizeText = sizeInput.getText();
                size[0] = Integer.parseInt(sizeText);

                openWindow(size[0], windowHeight, windowWidth);

                inputWindow.setVisible(false);
                inputWindow.dispose();
            }
        });

        inputWindow.add(sizeInput);
        inputWindow.add(submitButton);

        inputWindow.setSize(300,80);
        inputWindow.setLayout(null);
        inputWindow.show();
    }

    private static void openWindow(int size, int windowHeight, int windowWidth) {
        Window frame = new Window(size, windowHeight, windowWidth);

        addStartAction(frame);

        frame.show();
    }

    private static void start(Window w) {
        boolean programEnd = false;

        startSquare = w.getStartSquare();
        endSquare = w.getEndSquare();
        wallSquares = w.getWallSquares();

        allSquares = w.getAllSquares();

//        GridSquare current = startSquare;

        ArrayList<ArrayList<GridSquare>> paths = new ArrayList<>();
        ArrayList<GridSquare> startPath = new ArrayList<>();
        startPath.add(startSquare);
        paths.add(startPath);

        int turns = 0;

        while (!programEnd && !debugStop) {
            ArrayList<GridSquare> currentPath = paths.get(0);

            addNextSteps(currentPath, paths);
            paths.remove(0);
            sortPaths(paths);

//            allPathsText(paths);

            turns++;

            int cap = 100;
            if (turns > cap) {
                debugStop = true;
                System.out.println("The process has been stopped");
            }

            if (getLastSquare(paths.get(0)) == endSquare) {
                programEnd = true;
            }
        }

        if (programEnd) {
            ArrayList<GridSquare> x = paths.get(0);

            for (GridSquare po: x) {
                po.setBackground(Color.RED);
            }
        }
    }

    private static void addNextSteps(ArrayList<GridSquare> currentPath, ArrayList<ArrayList<GridSquare>> paths) {
        GridSquare current = getLastSquare(currentPath);

        addStep(paths, moveTopLeft(current));

        addStep(paths, moveUp(current));

        addStep(paths, moveTopRight(current));

        addStep(paths, moveRight(current));

        addStep(paths, moveBottomRight(current));

        addStep(paths, moveDown(current));

        addStep(paths, moveBottomLeft(current));

        addStep(paths, moveLeft(current));

//        allPathsText(paths);?
    }

    private static void addStep(ArrayList<ArrayList<GridSquare>> paths, GridSquare next) {
        ArrayList<GridSquare> currentPath = paths.get(0);
        ArrayList<GridSquare> newPath = new ArrayList<>();

        if (next != null && !(currentPath.contains(next))) {
            for (int i = 0; i < currentPath.size(); i++) {
                newPath.add(currentPath.get(i));
            }

            newPath.add(next);
            paths.add(newPath);

//            pathText(newPath);
        } else {
//            if (next != null) {
//                System.out.println("Excluded : " +  next.getCoordinate());
//            }
        }
    }

    private static double straightLineDistance(GridSquare current, GridSquare destination) {
        int xDistance = destination.getXCoordinate() - current.getXCoordinate();
        int yDistance = destination.getYCoordinate() - current.getYCoordinate();

        return Math.sqrt(Math.pow(xDistance, 2) + Math.pow(yDistance, 2));
    }

    private static void allPathsText(ArrayList<ArrayList<GridSquare>> paths) {
        System.out.println("Paths : ");
        for (int i = 0; i < paths.size(); i ++) {
            System.out.print((i + 1) + " : " );
            pathText(paths.get(i));
        }
    }

    private static void pathText(ArrayList<GridSquare> currentPath) {
        String x = "Path Added : ";
        for (int i = 0; i < currentPath.size(); i++) {
            x += currentPath.get(i).getCoordinate() + " ";
        }
        System.out.println(x);
    }

    private static GridSquare move(GridSquare current, int xDistance, int yDistance) {
        if (current == null) {
            return null;
        }

        Pair<Integer, Integer> coordinate = current.getCoordinate();

        Pair<Integer, Integer> newCoordinate = new Pair<>(coordinate.getKey() + xDistance, coordinate.getValue() + yDistance);

        GridSquare proposedSquare = getSquare(newCoordinate);

        if (proposedSquare != null && !(wallSquares.contains(proposedSquare))) {
            return proposedSquare;
        } else {
//            if (proposedSquare != null) {
//                System.out.println("Proposed Excluded : " + proposedSquare.getCoordinate());
//            }
            return null;
        }

//        return current;
    }

    private static GridSquare moveLeft(GridSquare current) {
        return move(current, -1, 0);
    }

    private static GridSquare moveRight(GridSquare current) {
        return move(current, 1, 0);
    }

    private static GridSquare moveUp(GridSquare current) {
        return move(current, 0, -1);
    }

    private static GridSquare moveDown(GridSquare current) {
        return move(current, 0, 1);
    }

    private static GridSquare moveTopLeft(GridSquare current) {
        return move(current, -1, -1);
    }

    private static GridSquare moveTopRight(GridSquare current) {
        return move(current, 1,-1);
    }

    private static GridSquare moveBottomLeft(GridSquare current) {
        return move(current, -1,1);
    }

    private static GridSquare moveBottomRight(GridSquare current) {
        return move(current, 1,1);
    }

    private static GridSquare getSquare(Pair<Integer, Integer> coordinate) {
        for (GridSquare i: allSquares) {
            if (coordinateEquals(i.getCoordinate(), coordinate)) {
                return i;
            }
        }

        return null;
    }

    private static boolean coordinateEquals(Pair<Integer, Integer> a, Pair<Integer, Integer> b) {
        return a.getValue() == b.getValue() && a.getKey() == b.getKey();
    }

    private static ArrayList<ArrayList<GridSquare>> sortPaths(ArrayList<ArrayList<GridSquare>> paths) {
        Collections.sort(paths, pathComparator);
        return paths;
    }

    public static Comparator<ArrayList<GridSquare>> pathComparator = new Comparator<ArrayList<GridSquare>>() {
        @Override
        public int compare(ArrayList<GridSquare> a, ArrayList<GridSquare> b) {
            return (Double.compare(straightLineDistance(getLastSquare(a), endSquare), straightLineDistance(getLastSquare(b), endSquare)));
        }
    };

    private static GridSquare getLastSquare(ArrayList<GridSquare> path) {
        return path.get(path.size() - 1);
    }

//    Adding Action Listeners

    private static void addStartAction(Window w) {
        w.getStartButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (!w.roadSet()) {
                    System.out.println(w.setRoadPrompt());
                } else {
//                    start1(w);
                    start(w);
                }
            }
        });
    }

//    Old Codes

    private static void start1(Window w) {
        GridSquare startSquare = w.getStartSquare();
        GridSquare endSquare = w.getEndSquare();
        ArrayList<GridSquare> wallSquares = w.getWallSquares();

        allSquares = w.getAllSquares();

        GridSquare current = startSquare;

        int turns = 0;

        while (current != endSquare && !debugStop) { // will end up in a non ending state, do change this.
            current.setVisited();

            int xDistance = endSquare.getCoordinate().getKey() - current.getCoordinate().getKey();
            int yDistance = endSquare.getCoordinate().getValue() - current.getCoordinate().getValue();

            if (xDistance < 0) {
                System.out.println("Direction : left");
                current = moveLeft(current);
            } else if (xDistance > 0) {
                System.out.println("Direction : right");
                current = moveRight(current);
            }

            if (yDistance < 0) {
                System.out.println("Direction : up");
                current = moveUp(current);
            } else if (yDistance > 0) {
                System.out.println("Direction : down");
                current = moveDown(current);
            }

            if (current == null) {
                debugStop = true;
            }

            System.out.println("Current : " + current.getCoordinate());

            turns++;
            System.out.println();

            int cap = 10;
            if (turns > cap) {
                debugStop = true;
            }
        }

        if (!debugStop) {
            current.setVisited();
            System.out.println("Success");
        }
    }
}