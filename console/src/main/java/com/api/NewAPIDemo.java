package com.api;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.function.Predicate;
import java.util.stream.IntStream;
import java.util.stream.IntStream.Builder;
import java.util.stream.Stream;

public class NewAPIDemo {
	public static void main(String[] args) throws Exception {
		// 使用lambda表达式遍历集合
		List<String> list = new ArrayList<>();
		list.add("a");
		list.add("b");
		list.add("c");
		list.forEach(item -> {
			if (item.equals("b")) {
				System.out.println(item);
			}
		});
		// 将集合转换成stream，使用stream中API来操作集合元素，由于Stream可以对集合元素进行整体的聚集操作，因此stream极大的丰富了集合的功能。
		Stream<String> lStream = list.stream();
		// lStream.findFirst();

		// 使用Stream操作集合。流式API，代表多个支持串行和并行聚集操作的元素
		Builder intBuilder = IntStream.builder();
		for (int i = 0; i < 5; i++) {
			int n = new Random(System.currentTimeMillis()).nextInt(10);
			intBuilder.add(n);
			Thread.sleep(10);
		}
		System.out.println();
		IntStream intStream = intBuilder.build();

		// intStream.forEach(item->System.out.print(item+","));
		System.out.println();
		// 对stream聚集操作，一次只能操作一次聚集
		// intStream.
		System.out.println("Max value:" + intStream.max().getAsInt());

		// System.out.println("Total value:" + intStream.sum());
		// System.out.println("Count: "+intStream.count());
		// System.out.println("Average value:" + intStream.average());
		// System.out.println("所有元素的平方是否都大于20：" + intStream.allMatch(item -> item * item
		// > 20));
		// System.out.println("是否包含任意一个元素的平方大于20：" + intStream.anyMatch(item -> item *
		// item > 20));
		// 将stream映射成一个新的stream，新的stream的每个元素是原来stream的元素的2倍+1
		// IntStream newIntStream = intStream.map(item -> item * 2 + 1);
		// newIntStream.forEach(item -> System.out.println(item));

		// 使用Stream API操作集合
		List<String> nameList = new ArrayList<>();
		nameList.add("Lily");
		nameList.add("Lucy");
		nameList.add("David");
		nameList.add("LinnDa");
		System.out.println(
				"Name strats  with 'L' count:" + nameList.stream().filter(item -> item.startsWith("L")).count());
		System.out.println("是否有个一名字长度大于5：" + nameList.stream().anyMatch(item -> item.length() > 5));

		//Predicate操作集合
		//removeIf参数是Predicate对象
		nameList.removeIf(item->item.length()==5);
		System.out.println(nameList.toString());
		System.out.println("The count of name length is 4 :"+calAll(nameList, item->((String)item).length()==4));
	}

	static int calAll(Collection<String> collection, Predicate p) {
		int total = 0;
		for (String obj : collection) {
			if (p.test(obj)) {
				total++;
			}
		}
		return total;
	}
}
