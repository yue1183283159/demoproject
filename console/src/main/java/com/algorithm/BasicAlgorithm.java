package com.algorithm;

import java.util.*;

public class BasicAlgorithm {

    public static void main(String[] args) {
        // 找出重复数字
        int[] my_array = {1, 2, 5, 5, 6, 6, 7, 2};
        for (int i = 0; i < my_array.length; i++) {
            for (int j = i + 1; j < my_array.length; j++) {
                if (my_array[i] == my_array[j] && i != j) {
                    System.out.println("重复数字 : " + my_array[j]);
                    continue;
                }
            }
        }

        // 汉诺塔
        int nDisks = 3;
        doTowers(nDisks, 'A', 'B', 'C');

        // 递归，斐波那契
        for (int i = 0; i < 10; i++) {
            System.out.println("Fibonacci of " + i + " is: " + fibonacci(i));
        }

        for (int counter = 0; counter <= 10; counter++) {
            System.out.printf("%d! = %d\n", counter, factorial(counter));
        }

        printRhombus(8);

    }

    static void doTowers(int topN, char from, char inter, char to) {
        if (topN == 1) {
            System.out.println("Disk 1 from " + from + " to " + to);
        } else {
            doTowers(topN - 1, from, to, inter);
            System.out.println("Disk " + topN + " from " + from + " to " + to);
            doTowers(topN - 1, inter, from, to);
        }
    }

    static int fibonacci(int number) {
        if (number == 0 || number == 1) {
            return number;
        } else {
            return fibonacci(number - 1) + fibonacci(number - 2);
        }
    }

    static int factorial(int number) {
        if (number <= 1) {
            return 1;
        } else {
            return number * factorial(number - 1);
        }
    }

    static void printRhombus(int size) {
        if (size % 2 == 0) {
            size++;
        }
        for (int i = 0; i < size / 2 + 1; i++) {
            for (int j = size / 2 + 1; j > i + 1; j--) {
                System.out.print(" ");
            }
            for (int j = 0; j < 2 * i + 1; j++) {
                System.out.print("*");
            }
            System.out.println();
        }
        for (int i = size / 2 + 1; i < size; i++) {
            for (int j = 0; j < i - size / 2; j++) {
                System.out.print(" ");
            }
            for (int j = 0; j < 2 * size - 1 - 2 * i; j++) {
                System.out.print("*");
            }
            System.out.println();
        }

    }

}
