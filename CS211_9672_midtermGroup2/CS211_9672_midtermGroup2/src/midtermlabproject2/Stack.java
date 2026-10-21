package midtermlabproject2;

/** Stack interface from Module 3. */
public interface Stack<T> {
    int size();
    boolean isEmpty();
    T top() throws StackException;
    T pop() throws StackException;
    void push(T item) throws StackException;
    T peek() throws StackException;
    void clear();
    int search(T item); // 1-based distance from the top; -1 if not found
}
