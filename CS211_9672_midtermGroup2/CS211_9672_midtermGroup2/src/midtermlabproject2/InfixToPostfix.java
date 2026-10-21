package midtermlabproject2;

import java.util.ArrayList;
import java.util.List;

/**
 * Converts an infix expression to postfix using a LinkedStack of operators and the
 * incoming/in-stack priority (icp/isp) scheme from Module 3.
 *
 * Priorities:  token   icp  isp
 *              operand  0    -
 *              + -      1    2
 *              * /      3    4
 *              ^        6    5   (icp > isp  => right-to-left associativity)
 *              (        7    0
 */
public class InfixToPostfix {

    public static final String[] HEADERS = {"Symbol", "postfixExpression", "operatorStack"};

    private final List<Row> rows = new ArrayList<>();
    private String postfix = "";

    public List<Row> getRows() { return rows; }
    public String getPostfix() { return postfix; }

    public static boolean isOperator(String t) {
        return t.length() == 1 && "+-*/^".contains(t);
    }

    private static int icp(String t) {
        switch (t) {
            case "+": case "-": return 1;
            case "*": case "/": return 3;
            case "^": return 6;
            case "(": return 7;
            default:  return 0;
        }
    }

    private static int isp(String t) {
        switch (t) {
            case "+": case "-": return 2;
            case "*": case "/": return 4;
            case "^": return 5;
            case "(": return 0;
            default:  return 0;
        }
    }

    /** Splits the (space-free) infix string into tokens, rejecting invalid characters. */
    static List<String> tokenize(String infix) throws InvalidExpressionException {
        List<String> tokens = new ArrayList<>();
        StringBuilder operand = new StringBuilder();
        for (int i = 0; i < infix.length(); i++) {
            char c = infix.charAt(i);
            if (Character.isLetterOrDigit(c) && c < 128 || c == '.') {
                operand.append(c);
                continue;
            }
            flushOperand(operand, tokens);
            if (Character.isWhitespace(c))
                throw new InvalidExpressionException("The infix expression must not contain spaces (found a space at position " + (i + 1) + ").");
            if ("+-*/^()".indexOf(c) >= 0)
                tokens.add(String.valueOf(c));
            else
                throw new InvalidExpressionException("Invalid character '" + c + "' at position " + (i + 1) + ".");
        }
        flushOperand(operand, tokens);
        return tokens;
    }

    private static void flushOperand(StringBuilder operand, List<String> tokens) throws InvalidExpressionException {
        if (operand.length() == 0) return;
        String s = operand.toString();
        if (!s.matches("[A-Za-z0-9]+(\\.[0-9]+)?"))
            throw new InvalidExpressionException("Malformed operand '" + s + "'.");
        tokens.add(s);
        operand.setLength(0);
    }

    /** Checks operand/operator order and parenthesis balance. */
    static void validate(List<String> tokens) throws InvalidExpressionException {
        if (tokens.isEmpty())
            throw new InvalidExpressionException("The expression is empty.");
        boolean expectOperand = true;
        int depth = 0;
        for (String t : tokens) {
            if (t.equals("(")) {
                if (!expectOperand)
                    throw new InvalidExpressionException("Missing operator before '('.");
                depth++;
            } else if (t.equals(")")) {
                if (expectOperand)
                    throw new InvalidExpressionException("Missing operand before ')'.");
                if (depth == 0)
                    throw new InvalidExpressionException("Unbalanced parentheses: extra ')'.");
                depth--;
            } else if (isOperator(t)) {
                if (expectOperand)
                    throw new InvalidExpressionException("Operator '" + t + "' is missing an operand.");
                expectOperand = true;
            } else { // operand
                if (!expectOperand)
                    throw new InvalidExpressionException("Missing operator before operand '" + t + "'.");
                expectOperand = false;
            }
        }
        if (expectOperand)
            throw new InvalidExpressionException("The expression ends with an operator; an operand is missing.");
        if (depth != 0)
            throw new InvalidExpressionException("Unbalanced parentheses: missing ')'.");
    }

    /** Converts the infix expression; the trace table is available through getRows(). */
    public String convert(String infix) throws InvalidExpressionException {
        rows.clear();
        postfix = "";

        List<String> tokens = tokenize(infix);
        validate(tokens);

        LinkedStack<String> operatorStack = new LinkedStack<>();
        List<String> out = new ArrayList<>();

        for (String symbol : tokens) {
            if (symbol.equals("(")) {
                operatorStack.push(symbol);
            } else if (symbol.equals(")")) {
                while (!operatorStack.peek().equals("("))
                    out.add(operatorStack.pop());
                operatorStack.pop(); // discard the open parenthesis
            } else if (isOperator(symbol)) {
                while (!operatorStack.isEmpty() && icp(symbol) < isp(operatorStack.peek()))
                    out.add(operatorStack.pop());
                operatorStack.push(symbol);
            } else {
                out.add(symbol);
            }
            rows.add(new Row(symbol, String.join(" ", out), operatorStack.toString(" ")));
        }

        // get all remaining operators from the stack
        while (!operatorStack.isEmpty())
            out.add(operatorStack.pop());
        rows.add(new Row("(end)", String.join(" ", out), operatorStack.toString(" ")));

        postfix = String.join(" ", out);
        return postfix;
    }
}
