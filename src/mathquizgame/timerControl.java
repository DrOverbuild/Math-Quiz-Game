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
	int secondsElapsed = 0;
	int overallDelaySeconds = 0;
	String threadName = "";
	public timerControl(int seconds){
		timer = new Timer(1000, this);
		timer.setRepeats(true);
		timer.setInitialDelay(1000);
		overallDelaySeconds = seconds;
	}
	public void start(){
		MathQuizGame.frame.setTitle("MathQuizGame " + MathQuizGame.VERSION_ID + " — " + (overallDelaySeconds) + " seconds remaining.");
		timer.start();
	}
	public void restart(){
		timer.restart();
		secondsElapsed = 0;
	}
	public void stop(){
		timer.stop();
		secondsElapsed = 0;
		MathQuizGame.frame.setTitle("MathQuizGame " + MathQuizGame.VERSION_ID + " — Timer set to " + overallDelaySeconds + " seconds.");
	}
	public void setInitialDelay(int delay){
		timer.setInitialDelay(delay);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		secondsElapsed++;
		
		if(threadName.equals("")){
			threadName = Thread.currentThread().getName();
		}
		
		if(secondsElapsed > overallDelaySeconds){
			MathQuizGame.EnterText("\nTimer reached " + overallDelaySeconds + " seconds.");
			MathQuizGame.endGame();
		}else{
			MathQuizGame.frame.setTitle("MathQuizGame " + MathQuizGame.VERSION_ID + " — " + (overallDelaySeconds - secondsElapsed) + " seconds remaining.");
		}

	}

}