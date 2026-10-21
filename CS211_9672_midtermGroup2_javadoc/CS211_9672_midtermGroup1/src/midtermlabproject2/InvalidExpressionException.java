package midtermlabproject2;

/**
 * Thrown when an infix expression cannot be converted or a postfix expression
 * cannot be evaluated. {@link Main} reports its message to the user.
 *
 * @see InfixToPostfix#convert(String)
 * @see PostfixEvaluator#evaluate(String)
 */
public class InvalidExpressionException extends Exception {

    /**
     * Creates a new exception with the given message.
     *
     * @param message a user-readable description of what is wrong with the expression
     */
    public InvalidExpressionException(String message) {
        super(message);
    }
}
