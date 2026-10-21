package midtermlabproject2;

/** Thrown when an infix expression cannot be converted or a postfix expression cannot be evaluated. */
public class InvalidExpressionException extends Exception {
    public InvalidExpressionException(String message) {
        super(message);
    }
}
