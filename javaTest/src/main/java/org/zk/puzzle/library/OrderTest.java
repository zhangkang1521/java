package org.zk.puzzle.library;

/**
 * Created by Administrator on 9/10/2016.
 */
public class OrderTest {
    public static void main(String[] args) {
        int[] array1 = new int[]{1, 2, 3};
        int[] array2 = new int[]{3, 2, 1};
        int[] array3 = new int[]{5, 7, 1};
        int[] array4 = new int[]{5, 5, 5};
        System.out.println(judgeOrder(array1));
        System.out.println(judgeOrder(array2));
        System.out.println(judgeOrder(array3));
        System.out.println(judgeOrder(array4));
    }

    /**
     * 一次遍历数组便可确定排序顺序
     * @param array
     * @return
     */
    public static Order judgeOrder(int[] array) {
        boolean asc = true;
        boolean desc = true;
        for (int i = 0; i < array.length - 1; i++) {
            asc &= array[i] <= array[i + 1];
            desc &= array[i] >= array[i + 1];
        }
        if(asc && !desc){
            return Order.ASC;
        }
        if(desc && !asc){
            return Order.DESC;
        }
        if(!asc && !desc){
            return Order.UNORDERED;
        }
        return Order.CONSTANT;
    }
}

enum Order {
    ASC, DESC, CONSTANT, UNORDERED;
}
