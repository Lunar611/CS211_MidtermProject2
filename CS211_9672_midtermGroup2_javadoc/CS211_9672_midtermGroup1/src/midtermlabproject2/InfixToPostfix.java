package midtermlabproject2;

import java.util.ArrayList;
import java.util.List;

/**
 * Converts an infix expression to postfix using a {@link LinkedStack} of operators
 * and the incoming/in-stack priority (icp/isp) scheme from Module 3.
 *
 * <p>Priorities:</p>
 * <pre>
 * token     icp   isp
 * operand    0     -
 * + -        1     2
 * * /        3     4
 * ^          6     5    (icp &gt; isp =&gt; right-to-left associativity)
 * (          7     0
 * </pre>
 *
 * <p>Infix input must contain no spaces. Operands are letters or numbers
 * (for example {@code A}, {@code 12}, {@code 3.5}); operators are
 * {@code + - * / ^} and parentheses. Each step of a conversion is recorded as a
 * {@link Row} so the trace table can be printed with {@link TablePrinter}.</p>
 *
 * <p>Example: {@code ((A-(B+C))*D)^(E+F)} becomes {@code A B C + - D * E F + ^}.</p>
 *
 * @see PostfixEvaluator
 */
public class InfixToPostfix {

    /** Creates a converter with an empty trace table. */
    public InfixToPostfix() { }

    /** Column titles of the conversion trace table. */
    public static final String[] HEADERS = {"Symbol", "postfixExpression", "operatorStack"};

    /** Trace rows recorded by the most recent call to {@link #convert(String)}. */
    private final List<Row> rows = new ArrayList<>();

    /** Result of the most recent call to {@link #convert(String)}. */
    private String postfix = "";

    /**
     * Returns the trace table rows from the most recent conversion.
     *
     * @return the rows, one per token plus a final {@code (end)} row (empty before
     *         the first conversion)
     */
    public List<Row> getRows() { return rows; }

    /**
     * Returns the postfix expression produced by the most recent conversion.
     *
     * @return the postfix expression, or an empty string if nothing has been converted
     */
    public String getPostfix() { return postfix; }

    /**
     * Tests whether a token is one of the supported operators {@code + - * / ^}.
     *
     * @param t the token to test
     * @return {@code true} if the token is a single operator character
     */
    public static boolean isOperator(String t) {
        return t.length() == 1 && "+-*/^".contains(t);
    }

    /**
     * Returns the incoming priority (icp) of a token, used when the token is
     * about to be pushed.
     *
     * @param t the operator or parenthesis token
     * @return the incoming priority ({@code 0} for operands and unknown tokens)
     */
    private static int icp(String t) {
        switch (t) {
            case "+": case "-": return 1;
            case "*": case "/": return 3;
            case "^": return 6;
            case "(": return 7;
            default:  return 0;
        }
    }

    /**
     * Returns the in-stack priority (isp) of a token, used when the token is
     * already on the operator stack.
     *
     * @param t the operator or parenthesis token
     * @return the in-stack priority ({@code 0} for {@code (} and unknown tokens)
     */
    private static int isp(String t) {
        switch (t) {
            case "+": case "-": return 2;
            case "*": case "/": return 4;
            case "^": return 5;
            case "(": return 0;
            default:  return 0;
        }
    }

    /**
     * Splits the space-free infix string into tokens (operands, operators and
     * parentheses), rejecting invalid characters.
     *
     * @param infix the infix expression
     * @return the tokens, in order
     * @throws InvalidExpressionException if the expression contains a space, an
     *         invalid character, or a malformed operand
     */
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

    /**
     * Moves any operand collected so far into the token list and resets the buffer.
     * Does nothing if the buffer is empty.
     *
     * @param operand the buffer holding the operand being read
     * @param tokens  the token list to append to
     * @throws InvalidExpressionException if the operand is malformed (for example {@code 1.})
     */
    private static void flushOperand(StringBuilder operand, List<String> tokens) throws InvalidExpressionException {
        if (operand.length() == 0) return;
        String s = operand.toString();
        if (!s.matches("[A-Za-z0-9]+(\\.[0-9]+)?"))
            throw new InvalidExpressionException("Malformed operand '" + s + "'.");
        tokens.add(s);
        operand.setLength(0);
    }

    /**
     * Checks that operands and operators alternate correctly and that parentheses
     * are balanced.
     *
     * @param tokens the tokens produced by {@link #tokenize(String)}
     * @throws InvalidExpressionException if the expression is empty, has a missing
     *         operand or operator, ends with an operator, or has unbalanced parentheses
     */
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

    /**
     * Converts an infix expression to postfix. The trace table is available
     * afterwards through {@link #getRows()}; any state from a previous call is discarded.
     *
     * @param infix the infix expression, with no spaces
     * @return the postfix expression, with tokens separated by single spaces
     * @throws InvalidExpressionException if the expression is invalid (see
     *         {@link #tokenize(String)} and {@link #validate(List)})
     */
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
