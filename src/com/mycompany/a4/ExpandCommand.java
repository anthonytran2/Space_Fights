package com.mycompany.a4;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;

public class ExpandCommand extends Command{
	private GameWorld gameworld;
	
	public ExpandCommand(GameWorld gw) {
		super("Expand");
		gameworld = gw;
	}
	
	@Override
	public void actionPerformed(ActionEvent e){
		gameworld.expand();
	}
}
