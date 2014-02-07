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
public class SetFontCommand implements Command{

	@Override
	public void execute(String[] args){
		mathquizgame.MathQuizGame.setfont(args[0]);
	}

	@Override
	public String getName() {
		return "setfont";
	}

	@Override
	public String getShortcut() {
		return "font";
	}

	@Override
	public String getDesc() {
		return "/setfont <font>: Sets the font of the output and input to <font>";
	}

}
