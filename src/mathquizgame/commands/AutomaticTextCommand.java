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
		if (args.length != 1){
			mathquizgame.MathQuizGame.automaticText = !mathquizgame.MathQuizGame.automaticText;
			mathquizgame.MathQuizGame.EnterText("Automatic Text now is " + mathquizgame.MathQuizGame.automaticText);
		}else{
			if (args[0].toLowerCase().startsWith("t")){
				mathquizgame.MathQuizGame.automaticText = true;
				mathquizgame.MathQuizGame.EnterText("Automatic Text now is true");
			}else if (args[0].toLowerCase().startsWith("f")){
				mathquizgame.MathQuizGame.automaticText = false;
				mathquizgame.MathQuizGame.EnterText("Automatic Text now is false");
			}
		}
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
		return "/autotext: Toggles the ability to not have to press enter when the correct answer is compeletely typed.";
	}

}
