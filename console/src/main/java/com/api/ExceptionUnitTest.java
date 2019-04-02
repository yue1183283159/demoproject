package com.api;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.io.StringWriter;

import org.junit.Test;

import com.sun.javafx.logging.PulseLogger;

public class ExceptionUnitTest
{
	@Test
	public void testTryCatch()
	{
		try
		{
			FileInputStream fileInputStream = new FileInputStream("");
		} catch (FileNotFoundException e)
		{
			System.out.println("出错之后执行");
		}

	}

	@Test
	public void testCustomException()
	{
		ItemService itemService = new ItemService();
		try
		{
			itemService.query();
		} catch (Exception e)
		{
			// e.getMessage();// only one row exception message, less info.

			StringWriter stringWriter = new StringWriter();
			PrintWriter printWriter = new PrintWriter(stringWriter);
			e.printStackTrace(printWriter);
			String info = stringWriter.toString();
			RandomAccessFile randomAccessFile;
			try
			{
				randomAccessFile = new RandomAccessFile("exception.txt", "rw");
				long len = randomAccessFile.length();
				randomAccessFile.seek(len);
				randomAccessFile.writeUTF(info);
				randomAccessFile.close();
			} catch (Exception e1)
			{
				e1.printStackTrace();
			}

		}
	}

}

class ItemService
{
	public void query() throws Exception
	{

		// try
		// {
		ItemEntity itemEntity = new ItemEntity();
		itemEntity.setTitle(null);
		// } catch (Exception e)
		// {
		// // save exception info to file
		// // send exception email
		// // send message
		// // for dev to handle the exception
		// e.printStackTrace();
		// }
	}
}

class StoreException extends Exception
{

}

class OrderTitleIsNullException extends StoreException
{
	@Override
	public String getMessage()
	{
		return "Order title is null.";
	}
}

class OrderUseNameIsNullException extends StoreException
{
	@Override
	public String getMessage()
	{
		return "Order usename is null.";
	}
}

class ItemEntity
{
	public void setTitle(String title) throws OrderTitleIsNullException
	{
		if (title == null)
		{
			OrderTitleIsNullException oIsNullException = new OrderTitleIsNullException();
			throw oIsNullException;
		}
	}

	public void setUserName(String userName) throws OrderUseNameIsNullException
	{
		if (userName == null)
		{
			OrderUseNameIsNullException useNameIsNullException = new OrderUseNameIsNullException();
			throw useNameIsNullException;
		}
	}
}
