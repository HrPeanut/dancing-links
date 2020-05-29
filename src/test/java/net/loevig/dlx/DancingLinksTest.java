package net.loevig.dlx;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DancingLinksTest {

    private int rowSize(DancingLinks<Integer>.Node node) {
        int size = 0;
        for (var n = node.right; n != node; n = n.right) {
            size++;
        }
        return size;
    }

    private int colSize(DancingLinks<Integer>.Node node) {
        int size = 0;
        for (var n = node.down; n != node; n = n.down) {
            size++;
        }
        return size;
    }

    @Test
    public void emptyDL() {
        var links = new DancingLinks<>(new Integer[0][0]);
        assertEquals(0, colSize(links.root));
        assertEquals(0, rowSize(links.root));
    }

    @Test
    public void oneElementDL() {
        var links = new DancingLinks<>(new Integer[][] {
                { 1 }
        });
        assertEquals(1, rowSize(links.root));
        assertEquals(1, colSize(links.root.right));
        assertEquals(1, ((DancingLinks<Integer>.ColumnNode) links.root.right).size);

        assertEquals(1, links.root.right.down.get());
    }

    @Test
    public void errorMalformedValues() {
        assertThrows(IllegalArgumentException.class, () -> {
            new DancingLinks<>(new Integer[][] {
                    { 1 }, { 2, 3 }
            });
        });
    }

    @Test
    public void setNodeValue() {
        var links = new DancingLinks<>(new Integer[][] {
                { 1 }
        });
        links.root.right.down.set(2);
        assertEquals(2, links.root.right.down.get());
    }
}