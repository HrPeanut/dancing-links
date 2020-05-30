package net.loevig.dlx;

import java.util.ArrayList;

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
     * The first node in the data structure.
     */
    ColumnNode<E> root;

    /**
     * Creates a new DancingLinks structure from the given matrix of values.
     * Each value in the matrix is represented as a node in the dancing links
     * structure.
     *
     * If a value in {@code values} is {@code null}, then a node is not created
     * for that index. Then the next and previous nodes will skip this row and
     * column in the linked lists.
     *
     * The provided {@code values} must not be {@code null} and must not be the
     * an empty array.
     *
     * @param values the values to base the dancing links structure on.
     */
    public DancingLinks(E[][] values) {
        if (values.length == 0 || values[0].length == 0) {
            // Cannot create links without elements.
            throw new IllegalArgumentException("values is empty");
        }

        int colLength = values[0].length;

        Node<E> prev = this.root = new ColumnNode<>(null);
        var columns = new ArrayList<ColumnNode<E>>(colLength);

        // Create column nodes and insert them to the right of root.
        for (int col = 0; col < colLength; col++) {
            var node = new ColumnNode<E>(root);
            columns.add(node);
            prev.insertRight(node);
            prev = node;
        }

        // Now create rows and insert them
        for (E[] row : values) {
            prev = null;

            for (int col = 0; col < colLength; col++) {
                // Each row must have equal columns
                if (row.length != colLength) {
                    throw new IllegalArgumentException("rows must have equal length");
                }
                var val = row[col];
                if (val == null) {
                    continue;
                }

                var column = columns.get(col);
                var node = new Node<>(column, val);

                if (prev == null) {
                    // node is the first in current row.
                    prev = node;
                }

                column.up.insertDown(node);
                prev.insertRight(node);
                prev = node;
            }
        }
    }


}
