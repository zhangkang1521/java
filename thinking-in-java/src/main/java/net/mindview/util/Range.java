package net.mindview.util;

/**
 * Created by Administrator on 9/9/2017.
 */
public class Range {

    /**
     * Produce a sequence [0..n)
     * @param n
     * @return
     */
    public static int[] range(int n) {
        int[] result = new int[n];
        for(int i = 0; i < n; i++)
            result[i] = i;
        return result;
    }

    /**
     * Produce a sequence [start..end)
     * @param start
     * @param end
     * @return
     */
    public static int[] range(int start, int end) {
        int sz = end - start;
        int[] result = new int[sz];
        for(int i = 0; i < sz; i++)
            result[i] = start + i;
        return result;
    }

    /**
     * Produce a sequence [start..end) incrementing by step
     * @param start
     * @param end
     * @param step
     * @return
     */
    public static int[] range(int start, int end, int step) {
        int sz = (end - start)/step;
        int[] result = new int[sz];
        for(int i = 0; i < sz; i++)
            result[i] = start + (i * step);
        return result;
    }
}
