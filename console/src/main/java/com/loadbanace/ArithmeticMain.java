package com.loadbanace;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

public class ArithmeticMain {
	public static void main(String[] args) {
		String result = null;

		System.out.println("轮询法");
		for (int i = 0; i < 10; i++) {
			result = RoundRobin.getServerIP();
			System.out.println(result);
		}

		System.out.println("随机法");
		for (int i = 0; i < 10; i++) {
			result = RandomIp.getServerIP();
			System.out.println(result);
		}

		System.out.println("源地址哈希法");
		result = HashIp.getServerIP("127.0.0.1");
		System.out.println(result);

		System.out.println("加权轮询法");
		for (int i = 0; i < 10; i++) {
			result = WeightRoundRobin.getServerIP();
			System.out.println(result);
		}
		
		System.out.println("加权随机法");
	}
}

class WeightRoundRobin {
	private static Integer pos = 0;

	public static String getServerIP() {
		// 重新在本地线程中copy一份IPMap，避免服务器上线下线导致出现并发问题
		Map<String, Integer> serverMap = new HashMap<>();
		serverMap.putAll(IPMap.serverWeightMap);

		Set<String> ipSet = serverMap.keySet();
		Iterator<String> iterator = ipSet.iterator();

		List<String> ipList = new ArrayList<>();
		while (iterator.hasNext()) {
			String server = iterator.next();
			int weight = serverMap.get(server);
			// 按照权重来添加等比例的ip到list中
			for (int i = 0; i < weight; i++) {
				ipList.add(server);
			}
		}

		String server = null;
		synchronized (pos) {
			if (pos > ipList.size()) {
				pos = 0;
			}
			server = ipList.get(pos);
			pos++;
		}

		return server;
	}

}

/**
 * 源地址哈希的思想是获取客户端访问的IP地址值，通过哈希函数计算得到一个数值，用该数值对服务器列表的大小进行取模运算，得到的结果便是要访问的服务器的序号
 * 源地址哈希法的优点在于：保证了相同客户端IP地址将会被哈希到同一台后端服务器，直到后端服务器列表变更。根据此特性可以在服务消费者与服务提供者之间建立有状态的session会话。
 */
class HashIp {
	public static String getServerIP(String requestIp) {
		// 重新在本地线程中copy一份IPMap，避免服务器上线下线导致出现并发问题
		Map<String, Integer> serverMap = new HashMap<>();
		serverMap.putAll(IPMap.serverWeightMap);

		Set<String> ipSet = serverMap.keySet();
		List<String> ipList = new ArrayList<>();
		ipList.addAll(ipSet);

		int hashCode = requestIp.hashCode();
		hashCode = Math.abs(hashCode);// 确保hash值是正数
		int pos = hashCode % ipList.size();

		return ipList.get(pos);
	}
}

class RandomIp {
	public static String getServerIP() {
		// 重新在本地线程中copy一份IPMap，避免服务器上线下线导致出现并发问题
		Map<String, Integer> serverMap = new HashMap<>();
		serverMap.putAll(IPMap.serverWeightMap);

		Set<String> ipSet = serverMap.keySet();
		List<String> ipList = new ArrayList<>();
		ipList.addAll(ipSet);

		Random random = new Random();
		int pos = random.nextInt(ipList.size());

		return ipList.get(pos);
	}
}

class RoundRobin {
	private static Integer pos = 0;

	public static String getServerIP() {
		// 重新在本地线程中copy一份IPMap，避免服务器上线下线导致出现并发问题
		Map<String, Integer> serverMap = new HashMap<>();
		serverMap.putAll(IPMap.serverWeightMap);

		Set<String> ipSet = serverMap.keySet();
		List<String> ipList = new ArrayList<>();
		ipList.addAll(ipSet);

		String server = null;
		synchronized (pos) {
			if (pos > ipList.size()) {
				pos = 0;
			}
			server = ipList.get(pos);
			pos++;
		}

		return server;
	}
}
