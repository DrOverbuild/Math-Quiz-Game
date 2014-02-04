/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mathquizgame.commands;


public class CommandImpl{
	public static final ChangeBackgroundCommand changeBackgroundCommand = new ChangeBackgroundCommand();
	public static final ClearCommand            clearCommand            = new ClearCommand();
	public static final ClearFileCommand		clearFileCommand		= new ClearFileCommand();
	public static final DebugCommand			debugCommand			= new DebugCommand();
	public static final HelpCommand				helpCommand				= new HelpCommand();
	public static final HistoryCommand			historyCommand			= new HistoryCommand();
	public static final QuitCommand				quitCommand				= new QuitCommand();
	public static final RestartCommand			restartCommand			= new RestartCommand();
	public static final SayCommand				sayCommand				= new SayCommand();
	public static final SetFontCommand			setFontCommand			= new SetFontCommand();
	public static final SetSizeCommand			setSizeCommand			= new SetSizeCommand();

	public void parseAndExecuteCommand(String txt) throws CommandNotFoundException{

		String commandName = txt.split(" ")[0];
		commandName = commandName.substring(1,commandName.length());
		commandName = commandName.toLowerCase();

		Command command = null;
		Command[] commands = new Command[11];
		commands[0] = changeBackgroundCommand;
		commands[1] = clearCommand;
		commands[2] = clearFileCommand;
		commands[3] = debugCommand;
		commands[4] = helpCommand;
		commands[5] = historyCommand;
		commands[6] = quitCommand;
		commands[7] = restartCommand;
		commands[8] = sayCommand;
		commands[9] = setFontCommand;
		commands[10]= setSizeCommand;

		for (Command command1 : commands) {
			if (commandIsEqualTo(command1, commandName)) command = command1;
		}

		if(command == null) throw new CommandNotFoundException(commandName);

		command.execute(parseArgs(commandName, txt));
	}

	public static boolean commandIsEqualTo(Command cmd, String txt){
		boolean getNameEquals = cmd.getName().equals(txt);
		boolean getShortcutEquals = cmd.getShortcut().equals(txt);
		return getNameEquals || getShortcutEquals;
	}

	public static String[] parseArgs(String command, String userInput){
		int lengthOfCommand = command.length()+2;
		int lengthOfUserInput = userInput.length();
		int lengthOfArgument = lengthOfUserInput - lengthOfCommand;
		char[] toCharArray = userInput.toCharArray();
		String argument = String.copyValueOf(toCharArray, lengthOfCommand, lengthOfArgument);

		String[] splitted = argument.split("\\s");

		return splitted;

	}

}
