/*
 * Copyright (c) 2013 Jasper Reddin
 * All rights reserved
 */

package mathquizgame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import mathquizgame.commands.CommandImpl;
import mathquizgame.commands.CommandNotFoundException;
import mathquizgame.commands.CustomCommand;

/**
 * @author Jasper
 */

public class MathQuizGame extends JFrame implements ActionListener, KeyListener{

	public static MathQuizGame frame;

	JScrollPane scrollPane;
	JPanel inputPanel;
	public JTextArea consoleMessages;
	public JTextField input;
	JButton enter;
	static CommandImpl commandParserAndExecuter;

	public ArrayList<String> inputtedLines;
	public int indexArrayThing;
	public String LastLine1;

	public int numberOfTimesPlayed;
	static float score;
	static int difficultyLevel;
	/**
	 * This field stores what the program is waiting for when it waits for user input.
	 */
	static int state;
	public static boolean isDebugOn;

	static String currentFont;
	static int currentSize;
	static Color currentColor;

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



	// State fields
	public static final int VARIABLE_STATE = 0;
	public static final int DIFFICULTY_CHANGING_STATE = 1;
	public static final int END_OF_GAME_STATE = 2;

	public static final double VERSION_ID = 1.6;

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
							   input.selectAll();
							   }
							   });

		input.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				input.selectAll();
			}

			@Override
			public void mousePressed(MouseEvent e) {
			}

			@Override
			public void mouseReleased(MouseEvent e) {
			}

			@Override
			public void mouseEntered(MouseEvent e) {
			}

			@Override
			public void mouseExited(MouseEvent e) {
			}
		});

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();
		setLocationRelativeTo(null);
		setVisible(true);

		commandParserAndExecuter = new CommandImpl();

		String userHome = System.getProperty("user.home");
		String libraryFolder;
		switch (OSValidator.findOS()) {
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
    	SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
		int calMonth = cal.get(Calendar.MONTH) + 1;
		String date = calMonth + "-" + cal.get(Calendar.DATE);

		printLineToFile("-------------------");
		printLineToFile("Date: " + date + " at " + sdf.format(cal.getTime()));

		timer = new timerControl(1000,false);

		isDebugOn = false;

		currentFont = "Courier";
		currentSize = 12;
		currentColor = Color.white;

		if (!wasCreated){
			EnterText("MATH QUIZ!!! Let's see how much you know...",true);
		} else{
			EnterText("MATH QUIZ!!! Let's see how much you know...");
		}
		EnterText("To see a list of a commands, type /help or /?.");
		EnterText("To set up a countdown timer, type /timer <time in seconds>");


		EnterText("Please enter your difficulty: Elementary, Middle School, High School, or Custom");
		setQuestionState(DIFFICULTY_CHANGING_STATE);


	}

	public static void printLineToFile(String txt){
		FileWriter fileWriter = null;
		try {
			fileWriter = new FileWriter(logFilePath,true);
			fileWriter.write(txt + System.getProperty("line.separator"));
			fileWriter.close();
		} catch (IOException ex) {
		}
	}

	public static void EnterText(String input){
		String str = frame.consoleMessages.getText() + System.getProperty("line.separator") + input;
		frame.consoleMessages.setText(str);
		frame.LastLine1 = input;
		printLineToFile(input);

	}

	public static void EnterText(String input, boolean doNotAddNewLine){
		String str = frame.consoleMessages.getText() + input;
		frame.consoleMessages.setText(str);
		frame.LastLine1 = input;
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

		input.selectAll();
		input.requestFocus();

		if (txtToLowerCase.startsWith("/")){
			try {
				commandParserAndExecuter.parseAndExecuteCommand(txt);
			} catch (CommandNotFoundException ex) {
				EnterText("Command not found.");
			}
		}else if(txtToLowerCase.equals("y")) restart();
		else if(txtToLowerCase.equals("n")) quit();
		else{
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
				}else if ((txtToLowerCase.startsWith("custom ")&&!txtToLowerCase.equals("custom ")) ||
					      (txtToLowerCase.startsWith("c ")&&!txtToLowerCase.equals("c "))){

					String[] customArgs;
					if(txtToLowerCase.startsWith("custom ")){
						customArgs = CommandImpl.parseArgs("c", txt);
					}else{
						customArgs = CommandImpl.parseArgs("c", txt);
					}

					new CustomCommand().execute(customArgs);
				}else if(txtToLowerCase.startsWith("custom") || txtToLowerCase.startsWith("c")){
					EnterText("----------------------");
					EnterText("When choosing custom, please specify the maximum number, minimum number, operation to use, and number of questions you want to answer. Example:");
					EnterText("custom 50 10 x 10");
					EnterText("OR: c 50 10 x 10");
					EnterText("While 50 is the maximum, 10 is the minimum, x is the operation to use, and the game will ask 10 questions.");
					EnterText("The available operations are: \"+\" (add) \"-\" (subtract) \"x\" (multiply)");
					setQuestionState(1);
					input.selectAll();
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
		frame = new MathQuizGame();

	}

	public static void restart(){
		EnterText("Please enter your difficulty: Elementary, Middle School, High School, or Custom");
		setQuestionState(DIFFICULTY_CHANGING_STATE);
	}
	public static void quit(){
		EnterText("Quiting...");
		System.exit(0);
	}
	public static void clear(){
		frame.consoleMessages.setText("");
		EnterText(frame.LastLine1);
	}
	public static void setfont(String txt){

		frame.input.setFont(new java.awt.Font(txt, 0, currentSize));
		frame.consoleMessages.setFont(new java.awt.Font(txt,0, currentSize));
		currentFont = txt;

		EnterText("Font changed to " + txt);
	}
	public static void setsize(String txt){
		try{
			int newSize = Integer.parseInt(txt);
			frame.input.setFont(new java.awt.Font(currentFont, 0, newSize));
			frame.consoleMessages.setFont(new java.awt.Font(currentFont, 0, newSize));
			currentSize = newSize;

			EnterText("Size set to " + newSize);
		}catch(NumberFormatException e){
			EnterText("The new size has to be a number.");
		}
	}
	public static void clearfile(){
		if(log.delete()){
			try {
				if(log.createNewFile()){
					EnterText("File cleared successfully!");
				}
			} catch (IOException ex) {
				frame.consoleMessages.append("\nError: File could not be recreated.");
			}
		}else{
			EnterText("Error: File could not be cleared.");
		}
	}
	public static void setupTimer(String txt){
		String args;

		if (!timerRunning) {
			try {
				args = CommandImpl.parseArgs("/timer", txt)[0];
			} catch (StringIndexOutOfBoundsException e) {
				EnterText("No amount of seconds specified.");
				args = "30";
			}
			try {
				int milliseconds = Integer.parseInt(args) * 1000;
				timer.setInitialDelay(milliseconds);
				EnterText("Timer setup with " + args + " seconds. Timer will start countdown as soon as you choose a difficulty level.");
				setTimerRunning(true);
			} catch (NumberFormatException e) {
				EnterText("Number of seconds has to be a number.");
			}
		}else{
			timerRunning = false;
			EnterText("Timer turned off.");
		}
		setQuestionState(1);
	}
	public void changeBackground() {
		Color color = JColorChooser.showDialog(null, "Choose Color", currentColor);
		if(color != null){
			consoleMessages.setBackground(color);
			input.setBackground(color);
			currentColor = color;
			int r = 255-color.getRed();
			int g = 255-color.getGreen();
			int b = 255-color.getBlue();
			consoleMessages.setForeground(new Color(r,g,b));
			input.setForeground(new Color(r,g,b));
			EnterText("Background Color: "+color.getRed()+", "+color.getGreen()+", "+color.getBlue()+".");
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {}

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
	public void keyReleased(KeyEvent e) {}
}