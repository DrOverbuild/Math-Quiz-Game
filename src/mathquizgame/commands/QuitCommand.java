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
public class QuitCommand implements Command{

	@Override
	public void execute(String[] args){
		mathquizgame.MathQuizGame.EnterText("Quiting...");
		mathquizgame.MathQuizGame.quit();
	}

	@Override
	public String getName() {
		return "quit";
	}

	@Override
	public String getShortcut() {
		return "n";
	}

	@Override
	public String getDesc() {
		return "/quit (or n): Quits the game.";
	}

}
