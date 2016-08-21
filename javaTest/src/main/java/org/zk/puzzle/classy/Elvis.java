package org.zk.puzzle.classy;

import java.util.Calendar;

/**
 * Created by Administrator on 8/20/2016.
 */
public class Elvis {

    public static final Elvis INSTANCE = new Elvis();
    private final int beltSize;
    private static final int CURRENT_YEAR = Calendar.getInstance().get(Calendar.YEAR);

    public Elvis(){
        beltSize = CURRENT_YEAR - 1930;
    }

    public int getBeltSize(){
        return beltSize;
    }

    public static void main(String[] args) {
        System.out.println(INSTANCE.beltSize);
    }
}
