package org.zk.other.algorithm;

import java.util.Arrays;

/**
 * Created by Administrator on 12/28/2016.
 */
public class SortDemo {

    /**
     * 冒泡排序
     *
     * @param array
     * @return
     */
    public static void bubbleSort(int[] array) {
        for (int i = 0; i < array.length - 1; i++) {
            for (int j = 0; j < array.length - i - 1; j++) {
                if (array[j] > array[j + 1]) {
                    swap(array, j, j + 1);
                }
            }
        }
    }

    /**
     * 选择排序
     *
     * @param array
     */
    public static void selectSort(int[] array) {
        for (int i = 0; i < array.length - 1; i++) {
            int min = i;
            for (int j = i + 1; j < array.length; j++) {
                if (array[j] < array[min]) {
                    min = j;
                }
            }
            swap(array, i, min);
        }
    }

    /**
     * 直接插入排序
     * @param array
     */
    public static void insertSort(int[] array) {
        for (int i = 0; i < array.length - 1; i++) {
            for (int j = i; j >= 0; j--) {
                if (array[j + 1] < array[j]) {
                    swap(array, j , j + 1);
                }
            }
        }
    }


    /**
     * 交换数组中两个元素的位置
     *
     * @param array
     * @param i
     * @param j
     */
    public static void swap(int[] array, int i, int j) {
        int tmp = array[i];
        array[i] = array[j];
        array[j] = tmp;
    }

    public static void main(String[] args) {
        int[] array = {3, 1, 9, 8, 20, 5};
        //bubbleSort(array);
        //selectSort(array);
        insertSort(array);
        System.out.println(Arrays.toString(array));
    }
}
