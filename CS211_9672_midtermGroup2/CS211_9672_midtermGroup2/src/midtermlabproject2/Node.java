package midtermlabproject2;

/** Singly linked node used by LinkedStack (from Module 3). */
public class Node<T> {
    private T info;
    private Node<T> link;

    public Node() { }

    public Node(T info, Node<T> link) {
        this.info = info;
        this.link = link;
    }

    public void setInfo(T info) { this.info = info; }
    public void setLink(Node<T> link) { this.link = link; }
    public T getInfo() { return info; }
    public Node<T> getLink() { return link; }
}
