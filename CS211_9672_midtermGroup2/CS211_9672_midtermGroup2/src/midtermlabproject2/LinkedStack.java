package midtermlabproject2;

/** Linked-list implementation of the Stack (Module 3), with numElements kept in sync. */
public class LinkedStack<T> implements Stack<T> {
    private Node<T> top;
    private int numElements = 0;

    @Override
    public int size() {
        return numElements;
    }

    @Override
    public boolean isEmpty() {
        return top == null;
    }

    @Override
    public T top() throws StackException {
        if (isEmpty())
            throw new StackException("Stack is empty.");
        return top.getInfo();
    }

    @Override
    public T pop() throws StackException {
        if (isEmpty())
            throw new StackException("Stack underflow.");
        Node<T> temp = top;
        top = top.getLink();
        numElements--;
        return temp.getInfo();
    }

    @Override
    public void push(T item) {
        Node<T> newNode = new Node<>();
        newNode.setInfo(item);
        newNode.setLink(top);
        top = newNode;
        numElements++;
    }

    @Override
    public T peek() throws StackException {
        return top();
    }

    @Override
    public void clear() {
        top = null;
        numElements = 0;
    }

    @Override
    public int search(T item) {
        int distance = 1;
        for (Node<T> cur = top; cur != null; cur = cur.getLink(), distance++) {
            if (cur.getInfo() == null ? item == null : cur.getInfo().equals(item))
                return distance;
        }
        return -1;
    }

    /** Contents from bottom to top, joined by the given separator. */
    public String toString(String separator) {
        return toString(separator, String::valueOf);
    }

    /** Contents from bottom to top, each element rendered by the formatter. */
    public String toString(String separator, java.util.function.Function<T, String> formatter) {
        StringBuilder sb = new StringBuilder();
        buildBottomToTop(top, sb, separator, formatter);
        return sb.toString();
    }

    // recursion reaches the bottom first, so elements are appended bottom -> top
    private void buildBottomToTop(Node<T> node, StringBuilder sb, String separator,
                                  java.util.function.Function<T, String> formatter) {
        if (node == null) return;
        buildBottomToTop(node.getLink(), sb, separator, formatter);
        if (sb.length() > 0) sb.append(separator);
        sb.append(formatter.apply(node.getInfo()));
    }

    @Override
    public String toString() {
        return toString(",");
    }
}
