package com.designpattern;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Spring AOP的原理就是java的动态代理机制 在java的动态代理机制中，有两个重要的类或接口，一个是
 * InvocationHandler(Interface)、另一个则是 Proxy(Class)，这一个类和接口是实现我们动态代理所必须用到的
 **/
public class DynamicProxy {
	public static void main(String[] args) {
		//要代理的真是对象
		Subject realSubject=new RealSubject();
		//要代理那个真实对象，就将该对象传入其动态代理类中
		InvocationHandler handler=new SubjectDynamicProxy(realSubject);
		//通过Proxy的newProxyInstance方法来创建我们的代理对象
		//第一个参数 handler.getClass().getClassLoader() ，使用handler这个类的ClassLoader对象来加载我们的代理对象
		//第二个参数realSubject.getClass().getInterfaces()，我们这里为代理对象提供的接口是真实对象所实行的接口，表示我要代理的是该真实对象，这样我就能调用这组接口中的方法了
		//第三个参数handler， 我们这里将这个代理对象关联到了上方的 InvocationHandler 这个对象上
		Subject subject= (Subject)Proxy.newProxyInstance(handler.getClass().getClassLoader(), realSubject.getClass().getInterfaces(), handler);
		subject.rent();
		subject.hello("World ");
	}
}

/**
 * 动态代理类，实现InvocationHandler接口
 *
 *
 */
class SubjectDynamicProxy implements InvocationHandler {

	private Object subject;

	// 构造方法，给我们要代理的真是对象附初始值
	public SubjectDynamicProxy(Object subject) {
		this.subject = subject;
	}

	/**
	 * proxy: 指代我们所代理的那个真实对象 
	 * method： 指代的是我们所要调用真实对象的某个方法的method对象 args:
	 * 指代的是调用真实对象某个方法时接受的参数
	 **/
	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		// 在代理真实对象钱，添加自定操作，比如日志，记录时间等
		System.out.println("before rent house");
		System.out.println("Method:" + method);

		// 当代理对象调用真实对象的方法时，其会自动的跳转到代理对象关联的handler对象的invoke方法来进行调用
		method.invoke(subject, args);
		///Object result=method.invoke(subject, args);//如果方法有结果返回

		// 在代理真实对象后我们也可以添加一些自己的操作
		System.out.println("after rent house");
		
		return null;
		//return result;
	}

}

interface Subject {
	void rent();

	void hello(String str);
}

class RealSubject implements Subject {

	@Override
	public void rent() {
		System.out.println("I want to rent my house");
	}

	@Override
	public void hello(String str) {
		System.out.println("hello: " + str);
	}

}