package net.loevig.dlx;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ColumnNodeTest {

    @Test
    public void coverEmpty() {
        var dl = new DancingLinks<>(new Integer[][] {
                { null }
        });
        // Remove first column
        ((ColumnNode<Integer>) dl.root.right).cover();
        assertEquals(0, dl.root.size);
        assertEquals(0, DancingLinksTest.countRight(dl.root));
    }

    @Test
    public void coverColumns() {
        var dl = new DancingLinks<>(new Integer[][] {
                { 1, 2 }
        });
        // Remove first column
        ((ColumnNode<Integer>) dl.root.right).cover();
        assertEquals(1, dl.root.size);
        assertEquals(1, DancingLinksTest.countRight(dl.root));

        assertEquals(0, ((ColumnNode<Integer>) dl.root.right).size);
        assertEquals(0, DancingLinksTest.countDown(dl.root.right));

        // Remove second column
        ((ColumnNode<Integer>) dl.root.right).cover();
        assertEquals(0, dl.root.size);
        assertEquals(0, DancingLinksTest.countRight(dl.root));
    }

    @Test
    public void coverRows() {
        var dl = new DancingLinks<>(new Integer[][] {
                { 1, 2 },
                { null, 3 }
        });
        // Remove first column
        ((ColumnNode<Integer>) dl.root.right).cover();
        assertEquals(1, dl.root.size);
        assertEquals(1, DancingLinksTest.countRight(dl.root));

        assertEquals(1, ((ColumnNode<Integer>) dl.root.right).size);
        assertEquals(1, DancingLinksTest.countDown(dl.root.right));

        // Remove second column
        ((ColumnNode<Integer>) dl.root.right).cover();
        assertEquals(0, dl.root.size);
        assertEquals(0, DancingLinksTest.countRight(dl.root));
    }

    @Test
    public void uncoverEmpty() {
        var dl = new DancingLinks<>(new Integer[][] {
                { null }
        });
        var node = ((ColumnNode<Integer>) dl.root.right);
        node.cover();
        node.uncover();
        assertEquals(1, dl.root.size);
        assertEquals(1, DancingLinksTest.countRight(dl.root));
    }

    @Test
    public void uncoverColumns() {
        var dl = new DancingLinks<>(new Integer[][] {
                { 1, 2 }
        });
        var node1 = ((ColumnNode<Integer>) dl.root.right);
        var node2 = ((ColumnNode<Integer>) node1.right);

        node1.cover();
        node2.cover();

        node2.uncover();
        assertEquals(1, dl.root.size);
        assertEquals(1, DancingLinksTest.countRight(dl.root));

        node1.uncover();
        assertEquals(2, dl.root.size);
        assertEquals(2, DancingLinksTest.countRight(dl.root));
    }

    @Test
    public void uncoverRows() {
        var dl = new DancingLinks<>(new Integer[][] {
                { 1, 2 },
                { null, 3 }
        });
        var node1 = ((ColumnNode<Integer>) dl.root.right);
        var node2 = ((ColumnNode<Integer>) node1.right);

        node1.cover();
        node2.cover();

        node2.uncover();
        assertEquals(1, node2.size);
        assertEquals(1, DancingLinksTest.countDown(node2));

        node1.uncover();
        assertEquals(1, node1.size);
        assertEquals(1, DancingLinksTest.countDown(node1));

        assertEquals(2, node2.size);
        assertEquals(2, DancingLinksTest.countDown(node2));
    }
}