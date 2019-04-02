package com.servlet;

import java.util.Date;

public class Person
{
	private int id;
	private String name;
	private String email;
	private Date birthDate;
	private boolean isValid;

	public Date getBirthDate()
	{
		return birthDate;
	}

	public void setBirthDate(Date birthDate)
	{
		this.birthDate = birthDate;
	}

	public boolean isValid()
	{
		return isValid;
	}

	public void setValid(boolean isValid)
	{
		this.isValid = isValid;
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

	public String getEmail()
	{
		return email;
	}

	public void setEmail(String email)
	{
		this.email = email;
	}

	public Person()
	{
	}
	
	public Person(int id, String name, String email, Date birthDate, boolean isValid)
	{
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.birthDate = birthDate;
		this.isValid = isValid;
	}
}
