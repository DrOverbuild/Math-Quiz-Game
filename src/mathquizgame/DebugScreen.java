/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mathquizgame;

import java.awt.GridLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author jasper
 */
public class DebugScreen extends JPanel implements Runnable{

	JLabel indexArrayThing = new JLabel(MathQuizGame.frame.indexArrayThing+"",JLabel.RIGHT);
	JLabel LastLine1 = new JLabel(MathQuizGame.frame.LastLine1+"",JLabel.RIGHT);
	JLabel numberOfTimesPlayed = new JLabel(MathQuizGame.frame.numberOfTimesPlayed+"",JLabel.RIGHT);
	JLabel score = new JLabel(MathQuizGame.score+"",JLabel.RIGHT);
	JLabel difficultyLevel = new JLabel(MathQuizGame.difficultyLevel+"",JLabel.RIGHT);
	JLabel state = new JLabel(MathQuizGame.state+"",JLabel.RIGHT);
	JLabel isDebugOn = new JLabel(MathQuizGame.isDebugOn+"",JLabel.RIGHT);
	JLabel currentFont = new JLabel(MathQuizGame.currentFont+"",JLabel.RIGHT);
	JLabel currentSize = new JLabel(MathQuizGame.currentSize+"",JLabel.RIGHT);
	JLabel timerRunning = new JLabel(MathQuizGame.timerRunning+"",JLabel.RIGHT);
	JLabel logFilePath = new JLabel(MathQuizGame.logFilePath+"",JLabel.RIGHT);
	JLabel blank = new JLabel("",JLabel.RIGHT);
	JLabel number1 = new JLabel(MathOperator.number1+"",JLabel.RIGHT);
	JLabel number2 = new JLabel(MathOperator.number2+"",JLabel.RIGHT);
	JLabel total = new JLabel(MathOperator.total+"",JLabel.RIGHT);
	JLabel operationToUse = new JLabel(MathOperator.operationToUse+"",JLabel.RIGHT);
	JLabel numberOfTimesWillBePlayed = new JLabel(MathOperator.numberOfTimesWillBePlayed+"",JLabel.RIGHT);
	JLabel pointsWorth = new JLabel(MathOperator.pointsWorth+"",JLabel.RIGHT);
	JLabel customMinRange = new JLabel(MathOperator.customMinRange+"",JLabel.RIGHT);
	JLabel customMaxRange = new JLabel(MathOperator.customMaxRange+"",JLabel.RIGHT);
	JLabel customOperations = new JLabel(MathOperator.customOperations+"",JLabel.RIGHT);

	JFrame frame;

	public DebugScreen(JFrame frame){

		this.frame = frame;

		setLayout(new GridLayout(21,2));
		setBorder(new EmptyBorder(10, 10, 10, 10));

		add(new JLabel("MathQuizGame.indexArrayThing: "));
		add(indexArrayThing);
		add(new JLabel("QathQuizGame.LastLine1: "));
		add(LastLine1);
		add(new JLabel("MathQuizGame.numberOfTimesPlayed: "));
		add(numberOfTimesPlayed);
		add(new JLabel("MathQuizGame.score: "));
		add(score);
		add(new JLabel("MathQuizGame.difficultyLevel: "));
		add(difficultyLevel);
		add(new JLabel("MathQuizGame.state: "));
		add(state);
		add(new JLabel("MathQuizGame.isDebugOn: "));
		add(isDebugOn);
		add(new JLabel("MathQuizGame.currentFont: "));
		add(currentFont);
		add(new JLabel("MathQuizGame.currentSize: "));
		add(currentSize);
		add(new JLabel("MathQuizGame.timerRunning: "));
		add(timerRunning);
		add(new JLabel("MathQuizGame.logFilePath: "));
		add(logFilePath);
		add(new JLabel(""));
		add(blank);
		add(new JLabel("MathOperator.number1: "));
		add(number1);
		add(new JLabel("MathOperator.number2: "));
		add(number2);
		add(new JLabel("MathOperator.total: "));
		add(total);
		add(new JLabel("MathOperator.operationToUse: "));
		add(operationToUse);
		add(new JLabel("MathOperator.numberOfTimesWillBePlayed: "));
		add(numberOfTimesWillBePlayed);
		add(new JLabel("MathOperator.pointsWorth: "));
		add(pointsWorth);
		add(new JLabel("MathOperator.customMinRange: "));
		add(customMinRange);
		add(new JLabel("MathOperator.customMaxRange: "));
		add(customMaxRange);
		add(new JLabel("MathOperator.customOperations: "));
		add(customOperations);

		this.frame.add(this);

		this.frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.frame.pack();
		this.frame.setSize(this.frame.getWidth()/2,this.frame.getHeight());
		this.frame.setVisible(true);

	}

	@Override
	public void run() {
		while(MathQuizGame.isDebugOn){
			indexArrayThing.setText(""+MathQuizGame.frame.indexArrayThing);
			LastLine1.setText(MathQuizGame.frame.LastLine1);
			numberOfTimesPlayed.setText(MathQuizGame.frame.numberOfTimesPlayed+"");
			score.setText(MathQuizGame.score+"");
			difficultyLevel.setText(MathQuizGame.difficultyLevel+"");
			state.setText(MathQuizGame.state+"");
			isDebugOn.setText(MathQuizGame.isDebugOn+"");
			currentFont.setText(MathQuizGame.currentFont);
			currentSize.setText(MathQuizGame.currentSize+"");
			timerRunning.setText(MathQuizGame.timerRunning+"");
			logFilePath.setText(MathQuizGame.logFilePath);
			number1.setText(MathOperator.number1+"");
			number2.setText(MathOperator.number2+"");
			total.setText(MathOperator.total+"");
			operationToUse.setText(MathOperator.operationToUse+"");
			numberOfTimesWillBePlayed.setText(MathOperator.numberOfTimesWillBePlayed+"");
			pointsWorth.setText(MathOperator.pointsWorth+"");
			customMaxRange.setText(MathOperator.customMaxRange+"");
			customMinRange.setText(MathOperator.customMinRange+"");
			customOperations.setText(MathOperator.customOperations);
		}
		frame.dispose();
	}
}
