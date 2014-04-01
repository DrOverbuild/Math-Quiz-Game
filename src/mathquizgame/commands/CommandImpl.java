/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mathquizgame.commands;


public class CommandImpl{
	/**
	 * This field stores all commands available to execute in a MathQuizGame instance. To add a new command,
	 * all that has to be done is add a new Object that implements the Command interface to this array. It
	 * might be helpful to have the class which implements the Command interface in a separate file and not as
	 * a nested class.
	 */
	public static Command[] commands = new Command[]{
		new AutomaticTextCommand(),
		new ChangeBackgroundCommand(),
		new ClearCommand(),
		new ClearFileCommand(),
		new DebugCommand(),
		new HelpCommand(),
		new HistoryCommand(),
		new QuitCommand(),
		new RestartCommand(),
		new SayCommand(),
		new SetFontCommand(),
		new SetSizeCommand(),
		new SystemInfoCommand()};

	public void parseAndExecuteCommand(String txt) throws CommandNotFoundException{

		String commandName = txt.split(" ")[0];
		commandName = commandName.substring(1,commandName.length());
		commandName = commandName.toLowerCase();

		Command command = null;

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

		String argument = null;
		if (userInput.equals("/"+command)) {
			argument = "";
		}else{
			int lengthOfCommand = ("/"+command).length() + 1;
			int lengthOfUserInput = userInput.length();
			int lengthOfArgument = lengthOfUserInput - lengthOfCommand;
			char[] toCharArray = userInput.toCharArray();
			argument = String.copyValueOf(toCharArray, lengthOfCommand, lengthOfArgument);
		}

		String[] splitted = argument.split("\\s");

		return splitted;

	}

}
