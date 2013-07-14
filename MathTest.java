import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Random;
import javax.swing.*;
import javax.swing.text.BadLocationException;

/**
 * 
 * @author Jasper
 * 
 */

@SuppressWarnings("serial")
public class MathTest extends JFrame {
	
	JScrollPane scrollPane;
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
	JPanel inputPanel;
	
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
		enter.addActionListener(new enterlistener());
		input.addKeyListener(new enterlistener());
		inputPanel.add(input);
		inputPanel.add(enter);
		add(inputPanel, BorderLayout.SOUTH);
		
		addWindowFocusListener(new WindowAdapter() {
		    public void windowGainedFocus(WindowEvent e) {
		        input.requestFocusInWindow();
		    }
		});
		
		numberOfTimesPlayed = 1;
		EnterText("Question #" + numberOfTimesPlayed);
		number1 = generator.nextInt(20);
		number2 = generator.nextInt(20);
		total = number1 + number2;
		EnterText("What is " + number1 + " + " + number2 + "?");
		
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


	
	public static void nextQuestion(int numberOne, int numberTwo, int answer){
		if (numberOfTimesPlayed <= 9){
			if (total == answer){
				EnterText("You are correct!");
				score += 10;
				
				numberOfTimesPlayed += 1;
				EnterText("Question #" + numberOfTimesPlayed);
				
				number1 = generator.nextInt(20);
				number2 = generator.nextInt(20);
				total = number1 + number2;
				
				EnterText("What is " + number1 + " + " + number2 + "?");
			} else {
				EnterText("Wrong.");
				
				numberOfTimesPlayed += 1;
				EnterText("Question #" + numberOfTimesPlayed);
				
				number1 = generator.nextInt(20);
				number2 = generator.nextInt(20);
				total = number1 + number2;
				
				EnterText("What is " + number1 + " + " + number2 + "?");
			}
		} else {
			
			if (total == answer){
				EnterText("You are correct!");
				score += 10;
				System.out.println("Adding 10 to score. current score: " + score);
			}else{
				EnterText("Wrong.");
			}
			
			EnterText("You are finished with your quiz.");
			EnterText("Here's your score: " + score + "%");
			
			EnterText("Play again? (Y/N)");
		}
	}

	public static void main(String[] args) {
		
		new MathTest();

	}

}

class enterlistener implements ActionListener, KeyListener{
	
	public void somethingHappened(){
		
		String txt = "";
		
		try{
			txt = MathTest.input.getText();
		} catch (NullPointerException e){
			// Do Nothing
		}
		
		if (txt.equals("Y") || txt.equals("y") || txt.equals("/restart")){
			MathTest.EnterText("Okay, here we go!");
			MathTest.score = 0;
			MathTest.numberOfTimesPlayed = 1;
			MathTest.EnterText("Question #" + MathTest.numberOfTimesPlayed);
			MathTest.number1 = MathTest.generator.nextInt(20);
			MathTest.number2 = MathTest.generator.nextInt(20);
			MathTest.total = MathTest.number1 + MathTest.number2;
			MathTest.EnterText("What is " + MathTest.number1 + " + " + MathTest.number2 + "?");
			MathTest.input.selectAll();
		} else if (txt.equals("N") || txt.equals("n") || txt.equals("/quit")){
			MathTest.EnterText("Okay. Good-bye.");
			MathTest.EnterText("Thank you for playing.");
			MathTest.EnterText("Please quit this application by clicking the red button on");
			MathTest.EnterText("the top of the window.");
			
			MathTest.input.setText("");
			
			MathTest.input.setEditable(false);
			MathTest.enter.setEnabled(false);
			
		}else if(txt.equals("/clear")){
			MathTest.consoleMessages.setText("");
			MathTest.input.selectAll();
			MathTest.EnterText(MathTest.LastLine1); 
		}else if (txt.toLowerCase().contains("/setfont")){
			char data[] = txt.toCharArray();
			String newFont = String.copyValueOf(data, 9, data.length - 9);
			
			MathTest.input.setFont(new java.awt.Font(newFont, 0, 12));
			MathTest.consoleMessages.setFont(new java.awt.Font(newFont,0, 12));
			
			MathTest.EnterText("Font changed to " + newFont);
			
			MathTest.input.selectAll();
		}else if(txt.toLowerCase().contains("/say")){
			char data[] = txt.toCharArray();
			String txtForWrapTest = String.copyValueOf(data, 5, data.length - 5);
			MathTest.EnterText(txtForWrapTest);
			
			MathTest.input.selectAll();
		}
		else {
			try{ 
				int inputValue = Integer.parseInt(MathTest.input.getText());
				MathTest.nextQuestion(MathTest.number1, MathTest.number2, inputValue);
				MathTest.input.requestFocusInWindow();
				MathTest.input.selectAll();
			} catch (NumberFormatException e){
				MathTest.EnterText("Please type a number or one of the");
				MathTest.EnterText("commands available");
				MathTest.input.selectAll();
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
	
}