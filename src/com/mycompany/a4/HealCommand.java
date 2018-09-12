package com.mycompany.a4;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;

public class HealCommand extends Command{
	private GameWorld gameworld;
	
	public HealCommand(GameWorld gw) {
		super("Heal");
		gameworld = gw;
	}
	
	@Override
	public void actionPerformed(ActionEvent e){
		IIterator gameObjects = gameworld.getGameWorldIterator();
		GameObject go;
		
		while(gameObjects.hasNext()){
			go = (GameObject) gameObjects.getNext();
			//If selected astronaut & game is in pause then heal to original state
			if(go instanceof ISelectable && ((ISelectable) go).isSelected() && gameworld.gameMode().equals("Pause")){
				//Astro to original state
				((Astronaut) go).orgiState();
				//Repaint map so heal shows while paused
				gameworld.notifyObs();
			}
		}
	}

}
