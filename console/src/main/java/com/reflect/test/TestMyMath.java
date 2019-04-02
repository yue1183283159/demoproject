package com.reflect.test;

import com.reflect.MyMath;
import com.reflect.annotation.Test;

public class TestMyMath
{
	@Test
	public boolean add()
	{
		System.out.println("通过@Test运行了add方法");
		return true;
	}

	public boolean testAdd()
	{
		int result = MyMath.add(1, 1);
		if (result == 2)
		{
			return true;
		}

		return false;
	}
}
