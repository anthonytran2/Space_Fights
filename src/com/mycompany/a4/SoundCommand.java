package com.mycompany.a4;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;

public class SoundCommand extends Command{
	private GameWorld gameworld;
	
	public SoundCommand(GameWorld gw) {
		super("Sound");
		gameworld = gw;
	}
	
	//Change sound flag command
	@Override
	public void actionPerformed(ActionEvent e){
		gameworld.sound();
	}
}
