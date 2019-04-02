package com.api;


public class ObjectDemo
{
	public static void main(String[] args)
	{
		Staff s1 = new Staff(1, "zhangsan", "Female", 23);
		Staff s2 = new Staff(1, "zhangsan", "Female", 23);
		System.out.println(s1.toString());
		System.out.println(s2.toString());
		System.out.println(s1 == s2);// 比较两个对象的内存地址
		System.out.println(s1.equals(s2));// 如果不重写，比较的也是对象的内存地址。重写之后，比较的是属性值。

		Staff s3 = s1;
		System.out.println(s3 == s1);

	}
}

class Staff
{
	private int id;
	private String name;
	private String gender;
	private int age;

	public Staff()
	{

	}

	public Staff(int id, String name, String gender, int age)
	{
		this.id = id;
		this.name = name;
		this.gender = gender;
		this.age = age;
	}

	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getGender()
	{
		return gender;
	}

	public void setGender(String gender)
	{
		this.gender = gender;
	}

	public int getAge()
	{
		return age;
	}

	public void setAge(int age)
	{
		this.age = age;
	}

	@Override
	public String toString()
	{
		return id + ", " + name + ", " + gender + ", " + age;
	}

	@Override
	public boolean equals(Object obj)
	{
		// null, this, type is Staff; return
		if (obj == null)
		{
			return false;
		}
		if (obj == this)
		{
			return true;
		}
		if (!(obj instanceof Staff))
		{
			return false;
		}

		Staff s = (Staff) obj;
		return id == s.id && name.equals(s.name) && gender.equals(s.gender) && age == s.age;

	}

}
