package midtermlabproject2;

import java.util.List;

/**
 * Utility class that prints a trace table in the console using
 * {@code +---+} style borders. Column widths adjust to the longest cell.
 *
 * <p>This class cannot be instantiated.</p>
 *
 * @see Row
 */
public final class TablePrinter {

    /** Not instantiable. */
    private TablePrinter() { }

    /**
     * Prints a bordered table to standard output.
     *
     * @param headers the column titles
     * @param rows    the table rows; each row must have at least as many cells
     *                as there are headers
     */
    public static void print(String[] headers, List<Row> rows) {
        int[] w = new int[headers.length];
        for (int i = 0; i < headers.length; i++) w[i] = headers[i].length();
        for (Row r : rows)
            for (int i = 0; i < headers.length; i++)
                w[i] = Math.max(w[i], r.getCells()[i].length());

        String sep = separator(w);
        System.out.println(sep);
        System.out.println(line(headers, w));
        System.out.println(sep);
        for (Row r : rows) System.out.println(line(r.getCells(), w));
        System.out.println(sep);
    }

    /**
     * Builds a horizontal border line such as {@code +-----+-----+}.
     *
     * @param w the width of each column
     * @return the border line
     */
    private static String separator(int[] w) {
        StringBuilder sb = new StringBuilder("+");
        for (int width : w) sb.append("-".repeat(width + 2)).append('+');
        return sb.toString();
    }

    /**
     * Builds one table line with each cell left-aligned and padded to its column width.
     *
     * @param cells the cell values
     * @param w     the width of each column
     * @return the formatted line
     */
    private static String line(String[] cells, int[] w) {
        StringBuilder sb = new StringBuilder("|");
        for (int i = 0; i < w.length; i++)
            sb.append(' ').append(String.format("%-" + w[i] + "s", cells[i])).append(" |");
        return sb.toString();
    }
}
