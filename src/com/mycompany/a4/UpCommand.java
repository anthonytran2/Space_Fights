package com.mycompany.a4;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;

public class UpCommand extends Command{
	private GameWorld gameworld;
	
	public UpCommand(GameWorld gw) {
		super("Up");
		gameworld = gw;
	}
	
	@Override
	public void actionPerformed(ActionEvent e){
		gameworld.moveU();
	}
}
