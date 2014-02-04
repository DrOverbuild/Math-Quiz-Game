/*
 * Copyright (c) 2014. Jasper Reddin.
 * All Rights Reserved.
 */

package mathquizgame.commands;

import mathquizgame.MathQuizGame;



/**
 * Created by jasper on 1/27/14.
 */
public class SayCommand implements Command {

	@Override
	public void execute(String[] args) {
		String printToLine = "";

		for(String str:args) {
			if (str != null) printToLine += str + " ";
			System.out.println(printToLine);
		}
		MathQuizGame.EnterText(printToLine);
	}

	@Override
	public String getName(){
		return "say";
	}

	@Override
	public String getShortcut() {
		return "say";
	}
}
