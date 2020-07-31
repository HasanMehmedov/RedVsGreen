package utils;

public final class Util {

    private Util() {
    }

    public static final int RED_CELL = 0;
    public static final int GREEN_CELL = 1;
    public static final String SEPARATOR_REGEX = "\\s*,\\s*";
    public static final String DIGIT_REGEX = "^\\d+$";
    public static final int COUNT_OF_GRID_DIMENSIONS = 2;
    public static final int COORDINATES_AND_GENERATIONS_NUMBER_PARAMETERS_COUNT = 3;
    public static final String GRID_ROW_REGEX = "^[0|1]{%d}$";
    public static final int[] REQUIRED_GREEN_CELLS_COUNTS_SURROUNDING_RED_CELL_TO_TURN_INTO_GREEN = {3, 6};
    public static final int[] REQUIRED_GREEN_CELLS_COUNTS_SURROUNDING_GREEN_CELL_TO_TURN_INTO_RED = {0, 1, 4, 5, 7, 8};
}
