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
public class ClearCommand implements Command{

	@Override
	public void execute(String[] args){
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public String getName() {
		return "clear";
	}

	@Override
	public String getShortcut() {
		return "clr";
	}

}
