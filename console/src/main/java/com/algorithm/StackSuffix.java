package com.algorithm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

/**
 * 利用栈实现后缀表达式。后缀表达式主要是为了将表达式转换成利于计算机计算的一种表达方式。
 * 正常的表达式a+b*c是中缀表达式，后缀表达式是：abc*+,将运算符置于数字之后。
 * 中缀表达式必然存在后缀表达式，后缀表达式不存在优先级问题，只需要利用栈进行“从左至右依次计算”即可 后缀表达式计算方法：
 * 将后缀表达式从左到右一次遍历，如果当前元素为数字则入栈，如果为操作符，则pop出栈两个元素(第一次pop出的是右操作数，第二次pop出的是左操作数)进行运算
 * 然后将计算的结果再次入栈，直至表达式结束，此时操作数栈内理应只剩一个元素即表达式结果
 */
public class StackSuffix {
	// 存储输入的中缀表达式，正常的表达式
	private static List<String> infixExpression = new ArrayList<>();
	// 存储转换的后缀表达式
	private static List<String> suffixExpression = new ArrayList<>();
	// 用于存储计算后缀表达式的操作数的栈
	private static Deque<Double> numStack = new LinkedList<>();

	private static void pushNum(double e) {
		numStack.push(e);
	}

	private static double popNum() {
		return numStack.pop();
	}

	private static double calculate() {
		// 遍历后缀表达式，输出表达式，检查后缀表达式
		for (String exp : suffixExpression) {
			System.out.print(exp);
		}
		System.out.println();

		// 后缀表达式的计算过程，遍历整个后缀表达式，理论上中途遇到“=”则结束遍历。
		for (String exp : suffixExpression) {
			// 如果当前元素不是数字并且是=，这说明到达表达式末尾了，此时弹出栈内元素作为计算结果返回
			if (!isNumeric(exp) && "=".equals(exp)) {
				return popNum();
			} else if (isNumeric(exp)) {
				// 如果当前元素是数字，将其转换成double类型并入栈
				pushNum(Double.valueOf(exp));
			} else {
				// 如果当前元素不是数字，也不是=，说明是一个运算符（后缀表达式没有括号）
				// 此时按计算方式pop出栈顶两个元素进行计算将结果重新入栈
				// 用于暂存栈顶两个元素的计算结果
				double temp = 0.0;
				// 第一次pop出的数字为右操作数，第二次pop出的数字为左操作数
				double rear = popNum();
				double front = popNum();
				switch (exp) {
				case "+":
					temp = front + rear;
					pushNum(temp);
					break;
				case "-":
					temp = front - rear;
					pushNum(temp);
					break;
				case "*":
					temp = front * rear;
					pushNum(temp);
					break;
				case "/":
					temp = front / rear;
					pushNum(temp);
					break;
				default:
					break;
				}
			}
		}

		return 0;
	}

	private static boolean isNumeric(String str) {
		Pattern pattern = Pattern.compile("[0-9]*");
		return pattern.matcher(str).matches();
	}

	// 用于存储计算后缀表达式的操作数的栈
	private static Deque<String> operStack = new LinkedList<>();

	private static void pushOper(String oper) {
		operStack.push(oper);
	}

	private static String popOper() {
		return operStack.pop();
	}

	// 将中缀表达式转换成后缀表达式
	// 1. 遍历中缀表达式
	// 2. 如果当前中缀元素为操作数，直接将此操作数输入到后缀表达式尾端
	// 3. 如果当前中缀元素为*，/,(，直接push到操作符栈
	// 4. 如果当前中缀元素为),依次pop出栈顶操作符，输入到后缀表达式尾端，直至pop出一个(停止，并丢弃该(
	// 5.
	// 如果当前中缀元素为'+'或'-'，则依次pop出栈顶操作符、“输出”到后缀表达式尾端，直至栈底（栈空）或pop得到了一个'('，若pop得到一个'('，将'('重新push入栈。达到这两个条件之一后，将此操作符（'+'或'-'）入栈。
	// 6. 如果当前中缀元素为'='，则依次pop出栈顶操作符、“输出”到后缀表达式尾端，直至栈底（栈空）。
	private static void translate() {
		// 遍历中缀表达式，将其中存储的中缀表达式转换成后缀表达式并存入后缀表达式集合中
		for (int i = 0; i < infixExpression.size(); i++) {
			String exp = infixExpression.get(i);
			// 当前中缀元素为数字，直接将其放入后缀表达式集合中
			if (isNumeric(exp)) {
				suffixExpression.add(exp);
			} else {
				// 如果当前中缀元素不是数字，择我们需要根据值决定应选择的栈的操作。中缀下标和后缀下标不同步。
				switch (exp) {
				case "(":
					isLeft();// 当前元素为'('时，我们调用IsLeft()，因为'('必然是直接入栈，所以我们的j不会发生变化
					break;
				case ")":
					isRight();// 当前元素为')'时调用IsRight()，因为')'可能导致输出元素至后缀表达式，所以需要知道后缀的下标j，并且j可能会发生变化，我们将j的地址传递过去
					break;
				case "+":
					isAdd();// 当前元素为'+'时调用IsAdd()，因为'+'可能导致输出元素至后缀表达式，所以需要知道后缀的下标j，并且j可能会发生变化，我们将j的地址传递过去
					break;
				case "-":
					isSub();// 当前元素为'-'时调用IsSub()，因为'-'可能导致输出元素至后缀表达式，所以需要知道后缀的下标j，并且j可能会发生变化，我们将j的地址传递过去
					break;
				case "*":
					isMulti();// 当前元素为'*'时调用IsMult()，因为'*'直接入栈，所以j不会发生变化，不需
					break;
				case "/":// 当前元素为'/'时调用IsDiv()，因为'/'直接入栈，所以j不会发生变化，不需要传递
					isDiv();
					break;
				case "=":// 当前元素为'='时调用IsEqual()，因为'='会导致输出元素至后缀表达式，所以需要知道j
					isEqual();
					break;
				default:
					break;

				}
			}
		}
	}

	// 如果是'='，则依次弹出栈顶元素输出至后缀表达式，直至栈空
	private static void isEqual() {
		while (!operStack.isEmpty()) {
			String oper = popOper();
			suffixExpression.add(oper);
		}
		suffixExpression.add("=");
	}

	// '*'和'/'都直接入栈
	private static void isDiv() {
		pushOper("/");

	}

	private static void isMulti() {
		pushOper("*");

	}

	// 如果是'-'则依次pop栈顶操作符，直至pop所得为'('或栈为空，若pop得到'('需要将其重新push入栈
	// pop至上述两种情况之一后，将'-'入栈
	private static void isSub() {
		while (!operStack.isEmpty()) {
			String oper = popOper();
			if ("(".equals(oper)) {
				pushOper(oper);
				break;
			} else {
				suffixExpression.add(oper);
			}

		}

		pushOper("-");
	}

	// 如果是'+'则依次pop栈顶操作符，直至pop所得为'('或栈为空，若pop得到'('需要将其重新push入栈
	// pop至上述两种情况之一后，将'+'入栈
	private static void isAdd() {
		while (!operStack.isEmpty()) {
			String oper = popOper();
			if ("(".equals(oper)) {
				pushOper(oper);
				break;
			} else {
				suffixExpression.add(oper);
			}

		}

		pushOper("+");

	}

	// 如果是')'则弹出栈顶元素直至栈顶元素为'('，当栈顶元素为'('时弹出并丢弃
	private static void isRight() {
		while (!operStack.isEmpty()) {
			String oper = popOper();
			if ("(".equals(oper)) {
				return;
			} else {
				suffixExpression.add(oper);
			}

		}

	}

	// 如果是'('则直接pushOper()
	private static void isLeft() {
		pushOper("(");
	}

	public static void main(String[] args) {
		// suffixExpression.add("1");
		// suffixExpression.add("4");
		// suffixExpression.add("2");
		// suffixExpression.add("/");
		// suffixExpression.add("+");
		// suffixExpression.add("=");
		// System.out.println("Result:" + calculate());

		// 中缀表达式：1+2*3=
		infixExpression.add("1");
		infixExpression.add("+");
		infixExpression.add("2");
		infixExpression.add("*");
		infixExpression.add("3");
		infixExpression.add("=");
		System.out.println("1+2*3=");
		translate();
		System.out.println("Result:" + calculate());
		System.out.println();

		infixExpression.clear();
		suffixExpression.clear();
		operStack.clear();
		numStack.clear();

		// 中缀表达式：1+2*(1+1)=
		infixExpression.add("1");
		infixExpression.add("+");
		infixExpression.add("2");
		infixExpression.add("*");
		infixExpression.add("(");
		infixExpression.add("1");
		infixExpression.add("+");
		infixExpression.add("1");
		infixExpression.add(")");
		infixExpression.add("=");
		System.out.println("1+2*(1+1)=");
		translate();
		System.out.println("Result:" + calculate());
		System.out.println();

		infixExpression.clear();
		suffixExpression.clear();
		operStack.clear();
		numStack.clear();

		System.out.println("input the expression:");
		String inputExp = new Scanner(System.in).nextLine();
		infixExpression.addAll(Arrays.asList(inputExp.split("")));
		translate();
		System.out.println("Result:" + calculate());
	}
}
