package net.loevig.dlx;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Spliterator;
import java.util.function.Consumer;

/**
 * Solution represents a list of nodes, which is a solution to an exact cover
 * problem represented by a {@link DancingLinks Dancing Links} structure.
 *
 * @author Anders Løvig
 */
public class Solution<E> implements Iterable<Node<E>> {

    /**
     * List of nodes, which is the solution.
     */
    private final List<Node<E>> nodes;

    /**
     * Create a new solution from the given list of nodes.
     * The nodes are copied to an internal list. Subsequent modifications
     * will of {@code nodes} will not change the new solution.
     *
     * @param nodes of the solution.
     */
    protected Solution(List<Node<E>> nodes) {
        this.nodes = List.copyOf(nodes);
    }

    /**
     * Returns number of nodes in this solution.
     *
     * @return the number of nodes of this solution.
     */
    public int size() {
        return nodes.size();
    }

    @Override
    public Iterator<Node<E>> iterator() {
        return nodes.iterator();
    }
}
