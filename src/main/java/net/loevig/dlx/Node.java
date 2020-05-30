package net.loevig.dlx;

/**
 * Node represents a single value in the Dancing Links structure. Each node
 * links to the next and previous node in the row and column linked list.
 */
public class Node<E> {
    /**
     * The value contained in this node.
     */
    private E value;

    /**
     * The next node in the row linked list.
     */
    protected Node<E> right;

    /**
     * The previous node in the row linked list.
     */
    protected Node<E> left;

    /**
     * The next node in the column linked list.
     */
    protected Node<E> down;

    /**
     * The previous node in the column linked list.
     */
    protected Node<E> up;

    /**
     * A direct link to the first node in the column.
     * This link is used for optimizing Knuth's Algorithm X.
     */
    protected DancingLinks.ColumnNode<E> column;

    /**
     * Creates a new Node. The new node is its own next and previous nodes
     * in its row and column linked list.
     * @param column the column node.
     * @param value the node value.
     */
    protected Node(DancingLinks.ColumnNode<E> column, E value) {
        right = left = down = up = this;
        this.column = column;
        this.value = value;
    }

    /**
     * Returns the value of this node.
     *
     * @return the value of this node.
     */
    public E get() {
        return value;
    }

    /**
     * Sets the value of this node.
     *
     * @param value the value of this node.
     */
    public void set(E value) {
        this.value = value;
    }

    /**
     * Inserts {@code node} as the right node of this node.
     *
     * @param node the node to insert.
     */
    protected void insertRight(Node<E> node) {
        node.right = this.right;
        node.right.left = node;
        node.left = this;
        this.right = node;
    }

    /**
     * Inserts {@code node} as the down node of this node.
     *
     * @param node the node to insert.
     */
    protected void insertDown(Node<E> node) {
        node.down = this.down;
        node.down.up = node;
        node.up = this;
        this.down = node;

        column.size++;
    }
}
