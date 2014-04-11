/*
	Copyrigh (c) Jasper Reddin 2013
	All rights reserved
*/
package mathquizgame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;

public final class timerControl implements ActionListener{
	Timer timer;
	public timerControl(int millisec){
		timer = new Timer(millisec, this);
		timer.setRepeats(false);
	}
	public void start(){
		timer.start();
	}
	public void restart(){
		timer.restart();
	}
	public void stop(){
		timer.stop();
	}
	public void setInitialDelay(int delay){
		timer.setInitialDelay(delay);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		int secs = timer.getInitialDelay() / 1000;
		MathQuizGame.EnterText("\nTimer reached " + secs + " seconds.");
		MathQuizGame.endGame();
	}

}