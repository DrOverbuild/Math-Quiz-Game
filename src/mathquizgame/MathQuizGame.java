/*
 * Copyright (c) 2013 Jasper Reddin
 * All rights reserved
 */

package mathquizgame;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Random;
import javax.swing.*;

/**
 * 
 * @author Jasper
 * 
 */

public class MathQuizGame extends JFrame implements ActionListener, KeyListener{
	
	JScrollPane scrollPane;
	JPanel inputPanel;
	static JTextArea consoleMessages;
	static JTextField input;
	static int numberOfTimesPlayed;
	static int number1;
	static int number2;
	static int total;
	static int score;
	static Random generator = new Random();
	static JButton enter;
	static String LastLine1;
	static int difficultyLevel;
	static String currentFont;
	static int currentSize;
	static File logDirectory;
	static File log;
	static String logFilePath;
	
	// This field stores what the program
	// is waiting for when it waits for 
	// user input.
	static int state;
	
	// Difficulty fields
	public static final int ELEMENTARY_DIFFICULTY = 0;
	public static final int MIDDLE_SCHOOL_DIFFICULTY = 1;
	public static final int HIGH_SCHOOL_DIFFICULTY = 2;
	// State fields
	public static final int VARIABLE_STATE = 0;
	public static final int DIFFICULTY_CHANGING_STATE = 1;
	
	// These fields have been added for the local
	// Multiplayer mode, but I don't think I will
	// put that feature in quite yet.
	/*ObjectOutputStream outputstream;
	 ObjectInputStream inputstream;
	 ServerSocket serverThing;
	 Socket connection;*/
	
	
	public MathQuizGame() {
		super("Math Quiz");
		BorderLayout b33 = new BorderLayout(5,5);
		setLayout(b33);
		
		LastLine1 = "";
		
		String userHome = System.getProperty("user.home");
		String libraryFolder;
		OSValidator osFinder = new OSValidator();
		String os = osFinder.findOS();
		if (os.equals("mac")){
			libraryFolder = userHome + "/Library/Application Support/mathquizgame/";
		}else if (os.equals("windows")){
			libraryFolder = userHome + "/AppData/mathquizgame/";
		}else{
			libraryFolder = userHome + "/.mathquizgame/";
		}
		
		logDirectory = new File(libraryFolder);
		logFilePath = libraryFolder + "log.txt";
		log = new File(logFilePath);
		
		if(!logDirectory.exists()){
			System.out.println("Directory " + libraryFolder + " does not exist.");
			System.out.println("Creating new directory...");
			try{
				boolean wasCreated = logDirectory.mkdir();
				System.out.println("Directory created successfully");
			}catch(SecurityException e){
				System.out.println("SecurityException");
			}
		}
		if(!log.exists()){
			System.out.println("log.txt does not exist.");
			System.out.println("Creating the file...");
			try{
				boolean doesExist = log.createNewFile();
				System.out.println("File created successfully.");
			}catch(SecurityException e){
				System.out.println("SecurityException");
			}catch(IOException otherE){
				otherE.printStackTrace();
			}
		}
		
		consoleMessages = new JTextArea(20,60);
		consoleMessages.setEditable(false);
		consoleMessages.setFont(new java.awt.Font("Courier", 0, 12));
		consoleMessages.setLineWrap(true);
		consoleMessages.setWrapStyleWord(true);
		scrollPane = new JScrollPane(consoleMessages);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.getVerticalScrollBar().addAdjustmentListener(new AdjustmentListener() {  
			 public void adjustmentValueChanged(AdjustmentEvent e) {  
				e.getAdjustable().setValue(e.getAdjustable().getMaximum());  
			}
		});
		add(scrollPane, BorderLayout.CENTER);
		
		// Find date of 
		Calendar cal = Calendar.getInstance();
    	SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
		String date = "" + cal.get(Calendar.MONTH) + "-" + cal.get(Calendar.DATE);
		
		printLineToFile("-------------------");
		printLineToFile("Date of use: " + date);
		printLineToFile("Time of use: " + sdf.format(cal.getTime()));
		
		currentFont = "Courier";
		currentSize = 12;
		
		EnterText("MATH QUIZ!!! Let's see how much you know...",true);
		EnterText("To see a list of a commands, type /help or /?.");
		
		input = new JTextField(35);
		input.setFont(new java.awt.Font("Courier", 0, 12));
		inputPanel = new JPanel();
		enter = new JButton("Enter");
		enter.addActionListener(this);
		input.addKeyListener(this);
		inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.LINE_AXIS));
		inputPanel.add(input);
		inputPanel.add(enter);
		add(inputPanel, BorderLayout.PAGE_END);
		
		addWindowFocusListener(new WindowAdapter() {
							   public void windowGainedFocus(WindowEvent e) {
							   input.requestFocusInWindow();
							   }
							   });
		
		EnterText("Please enter your difficulty: Elementary, Middle School, High School");
		setQuestionState(DIFFICULTY_CHANGING_STATE);
		
		setResizable(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		setLocationRelativeTo(null);
		pack();
	}
	
	public static void printLineToFile(String txt){
		
		try {
			FileWriter fileWriter = new FileWriter(logFilePath,true);
			fileWriter.write(txt + "\n");
			fileWriter.close();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
	
	public static void EnterText(String input){
		consoleMessages.append("\n" + input);
		LastLine1 = input;
		printLineToFile(input);
		
	}
	
	public static void EnterText(String input, boolean doNotAddNewLine){
		consoleMessages.append(input);
		LastLine1 = input;
		printLineToFile(input);
	}
	
	public static void setDifficulty(int newDifficulty){
		difficultyLevel = newDifficulty;
	}
	
	public static void setQuestionState (int newState){
		state = newState;
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
	
	public static void newNumbers(){
		
		numberOfTimesPlayed += 1;
		EnterText("Question #" + numberOfTimesPlayed);
		
		if (difficultyLevel == 0){
			number1 = generator.nextInt(11);
			number2 = generator.nextInt(11);
		} else if (difficultyLevel == 1){
			number1 = generator.nextInt(21);
			number2 = generator.nextInt(21);
		} else if (difficultyLevel == 2) {
			number1 = generator.nextInt(41) - 20;
			number2 = generator.nextInt(41) - 20;
		}
		
		total= number1 + number2;
		EnterText("What is " + number1 + " + " + number2 + "?");
	}
	
	public static void nextQuestion(int numberOne, int numberTwo, int userAnswer){
		EnterText(" " + userAnswer,true);
		printLineToFile("User\'s answer: " + userAnswer);
		
		if (numberOfTimesPlayed <= 9){
			if (total == userAnswer){
				EnterText("You are correct!");
				score += 10;
				
				newNumbers();
			} else {
				EnterText("Wrong.");
				
				newNumbers();
				
			}
		} else {
			
			if (total == userAnswer){
				EnterText("You are correct!");
				score += 10;
			}else{
				EnterText("Wrong.");
			}
			
			EnterText("You are finished with your quiz.");
			EnterText("Here's your score: " + score + "%");
			
			EnterText("Play again? (Y/N)");
		}
	}
	
	public void somethingHappened(){
		
		String txt;
		String txtToLowerCase;
		txt = input.getText();
		txtToLowerCase = txt.toLowerCase();
		
		if (state == VARIABLE_STATE){
			if (txtToLowerCase.equals("y") || txtToLowerCase.equals("/restart")){
				restart();
			}else if (txtToLowerCase.equals("n") || txtToLowerCase.equals("/quit")){
				quit();
			}else if(txtToLowerCase.equals("/clear")){
				clear();
			}else if (txtToLowerCase.contains("/setfont")){
				setfont(txt);
			}else if(txtToLowerCase.contains("/say")){
				say(txt);
			}else if (txtToLowerCase.contains("/setsize")){
				setsize(txt);
			}else if (txtToLowerCase.equals("/help") || txtToLowerCase.equals("/?")){
				help();
			}else if(txtToLowerCase.equals("/clearfile")){
				clearfile();
			}else if(txtToLowerCase.equals("/debug")){
				debug();

			}
			
			else {
				try{
					int inputValue = Integer.parseInt(input.getText());
					nextQuestion(number1, number2, inputValue);
					input.requestFocusInWindow();
					input.selectAll();
				} catch (NumberFormatException e){
					EnterText("Please type a number or one of the");
					EnterText("commands available");
					input.selectAll();
				}
				setQuestionState(0);
			}
		}else if(state == DIFFICULTY_CHANGING_STATE){
			if (txtToLowerCase.equals("elementary")) {
				setDifficulty(ELEMENTARY_DIFFICULTY);
				EnterText("Starting a new game set in the Elementary Difficulty.");
				numberOfTimesPlayed = 1;
				score = 0;
				EnterText("Question #" + numberOfTimesPlayed);
				number1 = generator.nextInt(11);
				number2 = generator.nextInt(11);
				total = number1 + number2;
				input.requestFocusInWindow();
				input.selectAll();
				EnterText("What is " + number1 + " + " + number2 + "?");
				setQuestionState(0);
			}else if (txtToLowerCase.equals("middle school")){
				setDifficulty(MIDDLE_SCHOOL_DIFFICULTY);
				EnterText("Starting a new game set in the Middle School Difficulty.");
				numberOfTimesPlayed = 1;
				score = 0;
				EnterText("Question #" + numberOfTimesPlayed);
				number1 = generator.nextInt(21);
				number2 = generator.nextInt(21);
				total = number1 + number2;
				EnterText("What is " + number1 + " + " + number2 + "?");
				input.requestFocusInWindow();
				input.selectAll();
				setQuestionState(0);
			}else if(txtToLowerCase.equals("high school")){
				setDifficulty(HIGH_SCHOOL_DIFFICULTY);
				EnterText("Starting a new game set in the High School Difficulty.");
				numberOfTimesPlayed = 1;
				score = 0;
				EnterText("Question #" + numberOfTimesPlayed);
				number1 = generator.nextInt(41)-20;
				number2 = generator.nextInt(41)-20;
				total = number1 + number2;
				EnterText("What is " + number1 + " + " + number2 + "?");		
				input.requestFocusInWindow();
				input.selectAll();
				setQuestionState(0);
			}else if(txtToLowerCase.equals("/debug")){
				debug();
				
			}else{
				EnterText("That is not available at the time. Please choose a difficulty level.");
				input.requestFocusInWindow();
				input.selectAll();
				setQuestionState(1);
			}
		}else{
			EnterText("Error: State not defined. Please report this bug to http://sourceforge.net/projects/mathquizgame/tickets/");
		}
	}
	
	public void actionPerformed(ActionEvent actionEvent) {
		somethingHappened();
		
	}
	public void keyTyped(KeyEvent e){
		
	}
	public void keyPressed(KeyEvent keyEvent){
		if(keyEvent.getKeyCode() == KeyEvent.VK_ENTER){
			somethingHappened();
        }
	}
	public void keyReleased(KeyEvent e){
		
	}
	
	public static void main(String[] args) {
		
		new MathQuizGame();
		
	}
	
	public static void restart(){
		EnterText("Please enter your difficulty: Elementary, Middle School, High School");
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
		setQuestionState(0);
	}
	public static void setfont(String txt){
		String newFont = findCommandArgument("/setfont",txt);
				
		input.setFont(new java.awt.Font(newFont, 0, currentSize));
		consoleMessages.setFont(new java.awt.Font(newFont,0, currentSize));
		currentFont = newFont;
				
		EnterText("Font changed to " + newFont);
				
		input.selectAll();
		setQuestionState(0);
	}
	public static void say(String txt){
		String sayMessage = findCommandArgument("/say",txt);
		EnterText(sayMessage);
				
		input.selectAll();
		setQuestionState(0);
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
		setQuestionState(0);
	}
	public static void help(){
		EnterText("\nHere are all the available commands, their arguments, and what they do:");
		EnterText("/clear: Clears the entire log except the last line that has been displayed.");
		EnterText("/clearfile: Clears text in " + logFilePath);
		EnterText("/debug: Shows technical information you wouldn't understand if you're not a programmer.");
		EnterText("/help (or /?): Displays all the available commands, their arguments, and what they do.");
		EnterText("/restart (or y): Restarts the game and resets the score to 0.");
		EnterText("/say <msg>: Displays the message you type.");
		EnterText("/setfont <font>: Changes the font of the program to the font you choose.");
		EnterText("/setsize <size>: Changes the size of the program to the size you choose. Note that the size must be a number.");
		EnterText("/quit (or n): Disables the controls and requires you to quit the program.");
		EnterText("\n");
		input.selectAll();
		setQuestionState(0);
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
		setQuestionState(0);
	}
	public static void debug(){
		EnterText("");
		EnterText("MathQuizGame.numberOfTimesPlayed = " + numberOfTimesPlayed);
		EnterText("MathQuizGame.number1             = " + number1);
		EnterText("MathQuizGame.number2             = " + number2);
		EnterText("MathQuizGame.total               = " + total);
		EnterText("MathQuizGame.score               = " + score);
		EnterText("MathQuizGame.difficultyLevel     = " + difficultyLevel);
		EnterText("MathQuizGame.currentFont         = " + currentFont);
		EnterText("MathQuizGame.currentSize         = " + currentSize);
		EnterText("MathQuizGame.state               = " + state);
		EnterText("MathQuizGame.logFilePath         = " + logFilePath);
		EnterText("");
		EnterText("OS Name      = " + System.getProperty("os.name"));
		EnterText("OS Verion    = " + System.getProperty("os.version"));
		EnterText("User Home Directory: " + System.getProperty("user.home"));
		EnterText("Java Version = " + System.getProperty("java.version"));
		EnterText("");
		input.selectAll();
	}
	
}