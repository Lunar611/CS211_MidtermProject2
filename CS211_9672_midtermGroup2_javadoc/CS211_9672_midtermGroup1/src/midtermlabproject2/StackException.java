package midtermlabproject2;

/**
 * Thrown when a stack operation is invalid, for example popping or peeking
 * an empty stack.
 *
 * @see Stack
 * @see LinkedStack
 */
public class StackException extends RuntimeException {

    /**
     * Creates a new exception with the given message.
     *
     * @param err a description of the error
     */
    public StackException(String err) {
        super(err);
    }
}
