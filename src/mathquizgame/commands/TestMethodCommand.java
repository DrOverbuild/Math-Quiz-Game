/*
 $ Copyright (c) 2014 Jasper Reddin
 $ All rights reserved.
 */

package mathquizgame.commands;

import mathquizgame.*;

/**
 *
 * @author jasper
 */
public class TestMethodCommand implements Command{

	@Override
	public void execute(String[] args) {
		//MathQuizGame.EnterText(""+MathOperator.randomInt(20, 40, false));
		MathQuizGame.EnterText("Nothing to test!");
	}

	@Override
	public String getName() {
		return "TestMethod";
	}

	@Override
	public String getShortcut() {
		return "tm";
	}

	@Override
	public String getDesc() {
		return "/testmethod: Used for development to test something in-game.";
	}
	
}
