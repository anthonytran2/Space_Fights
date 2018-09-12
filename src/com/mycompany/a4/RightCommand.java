package com.mycompany.a4;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;

public class RightCommand extends Command{
	private GameWorld gameworld;
	
	public RightCommand(GameWorld gw) {
		super("Right");
		gameworld = gw;
	}
	
	@Override
	public void actionPerformed(ActionEvent e){
		gameworld.moveR();
	}
}
