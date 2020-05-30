package net.loevig.dlx;

import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class DLXTest {

    @Test
    public void test1() {
        var dl = new DancingLinks<>(new String[][] {
                { "A", null, null, "A", null, null, "A" },
                { "B", null, null, "B", null, null, null },
                { null, null, null, "C", "C", null, "C" },
                { null, null, "D", null, "D", "D", null },
                { null, "E", "E", null, null, "E", "E" },
                { null, "F", null, null, null, null, "F" }
        });
        var solutions = DLX.solve(dl);
        assertEquals(1, solutions.size());

        var solution = solutions.get(0);
        assertEquals(3, solution.size());

        var rows = new HashSet<>(Set.of("B", "D", "F"));
        for (var node : solution) {
            rows.remove(node.get());
        }
        assertTrue(rows.isEmpty());
    }
}