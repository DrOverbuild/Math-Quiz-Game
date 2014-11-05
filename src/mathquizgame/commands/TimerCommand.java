package mathquizgame.commands;

import mathquizgame.MathQuizGame;
import static mathquizgame.MathQuizGame.EnterText;
import static mathquizgame.MathQuizGame.setTimerRunning;
import mathquizgame.timerControl;

/**
 *
 * @author jasper
 */
public class TimerCommand implements Command{

	@Override
	public void execute(String[] args) {
		if(MathQuizGame.state == MathQuizGame.DIFFICULTY_CHANGING_STATE){
			if(args.length==1){
				if(args[0].equals("off")){
					MathQuizGame.timerRunning = false;
					EnterText("Timer turned off");
					MathQuizGame.frame.setTitle("MathQuizGame " + MathQuizGame.VERSION_ID);
					return;
				}
			}
			String timerLength;
			System.out.println(args.length);
			if(args.length == 0){
				timerLength = "30";
			}else{
				timerLength = args[0];
				System.out.println(timerLength);
			}
			try {
				int milliseconds = Integer.parseInt(timerLength) * 1000;
				MathQuizGame.timer = new timerControl(milliseconds);
				EnterText("Timer setup with " + timerLength + " seconds. Timer will start countdown as soon as you choose a difficulty level.");
				setTimerRunning(true);
				MathQuizGame.frame.setTitle("MathQuizGame " + MathQuizGame.VERSION_ID + " -- Timer set to " + timerLength + " seconds.");
			} catch (NumberFormatException e) {
				EnterText("Number of seconds has to be a number.");
			}
		}
	}

	@Override
	public String getName() {
		return "setuptimer";
	}

	@Override
	public String getShortcut() {
		return "timer";
	}

	@Override
	public String getDesc() {
		return "/timer <seconds>: Sets up a countdown timer that starts as soon as you choose a difficulty. Type \"/timer off\" to turn off the timer.";
	}

}
