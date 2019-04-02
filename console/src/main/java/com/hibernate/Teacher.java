package com.hibernate;

import java.util.HashSet;
import java.util.Set;

public class Teacher
{
	private int id;
	private String name;
	private String type;
	private Set<Student> students = new HashSet<Student>();

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

	public String getType()
	{
		return type;
	}

	public void setType(String type)
	{
		this.type = type;
	}

	public Set<Student> getStudents()
	{
		return students;
	}

	public void setStudents(Set<Student> students)
	{
		this.students = students;
	}

}
