package com.api;

import java.util.Scanner;

public class InterfaceDemo
{
	public static void main(String[] args)
	{
		Transformer transformer = new Transformer();
		transformer.setWeapon(new Sword());
		transformer.attack();

		transformer.setWeapon(new AK47());
		transformer.attack();

		transformer.setWeapon(new Lyb());
		transformer.attack();

		System.out.println();
		System.out.println();
		System.out.println();
		System.out.println("Random use weapon to attacked");

		Sword s1 = new Sword();
		AK47 ak1 = new AK47();
		Lyb lyb = new Lyb();

		while (true)
		{
			double n = Math.random();
			if (n < 0.3333)
			{
				transformer.setWeapon(s1);
			} else if (n < 0.6666)
			{
				transformer.setWeapon(ak1);
			} else
			{
				transformer.setWeapon(lyb);
			}
			transformer.attack();
			
			new Scanner(System.in).nextLine();
		}

	}
}

// 接口中所有成员都是public的
interface Weapon
{
	// public static final是默认存在的，可以省略。
	// 接口中只能定义常量，不能定义变量
	int TYPE_COLD = 1;
	int TYPE_HOT = 2;
	int TYPE_NUCLEAR = 3;

	// 接口中方法必须是public abstract,所以这两个关键字可省略不写
	void kill();

	String getName();

	int getType();

}

class Sword implements Weapon
{
	@Override
	public void kill()
	{
		System.out.println("Play Sword");
	}

	@Override
	public String getName()
	{
		return "Yi Tian Jian";
	}

	@Override
	public int getType()
	{
		return Weapon.TYPE_COLD;
	}
}

class AK47 implements Weapon
{

	@Override
	public void kill()
	{
		System.out.println("Play AK47");
	}

	@Override
	public String getName()
	{
		return "AK47";
	}

	@Override
	public int getType()
	{
		return Weapon.TYPE_HOT;
	}
}

class Lyb implements Weapon
{

	@Override
	public void kill()
	{
		System.out.println("Play LangYaBang");
	}

	@Override
	public String getName()
	{

		return "Lang Ya Bang";
	}

	@Override
	public int getType()
	{
		return Weapon.TYPE_NUCLEAR;
	}
}

class Transformer
{
	private Weapon weapon;

	public void setWeapon(Weapon weapon)
	{
		this.weapon = weapon;
	}

	public void attack()
	{
		System.out.println("Transformer attack.");
		if (weapon == null)
		{
			System.out.println("No weapon, will bite.");
			return;
		}

		String type = "";
		switch (weapon.getType())
		{
		case Weapon.TYPE_COLD:
			type = "Cold Weapon";
			break;
		case Weapon.TYPE_HOT:
			type = "Hot Weapon";
			break;
		case Weapon.TYPE_NUCLEAR:
			type = "Nuclear Weapon";
			break;
		}

		System.out.println("Use " + type + weapon.getName() + " attacked.");
		weapon.kill();

	}

}
