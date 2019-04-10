package com.algorithm;

import java.util.Random;
import org.junit.*;

public class AlgorithmUnitTest {
    @Test
    public void testLink() {
        Link link = new Link();
        link.addFirst(2);
        link.addFirst(1);
        link.addFirst(4);
        link.addLast(5);
        link.addLast(6);
        // link.add(6, 2);
        link.printAll();

        link.reverse();
        link.printAll();
    }

    @Test
    public void testSort() {
        Random random = new Random();
        int[] array = new int[100];
        for (int i = 0; i < 100; i++) {
            int num = random.nextInt(1000);
            array[i] = num;
        }

        Sort sort = new Sort(array);

        // sort.bubbleSort1();;
        // sort.pringArray();
        //
        // System.out.println();
        // sort.bubbleSort2();
        // sort.pringArray();
        //
        // System.out.println();
        // sort.quickSort();
        // sort.pringArray();

        System.out.println();
        sort.insertSort();
        sort.pringArray();
    }

    @Test
    public void testSearch() {
        // 随机产生1000个0-100000的整数，然后进行查找测试
        Random random = new Random();
        int[] array = new int[100];
        for (int i = 0; i < 100; i++) {
            int num = random.nextInt(100);
            array[i] = num;
            System.out.print(num + " ");
        }

        System.out.println();
        Sort sort = new Sort(array);
        sort.quickSort();
        Search search = new Search(sort.getArray());
        //int index= search.sequentialSearch(70);
        int index = search.BinarySearch(70);
        System.out.println("70 index :" + index);
    }

}
