package midtermlabproject2;

/**
 * Singly linked node used by {@link LinkedStack} (Module 3).
 *
 * @param <T> the type of data stored in the node
 */
public class Node<T> {
    /** The data stored in this node. */
    private T info;

    /** The next node in the chain, or {@code null} if this is the last node. */
    private Node<T> link;

    /** Creates an empty node with no data and no link. */
    public Node() { }

    /**
     * Creates a node holding the given data and pointing to the given next node.
     *
     * @param info the data to store
     * @param link the next node, or {@code null} if this is the last node
     */
    public Node(T info, Node<T> link) {
        this.info = info;
        this.link = link;
    }

    /**
     * Sets the data stored in this node.
     *
     * @param info the new data
     */
    public void setInfo(T info) { this.info = info; }

    /**
     * Sets the node this node points to.
     *
     * @param link the next node, or {@code null} for none
     */
    public void setLink(Node<T> link) { this.link = link; }

    /**
     * Returns the data stored in this node.
     *
     * @return the stored data
     */
    public T getInfo() { return info; }

    /**
     * Returns the node this node points to.
     *
     * @return the next node, or {@code null} if there is none
     */
    public Node<T> getLink() { return link; }
}
