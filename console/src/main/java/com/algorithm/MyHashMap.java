package com.algorithm;

/**
 * 散列表，又叫哈希表，是能够通过给定的关键字的值直接访问到具体对应值的一个数据结构。把关键字映射到一个表中的位置来直接访问记录，加快访问速度。
 * 通过不同的key可能访问到同一个地址，叫做碰撞（collision）。但是通过一个key一定会得到唯一的value地址
 */
public class MyHashMap
{
	/**
	 * 默认散列表的初始化长度 实际使用中可以在初始化时传参，减少扩容操作 扩容很损耗性能
	 */
	private static final int INITIAL_CAPACITY = 4;

	/**
	 * 扩容因子
	 */
	private static final float LOAD_FACTOR = 0.75f;
	private MyEntry[] table = new MyEntry[INITIAL_CAPACITY];
	private int size = 0;// 散列表元素的个数
	private int use = 0;// 散列表使用地址的个数

	public void put(int key, int value)
	{
		int index = hash(key);
		if (table[index] == null)
		{
			table[index] = new MyEntry(-1, -1, null);
		}

		MyEntry entry = table[index];
		if (entry.next == null)
		{
			// 不存在值，向链表添加，有可能扩容，要用到table属性
			table[index].next = new MyEntry(key, value, null);
			size++;
			use++;
			// 不存在值，说明是个未用过的地址，需要判断是否需要扩容
			if (use >= table.length * LOAD_FACTOR)
			{
				resize();
			}
		} else
		{
			// 本身存在值，修改已有值
			for (entry = entry.next; entry != null; entry = entry.next)
			{
				int k = entry.key;
				if (k == key)
				{
					entry.value = value;
					return;
				}
			}

			// 不存在相同的值，直接向链表添加元素
			MyEntry temp = table[index].next;
			MyEntry newEntry = new MyEntry(key, value, temp);
			table[index].next = newEntry;
			size++;
		}
	}

	public void remove(int key)
	{
		int index = hash(key);
		MyEntry entry = table[index];
		MyEntry pre = table[index];
		if (entry != null && entry.next != null)
		{
			for (entry = entry.next; entry != null; pre = entry, entry = entry.next)
			{
				int k = entry.key;
				if (k == key)
				{
					pre.next = entry.next;
					size--;
					return;
				}
			}
		}
	}

	public int get(int key)
	{
		int index = hash(key);
		MyEntry entry = table[index];
		if (entry != null && entry.next != null)
		{
			for (entry = entry.next; entry != null; entry = entry.next)
			{
				int k = entry.key;
				if (k == key)
				{
					return entry.value;
				}
			}
		}
		return -1;
	}

	// 扩容
	private void resize()
	{
		int newlen = table.length * 2;
		MyEntry[] oldTable = table;
		table = new MyEntry[newlen];
		use = 0;
		for (int i = 0; i < oldTable.length; i++)
		{
			if (oldTable[i] != null && oldTable[i].next != null)
			{
				MyEntry entry = oldTable[i];
				while (null != entry.next)
				{
					MyEntry next = entry.next;
					// 重新计算哈希值，放入新的地址中
					int index = hash(next.key);
					if (table[index] == null)
					{
						use++;
						table[index] = new MyEntry(-1, -1, null);
					}

					MyEntry temp = table[index].next;
					MyEntry newEntry = new MyEntry(next.key, next.value, temp);
					table[index].next = newEntry;
					entry = next;
				}
			}
		}

	}

	// 根据key，通过哈希函数获取位于散列表数组中的那个位置
	private int hash(int key)
	{
		return key % table.length;
	}
}

class MyEntry
{
	int key;
	int value;
	MyEntry next;

	public MyEntry(int key, int value, MyEntry next)
	{
		super();
		this.key = key;
		this.value = value;
		this.next = next;
	}

}
