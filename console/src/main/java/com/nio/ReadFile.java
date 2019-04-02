package com.nio;

import java.io.FileInputStream;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;

public class ReadFile {
	public static void main(String[] args) {
		try (
				// 创建文件输入流
				FileInputStream fis = new FileInputStream("src/test.txt");
				// 创建一个FileChannel
				FileChannel fc = fis.getChannel()) {
			// 定义一个ByteBuffer对象，用于重复取水
			ByteBuffer byteBuffer = ByteBuffer.allocate(256);
			// 将FileChannel中的数据放入ByteBuffer中
			while (fc.read(byteBuffer) != -1) {
				// 锁定Buffer的空白区，将数据的区域“封印”起来，避免程序从Buffer中取出null值
				byteBuffer.flip();
				// 创建Charset对象
				Charset charset = Charset.forName("GBK");
				// 创建解码器对象
				CharsetDecoder decoder = charset.newDecoder();
				// 将ByteBuffer的内容转码
				CharBuffer charBuffer = decoder.decode(byteBuffer);
				System.out.println(charBuffer);
				// 将Buffer初始化。为下一次读取数据做准备
				byteBuffer.clear();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
