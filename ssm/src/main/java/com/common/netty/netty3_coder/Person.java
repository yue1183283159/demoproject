package com.common.netty.netty3_coder;

import java.io.Serializable;
import java.nio.ByteBuffer;

import io.netty.buffer.ByteBuf;

public class Person implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;
	private String name;

	public Person buildName(String name) {
		this.name = name;
		return this;

	}

	public Person buildId(int id) {
		this.id = id;
		return this;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	

}
