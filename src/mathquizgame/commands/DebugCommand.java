/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mathquizgame.commands;

import javax.swing.JFrame;
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
			JFrame debugScreenFrame = new JFrame("Debug");
			DebugScreen debugScreen = new DebugScreen(debugScreenFrame);
			debugScreenFrame.setBounds(0, 0, debugScreen.getWidth(), debugScreen.getHeight());
			MathQuizGame.frame.toFront();
			Thread thread = new Thread(debugScreen);
			thread.start();

		}
		String io = "off";
		if (MathQuizGame.isDebugOn) io = "on";
		MathQuizGame.EnterText("Debug turned " + io);
	}

	@Override
	public String getName() {
		return "debug";
	}

	@Override
	public String getShortcut() {
		return "no shortcut";
	}

	@Override
	public String getDesc() {
		return "/debug: Displays a window showing you some technical stuff that happens behind the scenes.";
	}

}
