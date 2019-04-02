package com.spring.aop;

import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements OrderService
{

	@Override
	public int saveOrder()
	{
		System.out.println("Save Order Business WorkFlow.");
		return 0;
	}

}
