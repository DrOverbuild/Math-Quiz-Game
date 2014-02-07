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
public class SetSizeCommand implements Command{

	@Override
	public void execute(String[] args) {
		mathquizgame.MathQuizGame.setsize(args[0]);
	}

	@Override
	public String getName() {
		return "setsize";
	}

	@Override
	public String getShortcut() {
		return "size";
	}

	@Override
	public String getDesc() {
		return "/setsize <size>: Sets the size to <size>. <size> must be a number.";
	}

}
