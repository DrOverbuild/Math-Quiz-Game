/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mathquizgame.commands;

/**
 *
 * @author jasper
 */
public class AutomaticTextCommand implements Command{

	@Override
	public void execute(String[] args) {
		mathquizgame.MathQuizGame.automaticText = !mathquizgame.MathQuizGame.automaticText;
		mathquizgame.MathQuizGame.EnterText("Automatic Text toggled.");
	}

	@Override
	public String getName() {
		return "automatictext";
	}

	@Override
	public String getShortcut() {
		return "autotext";
	}

	@Override
	public String getDesc() {
		return "Toggles the ability to not have to press enter when the correct answer is compeletely typed.";
	}

}
