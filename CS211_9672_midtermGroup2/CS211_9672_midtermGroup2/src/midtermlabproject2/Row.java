package midtermlabproject2;

/** One row of a trace table. */
public class Row {
    private final String[] cells;

    public Row(String... cells) {
        this.cells = cells;
    }

    public String[] getCells() {
        return cells;
    }
}
