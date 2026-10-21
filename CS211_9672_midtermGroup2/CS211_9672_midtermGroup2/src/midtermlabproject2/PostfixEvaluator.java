package midtermlabproject2;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

/** Evaluates a space-separated postfix expression using a LinkedStack of operands (Module 3). */
public class PostfixEvaluator {

    public static final String[] HEADERS = {"symbol", "operand1", "operand2", "value", "operandStack"};

    private final List<Row> rows = new ArrayList<>();

    public List<Row> getRows() { return rows; }

    public static String format(double d) {
        return new BigDecimal(d).setScale(10, RoundingMode.HALF_UP).stripTrailingZeros().toPlainString();
    }

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
