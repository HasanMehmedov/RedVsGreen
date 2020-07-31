package entities;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Cell {
    private List<Integer> values;

    public Cell() {
        this.values = new ArrayList<>();
    }

    public void addValue(int cellValue) {
        this.values.add(cellValue);
    }

    public List<Integer> getValues(){
        return Collections.unmodifiableList(this.values);
    }
}
