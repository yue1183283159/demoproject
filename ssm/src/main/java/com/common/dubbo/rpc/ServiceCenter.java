package com.common.dubbo.rpc;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Method;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServiceCenter implements IServer {

	// 根据运行时获取机器cpu数来初始化线程池
	private static ExecutorService threadPool = Executors
			.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
	// 注册的服务
	private static final HashMap<String, Class> registeredServiceMap = new HashMap<>();
	private static volatile boolean isRunning = false;
	private static int port;

	public ServiceCenter(int port) {
		this.port = port;
		isRunning = true;
	}

	@Override
	public void stop() {
		isRunning = false;
		threadPool.shutdown();
	}

	@Override
	public void start() {
		ServerSocket serverSocket;
		try {
			serverSocket = new ServerSocket();
			serverSocket.bind(new InetSocketAddress(port));

			System.out.println("start server...");

			while (isRunning) {
				// 监听客户端的tcp连接，接到tcp连接之后将其封装成task交给线程池执行
				threadPool.execute(new ServiceTask(serverSocket.accept()));

			}

		} catch (IOException e) {

			e.printStackTrace();
		}

	}

	@Override
	public void register(Class serviceInterface, Class impl) {
		registeredServiceMap.put(serviceInterface.getName(), impl);
	}

	@Override
	public boolean isRunning() {
		return isRunning;
	}

	@Override
	public int getPort() {
		return port;
	}

	private static class ServiceTask implements Runnable {

		Socket client = null;

		public ServiceTask(Socket client) {
			this.client = client;
		}

		@Override
		public void run() {
			ObjectInputStream input = null;
			ObjectOutputStream output = null;
			try {
				// 2. 将客户端发送的码流反序列化成对象，反射调用服务实现者，获取执行结果
				input = new ObjectInputStream(client.getInputStream());
				String serviceName = input.readUTF();
				String methodName = input.readUTF();
				Class<?>[] parameterTypes = (Class<?>[]) input.readObject();
				Object[] arguments = (Object[]) input.readObject();
				Class serviceClass = registeredServiceMap.get(serviceName);
				if (serviceClass == null) {
					throw new ClassNotFoundException(serviceName + " not found");
				}

				Method method = serviceClass.getMethod(methodName, parameterTypes);
				// 执行目标方法，得到返回结果
				Object result = method.invoke(serviceClass.newInstance(), arguments);
				// 3. 将执行结果序列化，通过socket发送给客户端
				output = new ObjectOutputStream(client.getOutputStream());
				output.writeObject(result);

			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (output != null) {
					try {
						output.close();
					} catch (Exception e) {
						e.printStackTrace();
					} finally {
						output = null;
					}
				}

				if (input != null) {
					try {
						input.close();
					} catch (Exception e) {
						e.printStackTrace();
					} finally {
						input = null;
					}
				}

				if (client != null) {
					try {
						client.close();
					} catch (Exception e2) {
						e2.printStackTrace();
					} finally {
						client = null;
					}
				}

			}

		}

	}

}
