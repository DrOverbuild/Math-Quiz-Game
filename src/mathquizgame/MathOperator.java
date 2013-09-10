/*
 * Copyright (c) 2013
 * Jasper Reddin
 */

package mathquizgame;

import java.util.Random;

/**
 *
 * @author jasper
 */
public class MathOperator {
	
	static Random generator = new Random();
	static int number1;
	static int number2;
	static char operationToUse;
	
	static int customMinRange;
	static int customMaxRange;
	static char customOperation;
	
	// Operation characters
	public static final char PLUS_OPERATION = '+';
	public static final char SUBTRACT_OPERATION = '-';
	public static final char MULTIPLY_OPERATION = '*';
	public static final char DIVIDE_OPERATION = '/';
	
	// Difficulty fields
	public static final int ELEMENTARY_DIFFICULTY = 0;
	public static final int MIDDLE_SCHOOL_DIFFICULTY = 1;
	public static final int HIGH_SCHOOL_DIFFICULTY = 2;
	public static final int CUSTOM_DIFFICULTY = 3;
	
	public static void operate(int userAnswer){
		
		if (MathQuizGame.numberOfTimesPlayed <= 9){
			if (userAnswer == MathQuizGame.total){
				EnterText("You are correct!");
				MathQuizGame.score += 10;
				
				newNumbers();
			} else {
				EnterText("Wrong. Correct Answer: " + MathQuizGame.total);
				
				newNumbers();
				
			}
		} else {
			
			if (MathQuizGame.total == userAnswer){
				EnterText("You are correct!");
				MathQuizGame.score += 10;
			}else{
				EnterText("Wrong. Correct Answer: " + MathQuizGame.total);
			}
			
			EnterText("You are finished with your quiz.");
			EnterText("Here's your score: " + MathQuizGame.score + "%");
			
			EnterText("Play again? (Y/N)");
		}
	}
	
	public static void startGameElementary(){
		resetFields();
		setDifficulty(ELEMENTARY_DIFFICULTY);
		EnterText("Starting a new game set in the Elementary Difficulty.");
		EnterText("Question #" + MathQuizGame.numberOfTimesPlayed);
		GenerateNewNumbers(0);
		determineQuestionToAsk();
		MathQuizGame.number1 = number1;
		MathQuizGame.number2 = number2;
		MathQuizGame.input.requestFocusInWindow();
		MathQuizGame.input.selectAll();
		MathQuizGame.setQuestionState(0);
	}
	public static void startGameMiddle(){
		resetFields();
		setDifficulty(MIDDLE_SCHOOL_DIFFICULTY);
		EnterText("Starting a new game set in the Middle School Difficulty.");
		EnterText("Question #" + MathQuizGame.numberOfTimesPlayed);
		GenerateNewNumbers(1);
		determineQuestionToAsk();
		MathQuizGame.number1 = number1;
		MathQuizGame.number2 = number2;
		MathQuizGame.input.requestFocusInWindow();
		MathQuizGame.input.selectAll();
		MathQuizGame.setQuestionState(0);
	}
	public static void startGameHigh(){
		resetFields();
		setDifficulty(HIGH_SCHOOL_DIFFICULTY);
		EnterText("Starting a new game set in the High School Difficulty.");
		EnterText("Question #" + MathQuizGame.numberOfTimesPlayed);
		GenerateNewNumbers(2);
		determineQuestionToAsk();
		MathQuizGame.total = number1 + number2;
		MathQuizGame.number1 = number1;
		MathQuizGame.number2 = number2;
		MathQuizGame.input.requestFocusInWindow();
		MathQuizGame.input.selectAll();
		MathQuizGame.setQuestionState(0);
	}
	public static void startGameCustom(String[] customArgs){
		resetFields();
		setDifficulty(CUSTOM_DIFFICULTY);
		EnterText("Starting a new game set in the Custom Difficulty.");
		EnterText("Question #" + MathQuizGame.numberOfTimesPlayed);
		customMaxRange = Integer.parseInt(customArgs[0]);
		customMinRange = Integer.parseInt(customArgs[1]);
		char[] customOperationArray = customArgs[2].toCharArray();
		customOperation = customOperationArray[0];
		GenerateNewNumbers(3);
		determineQuestionToAsk();
		MathQuizGame.number1 = number1;
		MathQuizGame.number2 = number2;
		MathQuizGame.input.requestFocusInWindow();
		MathQuizGame.input.selectAll();
		MathQuizGame.setQuestionState(0);
	}

	
	
	private static void EnterText(String txt){
		MathQuizGame.EnterText(txt);
	}
	
	private static void GenerateNewNumbers(int difficulty){
		switch (difficulty) {
			case 0:
				number1 = generator.nextInt(11);
				number2 = generator.nextInt(11);
				operationToUse = '+';
				break;
			case 1:
				number1 = generator.nextInt(21);
				number2 = generator.nextInt(21);
				operationToUse = randomOp('+','*',' ',' ');
				break;
			case 2:
				number1 = generator.nextInt(41) - 20;
				number2 = generator.nextInt(41) - 20;
				operationToUse = randomOp('+','-','*',' ');
				break;
			case 3:
				number1 = generator.nextInt(customMaxRange - customMinRange) + customMinRange;
				number2 = generator.nextInt(customMaxRange - customMinRange) + customMinRange;
				operationToUse = customOperation;
				break;
			default:
				EnterText("Error: Difficulty out of range.");
				break;
		}
	}
	
	private static void setDifficulty(int newDifficulty){
		MathQuizGame.setDifficulty(newDifficulty);
	}
	
	private static void resetFields(){
		MathQuizGame.numberOfTimesPlayed = 1;
		MathQuizGame.score = 0;
		MathQuizGame.total = 0;
		MathQuizGame.number1 = 0;
		MathQuizGame.number2 = 0;
	}
	
	private static char randomOp(char op1, char op2, char op3, char op4){
		char[] ops = new char[4];
		ops[0] = op1;
		ops[1] = op2;
		ops[2] = op3;
		ops[3] = op4;
		
		if(op4 == ' '){
			int randomIndex = generator.nextInt(3);
			return ops[randomIndex];
		}else if (op3 == ' '){
			int randomIndex = generator.nextInt(2);
			return ops[randomIndex];
		}else{
			int randomIndex = generator.nextInt(4);
			return ops[randomIndex];
		}
	}
	
	private static void determineQuestionToAsk(){
		if(operationToUse == '+'){
			MathQuizGame.total = number1 + number2;
			EnterText("What is " + number1 + " + " + number2 + "?");
		}else if (operationToUse == '-'){
			MathQuizGame.total = number1 - number2;
			EnterText("What is " + number1 + " - " + number2 + "?");
		}else{
			MathQuizGame.total = number1 * number2;
			EnterText("What is " + number1 + " * " + number2 + "?");
		}
	}
	private static void newNumbers(){
		MathQuizGame.numberOfTimesPlayed += 1;
		EnterText("Question #" + MathQuizGame.numberOfTimesPlayed);
		GenerateNewNumbers(MathQuizGame.difficultyLevel);
		determineQuestionToAsk();
		MathQuizGame.number1 = number1;
		MathQuizGame.number2 = number2;
		MathQuizGame.input.requestFocusInWindow();
		MathQuizGame.input.selectAll();
		MathQuizGame.setQuestionState(0);
	}
}