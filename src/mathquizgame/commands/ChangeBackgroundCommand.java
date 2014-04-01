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
public class ChangeBackgroundCommand implements Command{

	@Override
	public void execute(String[] args){
		mathquizgame.MathQuizGame.frame.changeBackground();
	}

	@Override
	public String getName() {
		return "changebackground";
	}

	@Override
	public String getShortcut(){
		return "setcolor";
	}

	@Override
	public String getDesc() {
		return "/changebackground: Sets the background color of the output panel and the text box.";
	}

}
