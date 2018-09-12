package com.mycompany.a4;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;

public class DownCommand extends Command{
	private GameWorld gameworld;
	
	public DownCommand(GameWorld gw) {
		super("Down");
		gameworld = gw;
	}
	
	@Override
	public void actionPerformed(ActionEvent e){
		gameworld.moveD();
	}
}
