package com.common.dubbo.rpc;

public interface IServer {
	void stop();

	void start();

	void register(Class serviceInterface, Class impl);

	boolean isRunning();

	int getPort();
}
