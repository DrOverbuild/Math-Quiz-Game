/*
 * Copyright (c) 2014. Jasper Reddin.
 * All Rights Reserved.
 */

package mathquizgame.commands;



/**
 * Created by jasper on 1/27/14.
 */
public interface Command {

	public abstract void execute(String[] args);

	public abstract String getName();
	public abstract String getShortcut();

}
