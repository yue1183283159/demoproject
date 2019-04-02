package com.others;

/**
 * 简单的雪花算法（snowflake）
 */
public class IdWorker {
	private long workerId;
	private long datacenterId;
	private long sequence = 0;
	// 从2018/9/29日开始计算，可以用到2089年
	private long twepoch = 1538211907857L;

	private long workerIdBits = 5L;
	private long datacenterIdBits = 5L;
	private long sequenceBits = 12L;

	private long workerIdShift = sequenceBits;
	private long datacenterIdShift = sequenceBits + workerIdBits;
	private long timestampLeftShift = sequenceBits + workerIdBits + datacenterIdBits;
	// 得到0000000000000000000000000000000000000000000000000000111111111111
	private long sequenceMask = -1L ^ (-1L << sequenceBits);
	private long lastTimestamp = -1L;

	public IdWorker(long workerId, long datacenterId) {
		this.workerId = workerId;
		this.datacenterId = datacenterId;
	}

	public synchronized long nextId() {
		long timestamp = timeGen();
		// 时间回拨，抛出异常
		if (timestamp < lastTimestamp) {
			System.out.println("clock is moved backwards.");
			throw new RuntimeException(String.format(
					"Clock moved backwards.  Refusing to generate id for %d milliseconds", lastTimestamp - timestamp));
		}

		if (lastTimestamp == timestamp) {
			sequence = (sequence + 1) & sequenceMask;
			if (sequence == 0) {
				timestamp = tilNextMills(lastTimestamp);
			}
		} else {
			sequence = 0;
		}

		lastTimestamp = timestamp;

		return ((timestamp - twepoch) << timestampLeftShift) | (datacenterId << datacenterIdShift)
				| (workerId << workerIdShift) | sequence;
	}

	private long tilNextMills(long lastTimestamp) {
		long timestamp = timeGen();
		while (timestamp <= lastTimestamp) {
			timestamp = timeGen();
		}
		return timestamp;
	}

	private long timeGen() {

		return System.currentTimeMillis();
	}

	public static void main(String[] args) {
		for (int i = 0; i < 10; i++) {
			System.out.println(new IdWorker(11, 11).nextId());
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
