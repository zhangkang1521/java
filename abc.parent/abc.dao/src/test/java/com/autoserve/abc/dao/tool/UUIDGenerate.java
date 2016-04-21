package com.autoserve.abc.dao.tool;


import java.util.UUID;

/**
 * @author RJQ 2015/1/8 15:16.
 */
public class UUIDGenerate {
    public static void main(String[] args) {
        String s = UUID.randomUUID().toString();
        System.out.println(s);
    }
}
