package midtermlabproject2;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

/**
 * Evaluates a space-separated postfix expression using a {@link LinkedStack} of
 * operands (Module 3).
 *
 * <p>Operands must be numbers (optionally negative or decimal). Supported operators
 * are {@code + - * / ^}, where {@code ^} is exponentiation. Each step is recorded
 * as a {@link Row} so the trace table can be printed with {@link TablePrinter}.</p>
 *
 * <p>Example: {@code 6 2 3 + - 3 8 2 / + * 2 ^ 3 +} evaluates to {@code 52}.</p>
 *
 * @see InfixToPostfix
 */
public class PostfixEvaluator {

    /** Creates an evaluator with an empty trace table. */
    public PostfixEvaluator() { }

    /** Column titles of the evaluation trace table. */
    public static final String[] HEADERS = {"symbol", "operand1", "operand2", "value", "operandStack"};

    /** Trace rows recorded by the most recent call to {@link #evaluate(String)}. */
    private final List<Row> rows = new ArrayList<>();

    /**
     * Returns the trace table rows from the most recent evaluation.
     *
     * @return the rows, one per token processed (empty before the first evaluation)
     */
    public List<Row> getRows() { return rows; }

    /**
     * Formats a number for display: rounded to 10 decimal places (half-up),
     * with trailing zeros removed and never in scientific notation.
     *
     * @param d the number to format
     * @return the plain-text representation of the number
     */
    public static String format(double d) {
        return new BigDecimal(d).setScale(10, RoundingMode.HALF_UP).stripTrailingZeros().toPlainString();
    }

    /**
     * Evaluates a postfix expression. The trace table is available afterwards
     * through {@link #getRows()}; any rows from a previous call are discarded.
     *
     * @param postfix the postfix expression, with tokens separated by spaces
     * @return the numeric value of the expression
     * @throws InvalidExpressionException if the expression is empty, contains a token
     *         that is neither a number nor an operator, has an operator without two
     *         operands, leaves more than one value on the stack, divides by zero, or
     *         produces a result that is not a valid number
     */
    public double evaluate(String postfix) throws InvalidExpressionException {
        rows.clear();

        String trimmed = postfix.trim();
        if (trimmed.isEmpty())
            throw new InvalidExpressionException("The expression is empty.");
        String[] tokens = trimmed.split("\\s+");

        LinkedStack<Double> operandStack = new LinkedStack<>();

        for (String symbol : tokens) {
            if (symbol.matches("-?\\d+(\\.\\d+)?")) {
                operandStack.push(Double.parseDouble(symbol));
                rows.add(new Row(symbol, "", "", "", operandStack.toString(",", PostfixEvaluator::format)));
            } else if (InfixToPostfix.isOperator(symbol)) {
                if (operandStack.size() < 2)
                    throw new InvalidExpressionException("Operator '" + symbol + "' needs two operands, but the stack has "
                            + operandStack.size() + ".");
                double operand2 = operandStack.pop();
                double operand1 = operandStack.pop();
                double value = apply(symbol.charAt(0), operand1, operand2);
                operandStack.push(value);
                rows.add(new Row(symbol, format(operand1), format(operand2), format(value), operandStack.toString(",", PostfixEvaluator::format)));
            } else {
                throw new InvalidExpressionException("Invalid token '" + symbol
                        + "': only numbers and the operators + - * / ^ are allowed.");
            }
        }

        if (operandStack.size() != 1)
            throw new InvalidExpressionException("Too many operands: " + operandStack.size()
                    + " values are left on the stack (an operator is missing).");
        return operandStack.pop();
    }

    /**
     * Applies a binary operator to two operands.
     *
     * @param op the operator: one of {@code + - * / ^}
     * @param a  the left operand (operand1)
     * @param b  the right operand (operand2)
     * @return the result of {@code a op b}
     * @throws InvalidExpressionException if dividing by zero, if the operator is unknown,
     *         or if the result is NaN or infinite
     */
    private double apply(char op, double a, double b) throws InvalidExpressionException {
        double result;
        switch (op) {
            case '+': result = a + b; break;
            case '-': result = a - b; break;
            case '*': result = a * b; break;
            case '/':
                if (b == 0) throw new InvalidExpressionException("Division by zero (" + format(a) + " / 0).");
                result = a / b;
                break;
            case '^': result = Math.pow(a, b); break;
            default: throw new InvalidExpressionException("Unknown operator '" + op + "'.");
        }
        if (Double.isNaN(result) || Double.isInfinite(result))
            throw new InvalidExpressionException("The operation " + format(a) + " " + op + " " + format(b)
                    + " has no valid numeric result.");
        return result;
    }
}
