package midtermlabproject2;

/**
 * Stack abstract data type (Module 3).
 *
 * <p>Elements are added and removed from the top only (last-in, first-out).</p>
 *
 * @param <T> the type of elements held in the stack
 * @see LinkedStack
 */
public interface Stack<T> {

    /**
     * Returns the number of elements currently in the stack.
     *
     * @return the element count
     */
    int size();

    /**
     * Tests whether the stack has no elements.
     *
     * @return {@code true} if the stack is empty, {@code false} otherwise
     */
    boolean isEmpty();

    /**
     * Returns the top element without removing it.
     *
     * @return the element at the top of the stack
     * @throws StackException if the stack is empty
     */
    T top() throws StackException;

    /**
     * Removes and returns the top element.
     *
     * @return the element that was at the top of the stack
     * @throws StackException if the stack is empty (underflow)
     */
    T pop() throws StackException;

    /**
     * Places an element on top of the stack.
     *
     * @param item the element to push
     * @throws StackException if the element cannot be added
     */
    void push(T item) throws StackException;

    /**
     * Returns the top element without removing it. Equivalent to {@link #top()}.
     *
     * @return the element at the top of the stack
     * @throws StackException if the stack is empty
     */
    T peek() throws StackException;

    /** Removes every element from the stack. */
    void clear();

    /**
     * Finds an element and reports how far it is from the top.
     *
     * @param item the element to look for
     * @return the 1-based distance from the top ({@code 1} means the top element),
     *         or {@code -1} if the element is not in the stack
     */
    int search(T item);
}
