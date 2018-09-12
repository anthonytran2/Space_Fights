package com.mycompany.a4;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;

public class ScoreCommand extends Command{
	private GameWorld gameworld;
	
	public ScoreCommand(GameWorld gw) {
		super("Score");
		gameworld = gw;
	}
	
	@Override
	public void actionPerformed(ActionEvent e){
		gameworld.openDoor();
	}
}
