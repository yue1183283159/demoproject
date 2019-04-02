package com.ssm.common;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:config.properties")
public class Configuration {

	// 此类对象初始化的时候执行。
	// 初始化的时候可以读取属性文件，将配置数据封装起来
	// @PostConstruct 借助于Spring,不用自己来完成属性的封装赋值操作，使用@Value注解可以自动完成
	public void init() {
		InputStream in = null;
		try {
			Properties properties = new Properties();
			Class clazz = Configuration.class;
			in = clazz.getResourceAsStream("/config.properties");
			properties.load(in);
			uploadPath = properties.getProperty("uploadpath");
			version = properties.getProperty("version");

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (in != null) {
					in.close();
				}

			} catch (IOException e) {
				in = null;
			}
		}

	}

	// config in properties file
	@Value("${uploadpath}")
	private String uploadPath;

	@Value("${version}")
	private String version;

	public String getUploadPath() {
		if (uploadPath == null) {
			uploadPath = "/upload";
		}
		return uploadPath;
	}

	public String getVersion() {
		if (version == null) {
			version = "1.0";
		}
		return version;
	}
}
