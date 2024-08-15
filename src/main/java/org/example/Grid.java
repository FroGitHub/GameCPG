package org.example;

import java.util.ArrayList;
import java.util.List;

class Grid {
    private int cellSize;
    private int rows, cols;
    private List<Object>[][] cells;

    public Grid(int worldWidth, int worldHeight, int cellSize) {
        this.cellSize = cellSize;
        this.rows = (int) Math.ceil((double) worldHeight / cellSize);
        this.cols = (int) Math.ceil((double) worldWidth / cellSize);
        cells = new ArrayList[rows][cols];

        // Ініціалізація списків у комірках
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                cells[i][j] = new ArrayList<>();
            }
        }
    }

    private int getCellIndex(float coord) {
        return (int) Math.floor(coord / cellSize);
    }

    public void addSquare(Object object) {
        int row = getCellIndex(object.getY());
        int col = getCellIndex(object.getX());

        if (row >= 0 && row < rows && col >= 0 && col < cols) {
            cells[row][col].add(object);
        }
    }

    public List<Object> getNearbySquares(Person person) {
        int row = getCellIndex(person.getY());
        int col = getCellIndex(person.getX());

        List<Object> nearbySquares = new ArrayList<>();

        for (int i = Math.max(0, row - 1); i <= Math.min(rows - 1, row + 1); i++) {
            for (int j = Math.max(0, col - 1); j <= Math.min(cols - 1, col + 1); j++) {
                nearbySquares.addAll(cells[i][j]);
            }
        }

        return nearbySquares;
    }
}
