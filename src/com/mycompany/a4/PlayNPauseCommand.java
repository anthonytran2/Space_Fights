package com.mycompany.a4;

import com.codename1.ui.Command;
import com.codename1.ui.Form;
import com.codename1.ui.events.ActionEvent;

public class PlayNPauseCommand extends Command{
	private GameWorld gameworld;
	private Form game;
	private static String name = "Pause";
	
	public PlayNPauseCommand(GameWorld gw, Form f) { 	//Takes in form to access timer
		super(name);
		gameworld = gw;
		game = f;
	}
	
	@Override
	public void actionPerformed(ActionEvent e){
		//Change name
		if(name.equals("Pause")){
			name = "Play";
		} else if(name.equals("Play")){
			name = "Pause";
		}
		this.setCommandName(name);
		//Play pressed name is now pause, resume timer
		if(name.equals("Pause")){
			((Game) game).getTimer().schedule(((Game) game).getElaTime(), true, game);
			gameworld.gameWorldMode(name);
		}
	}
}
