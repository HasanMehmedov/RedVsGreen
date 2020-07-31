package entities;

public class Grid {
    private static final int DEFAULT_WIDTH = 5;
    private static final int DEFAULT_HEIGHT = 5;

    private Cell[][] cells;

    public Grid() {
        this(DEFAULT_HEIGHT, DEFAULT_WIDTH);
    }

    public Grid(int height, int width) {
        this.cells = new Cell[height][width];

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {

                this.cells[i][j] = new Cell();
            }
        }
    }

    public int getWidth() {
        if (this.cells.length == 0) {
            return 0;
        }

        return this.cells[0].length;
    }

    public int getHeight() {

        return this.cells.length;
    }

    public Cell getCell(int row, int col) {
        if (row >= 0 && row < this.getHeight()
            && col >= 0 && col < this.getWidth()) {

            return this.cells[row][col];
        }

        throw new IllegalArgumentException("Invalid coordinates!");
    }

    public int getRequiredNeighboursCount(int row, int col, int generation, int requiredValue) {
        int count = 0;

        int startRow = Math.max(row - 1, 0);
        int startCol = Math.max(col - 1, 0);
        int endRow = Math.min(this.getHeight() - 1, row + 1);
        int endCol = Math.min(this.getWidth() - 1, col + 1);

        for (int currentRow = startRow; currentRow <= endRow; currentRow++) {
            for (int currentCol = startCol; currentCol <= endCol; currentCol++) {

                if (currentRow == row && currentCol == col) {
                    continue;
                }

                if (this.cells[currentRow][currentCol].getValues().get(generation) == requiredValue) {
                    count++;
                }
            }
        }

        return count;
    }
}
