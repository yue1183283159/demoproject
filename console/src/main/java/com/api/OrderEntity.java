package com.api;

import java.io.Serializable;

public class OrderEntity implements Serializable
{

	private static final long serialVersionUID = 1L;

	private int orderId;
	private double payment;

	public int getOrderId()
	{
		return orderId;
	}

	public void setOrderId(int orderId)
	{
		this.orderId = orderId;
	}

	public double getPayment()
	{
		return payment;
	}

	public void setPayment(double payment)
	{
		this.payment = payment;
	}

}
