package com.vienan.retrofitdemo;

import org.junit.Test;

import rx.Observable;
import rx.functions.Func1;
import rx.functions.Func2;

/**
 * 求子数组的最大和 题目描述： 输入一个整形数组，数组里有正数也有负数。 数组中连续的一个或多个整数组成一个子数组，每个子数组都有一个和。 求所有子数组的和的最大值。要求时间复杂度为O(n)。
 * <p>
 * 例如输入的数组为1, -2, 3, 10, -4, 7, 2, -5，和最大的子数组为3, 10, -4, 7, 2， 因此输出为该子数组的和18。
 * <p>
 * Created by ditclear on 2017/2/27.
 */

public class SubArrayMaxSum {


    @Test
    public void main() {
        Integer[] arr = new Integer[]{1, -2, 3, 10, -4, 7, 2, -5};

        System.out.println(getMaxInRxJava());

    }

    private Integer maxSumInRxJava(Integer[] arr) {
        if (arr == null) return 0;
        final Integer[] sum = {0};
        Observable.from(arr)
                .reduce(new Func2<Integer,Integer,Integer>() {
                    @Override
                    public Integer call(Integer a, Integer b) {
                        sum[0] = Math.max(a + b, a);
                        return a + b > 0 ? a + b : 0;
                    }
                }).subscribe();
        return sum[0];
    }

    private Integer restZeroInRxJava(int n) {
        return Observable.just(n)
                .map(new Func1<Integer,Integer>() {
                    @Override
                    public Integer call(Integer n) {
                        int rest = 0;
                        while (n != 0) {
                            rest += n / 5;
                            n /= 5;
                        }
                        return rest;
                    }
                }).toBlocking().first();
    }

    private int getMaxInRxJava() {
        final String[] array = new String[]{"A", "B", "C", "Y", "D", "Y", "E"};
        return Observable.from(array)
                .reduce(new Func2<String,String,String>() {
                    @Override
                    public String call(String s, String s2) {
                        return s + s2;
                    }
                })
                .first(new Func1<String,Boolean>() {
                    @Override
                    public Boolean call(String str) {
                        return (str.contains("A") && str.contains("B") && str.contains("C") &&
                                str.contains("D") && str.contains("E"));
                    }
                }).map(new Func1<String,Integer>() {
                    @Override
                    public Integer call(String s) {
                        return array.length - s.length();
                    }
                }).toBlocking().first();
    }
}
