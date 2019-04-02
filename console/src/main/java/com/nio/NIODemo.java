package com.nio;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.RandomAccessFile;
import java.io.Serializable;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;

public class NIODemo
{

	public static void main(String[] args) throws FileNotFoundException, IOException
	{
		// TODO Auto-generated method stub
		// fileReader1();
		// fileInputStream();
		// fileWriter();
		// serializeObj();
		/*
		 * try { //Person person = deserializePerson();
		 * //System.out.println(person.getName()); } catch (ClassNotFoundException e) {
		 * e.printStackTrace(); }
		 */

		//channelDemo();
		//copyConentToSelfFile();
		//fileChannel();
	}

	static void fileChannel() {
		try(FileInputStream fStream=new FileInputStream("new.txt");
				FileChannel fChannel=fStream.getChannel();
				){
			ByteBuffer buffer=ByteBuffer.allocate(256);
			while (fChannel.read(buffer)!=-1)
			{
				buffer.flip();
				Charset charset=Charset.forName("UTF-8");
				CharsetDecoder decoder=charset.newDecoder();
				CharBuffer cBuffer=decoder.decode(buffer);
				System.out.println(cBuffer.toString());
				buffer.clear();
			}
			
		} catch (FileNotFoundException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		
	}
	
	static void copyConentToSelfFile()
	{
		File file = new File("new.txt");
		try (RandomAccessFile randomAccessFile = new RandomAccessFile(file, "rw");
				FileChannel randomChannel = randomAccessFile.getChannel();)
		{
			ByteBuffer buffer = randomChannel.map(FileChannel.MapMode.READ_ONLY, 0, file.length());
			randomChannel.position(file.length());
			randomChannel.write(buffer);
		} catch (FileNotFoundException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	static void fileReader1()
	{
		try (FileReader fReader = new FileReader("test.txt");)
		{
			char[] cs = new char[32];
			int hasRead = 0;
			while ((hasRead = fReader.read(cs)) > 0)
			{
				System.out.print(new String(cs, 0, hasRead));
			}
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	static void fileInputStream()
	{
		try (FileInputStream fileInputStream = new FileInputStream("test.txt");
				FileOutputStream fileOutputStream = new FileOutputStream("new.txt");)
		{
			byte[] bs = new byte[32];
			int hasRead = 0;
			while ((hasRead = fileInputStream.read(bs)) > 0)
			{
				fileOutputStream.write(bs, 0, hasRead);
			}
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	static void fileWriter()
	{
		try (FileWriter fWriter = new FileWriter("new.txt");)
		{
			fWriter.write("test data.");
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	static void serializeObj() throws FileNotFoundException, IOException
	{
		/*Person1 person = new Person1();
		person.setId(1);
		person.setName("sa");
		try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream("person.txt")))
		{
			objectOutputStream.writeObject(person);
		}*/
	}

	static Person1 deserializePerson() throws FileNotFoundException, IOException, ClassNotFoundException
	{
		try (ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream("person.txt")))
		{
			Person1 person = (Person1) objectInputStream.readObject();
			return person;
		}

	}

	static void channelDemo()
	{
		File file = new File("test.txt");
		try (FileChannel inChannel = new FileInputStream(file).getChannel();
				FileChannel outChannel = new FileOutputStream("new1.txt").getChannel();)
		{
			MappedByteBuffer buffer = inChannel.map(FileChannel.MapMode.READ_ONLY, 0, file.length());
			Charset charset = Charset.forName("UTF-8");
			outChannel.write(buffer);
			buffer.clear();
			CharsetDecoder decoder = charset.newDecoder();
			CharBuffer charBuffer = decoder.decode(buffer);
			System.out.println(charBuffer.toString());
		} catch (FileNotFoundException e)
		{
			e.printStackTrace();
		} catch (IOException e)
		{
			e.printStackTrace();
		}

	}

}

class Person1 implements Serializable
{
	private int id;
	private String name;

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

}

