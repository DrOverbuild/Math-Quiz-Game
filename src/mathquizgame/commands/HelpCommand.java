/*
 * Copyright (c) 2014. Jasper Reddin.
 * All Rights Reserved.
 */

package mathquizgame.commands;

/**
 * Created by jasper on 1/27/14.
 */
public class HelpCommand implements Command {

	@Override
	public void execute(String[] args){
	}

	public String getName(){
		return "help";
	}

	@Override
	public String getShortcut() {
		return "?";
	}

}
