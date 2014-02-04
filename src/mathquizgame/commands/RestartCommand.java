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
public class RestartCommand implements Command {

	@Override
	public void execute(String[] args) {
		mathquizgame.MathQuizGame.restart();
	}

	@Override
	public String getName() {
		return "restart";
	}

	@Override
	public String getShortcut() {
		return "y";
	}

}
