/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mathquizgame.commands;

import mathquizgame.DebugScreen;
import mathquizgame.MathQuizGame;

/**
 *
 * @author jasper
 */
public class DebugCommand implements Command{

	@Override
	public void execute(String[] args){
		if(MathQuizGame.isDebugOn) MathQuizGame.isDebugOn = false;
		else{
			MathQuizGame.isDebugOn = true;
			DebugScreen debugScreen = new DebugScreen();
			debugScreen.setBounds(0, 0, debugScreen.getWidth(), debugScreen.getHeight());
			MathQuizGame.frame.toFront();
			Thread thread = new Thread(debugScreen);
			thread.start();

		}
	}

	@Override
	public String getName() {
		return "debug";
	}

	@Override
	public String getShortcut() {
		return "no shortcut";
	}

}
