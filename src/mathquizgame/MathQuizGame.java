/*
 * Copyright (c) 2013 Jasper Reddin
 * All rights reserved
 */

package mathquizgame;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;

/**
 * @author Jasper
 */

public class MathQuizGame extends JFrame implements ActionListener, KeyListener{

	JScrollPane scrollPane;
	JPanel inputPanel;
	static JTextArea consoleMessages;
	static JTextField input;
	static ArrayList<String> inputtedLines;
	static int indexArrayThing;
	static int numberOfTimesPlayed;
	//static int number1;
	//static int number2;
	static int total;
	static float score;
	static Random generator = new Random();
	static JButton enter;
	static String LastLine1;
	static int difficultyLevel;
	static String currentFont;
	static int currentSize;

	static timerControl timer;
	static boolean timerRunning;

	// Fields for File management
	static File logDirectory;
	static File log;
	static String logFilePath;

	// Fields for custom difficulty level
	static int customMaxRange;
	static int customMinRange;
	static char customOperation;

	/**
	 * This field stores what the program is waiting for when it waits for user input.
	 */
	static int state;

	// State fields
	public static final int VARIABLE_STATE = 0;
	public static final int DIFFICULTY_CHANGING_STATE = 1;
	public static final int END_OF_GAME_STATE = 2;

	public static final double VERSION_ID = 1.5;

	// These fields have been added for the local
	// Multiplayer mode, but I don't think I will
	// put that feature in quite yet.
	/*ObjectOutputStream outputstream;
	 ObjectInputStream inputstream;
	 ServerSocket serverThing;
	 Socket connection;*/


	public MathQuizGame() {
		super("MathQuizGame " + VERSION_ID);
		BorderLayout b33 = new BorderLayout(5,5);
		setLayout(b33);

		LastLine1 = "";

		consoleMessages = new JTextArea(20,60);
		consoleMessages.setEditable(false);
		consoleMessages.setFont(new java.awt.Font("Courier", 0, 12));
		consoleMessages.setLineWrap(true);
		consoleMessages.setWrapStyleWord(true);
		scrollPane = new JScrollPane(consoleMessages);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		add(scrollPane, BorderLayout.CENTER);

		String userHome = System.getProperty("user.home");
		String libraryFolder;
		String os = OSValidator.findOS();
		switch (os) {
			case "mac":
				libraryFolder = userHome + "/Library/Application Support/mathquizgame/";
				break;
			case "windows":
				libraryFolder = userHome + "\\Application Data\\mathquizgame\\";
				break;
			default:
				libraryFolder = userHome + "/.mathquizgame/";
				break;
		}

		logDirectory = new File(libraryFolder);
		logFilePath = libraryFolder + "log.txt";
		log = new File(logFilePath);

		boolean wasCreated = false;

		if(!logDirectory.exists()){
			try{
				wasCreated = logDirectory.mkdir();
			}catch(SecurityException e){
			}
		}
		if(!log.exists()){
			try{
				wasCreated = log.createNewFile();
				EnterText("File " + logFilePath + " created successfully.",true);
			}catch(SecurityException | IOException e){
			}
		}

		// Find date of
		Calendar cal = Calendar.getInstance();
    	SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
		int calMonth = cal.get(Calendar.MONTH) + 1;
		String date = "" + calMonth + "-" + cal.get(Calendar.DATE);

		printLineToFile("-------------------");
		printLineToFile("Date of use: " + date);
		printLineToFile("Time of use: " + sdf.format(cal.getTime()));

		timer = new timerControl(1000,false);

		currentFont = "Courier";
		currentSize = 12;
		if (!wasCreated){
			EnterText("MATH QUIZ!!! Let's see how much you know...",true);
		} else{
			EnterText("MATH QUIZ!!! Let's see how much you know...");
		}
		EnterText("To see a list of a commands, type /help or /?.");
		EnterText("To set up a countdown timer, type /timer <time in seconds>");

		input = new JTextField(35);
		input.setFont(new java.awt.Font("Courier", 0, 12));
		inputPanel = new JPanel();
		enter = new JButton("Enter");
		enter.addActionListener(this);
		input.addActionListener(this);
		input.addKeyListener(this);
		inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.LINE_AXIS));
		inputPanel.add(input);
		inputPanel.add(enter);
		add(inputPanel, BorderLayout.PAGE_END);
		inputtedLines = new ArrayList<>(0);
		indexArrayThing = 0;

		addWindowFocusListener(new WindowAdapter() {
							   @Override
							   public void windowGainedFocus(WindowEvent e) {
							   input.requestFocusInWindow();
							   }
							   });

		EnterText("Please enter your difficulty: Elementary, Middle School, High School, or Custom");
		setQuestionState(DIFFICULTY_CHANGING_STATE);

		setResizable(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		setLocationRelativeTo(null);
		pack();
	}

	public static void printLineToFile(String txt){

		try {
			try (FileWriter fileWriter = new FileWriter(logFilePath,true)) {
				fileWriter.write(txt + "\n");
			}
		} catch (IOException ex) {
		}
	}

	public static void EnterText(String input){
		String str = consoleMessages.getText() + "\n" + input;
		consoleMessages.setText(str);
		LastLine1 = input;
		printLineToFile(input);

	}

	public static void EnterText(String input, boolean doNotAddNewLine){
		String str = consoleMessages.getText() + input;
		consoleMessages.setText(str);
		LastLine1 = input;
		printLineToFile(input);
	}

	public static void endGame(){
		EnterText("You are finished with your quiz.");
		EnterText("Here's your score: " + MathQuizGame.score + "%");

		EnterText("Play again? (Y/N)");
		setQuestionState(2);
	}

	public static void setDifficulty(int newDifficulty){
		difficultyLevel = newDifficulty;
	}

	public static void setQuestionState (int newState){
		state = newState;
	}

	public static void setTimerRunning(boolean setRunning){
		timerRunning = setRunning;
	}

	public static boolean getTimerRunning(){
		return timerRunning;
	}

	public static String[] findCommandArguments(String command, String userInput){

		int lengthOfCommand = command.length() + 1;
		int lengthOfUserInput = userInput.length();
		int lengthOfArgument = lengthOfUserInput - lengthOfCommand;
		char[] toCharArray = userInput.toCharArray();
		String argument = String.copyValueOf(toCharArray, lengthOfCommand, lengthOfArgument);

		String[] splitted = argument.split("\\s");

		return splitted;
	}
	public static String findCommandArgument(String command, String userInput){

		// This command will only find one argument.

		int lengthOfCommand = command.length() + 1;
		int lengthOfUserInput = userInput.length();
		int lengthOfArgument = lengthOfUserInput - lengthOfCommand;
		char[] toCharArray = userInput.toCharArray();
		String argument = String.copyValueOf(toCharArray, lengthOfCommand, lengthOfArgument);

		return argument;
	}

	/**
	 * This method is called when user presses enter on the keyboard or clicks the enter button on the UI.
	 */
	public void somethingHappened(){

		String txt;
		String txtToLowerCase;
		txt = input.getText();
		txtToLowerCase = txt.toLowerCase();
		inputtedLines.add(txt);
		indexArrayThing=inputtedLines.size()-1;

		if (txtToLowerCase.equals("y") || txtToLowerCase.equals("/restart")){
			restart();
		}else if (txtToLowerCase.equals("n") || txtToLowerCase.equals("/quit")){
			quit();
		}else if(txtToLowerCase.equals("/clear")){
			clear();
		}else if (txtToLowerCase.startsWith("/setfont ")){
			setfont(txt);
		}else if(txtToLowerCase.startsWith("/say ")){
			say(txt);
		}else if (txtToLowerCase.startsWith("/setsize ")){
			setsize(txt);
		}else if (txtToLowerCase.equals("/help") || txtToLowerCase.equals("/?")){
			help();
		}else if(txtToLowerCase.equals("/clearfile")){
			clearfile();
		}else if(txtToLowerCase.equals("/debug")){
			debug();
		}else if(txtToLowerCase.equals("/history")){
			displayHistory();
		}else{
			if (state == VARIABLE_STATE){
				try{
					int inputValue = Integer.parseInt(input.getText());
					EnterText(" " + txt,true);
					MathOperator.operate(inputValue);
					input.requestFocusInWindow();
					input.selectAll();
				} catch (NumberFormatException e){
					EnterText("Please type a number or one of the commands available.");
					input.selectAll();
				}
			}else if(state == DIFFICULTY_CHANGING_STATE){
				if (txtToLowerCase.equals("elementary") || txtToLowerCase.equals("e")) {
					MathOperator.startGameElementary();
				}else if (txtToLowerCase.equals("middle school") || txtToLowerCase.equals("m")){
					MathOperator.startGameMiddle();
				}else if(txtToLowerCase.equals("high school") || txtToLowerCase.equals("h")){
					MathOperator.startGameHigh();
				}else if(txtToLowerCase.equals("custom") || txtToLowerCase.equals("c")){
					EnterText("----------------------");
					EnterText("When choosing custom, please specify the maximum number, minimum number, operation to use, and number of questions you want to answer. Example:");
					EnterText("custom 50 10 x 10");
					EnterText("OR: c 50 10 x 10");
					EnterText("While 50 is the maximum, 10 is the minimum, x is the operation to use, and the game will ask 10 questions.");
					EnterText("The available operations are: \"+\" (add) \"-\" (subtract) \"x\" (multiply)");
					setQuestionState(1);
					input.selectAll();
				}else if (txtToLowerCase.startsWith("custom ") || txtToLowerCase.startsWith("c ")){
					String[] customArgs;
					if(txtToLowerCase.startsWith("custom ")){
						customArgs = findCommandArguments("custom",txt);
					}else{
						customArgs = findCommandArguments("c",txt);
					}
					try{
						MathOperator.startGameCustom(customArgs);
					}catch(NumbersAreSameException e){
						EnterText("The numbers used in the range must not be the same. Please pick different ones.");
						setQuestionState(1);
						input.selectAll();
					}
				}else if(txtToLowerCase.startsWith("/setuptimer ") || txtToLowerCase.startsWith("/timer ")){
					setupTimer(txt);
				}else{
					EnterText("That is not available at the time. Please choose a difficulty level.");
					input.requestFocusInWindow();
					input.selectAll();
					setQuestionState(1);
				}
			}else if (state == END_OF_GAME_STATE){
				EnterText("That is not available at the time. Please choose whether you want to play again or not.");
			}else{
				EnterText("Error: State not defined. Please report this bug to http://sourceforge.net/projects/mathquizgame/tickets/");
			}
		}
	}

	@Override
	public void actionPerformed(ActionEvent actionEvent) {
		somethingHappened();

	}

	public static void main(String[] args) {
		MathQuizGame mathQuizGame = new MathQuizGame();

	}

	public static void restart(){
		EnterText("Please enter your difficulty: Elementary, Middle School, High School, or Custom");
		setQuestionState(DIFFICULTY_CHANGING_STATE);

		input.selectAll();
	}
	public static void quit(){
		EnterText("Quiting...");
		System.exit(0);
	}
	public static void clear(){
		consoleMessages.setText("");
		input.selectAll();
		EnterText(LastLine1);
	}
	public static void setfont(String txt){
		String newFont = findCommandArgument("/setfont",txt);

		input.setFont(new java.awt.Font(newFont, 0, currentSize));
		consoleMessages.setFont(new java.awt.Font(newFont,0, currentSize));
		currentFont = newFont;

		EnterText("Font changed to " + newFont);

		input.selectAll();
	}
	public static void say(String txt){
		String sayMessage = findCommandArgument("/say",txt);

		EnterText(sayMessage);

		input.selectAll();
	}
	public static void setsize(String txt){
		String newSizeSTR = findCommandArgument("/setsize",txt);
		try{
			int newSize = Integer.parseInt(newSizeSTR);
			input.setFont(new java.awt.Font(currentFont, 0, newSize));
			consoleMessages.setFont(new java.awt.Font(currentFont, 0, newSize));
			currentSize = newSize;

			EnterText("Size set to " + newSize);
		}catch(NumberFormatException e){
			EnterText("The new size has to be a number.");
		}
		input.selectAll();
	}
	public static void help(){
		EnterText("\nHere are all the available commands, their arguments, and what they do:");
		EnterText("/clear: Clears the entire log except the last line that has been displayed.");
		EnterText("/clearfile: Clears text in " + logFilePath);
		EnterText("/debug: Shows technical information you wouldn't understand if you're not a programmer.");
		EnterText("/help (or /?): Displays all the available commands, their arguments, and what they do.");
		EnterText("/history: Displays everything you have entered in.");
		EnterText("/restart (or y): Restarts the game and resets the score to 0.");
		EnterText("/say <msg>: Displays the message you type.");
		EnterText("/setfont <font>: Changes the font of the program to the font you choose.");
		EnterText("/setsize <size>: Changes the size of the program to the size you choose. Note that the size must be a number.");
		EnterText("/quit (or n): Disables the controls and requires you to quit the program.");
		EnterText("\n");
		input.selectAll();
	}
	public static void clearfile(){
		if(log.delete()){
			try {
				if(log.createNewFile()){
					EnterText("File cleared successfully!");
				}
			} catch (IOException ex) {
				consoleMessages.append("\nError: File could not be recreated.");
			}
		}else{
			EnterText("Error: File could not be cleared.");
		}
		input.selectAll();
	}
	public static void debug(){
		EnterText("");
		EnterText("MathQuizGame.numberOfTimesPlayed = " + numberOfTimesPlayed);
		//EnterText("MathQuizGame.number1             = " + number1);
		//EnterText("MathQuizGame.number2             = " + number2);
		EnterText("MathQuizGame.total               = " + total);
		EnterText("MathQuizGame.score               = " + score);
		EnterText("MathQuizGame.difficultyLevel     = " + difficultyLevel);
		EnterText("MathQuizGame.indexArrayThing     = " + indexArrayThing);
		EnterText("MathQuizGame.currentFont         = " + currentFont);
		EnterText("MathQuizGame.currentSize         = " + currentSize);
		EnterText("MathQuizGame.state               = " + state);
		EnterText("MathQuizGame.logFilePath         = " + logFilePath);
		EnterText("");
		EnterText("MathOperator.operationToUse            = " + MathOperator.operationToUse);
		EnterText("MathOperator.numberOfTimesWillBePlayed = " + MathOperator.numberOfTimesWillBePlayed);
		EnterText("MathOperator.pointsWorth               = " + MathOperator.pointsWorth);
		EnterText("MathOperator.customMinRange            = " + MathOperator.customMinRange);
		EnterText("MathOperator.customMaxRange            = " + MathOperator.customMaxRange);
		EnterText("MathOperator.customOperation           = " + MathOperator.operationToUse);
		EnterText("");
		EnterText("OS Name      = " + System.getProperty("os.name"));
		EnterText("OS Verion    = " + System.getProperty("os.version"));
		EnterText("User Home Directory: " + System.getProperty("user.home"));
		EnterText("Java Version = " + System.getProperty("java.version"));
		EnterText("");
		input.selectAll();
	}
	public static void displayHistory(){
		EnterText(" --- HISTORY ---");
		for(int i = 0;i<inputtedLines.size();i++){
			EnterText(inputtedLines.get(i));
		}
		input.requestFocus();
		input.selectAll();
		EnterText(" ---   END   ---");
	}
	public static void setupTimer(String txt){
		String args;
		try{
			if (txt.contains("/timer")){
				args = findCommandArgument("/timer",txt);
			}else{
				args = findCommandArgument("/setuptimer",txt);
			}
		}catch(StringIndexOutOfBoundsException e){
			EnterText("No amount of seconds specified.");
			args = "30";
		}
		try{
			int milliseconds = Integer.parseInt(args) * 1000;
			timer.setInitialDelay(milliseconds);
			EnterText("Timer setup with " + args + " seconds. Timer will start countdown as soon as you choose a difficulty level.");

		}catch(NumberFormatException e){
			EnterText("Number of seconds has to be a number.");
		}
		setTimerRunning(true);
		setQuestionState(1);
		input.requestFocus();
		input.selectAll();
	}

	@Override
	public void keyTyped(KeyEvent e) {
		}

	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_UP){
			e.consume();
			if(indexArrayThing == 0){
				input.requestFocus();
				input.selectAll();
			}else if(indexArrayThing > 0){
				indexArrayThing--;
				input.setText(inputtedLines.get(indexArrayThing));
				input.requestFocus();
				input.selectAll();
			}
		}else if(e.getKeyCode() == KeyEvent.VK_DOWN){
			e.consume();
			if(indexArrayThing == inputtedLines.size()-1){
				input.requestFocus();
				input.selectAll();
			}else if(indexArrayThing < inputtedLines.size()-1){
				indexArrayThing++;
				input.setText(inputtedLines.get(indexArrayThing));
				input.requestFocus();
				input.selectAll();
			}
		}else{
			indexArrayThing = inputtedLines.size();
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		//throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

}