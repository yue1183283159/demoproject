package com.servlet;

public class SysUser
{
	private int id;
	private String name;
	private String pwd;
	private String gender;
	private String email;
	private String birthDate;

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

	public String getPwd()
	{
		return pwd;
	}

	public void setPwd(String pwd)
	{
		this.pwd = pwd;
	}

	public String getGender()
	{
		return gender;
	}

	public void setGender(String gender)
	{
		this.gender = gender;
	}

	public String getEmail()
	{
		return email;
	}

	public void setEmail(String email)
	{
		this.email = email;
	}

	public String getBirthDate()
	{
		return birthDate;
	}

	public void setBirthDate(String birthDate)
	{
		this.birthDate = birthDate;
	}
}

