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
		StringBuilder font = new StringBuilder();
		for (int i = 0; i < args.length - 1; i++){
			font.append(args[i]).append(" ");
		}
		font.append(args[args.length-1]);
		mathquizgame.MathQuizGame.setfont(font.toString());
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
