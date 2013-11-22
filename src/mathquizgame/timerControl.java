/*
	Copyrigh (c) Jasper Reddin 2013
	All rights reserved
*/
package mathquizgame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;

final class timerControl implements ActionListener{
	Timer timer;
	timerControl(int millisec, boolean repeats){
		System.out.println("Creating new timer.");
		timer = new Timer(millisec, this);
		timer.setRepeats(repeats);
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
		int secs = timer.getDelay() / 1000;
		MathQuizGame.EnterText("\nTimer reached " + secs + " seconds.");
		MathOperator.endGame();
	}
	
}