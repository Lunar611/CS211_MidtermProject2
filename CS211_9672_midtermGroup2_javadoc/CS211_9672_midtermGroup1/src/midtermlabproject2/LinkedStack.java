package midtermlabproject2;

/**
 * Linked-list implementation of {@link Stack} (Module 3).
 *
 * <p>Elements are kept in a chain of {@link Node}s with {@code top} pointing at the
 * most recently pushed one. The element count is kept in sync on every push, pop
 * and clear, so {@link #size()} runs in constant time. No {@code java.util} stack
 * classes are used.</p>
 *
 * @param <T> the type of elements held in the stack
 */
public class LinkedStack<T> implements Stack<T> {
    /** Creates an empty stack. */
    public LinkedStack() { }

    /** The node at the top of the stack, or {@code null} when the stack is empty. */
    private Node<T> top;

    /** Number of elements currently in the stack. */
    private int numElements = 0;

    /** {@inheritDoc} */
    @Override
    public int size() {
        return numElements;
    }

    /** {@inheritDoc} */
    @Override
    public boolean isEmpty() {
        return top == null;
    }

    /**
     * {@inheritDoc}
     *
     * @throws StackException if the stack is empty ("Stack is empty.")
     */
    @Override
    public T top() throws StackException {
        if (isEmpty())
            throw new StackException("Stack is empty.");
        return top.getInfo();
    }

    /**
     * {@inheritDoc}
     *
     * @throws StackException if the stack is empty ("Stack underflow.")
     */
    @Override
    public T pop() throws StackException {
        if (isEmpty())
            throw new StackException("Stack underflow.");
        Node<T> temp = top;
        top = top.getLink();
        numElements--;
        return temp.getInfo();
    }

    /**
     * {@inheritDoc}
     *
     * <p>This implementation never throws, since a linked stack has no fixed capacity.</p>
     */
    @Override
    public void push(T item) {
        Node<T> newNode = new Node<>();
        newNode.setInfo(item);
        newNode.setLink(top);
        top = newNode;
        numElements++;
    }

    /**
     * {@inheritDoc}
     *
     * @throws StackException if the stack is empty
     */
    @Override
    public T peek() throws StackException {
        return top();
    }

    /** {@inheritDoc} */
    @Override
    public void clear() {
        top = null;
        numElements = 0;
    }

    /**
     * {@inheritDoc}
     *
     * <p>Elements are compared with {@code equals}; {@code null} items are supported.</p>
     */
    @Override
    public int search(T item) {
        int distance = 1;
        for (Node<T> cur = top; cur != null; cur = cur.getLink(), distance++) {
            if (cur.getInfo() == null ? item == null : cur.getInfo().equals(item))
                return distance;
        }
        return -1;
    }

    /**
     * Returns the contents from bottom to top, joined by the given separator.
     *
     * @param separator the text placed between elements
     * @return the joined contents, or an empty string if the stack is empty
     */
    public String toString(String separator) {
        return toString(separator, String::valueOf);
    }

    /**
     * Returns the contents from bottom to top, with each element rendered by the
     * given formatter and joined by the given separator.
     *
     * @param separator the text placed between elements
     * @param formatter converts an element to the text shown for it
     * @return the joined contents, or an empty string if the stack is empty
     */
    public String toString(String separator, java.util.function.Function<T, String> formatter) {
        StringBuilder sb = new StringBuilder();
        buildBottomToTop(top, sb, separator, formatter);
        return sb.toString();
    }

    /**
     * Recursively appends elements bottom to top. The recursion reaches the bottom
     * node first, so elements are appended in bottom-to-top order.
     *
     * @param node      the node to process (the recursion ends at {@code null})
     * @param sb        the buffer being built
     * @param separator the text placed between elements
     * @param formatter converts an element to text
     */
    private void buildBottomToTop(Node<T> node, StringBuilder sb, String separator,
                                  java.util.function.Function<T, String> formatter) {
        if (node == null) return;
        buildBottomToTop(node.getLink(), sb, separator, formatter);
        if (sb.length() > 0) sb.append(separator);
        sb.append(formatter.apply(node.getInfo()));
    }

    /**
     * Returns the contents from bottom to top, separated by commas.
     *
     * @return the comma-separated contents
     * @see #toString(String)
     */
    @Override
    public String toString() {
        return toString(",");
    }
}
