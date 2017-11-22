package net.mindview.util;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Administrator on 9/9/2017.
 */
public class RangeTest {

    @Test
    public void range() throws Exception {
        int[] range = Range.range(10);
        assertEquals(range.length, 10);
        assertEquals(range[0], 0);
        assertEquals(range[9], 9);
    }

    @Test
    public void range1() throws Exception {
        int[] range = Range.range(5, 10);
        assertEquals(range.length, 5);
        assertEquals(range[0], 5);
        assertEquals(range[4], 9);
    }

    @Test
    public void range2() throws Exception {
        int[] range = Range.range(5, 20, 3);

    }
}