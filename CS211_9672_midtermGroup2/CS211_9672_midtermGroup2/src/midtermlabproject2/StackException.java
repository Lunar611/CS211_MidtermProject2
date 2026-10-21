package midtermlabproject2;

/** Thrown when a stack operation is invalid (e.g., pop on an empty stack). */
public class StackException extends RuntimeException {
    public StackException(String err) {
        super(err);
    }
}
