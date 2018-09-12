package com.mycompany.a4;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;

public class AlienJumpCommand extends Command {
	private GameWorld gameworld;
	
	public AlienJumpCommand(GameWorld gw) {
		super("MoveToAlien");
		gameworld = gw;
	}
	
	@Override
	public void actionPerformed(ActionEvent e){
		gameworld.jumpAlien();
	}
}
