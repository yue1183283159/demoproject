package com.designpattern;

import java.util.ArrayList;
import java.util.List;

/**
 * 一种数据驱动的设计模式，它属于行为型模式.请求以命令的形式包裹在对象中，并传给调用对象。调用对象寻找可以处理该命令的合适的对象，并把该命令传给相应的对象，该对象执行命令
 * 
 */
public class CommandPattern
{
	public static void main(String[] args)
	{
		Stock abcStock = new Stock();
		BuyStock buyStock = new BuyStock(abcStock);
		SellStock sellStock = new SellStock(abcStock);
		Broker broker = new Broker();
		broker.takeOrder(buyStock);
		broker.takeOrder(sellStock);
		broker.placeOrder();
	}
}

class Broker
{
	private List<Order> orders = new ArrayList<>();

	public void takeOrder(Order order)
	{
		orders.add(order);
	}

	public void placeOrder()
	{
		for (Order order : orders)
		{
			order.execute();
		}

		orders.clear();
	}
}

interface Order
{
	void execute();
}

class Stock
{
	private String name = "ABC";
	private int quantity = 10;

	public void buy()
	{
		System.out.println("Stock [ Name: " + name + ", Quantity: " + quantity + " ] bought");
	}

	public void sell()
	{
		System.out.println("Stock [ Name: " + name + ", Quantity: " + quantity + " ] sold");
	}
}

class BuyStock implements Order
{

	private Stock abcStock;

	public BuyStock(Stock abcStock)
	{
		this.abcStock = abcStock;
	}

	@Override
	public void execute()
	{
		abcStock.buy();

	}

}

class SellStock implements Order
{

	private Stock abcStock;

	public SellStock(Stock abcStock)
	{
		this.abcStock = abcStock;
	}

	@Override
	public void execute()
	{
		abcStock.sell();
	}

}
