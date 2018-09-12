package com.mycompany.a4;

import com.codename1.ui.Command;
import com.codename1.ui.Dialog;
import com.codename1.ui.TextArea;
import com.codename1.ui.events.ActionEvent;

public class AboutCommand extends Command{
	
	public AboutCommand() {
		super("About");
	}
	
	//Dialog with information
	@Override
	public void actionPerformed(ActionEvent e){
		Command cOk = new Command("Ok");
		TextArea ta = new TextArea("Anthony Tran\n" 
				+ "CSC133-03\n" 
				+ "Verison: 3.0\n");
		
		ta.setEditable(false);
		Dialog.show("About", ta, cOk);
	}
}
