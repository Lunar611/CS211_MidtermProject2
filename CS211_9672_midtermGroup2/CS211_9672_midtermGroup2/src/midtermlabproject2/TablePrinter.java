package midtermlabproject2;

import java.util.List;

/** Prints a trace table in the console. */
public final class TablePrinter {
    private TablePrinter() { }

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

    private static String separator(int[] w) {
        StringBuilder sb = new StringBuilder("+");
        for (int width : w) sb.append("-".repeat(width + 2)).append('+');
        return sb.toString();
    }

    private static String line(String[] cells, int[] w) {
        StringBuilder sb = new StringBuilder("|");
        for (int i = 0; i < w.length; i++)
            sb.append(' ').append(String.format("%-" + w[i] + "s", cells[i])).append(" |");
        return sb.toString();
    }
}
