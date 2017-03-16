package org.zk.other.lambda;


import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by zhangkang on 2017/3/16.
 */
public class LambdaTest {
    public static void main(String[] args) {
        List<Integer> list  = Arrays.asList(1, 2, 3, 4);
        list.stream().map((x) -> 2 * x).forEach(System.out::println);
        // System.out::println 是一个语法糖
//        list.stream().map((x) -> 2 * x).forEach((x) -> System.out.println(x));
        List list2 = list.stream().map((x) -> 2 * x).collect(Collectors.toList());
        System.out.println(list2);

//        List<Integer> list = Arrays.asList(1, 2, 3);
//        long sum = list.stream().map(x -> 2*x).reduce((x, y) -> x + y).get();
//        System.out.println(sum);
    }
}
