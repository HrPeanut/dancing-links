package net.loevig.dlx;

/**
 * Implementation of the Dancing Links structure. This structure is used by
 * Knuth's Algorithm X, to find all solutions to exact cover problems.
 * <p>
 * The data structure is a matrix of nodes, where each row and column is a
 * circular double linked list. This allows for easy removal of nodes with
 * enables an efficient implementation of Algorithm X. Each node also stores
 * data of a generic type. This allows for easy decoding of solutions for the
 * exact cover problem.
 *
 * @author Anders Løvig
 * @see <a href="https://arxiv.org/abs/cs/0011047">Donald Knuth: Dancing Links</a>
 */
public class DancingLinks<E> {
    /**
     * Node represents a single value in the Dancing Links structure. Each node
     * links to the next and previous node in the row and column linked list.
     */
    class Node {
        /**
         * The value contained in this node.
         */
        private E value;

        /**
         * The next node in the row linked list.
         */
        Node right;

        /**
         * The previous node in the row linked list.
         */
        Node left;

        /**
         * The next node in the column linked list.
         */
        Node down;

        /**
         * The previous node in the column linked list.
         */
        Node up;

        /**
         * A direct link to the first node in the column.
         * This link is used for optimizing Knuth's Algorithm X.
         */
        ColumnNode column;

        /**
         * Creates a new Node. The new node is its own next and previous nodes
         * in its row and column linked list.
         * @param column the column node.
         * @param value the node value.
         */
        public Node(ColumnNode column, E value) {
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
        void insertRight(Node node) {
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
        protected void insertDown(Node node) {
            node.down = this.down;
            node.down.up = node;
            node.up = this;
            this.down = node;

            column.size++;
        }
    }

    /**
     * Column node represents
     */
    class ColumnNode extends Node {
        /**
         * The number of nodes in the column linked list excluding this node.
         */
        int size;

        /**
         * Create a new ColumnNode. The new node have size 0 and is its own
         * next and previous node.
         */
        public ColumnNode() {
            super(null, null);
            super.column = this;
            size = 0;
        }
    }

    /**
     * The first node in the data structure.
     */
    ColumnNode root;

    public DancingLinks(E[][] values) {
        root = new ColumnNode();

        if (values.length == 0 || values[0].length == 0) {
            // Cannot create links without elements.
            return;
        }

        Node prev = root;
        for (int col = 0; col < values[0].length; col++) {
            // Create and insert the column node first.
            var c = new ColumnNode();
            prev.insertRight(c);

            for (int row = 0; row < values.length; row++) {
                if (values[0].length != values[row].length) {
                    throw new IllegalArgumentException("rows must have same size");
                }

                var n = new Node(c, values[col][row]);
                c.insertDown(n);
            }
        }
    }
}
