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
	int millisecondsElapsed = 0;
	int overallDelayInSeconds = 0;
	public timerControl(int millisec){
		timer = new Timer(1000, this);
		timer.setRepeats(true);
	}
	public void start(){
		timer.start();
	}
	public void restart(){
		timer.restart();
	}
	public void stop(){
		timer.stop();
		millisecondsElapsed = 0;
	}
	public void setInitialDelay(int delay){
		timer.setInitialDelay(delay);
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		int secs = overallDelayInSeconds;
		


	}

}