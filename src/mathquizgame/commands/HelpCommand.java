/*
 * Copyright (c) 2014. Jasper Reddin.
 * All Rights Reserved.
 */

package mathquizgame.commands;

import mathquizgame.MathQuizGame;

/**
 * Created by jasper on 1/27/14.
 */
public class HelpCommand implements Command {

	@Override
	public void execute(String[] args){
		MathQuizGame.EnterText(System.getProperty("line.separator")+"Here is a list of all the commands you can use.");
		for(Command command:CommandImpl.commands){
			MathQuizGame.EnterText(command.getDesc());
		}
		MathQuizGame.EnterText("");
	}

	public String getName(){
		return "help";
	}

	@Override
	public String getShortcut() {
		return "?";
	}

	@Override
	public String getDesc() {
		return "/help (or /?): Displays this list of commands.";
	}

}
