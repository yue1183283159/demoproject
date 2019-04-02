package com.webserver;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.sun.org.apache.xalan.internal.xsltc.DOM;

/**
 * 用于存储服务器相关的一些参数信息
 */
public class ServerContext
{
	public static String PROTOCOL;
	public static int PORT;
	public static int MAX_THREAD;
	public static String WEB_ROOT;
	//浏览器请求资源的后缀名和对应的响应数据类型
	public static Map<String, String> Types;
	public static String NotFoundPage;
	
	static //静态代码块是在类被加载的时候执行，因为类只会加载一次，所以这个代码块也只会执行一次
	{
		init();
	}

	// 解析config.xml文件，获取配置参数信息，封装到ServerContext类中
	private static void init()
	{
		try
		{
			SAXReader reader = new SAXReader();
			Document document = reader.read("src/com/webserver/config.xml");
			Element root = document.getRootElement();

			Element connEle = root.element("service").element("connector");
			PROTOCOL = connEle.attributeValue("protocol");
			PORT = Integer.parseInt(connEle.attributeValue("port"));
			MAX_THREAD = Integer.parseInt(connEle.attributeValue("maxThread"));
			WEB_ROOT = root.element("service").elementText("webroot");
			NotFoundPage=root.element("service").elementText("not-found-page");
			
			Types=new HashMap<>();
			List<Element> mappingEles=root.element("type-mappings").elements();
			for(Element mappingEle:mappingEles) 
			{
				Types.put(mappingEle.attributeValue("ext"), mappingEle.attributeValue("type"));
			}
			
		} catch (Exception e)
		{
			e.printStackTrace();
		}

	}
}
