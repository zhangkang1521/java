package org.zk.other.algorithm;

import java.util.Arrays;
import java.util.Random;

/**
 * Created by zhangkang on 2017/1/23.
 */
public class SubArrayMaxSum {

    public static void main(String[] args) {
        // 连续子数组的最大和
        //int[] arr = {1, -2, 3, 10, -4, 7, 2, -5};
        int[] arr = {1, -2, 3, 10, -4, 7, 2, -5};
        findMaxSumFromArray(arr);
        findMaxSumFromArray2(arr);
    }

    /**
     * 关键在于找到开始位置，结束位置
     * @param arr
     * @return
     */
    public static long findMaxSumFromArray2(int[] arr) {
        long max = 0;
        long currentSum = 0;
        int begin =0, end = 0; //开始位置，结束位置
        for (int i = 0; i < arr.length; i++) {
            currentSum += arr[i];
            if (currentSum < 0) {
                currentSum = 0;
                begin = i + 1;
            }
            if (currentSum > max) {
                max = currentSum;
                end = i;
            }
        }
        System.out.println(begin + "-" + end + " " + max);
        return max;
    }



    /**
     * 找出所有连续子数组，返回最大值
     *
     * @param arr
     * @return
     */
    public static long findMaxSumFromArray(int[] arr) {
        long max = Long.MIN_VALUE;
        int begin = 0, end = 0;
        for (int i = 0; i < arr.length; i++) {
            for (int j = i; j < arr.length; j++) {
                long sum = sum(arr, i, j);
                if (sum > max) {
                    max = sum;
                    begin = i;
                    end = j;
                }
            }
        }
        System.out.println(begin + "-" + end + " " + max);
        return max;
    }

    public static long sum(int[] arr, int begin, int end) {
        if (begin < 0 || end >= arr.length || begin > end) {
            throw new IllegalArgumentException("参数错误");
        }
        long sum = 0;
        for (int i = begin; i <= end; i++) {
            sum += arr[i];
        }
        return sum;
    }
}
