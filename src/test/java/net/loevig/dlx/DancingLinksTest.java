package net.loevig.dlx;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DancingLinksTest {

    private int countRight(DancingLinks<Integer>.Node node) {
        int size = 0;
        for (var n = node.right; n != node; n = n.right) {
            size++;
        }
        return size;
    }

    private int countLeft(DancingLinks<Integer>.Node node) {
        int size = 0;
        for (var n = node.left; n != node; n = n.left) {
            size++;
        }
        return size;
    }

    private int countDown(DancingLinks<Integer>.Node node) {
        int size = 0;
        for (var n = node.down; n != node; n = n.down) {
            size++;
        }
        return size;
    }

    private int countUp(DancingLinks<Integer>.Node node) {
        int size = 0;
        for (var n = node.up; n != node; n = n.up) {
            size++;
        }
        return size;
    }

    @Test
    public void invalidMatrix() {
        // Cannot create DL from a null matrix
        assertThrows(NullPointerException.class, () -> new DancingLinks<>(null));
        // Cannot create DL if matrix is empty
        assertThrows(IllegalArgumentException.class, () -> new DancingLinks<>(new Integer[0][0]));

        // Each row must have equal length
        assertThrows(IllegalArgumentException.class, () -> {
            new DancingLinks<>(new Integer[][] {
                    { 1 }, { 2, 3 }
            });
        });
    }

    @Test
    public void oneElement() {
        var dl = new DancingLinks<>(new Integer[][] {
                { 1 }
        });
        // Root should not have any down nodes
        assertEquals(0, countDown(dl.root));
        // There should be 1 column
        assertEquals(1, countRight(dl.root));
        // The column should have one row
        assertEquals(1, countDown(dl.root.right));
        assertEquals(1, ((DancingLinks<Integer>.ColumnNode) dl.root.right).size);
    }

    @Test
    public void getNodeValue() {
        var dl = new DancingLinks<>(new Integer[][] {
                { 1 }
        });
        assertEquals(1, dl.root.right.down.get());
    }

    @Test
    public void setNodeValue() {
        var dl = new DancingLinks<>(new Integer[][] {
                { 2 }
        });
        var node = dl.root.right.down;
        assertEquals(2, node.get());
        node.set(3);
        assertEquals(3, node.get());
        node.set(8);
        assertEquals(8, node.get());

    }

    @Test
    public void oneNullElement() {
        var dl = new DancingLinks<>(new Integer[][] {
                { null }
        });
        // Should have 1 column
        assertEquals(1, countRight(dl.root));
        // But column should be empty.
        assertEquals(0, countDown(dl.root.right));
    }

    @Test
    public void twoColumns() {
        var dl = new DancingLinks<>(new Integer[][] {
                { 4, 7 }
        });
        DancingLinks<Integer>.Node node = dl.root;
        assertEquals(0, countDown(node));
        assertEquals(2, countRight(node));

        node = node.right;
        assertEquals(1, countDown(node));
        assertEquals(4, node.down.get()); // Value of column 1

        node = node.right;
        assertEquals(1, countDown(node));
        assertEquals(7, node.down.get()); // Value of column 2
    }

    @Test
    public void twoRows() {
        var dl = new DancingLinks<>(new Integer[][] {
                { 3 },
                { 6 }
        });
        DancingLinks<Integer>.Node node = dl.root;
        assertEquals(0, countDown(node));
        assertEquals(1, countRight(node));

        node = node.right;
        assertEquals(2, countDown(node));
        assertEquals(2, ((DancingLinks<Integer>.ColumnNode) dl.root.right).size);

        node = node.down;
        assertEquals(3, node.get()); // Value of row 1
        node = node.down;
        assertEquals(6, node.get()); // Value of row 2
    }

    @Test
    public void twoRowAndColumns() {
        var dl = new DancingLinks<>(new Integer[][] {
                { 2, 5 },
                { 9, 1 }
        });
        DancingLinks<Integer>.Node node = dl.root;
        assertEquals(0, countDown(node));
        assertEquals(2, countRight(node));

        node = node.right; // 1. column
        assertEquals(2, countDown(node));
        node = node.down; // 1. row, 1. column
        assertEquals(1, countRight(node));
        assertEquals(2, node.get()); // Value of row 1
        node = node.down; // 2. row, 1. column
        assertEquals(1, countRight(node));
        assertEquals(9, node.get()); // Value of row 2

        node = node.down.right; // 2. column
        assertEquals(2, countDown(node));
        node = node.down; // 1. row, 2. column
        assertEquals(5, node.get()); // Value of row 1
        node = node.down; // 2. row, 2. column
        assertEquals(1, node.get()); // Value of row 2
    }

    @Test
    public void matrix3_3withNull() {
        var dl = new DancingLinks<>(new Integer[][] {
                { 4, null, 2 },
                { null, null, 3 },
                { 1, 9, 3 }
        });
        DancingLinks<Integer>.Node node = dl.root;
        assertEquals(3, countRight(node));

        // Check each column nodes first
        node = node.right; // 1. col
        assertEquals(2, countDown(node));
        assertEquals(2, countUp(node));
        node = node.right; // 2. col
        assertEquals(1, countDown(node));
        assertEquals(1, countUp(node));
        node = node.right; // 3. col
        assertEquals(3, countDown(node));
        assertEquals(3, countUp(node));
        node = node.right; // root

        // Assert each node in each row have correct values and links

        node = node.right.down; // 1. row, 1.col
        assertEquals(1, countRight(node));
        assertEquals(1, countLeft(node));
        assertEquals(4, node.get());
        node = node.right; // 1. row, 3. col
        assertEquals(2, node.get());

        node = node.down; // 2.row, 3.col
        assertEquals(0, countRight(node));
        assertEquals(0, countLeft(node));
        assertEquals(3, node.get());

        node = node.down; // 3.row, 3.col
        assertEquals(2, countRight(node));
        assertEquals(2, countLeft(node));
        assertEquals(3, node.get());
        node = node.left; // 3. row, 2. col
        assertEquals(9, node.get());
        node = node.left; // 3. row, 1. col
        assertEquals(1, node.get());
    }
}