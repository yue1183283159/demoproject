package com.designpattern;

import sun.misc.Signal;

/**
 * 装饰器模式，向一个现有对象添加新的功能，同时不改变其结构。 创建一个装饰类，用来包装原来的类，并在保持类
 * 意图：动态地给一个对象添加一些额外的职责。就增加功能来说，装饰器模式相比生成子类更为灵活。
 * 主要解决：一般的，我们为了扩展一个类经常使用继承方式实现，由于继承为类引入静态特征，并且随着扩展功能的增多，子类会很膨胀。
 * 何时使用：在不想增加很多子类的情况下扩展类。 使用场景： 1、扩展一个类的功能。 2、动态增加功能，动态撤销。
 * 
 * 装饰模式为已有类动态附加额外的功能就像LOL、王者荣耀等类Dota游戏中， 英雄升级一样。每次英雄升级都会附加一个额外技能点学习技能。
 * 具体的英雄就是ConcreteComponent，技能栏就是装饰器Decorator，每个技能就是ConcreteDecorator；
 */
public class DecoratorPattern
{
	public static void main(String[] args)
	{
		Hero hero = new BlindMonk("Lily");
		Skill skill = new Skill(hero);
		Skill wSkill = new Skill_W(hero, "test skill");
		Skill rSkill = new Skill_R(hero, "test skillEx");
		hero.learnSkill();

	}
}

class Skill implements Hero
{

	private Hero hero;

	public Skill(Hero hero)
	{
		this.hero = hero;
	}

	@Override
	public void learnSkill()
	{
		if (hero != null)
		{
			hero.learnSkill();
		}

	}

}

class Skill_W extends Skill
{

	private String skillName;

	public Skill_W(Hero hero, String skillName)
	{
		super(hero);
		this.skillName = skillName;

	}

	@Override
	public void learnSkill()
	{
		System.out.println("Learned skill W" + skillName);
		super.learnSkill();
	}

}

class Skill_R extends Skill
{

	private String skillName;

	public Skill_R(Hero hero, String skillName)
	{
		super(hero);
		this.skillName = skillName;

	}

	@Override
	public void learnSkill()
	{
		System.out.println("Learned skill R" + skillName);
		super.learnSkill();
	}

}

// Component 英雄接口
interface Hero
{
	void learnSkill();
}

// ConcreteComponent具体英雄盲僧
class BlindMonk implements Hero
{

	private String name;

	public BlindMonk(String name)
	{
		this.name = name;
	}

	@Override
	public void learnSkill()
	{
		System.out.println(name + " learned new skills.");
	}

}