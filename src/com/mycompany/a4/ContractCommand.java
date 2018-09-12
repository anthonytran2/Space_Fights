package com.mycompany.a4;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;

public class ContractCommand extends Command{
	private GameWorld gameworld;
	
	public ContractCommand(GameWorld gw) {
		super("Contract");
		gameworld = gw;
	}
	
	@Override
	public void actionPerformed(ActionEvent e){
		gameworld.contract();
	}
}
