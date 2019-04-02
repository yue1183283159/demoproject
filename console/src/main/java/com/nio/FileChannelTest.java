package com.nio;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.CharBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;

public class FileChannelTest {
	public static void main(String[] args) {
		File f = new File("src/test.txt");
		try (
				// 创建FileInputStream,以该文件输入流创建FileChannel
				FileChannel inChannel = new FileInputStream(f).getChannel();
				// 以文件输出流创建FileChannel,用以控制输出.
				FileChannel outChannel = new FileOutputStream("src/a.txt").getChannel();
		) {
			//将FileChannel里的数据全部映射成ByteBuffer
			MappedByteBuffer mappedByteBuffer=inChannel.map(FileChannel.MapMode.READ_ONLY, 0, f.length());
			//使用GBK的字符集来创建解码器
			Charset charset=Charset.forName("GBK");
			//直接将buffer里的数据全部输出，即a.txt文件里面的数据变成FileChannelTest.java文件里面的数据
			outChannel.write(mappedByteBuffer);
			//再次调用buffer的clear()方法，复原limit,position的位置
			mappedByteBuffer.clear();
			
			//创建解码器对象
			CharsetDecoder decoder = charset.newDecoder();
			//使用解码器将ByteBuffer转换成CharBuffer
			CharBuffer charBuffer = decoder.decode(mappedByteBuffer);
			//CharBuffer的toString方法可以获取对应的字符串
			System.out.println(charBuffer);
			
		}catch (Exception e) {
			
		}
	}
}
