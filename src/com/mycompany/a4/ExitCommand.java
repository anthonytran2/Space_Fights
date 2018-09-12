package com.mycompany.a4;

import com.codename1.ui.Command;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.TextArea;
import com.codename1.ui.events.ActionEvent;

public class ExitCommand extends Command{

	public ExitCommand() {
		super("Exit");
	}
	
	//Dialog exit message & confirm
	@Override
	public void actionPerformed(ActionEvent e){
		//'x'- Exit, then confirm.
		Command cOk = new Command("Ok");
		Command cCancel = new Command("Cancel");
		Command[] cmds = new Command[] {cOk, cCancel};
			
		TextArea ta = new TextArea("Do you want to exit?");
		ta.setEditable(false);
		Command c = Dialog.show("Exit", ta, cmds);
		if(c == cOk)
			Display.getInstance().exitApplication();
	}
}

