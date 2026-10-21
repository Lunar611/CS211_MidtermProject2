package midtermlabproject2;

/**
 * One row of a trace table, holding one string per column.
 *
 * @see TablePrinter
 */
public class Row {
    /** The cell values of this row, in column order. */
    private final String[] cells;

    /**
     * Creates a row from the given cell values, in column order.
     *
     * @param cells the text of each cell
     */
    public Row(String... cells) {
        this.cells = cells;
    }

    /**
     * Returns the cell values of this row.
     *
     * @return the cells, in column order
     */
    public String[] getCells() {
        return cells;
    }
}
