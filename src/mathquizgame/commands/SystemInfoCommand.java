/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mathquizgame.commands;

import mathquizgame.MathQuizGame;

/**
 *
 * @author jasper
 */
public class SystemInfoCommand implements Command{

	@Override
	public void execute(String[] args) {
		MathQuizGame.EnterText("");
		MathQuizGame.EnterText("Operating System: " + System.getProperty("os.name"));
		MathQuizGame.EnterText("Operating System version: " + System.getProperty("os.version"));
		MathQuizGame.EnterText("");
		MathQuizGame.EnterText("Java Version: " + System.getProperty("java.version"));
		MathQuizGame.EnterText("Java Vendor: " + System.getProperty("java.vendor"));
		MathQuizGame.EnterText("");
	}

	@Override
	public String getName() {
		return "systeminfo";
	}

	@Override
	public String getShortcut() {
		return "system";
	}

	@Override
	public String getDesc() {
		return "/systeminfo: Displays to you info about your system";
	}

}
