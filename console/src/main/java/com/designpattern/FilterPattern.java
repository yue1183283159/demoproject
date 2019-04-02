package com.designpattern;

import java.util.ArrayList;
import java.util.List;

/*
 * 使用不同的标准来过滤一组对象，通过逻辑运算以解耦的方式把它们连接起来
 */
public class FilterPattern
{

	public static void main(String[] args)
	{
		List<TestPerson> persons = new ArrayList<TestPerson>();

		persons.add(new TestPerson("Robert", "Male", "Single"));
		persons.add(new TestPerson("John", "Male", "Married"));
		persons.add(new TestPerson("Laura", "Female", "Married"));
		persons.add(new TestPerson("Diana", "Female", "Single"));
		persons.add(new TestPerson("Mike", "Male", "Single"));
		persons.add(new TestPerson("Bobby", "Male", "Single"));

		ICriteria male = new CriteriaMale();
		ICriteria female = new CriteriaFemale();
		ICriteria single = new CriteriaSingle();
		ICriteria singleMale = new AndCriteria(single, male);
		ICriteria singleOrFemale = new OrCriteria(single, female);

		System.out.println("Males: ");
		printPersons(male.meetCriteria(persons));

		System.out.println("\nFemales: ");
		printPersons(female.meetCriteria(persons));

		System.out.println("\nSingle Males: ");
		printPersons(singleMale.meetCriteria(persons));

		System.out.println("\nSingle Or Females: ");
		printPersons(singleOrFemale.meetCriteria(persons));
	}

	public static void printPersons(List<TestPerson> persons)
	{
		for (TestPerson person : persons)
		{
			System.out.println("Person : [ Name : " + person.getName() + ", Gender : " + person.getGender()
					+ ", Marital Status : " + person.getMaritalStatus() + " ]");
		}
	}
}

interface ICriteria
{
	public List<TestPerson> meetCriteria(List<TestPerson> persons);
}

class CriteriaMale implements ICriteria
{
	@Override
	public List<TestPerson> meetCriteria(List<TestPerson> persons)
	{
		List<TestPerson> malePersons = new ArrayList<TestPerson>();
		for (TestPerson person : persons)
		{
			if (person.getGender().equalsIgnoreCase("male"))
			{
				malePersons.add(person);
			}
		}
		return malePersons;
	}

}

class CriteriaFemale implements ICriteria
{

	@Override
	public List<TestPerson> meetCriteria(List<TestPerson> persons)
	{
		List<TestPerson> femalePersons = new ArrayList<TestPerson>();
		for (TestPerson p : persons)
		{
			if (p.getGender().equalsIgnoreCase("female"))
			{
				femalePersons.add(p);
			}
		}

		return femalePersons;
	}

}

class CriteriaSingle implements ICriteria
{

	@Override
	public List<TestPerson> meetCriteria(List<TestPerson> persons)
	{
		List<TestPerson> singlePersons = new ArrayList<TestPerson>();
		for (TestPerson person : persons)
		{
			if (person.getMaritalStatus().equalsIgnoreCase("SINGLE"))
			{
				singlePersons.add(person);
			}
		}
		return singlePersons;
	}
}

class AndCriteria implements ICriteria
{

	private ICriteria criteria;
	private ICriteria otherCriteria;

	public AndCriteria(ICriteria criteria, ICriteria otherCriteria)
	{
		this.criteria = criteria;
		this.otherCriteria = otherCriteria;
	}

	@Override
	public List<TestPerson> meetCriteria(List<TestPerson> persons)
	{
		List<TestPerson> firstCriteriaPersons = this.criteria.meetCriteria(persons);
		return this.otherCriteria.meetCriteria(firstCriteriaPersons);
	}

}

class OrCriteria implements ICriteria
{
	private ICriteria criteria;
	private ICriteria otherCriteria;

	public OrCriteria(ICriteria criteria, ICriteria otherCriteria)
	{
		this.criteria = criteria;
		this.otherCriteria = otherCriteria;
	}

	@Override
	public List<TestPerson> meetCriteria(List<TestPerson> persons)
	{
		List<TestPerson> firstPersons = this.criteria.meetCriteria(persons);
		List<TestPerson> otherPersons = this.otherCriteria.meetCriteria(persons);
		for (TestPerson p : otherPersons)
		{
			if (!firstPersons.contains(p))
			{
				firstPersons.add(p);
			}
		}
		return firstPersons;
	}

}

class TestPerson
{
	private String name;
	private String gender;
	private String maritalStatus;

	public String getName()
	{
		return name;
	}

	public String getGender()
	{
		return gender;
	}

	public String getMaritalStatus()
	{
		return maritalStatus;
	}

	public TestPerson(String name, String gender, String maritalStatus)
	{
		super();
		this.name = name;
		this.gender = gender;
		this.maritalStatus = maritalStatus;
	}

}
