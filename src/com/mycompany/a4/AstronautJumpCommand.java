package com.mycompany.a4;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;

public class AstronautJumpCommand extends Command{
	private GameWorld gameworld;
	
	public AstronautJumpCommand(GameWorld gw) {
		super("MoveToAstronaut");
		gameworld = gw;
	}
	
	@Override
	public void actionPerformed(ActionEvent e){
		gameworld.jumpAstro();
	}
}
