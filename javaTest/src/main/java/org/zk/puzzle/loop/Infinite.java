package org.zk.puzzle.loop;

import org.jcp.xml.dsig.internal.dom.DOMUtils;

/**
 * Created by Administrator on 9/28/2016.
 */
public class Infinite {
    public static void main(String[] args) {
        double a = Double.POSITIVE_INFINITY;
        System.out.println(Long.toHexString(Double.doubleToRawLongBits(a)));

        double b = Double.NEGATIVE_INFINITY;
        System.out.println(Long.toHexString(Double.doubleToRawLongBits(b)));

        System.out.println(a == a + 1);
    }
}
