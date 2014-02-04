/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mathquizgame.commands;

import mathquizgame.MathOperator;
import static mathquizgame.MathQuizGame.EnterText;
import static mathquizgame.MathQuizGame.setQuestionState;
import mathquizgame.NumbersAreSameException;

/**
 *
 * @author jasper
 */
public class CustomCommand implements Command{

	@Override
	public void execute(String[] args) {
		try{
			MathOperator.startGameCustom(args);
		}catch(NumbersAreSameException e){
			EnterText("The numbers used in the range must not be the same. Please pick different ones.");
			setQuestionState(1);
			mathquizgame.MathQuizGame.input.selectAll();
		}
	}

	@Override
	public String getName() {
		return "custom";
	}

	@Override
	public String getShortcut() {
		return "c";
	}


}
