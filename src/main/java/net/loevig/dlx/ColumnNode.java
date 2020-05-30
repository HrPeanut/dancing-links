package net.loevig.dlx;

/**
 * ColumnNode represents a node in the first row. A ColumnNode contains
 * a size indicating the number of downwards links until the column node
 * is reached again.
 *
 * @author Anders Løvig
 */
class ColumnNode<E> extends Node<E> {

    /**
     * The number of nodes in the column linked list excluding this node.
     */
    protected int size;

    /**
     * Direct link to the root node. Used for optimizing Algorithm X.
     */
    protected ColumnNode<E> root;

    /**
     * Create a new ColumnNode. The new node have size 0 and is its own
     * next and previous node.
     *
     * @param root the root node.
     */
    public ColumnNode(ColumnNode<E> root) {
        super(null, null);
        super.column = this;

        this.root = root;
        if (root == null) {
            this.root = this;
        }
        this.size = 0;
    }

    @Override
    protected void insertRight(Node<E> node) {
        super.insertRight(node);
        root.size++;
    }

    /**
     * Cover this column, by temporarily removing it from the dancing links
     * structure. All rows having nodes in this column are also removed.
     */
    protected void cover() {
        this.right.left = this.left;
        this.left.right = this.right;

        for (Node<E> i = this.down; i != this; i = i.down) {
            for (Node<E> j = i.right; j != i; j = j.right) {
                j.down.up = j.up;
                j.up.down = j.down;
                j.column.size--;
            }
        }

        this.root.size--;
    }

    /**
     * Uncover this column, by inserting it back into the dancing links
     * structure. All rows having nodes in this column are also reinserted.
     */
    protected void uncover() {
        this.root.size++;

        for (Node<E> i = this.up; i != this; i = i.up) {
            for (Node<E> j = i.left; j != i; j = j.left) {
                j.column.size++;
                j.down.up = j;
                j.up.down = j;
            }
        }

        this.right.left = this;
        this.left.right = this;
    }
}
