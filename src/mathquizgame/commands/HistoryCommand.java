/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mathquizgame.commands;

import static mathquizgame.MathQuizGame.EnterText;

/**
 *
 * @author jasper
 */
public class HistoryCommand implements Command{

	@Override
	public void execute(String[] args){
		EnterText(" --- HISTORY ---");
		for(int i = 0;i<mathquizgame.MathQuizGame.frame.inputtedLines.size();i++){
			EnterText(mathquizgame.MathQuizGame.frame.inputtedLines.get(i));
		}
		EnterText(" ---   END   ---");
	}

	@Override
	public String getName() {
		return "history";
	}

	@Override
	public String getShortcut() {
		return "displayhistory";
	}

}
