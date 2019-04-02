package com.others;

//静态导包，1.5之后的新特性，用来指定导入某个类中静态资源，并且不需要类名。可以在代码中直接使用资源名
import static java.lang.Math.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Deque;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import org.apache.commons.codec.digest.DigestUtils;

import com.sun.javafx.fxml.BeanAdapter;

public class OtherKnowledge {
	public static void main(String[] args) {
		String s1 = "sa";
		// intern()方法会首先从常量池中查找是否存在该常量值，如果常量池中不存在则创建，如果存在直接返回。
		String s2 = s1.intern();
		System.out.println(s1 == s2);// true

		// import static xxxx
		System.out.println(sin(20));// 直接使用资源，不需要类名

		// 生产者-消费者队列
		// 1. 使用阻塞队列实现
		// 2. 使用wait-notify来实现
		// LinkedBlockingQueue是无界队列，可以近乎认为是一个无穷大的队列。ArrayBlockingQueue如果满了，则会使用
		// 拒绝策略RejectedExecutionHandler处理满了的任务，默认是AbortPolicy
		BlockingQueue<String> queue = new ArrayBlockingQueue<>(16);
		Producer producer = new Producer(queue);
		Consumer consumer1 = new Consumer(queue);
		Consumer consumer2 = new Consumer(queue);
		// new Thread(producer).start();
		// new Thread(consumer1).start();
		// new Thread(consumer2).start();

		// Thread.sleep(0)的作用：由于java采用抢占式的线程调度算法，因此可能会出现某条线程常常获取CPU的控制权。
		// 为了让某些优先级比较低的线程也能获取到CPU控制权，可以使用Thread.sleep(0)手动触发一次操作系统分配时间片的操作
		// 这也是平衡CPU控制权的一种操作

		// CAS：Compare and Swap，比较-替换。jdk1.8中，ConcurrentHashMap不再使用Segment分离锁
		// 而是采用一种客观锁CAS算法来实现同步问题的，底层还是“数组+链表->红黑树”的实现

		// java中++，--操作符不是线程安全的，它涉及多个指令，如读取变量值，增加，然后存储回内存。这个过程可能会出现多个线程交差
		// 多线程开发的良好实践：
		// 1. 合理使用try catch，确保线程异常不会使整个应用程序崩溃
		// 2. 最小化同步范围。 3. 考虑使用线程池 4.优先使用volatile 5.优先使用并发容器而非同步容器

		// LinkedHashMap和PriorityQueue
		// PriorityQueue是一个优先级队列，保证最高或者最低优先级的元素总是杂队列头部
		PriorityQueue<String> priorityQueue = new PriorityQueue<>();
		priorityQueue.add("a");
		priorityQueue.add("1a");

		// ArrayList默认大小是10，HashMap默认大小是16个元素（必须是2的幂）
		// 打印数组内容，由于数组没有实现toString()方法，所以将数组传递给System.out.println()无法打印内容
		// 需要使用Arrays.toString()和Arrays.deepToString()方法来打印数组

		// “快速失败”也就是fail-fast，它是Java集合的一种错误检测机制。当多个线程对集合进行结构上的改变的操作时，有可能会产生fail-fast机制
		// 程序在对 collection 进行迭代时，某个线程对该 collection 在结构上对其做了修改，这时迭代器就会抛出
		// ConcurrentModificationException 异常信息，从而产生 fail-fast

		// 寻找数组中第二大,第二小的元素。最大最小的数据不止一个。
		findSecondData();

		// 找到数组中第一个不重复出现的数据
		findFirstNoRepeatData();

		// 利用栈实现后缀表达式。后缀表达式主要是为了将表达式转换成利于计算机计算的一种表达方式。
		// 正常的表达式a+b*c是中缀表达式，后缀表达式是：abc*+,将运算符置于数字之后。
		// 中缀表达式必然存在后缀表达式，后缀表达式不存在优先级问题，只需要利用栈进行“从左至右依次计算”即可
		/*
		 * 后缀表达式计算方法：
		 * 将后缀表达式从左到右一次遍历，如果当前元素为数字则入栈，如果为操作符，则pop出栈两个元素(第一次pop出的是右操作数，第二次pop出的是左操作数)
		 * 进行运算 然后将计算的结果再次入栈，直至表达式结束，此时操作数栈内理应只剩一个元素即表达式结果
		 */

		// 按升序对栈进行排序（即最大元素位于栈顶，最多只能使用一个额外的栈存放临时数据，不得将元素复制到别的数据结构（如数组））
		System.out.println("Sort Stack Data:");
		sortStack();

		// 使用栈判断表达式括号平衡问题
		System.out.println("Check Brace Balance By Stack:");
		checkBraceBalance();

		// 将长地址转成短地址，将长地址压成6个字母的地址
		// 1. 使用MD5算法对原始链接地址进行加密（MD5加密之后的字符串长度总是32位），然后对加密的字符串进行处理得到短链接地址。当获取到这个短链接
		// 地址之后，去数据库中查询短地址对应原始的地址，然后再进行处理。使用MD5加密包commons-codec
		String longUrl = "https://blog.csdn.net/yushouling/article/details/55096992?pageid=123";
		String shortUrl = shortUrl(longUrl);
		System.out.println(shortUrl);
	}

	private static String shortUrl(String longUrl) {
		// 自定义生产MD5加密字符串前的混合Key，可以认为是salt
		String key = "123456";
		// 要使用生成URL的字符
		String[] chars = new String[] { "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p",
				"q", "r", "s", "t", "u", "v", "w", "x", "y", "z", "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A",
				"B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",
				"W", "X", "Y", "Z" };
		// 对传入的网址加密
		String md5EncryptResult = DigestUtils.md5Hex(key + longUrl);
		// 此时加密的字符串有数字，不友好，需要处理成只包含字母的字符串
		String[] results = new String[4];
		for (int j = 0; j < 4; j++) {
			// 把加密字符按照8位一组，16进制与0x3FFFFFFF进行为运算
			String stempSubString = md5EncryptResult.substring(j * 8, j * 8 + 8);
			// 这里需要使用 long 型来转换，因为 Inteper .parseInt() 只能处理 31 位 , 首位为符号位 , 如果不用 long ，则会越界
			long lhexLong = 0x3FFFFFFF & Long.parseLong(stempSubString, 16);
			String result = "";
			// 要将加密串压成6个字母的字符串
			for (int i = 0; i < 6; i++) {
				// 把得到的值与 0x0000003D 进行位与运算，取得字符数组 chars 索引
				long index = 0x0000003D & lhexLong;
				// 把取到的字符进行拼接得到最后的URL字符串
				result += chars[(int) index];
				// 每次循环按位右移5位
				lhexLong = lhexLong >> 5;
			}
			results[j] = result;
		}

		return results[0];
	}

	private static void checkBraceBalance() {
		String str = "{a+[b+(c*a)/(d*e)]}";
		System.out.println(str);
		Deque<String> stack = new LinkedList<>();
		String[] strArr = str.split("");
		System.out.println(Arrays.toString(strArr));
		for (int i = 0; i < strArr.length; i++) {
			// 如果数组中有这三种左括号元素那么直接进行入栈操作
			String s = strArr[i];
			if (s.equals("(") || s.equals("[") || s.equals("{")) {
				stack.push(s);
			} else if (s.equals(")") && !stack.isEmpty() && stack.peek().equals("(")) {
				// 当遇到右括号时，发现当前位于栈顶的是左括号，那么此时可以出栈
				stack.pop();
			} else if (s.equals(")") && !stack.isEmpty() && !stack.peek().equals("(")) {
				System.out.println("左右小括号匹配次序不成功");
				return;
			} else if (s.equals("]") && !stack.isEmpty() && stack.peek().equals("[")) {
				// 当遇到右括号时，发现当前位于栈顶的是左括号，那么此时可以出栈
				stack.pop();
			} else if (s.equals("]") && !stack.isEmpty() && !stack.peek().equals("[")) {
				System.out.println("左右中括号匹配次序不成功");
				return;
			} else if (s.equals("}") && !stack.isEmpty() && stack.peek().equals("{")) {
				// 当遇到右括号时，发现当前位于栈顶的是左括号，那么此时可以出栈
				stack.pop();
			} else if (s.equals("}") && !stack.isEmpty() && !stack.peek().equals("{")) {
				System.out.println("左右大括号匹配次序不成功");
				return;
			} else if (s.equals(")") || s.equals("]") || s.equals("}") && stack.isEmpty()) {
				System.out.println("右括号多于左括号");
				return;
			}
		}

		// 经历完一趟循环后如果堆栈不为空，那么左括号就多了
		if (!stack.isEmpty()) {
			System.out.println("左括号多于右括号");
		} else {
			System.out.println("匹配正确");
		}

	}

	private static void sortStack() {

		Deque<Integer> stack1 = new LinkedList<>();
		stack1.push(5);
		stack1.push(3);
		stack1.push(6);
		stack1.push(9);
		stack1.push(8);
		stack1.push(2);
		System.out.println(stack1);

		// 增加一个额外的栈用来存放已经有序（从小到大）的数据，最后将数据全部出栈放入原来的栈中
		Deque<Integer> tempstack = new LinkedList<>();
		// 先将第一个元素直接放入临时栈中
		int first = stack1.pop();
		tempstack.push(first);

		while (!stack1.isEmpty()) {
			int top = stack1.pop();
			// 将临时栈中比当前数据小的元素出栈并放入当前栈中，然后将当前元素放入临时栈中
			while (!tempstack.isEmpty() && top > tempstack.peek()) {
				stack1.push(tempstack.pop());
			}
			tempstack.push(top);
		}

		// 将临时栈中元素出栈放入原来的栈
		while (!tempstack.isEmpty()) {
			stack1.push(tempstack.pop());
		}

		System.out.println(stack1);
	}

	private static void findFirstNoRepeatData() {
		int[] testArr = { 2, 3, 10, 2, 10, 3, 7, 8, 6 };
		// 从前往后遍历，遍历一次，每到一个数据，就以这个数据为key，然后记录出现的次数。
		// 使用LinkedHashMap记录统计数据，保证存入的顺序不变，然后取出第一个次数为1的数据即为要找的数据。
		int firstNoRepeatData = testArr[0];
		Map<Integer, Integer> countMap = new LinkedHashMap<>();
		for (int n : testArr) {
			if (countMap.keySet().contains(n)) {
				countMap.put(n, countMap.get(n) + 1);
			} else {
				countMap.put(n, 1);
			}
		}

		for (int key : countMap.keySet()) {
			if (countMap.get(key) == 1) {
				firstNoRepeatData = key;
				break;
			}
		}
		System.out.println("Use LinkedHashMap store the count.");
		System.out.println(countMap);
		System.out.println(Arrays.toString(testArr));
		System.out.println("First No Repeat Data:" + firstNoRepeatData);

		// 这种遍历查找的方法，遍历次数较多，效率低
		// 多次遍历数组，每取数组中一个数，然后遍历整个数组，如果没有重复的数据，则停止，这个数就是第一次不重复出现的数
		firstNoRepeatData = testArr[0];
		for (int i = 0; i < testArr.length; i++) {
			int n = testArr[i];
			boolean isRepeat = false;
			for (int j = 0; j < testArr.length; j++) {
				// 数据相等，索引不等，表明数组中还存在一个与当前数据相等的数据，这个是有重复的数据
				if (n == testArr[j] && j != i) {
					isRepeat = true;
					break;
				}
			}

			if (!isRepeat) {
				firstNoRepeatData = n;
				break;
			}
		}

		System.out.println("Use loop to find the data. ");
		System.out.println("First No Repeat Data:" + firstNoRepeatData);

	}

	private static void findSecondData() {
		int[] testArr = { 2, 3, 10, 10, 3, 7, 8, 6 };
		Arrays.sort(testArr);
		System.out.println(Arrays.toString(testArr));
		// 排序之后默认是从小到大的，最大的一定在最后一位。
		// 取出最大值，然后从倒数第二位往前比，找到第一个比它小的数据则停止
		int maxData = testArr[testArr.length - 1];
		int secondMaxData = maxData;
		for (int i = testArr.length - 2; i >= 0; i--) {
			if (testArr[i] < maxData) {
				secondMaxData = testArr[i];
				break;
			}
		}
		System.out.println("Second Max Data:" + secondMaxData);

		// 如果是第二小的数据，则是从第二位开始往后比，找打第一个比最小数据大的则停止，找到的数据为第二小。
		int minData = testArr[0];
		int secondMinData = minData;
		for (int i = 1; i < testArr.length; i++) {
			if (testArr[i] > minData) {
				secondMinData = testArr[i];
				break;
			}
		}

		System.out.println("Second Min Data:" + secondMinData);
	}
}

class Producer implements Runnable {

	private final BlockingQueue<String> queue;

	public Producer(BlockingQueue<String> queue) {
		this.queue = queue;
	}

	@Override
	public void run() {
		try {
			while (true) {
				Thread.sleep(1000);
				queue.put(produce());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private String produce() {
		int n = new Random().nextInt(1000);
		return "test" + n;
	}

}

class Consumer implements Runnable {

	private final BlockingQueue<String> queue;

	public Consumer(BlockingQueue<String> queue) {
		this.queue = queue;
	}

	@Override
	public void run() {
		try {
			while (true) {
				Thread.sleep(1000);
				consumer(queue.take());
			}
		} catch (Exception e) {

		}

	}

	private void consumer(String data) {
		System.out.println("Thread: " + Thread.currentThread().getId() + ", " + data);
	}

}
