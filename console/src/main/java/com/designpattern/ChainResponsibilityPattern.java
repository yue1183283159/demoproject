package com.designpattern;

/**
 * 为请求创建了一个接收者对象的链
 * 避免请求发送者与接收者耦合在一起，让多个对象都有可能接收请求，将这些对象连接成一条链，并且沿着这条链传递请求，直到有对象处理它为止。
 * 主要解决：职责链上的处理者负责处理请求，客户只需要将请求发送到职责链上即可，
 * 无须关心请求的处理细节和请求的传递，所以职责链将请求的发送者和请求的处理者解耦了
 */
public class ChainResponsibilityPattern
{
	public static void main(String[] args)
	{
		AbstractLogger loggerChain = getChainOfLogger();

		loggerChain.logMessage(AbstractLogger.INFO, "This is an information.");

		loggerChain.logMessage(AbstractLogger.DEBUG, "This is an debug level information.");

		loggerChain.logMessage(AbstractLogger.ERROR, "This is an error information.");
	}

	private static AbstractLogger getChainOfLogger()
	{
		AbstractLogger errorLogger = new ErrorLogger(AbstractLogger.ERROR);
		AbstractLogger fileLogger = new FileLogger(AbstractLogger.DEBUG);
		AbstractLogger consoleLogger = new ConsoleLogger(AbstractLogger.INFO);

		errorLogger.setNextLogger(fileLogger);
		fileLogger.setNextLogger(consoleLogger);
		return errorLogger;
	}
}

abstract class AbstractLogger
{
	public static int INFO = 1;
	public static int DEBUG = 2;
	public static int ERROR = 3;

	protected int level;
	protected AbstractLogger nextLogger;

	public void setNextLogger(AbstractLogger nextLogger)
	{
		this.nextLogger = nextLogger;
	}

	public void logMessage(int level, String message)
	{
		if (this.level <= level)
		{
			write(message);
		}
		if (nextLogger != null)
		{
			nextLogger.logMessage(level, message);
		}
	}

	abstract protected void write(String message);
}

class ConsoleLogger extends AbstractLogger
{

	public ConsoleLogger(int level)
	{
		this.level = level;
	}

	@Override
	protected void write(String message)
	{
		System.out.println("Standard Console::Logger: " + message);
	}

}

class ErrorLogger extends AbstractLogger
{

	public ErrorLogger(int level)
	{
		this.level = level;
	}

	@Override
	protected void write(String message)
	{
		System.out.println("Error Console::Logger: " + message);
	}

}

class FileLogger extends AbstractLogger
{

	public FileLogger(int level)
	{
		this.level = level;
	}

	@Override
	protected void write(String message)
	{
		System.out.println("File::Logger: " + message);
	}

}
