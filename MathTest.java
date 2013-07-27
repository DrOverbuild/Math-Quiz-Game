import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;
import javax.swing.*;
import javax.swing.text.BadLocationException;

/**
 * 
 * @author Jasper
 * 
 */

@SuppressWarnings("serial")
public class MathTest extends JFrame implements ActionListener, KeyListener{
	
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
	static String LastLine2;
	static int difficultyLevel;
	
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
	
	
	public MathTest() {
		super("Math Quiz");
		BorderLayout b33 = new BorderLayout(5,5);
		setLayout(b33);
		
		LastLine1 = "";
		LastLine2 = "";

		consoleMessages = new JTextArea(35,60);
		consoleMessages.setEditable(false);
		consoleMessages.setFont(new java.awt.Font("Courier", 0, 12));
		consoleMessages.setLineWrap(true);
		consoleMessages.setWrapStyleWord(true);
		scrollPane = new JScrollPane(consoleMessages);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		add(scrollPane, BorderLayout.NORTH);
		EnterText("MATH QUIZ!!! Let's see how much you know...");
		EnterText("To start over, type /restart.");
		EnterText("To quit, type /quit.");
		EnterText("To clear the output, type /clear.");
		
		input = new JTextField(45);
		input.setFont(new java.awt.Font("Courier", 0, 12));
		inputPanel = new JPanel();
		enter = new JButton("Enter");
		enter.addActionListener(this);
		input.addKeyListener(this);
		inputPanel.add(input);
		inputPanel.add(enter);
		add(inputPanel, BorderLayout.SOUTH);
		
		addWindowFocusListener(new WindowAdapter() {
		    public void windowGainedFocus(WindowEvent e) {
		        input.requestFocusInWindow();
		    }
		});
		
		EnterText("Please enter your difficulty: Elementary, Middle School, High School");
		setQuestionState(DIFFICULTY_CHANGING_STATE);
		
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		this.setLocation(550,160);
		pack();
	}
	
	public static void EnterText(String input){
		String currentValue = consoleMessages.getText();
		String newValue = currentValue + input + "\n";
		consoleMessages.setText(newValue);
		LastLine1 = input;
		LastLine2 = LastLine1;
	}
	
	public static void setDifficulty(int newDifficulty){
		difficultyLevel = newDifficulty;
	}
	
	public static void setQuestionState (int newState){
		state = newState;
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
	
	public static void nextQuestion(int numberOne, int numberTwo, int answer){
		if (numberOfTimesPlayed <= 9){
				if (total == answer){
					EnterText("You are correct!");
					score += 10;
					
					newNumbers();
				} else {
					EnterText("Wrong.");

					newNumbers();

				}
		} else {
			
			if (total == answer){
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
		
		String txt = "";
		String txtToLowerCase = "";
		txt = input.getText();
		txtToLowerCase = txt.toLowerCase();
		
		if (txtToLowerCase.equals("y") || txtToLowerCase.equals("/restart")){
			EnterText("Please enter your difficulty: Elementary, Middle School, High School");
			setQuestionState(DIFFICULTY_CHANGING_STATE);
			
			input.selectAll();
		} else if (txtToLowerCase.equals("n") || txtToLowerCase.equals("/quit")){
			EnterText("Okay. Good-bye.");
			EnterText("Thank you for playing.");
			EnterText("Please quit this application by clicking the red button on");
			EnterText("the top of the window.");
			
			input.setText("");
			
			input.setEditable(false);
			enter.setEnabled(false);
			
		}else if(txtToLowerCase.equals("/clear")){
			consoleMessages.setText("");
			input.selectAll();
			EnterText(LastLine1); 
		}else if (txtToLowerCase.contains("/setfont")){
			char data[] = txt.toCharArray();
			String newFont = String.copyValueOf(data, 9, data.length - 9);
			
			input.setFont(new java.awt.Font(newFont, 0, 12));
			consoleMessages.setFont(new java.awt.Font(newFont,0, 12));
			
			EnterText("Font changed to " + newFont);
			
			input.selectAll();
		}else if(txtToLowerCase.contains("/say")){
			char data[] = txt.toCharArray();
			String txtForWrapTest = String.copyValueOf(data, 5, data.length - 5);
			EnterText(txtForWrapTest);
			
			input.selectAll();
		}else if(txtToLowerCase.equals("elementary") && state == DIFFICULTY_CHANGING_STATE){
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
		}else if(txtToLowerCase.equals("middle school") && state == DIFFICULTY_CHANGING_STATE){
			setDifficulty(MIDDLE_SCHOOL_DIFFICULTY);
			EnterText("Starting a new game set in the Middle School Difficulty.");
			numberOfTimesPlayed = 1;
			score = 0;
			EnterText("Question #" + numberOfTimesPlayed);
			number1 = generator.nextInt(11);
			number2 = generator.nextInt(11);
			total = number1 + number2;
			EnterText("What is " + number1 + " + " + number2 + "?");
			input.requestFocusInWindow();
			input.selectAll();
		}else if(txtToLowerCase.equals("high school") && state == DIFFICULTY_CHANGING_STATE){
			setDifficulty(HIGH_SCHOOL_DIFFICULTY);
			EnterText("Starting a new game set in the High School Difficulty.");
			numberOfTimesPlayed = 1;
			score = 0;
			EnterText("Question #" + numberOfTimesPlayed);
			number1 = generator.nextInt(11);
			number2 = generator.nextInt(11);
			total = number1 + number2;
			EnterText("What is " + number1 + " + " + number2 + "?");
			input.requestFocusInWindow();
			input.selectAll();
		}else {
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
		
		new MathTest();

	}

}