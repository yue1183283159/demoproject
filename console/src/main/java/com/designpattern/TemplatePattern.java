package com.designpattern;

/**
 * 一个抽象类公开定义了执行它的方法的方式、模板。它的子类可以按需重写方法的实现，但是调用将以抽象类中定义的方式进行
 * 模板方法使得子类可以不改变一个算法的结构即可重定义该算法的某些特定步骤
 */
public class TemplatePattern {
	public static void main(String[] args) {
		Game game = new Cricket();
		game.play();

		game = new Football();
		game.play();
	}
}

abstract class Game {
	abstract void initialize();

	abstract void startPlay();

	abstract void endPlay();

	// 模板
	public final void play() {
		initialize();
		startPlay();
		endPlay();
	}
}

class Football extends Game {

	@Override
	void endPlay() {
		System.out.println("Football Game Finished!");
	}

	@Override
	void initialize() {
		System.out.println("Football Game Initialized! Start playing.");
	}

	@Override
	void startPlay() {
		System.out.println("Football Game Started. Enjoy the game!");
	}

}

class Cricket extends Game {

	@Override
	void initialize() {
		System.out.println("Cricket Game Initialized! Start playing.");

	}

	@Override
	void startPlay() {
		System.out.println("Cricket Game Started. Enjoy the game!");

	}

	@Override
	void endPlay() {
		System.out.println("Cricket Game Finished!");

	}

}
