import entities.Cell;
import entities.Grid;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

import static utils.Util.*;

public class Demo {
    private final static BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

    public static void main(String[] args) throws IOException {

        Grid grid = createGrid();
        setGridInitialState(grid);

        int cellHeight;
        int cellWidth;
        int generationsNumber;

        System.out.print("Enter coordinates and Generation number: ");

        // Input for coordinates of the result cell and the Generations number
        while (true) {
            String[] arguments = bufferedReader.readLine().split(SEPARATOR_REGEX);

            if (validCoordinates(arguments, grid)) {

                cellHeight = Integer.parseInt(arguments[1]);
                cellWidth = Integer.parseInt(arguments[0]);
                generationsNumber = Integer.parseInt(arguments[2]);
                break;
            } else {
                System.out.println("Invalid coordinates or Generation number!\n\r");
            }
        }

        calculateGridCellsValues(grid, generationsNumber);

        printCellGivenValuesCount(grid, cellHeight, cellWidth, GREEN_CELL);
    }

    private static Grid createGrid() throws IOException {
        int[] gridDimensions;

        while (true) {
            System.out.print("Grid dimensions: ");
            String[] dimensionsInputString = bufferedReader.readLine().split(SEPARATOR_REGEX);

            // If the input contains non-digit characters, the dimensions are not bigger than 0
            // or the arguments count is not equal to the required count the program continues to demand an input
            if (Arrays.stream(dimensionsInputString).allMatch(dimension -> dimension.matches(DIGIT_REGEX)
                    && Integer.parseInt(dimension) > 0)
                    && dimensionsInputString.length == COUNT_OF_GRID_DIMENSIONS) {

                gridDimensions = Arrays.stream(dimensionsInputString).mapToInt(Integer::parseInt).toArray();
                break;
            } else {
                System.out.println("Invalid input!\n\r");
            }
        }

        int gridWidth = gridDimensions[0];
        int gridHeight = gridDimensions[1];

        return new Grid(gridHeight, gridWidth);
    }

    private static void setGridInitialState(Grid grid) throws IOException {

        System.out.println("Enter grid's values:");

        for (int row = 0; row < grid.getHeight(); row++) {
            String rowInput;

            // Input for a row of the grid
            while (true) {
                rowInput = bufferedReader.readLine();

                if (rowIsValid(grid, rowInput)) {
                    break;
                } else {
                    System.out.println("Invalid row!");
                }
            }

            // Split and parse the row elements string to integers
            int[] rowValues = Arrays.stream(rowInput.split("")).mapToInt(Integer::parseInt).toArray();

            // Initialize each cell with value
            for (int col = 0; col < rowValues.length; col++) {
                int cellValue = rowValues[col];

                grid.getCell(row, col).addValue(cellValue);
            }
        }
    }

    // Check whether the row input contains 0s and/or 1s and whether it's length is equal to the grid's width
    private static boolean rowIsValid(Grid grid, String rowInput) {

        return rowInput.matches(String.format(GRID_ROW_REGEX, grid.getWidth()));
    }

    private static void calculateGridCellsValues(Grid grid, int generationsNumber) throws IOException {

        for (int generation = 0; generation < generationsNumber; generation++) {
            for (int row = 0; row < grid.getHeight(); row++) {
                for (int col = 0; col < grid.getWidth(); col++) {
                    applyRules(grid, row, col, generation);
                }
            }
        }
    }

    private static void applyRules(Grid grid, int row, int col, int generation) {

        Cell currentCell = grid.getCell(row, col);
        int currentCellLatestValue = currentCell.getValues().get(currentCell.getValues().size() - 1);
        int greenNeighboursCount = grid.getRequiredNeighboursCount(row, col, generation, GREEN_CELL);

        if (currentCellLatestValue == RED_CELL
                && Arrays.stream(REQUIRED_GREEN_CELLS_COUNTS_SURROUNDING_RED_CELL_TO_TURN_INTO_GREEN)
                .anyMatch(count -> count == greenNeighboursCount)) {

            currentCell.addValue(GREEN_CELL);

        } else if (currentCellLatestValue == GREEN_CELL
                && Arrays.stream(REQUIRED_GREEN_CELLS_COUNTS_SURROUNDING_GREEN_CELL_TO_TURN_INTO_RED)
                .anyMatch(count -> count == greenNeighboursCount)) {

            currentCell.addValue(RED_CELL);

        } else {
            currentCell.addValue(currentCellLatestValue);
        }
    }

    private static boolean validCoordinates(String[] arguments, Grid grid) {
        return Arrays.stream(arguments).allMatch(argument -> argument.matches(DIGIT_REGEX))
                && (arguments.length == COORDINATES_AND_GENERATIONS_NUMBER_PARAMETERS_COUNT)
                && (Integer.parseInt(arguments[0]) < grid.getWidth())
                && (Integer.parseInt(arguments[1]) < grid.getHeight()
                && (Integer.parseInt(arguments[2]) > 0)); // Positive generations number
    }

    private static void printCellGivenValuesCount(Grid grid, int cellHeight, int cellWidth, int givenValue) {

        long cellGivenValuesCount = grid.getCell(cellHeight, cellWidth).getValues()
                .stream().filter(value -> value == givenValue).count();

        System.out.println(cellGivenValuesCount);
    }
}
