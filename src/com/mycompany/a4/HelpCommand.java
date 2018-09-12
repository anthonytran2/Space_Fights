package com.mycompany.a4;

import com.codename1.ui.Command;
import com.codename1.ui.Dialog;
import com.codename1.ui.TextArea;
import com.codename1.ui.events.ActionEvent;

public class HelpCommand extends Command{
	
	public HelpCommand() {
		super("Help");
	}
	
	//Dialog help - shows key bindings
	@Override
	public void actionPerformed(ActionEvent e){
		Command cOk = new Command("Ok");
		TextArea ta = new TextArea("e - expand door\n"
				+ "c - contract door\n"
				+ "r - open door\n"
				+ "d - move right\n"
				+ "a - move left\n"
				+ "w - move up\n"
				+ "s - move down\n"
				+ "x - exit game\n");
		
		ta.setEditable(false);
		Dialog.show("Help", ta, cOk);
	}
}
