package net.loevig.dlx;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of {@link #solve(DancingLinks) Knuth's Algorithm X}.
 *
 * @author Anders Løvig
 */
public class DLX {

    /**
     * Solve an exact cover problem represented in the given dancing links
     * structure. This method reteurns all possible solutions to the problem.
     *
     * @param dl the exact cover problem.
     * @return a list of all solutions.
     */
    public static <E> List<Solution<E>> solve(DancingLinks<E> dl) {
        var solutions = new ArrayList<Solution<E>>();
        var partialSolution =  new ArrayList<Node<E>>();

        solve(dl.root, solutions, partialSolution);

        return solutions;
    }

    private static <E> void solve(ColumnNode<E> root, List<Solution<E>> solutions, List<Node<E>> partialSolution) {
        if (root.right == root) {
            // We got an solution!!!
            solutions.add(new Solution<>(partialSolution));
        }
        else {
            // Select a column with minimal size and remove it.
            var c = selectColumn(root);
            c.cover();

            // Try to find possible solutions with each node in current column.
            for (var r = c.down; r != c; r = r.down) {
                partialSolution.add(r);

                // Remove all columns with a node in current row.
                for (var j = r.right; j != r; j = j.right) {
                    j.column.cover();
                }

                // Repeat until we have removed all columns
                solve(root, solutions, partialSolution);

                // Remove this row node to try another combination of rows.
                partialSolution.remove(partialSolution.size() - 1);

                // Uncover all columns with a node in current row.
                for (var j = r.left; j != r; j = j.left) {
                    j.column.uncover();
                }
            }

            // Reinsert the column.
            c.uncover();
        }
    }

    /**
     * Returns the first row with a minimal size.
     *
     * @param root the root node.
     * @return a column node with minimal size.
     */
    private static <E> ColumnNode<E> selectColumn(ColumnNode<E> root) {
        var node = (ColumnNode<E>) root.right;

        for (var n = (ColumnNode<E>) node.right; n != root; n = (ColumnNode<E>) n.right) {
            if (n.size < node.size) {
                node = n;
            }
        }

        return node;
    }
}
