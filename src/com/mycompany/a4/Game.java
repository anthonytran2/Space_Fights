package com.mycompany.a4;


import java.util.ArrayList;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Button;
import com.codename1.ui.Command;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Form;
import com.codename1.ui.Toolbar;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.plaf.Border;
import com.codename1.ui.util.UITimer;

public class Game extends Form implements Runnable{
	private GameWorld gw; //Observable
	private MapView mv;	  //Observer
	private ScoreView sv; //Observer
	
	private UITimer timer = null;
	private int elaTime = 40; //Timer tick time ms

	//Buttons east, west, south
	private Button contract;
	private Button down;
	private Button right;
	private Button moveToAlien;
	private Button score;
	private Button expand;
	private Button up;
	private Button left;
	private Button moveToAstronaut;
	private Button heal;
	private Button playNPause;
	
	//Side menu buttons
	private Button smSound;
	private Button smAbout;
	private Button smExit;
	
	//Commands
	private ExpandCommand expandc;
	private ContractCommand contractc;
	private ScoreCommand scorec;
	private RightCommand rightc;
	private LeftCommand leftc;
	private UpCommand upc;
	private DownCommand downc;
	private AstronautJumpCommand astroc;
	private AlienJumpCommand alienc;
	private HealCommand healc;
	private PlayNPauseCommand playNPausec;
	private ExitCommand exitc;
	private SoundCommand soundc;
	private AboutCommand aboutc;
	private HelpCommand helpc;
	
	//List of buttons to be enabled/disabled
	private ArrayList<Button> buttonList = new ArrayList<Button>();
	private ArrayList<Command> commandList = new ArrayList<Command>();
	private ArrayList<Integer> keyList = new ArrayList<Integer>();
	private ArrayList<Button> sideMenuList = new ArrayList<Button>();
	
	private BorderLayout bl;
	private int width;
	private int height;
	
	public Game(){
		
		//Button East
		contract = new Button("Contract");
		down = new Button("Down");
		right = new Button("Right");
		moveToAlien = new Button("MoveToAlien");
		score = new Button("Score");
		//Button West 
		expand = new Button("Expand");
		up = new Button("Up");
		left = new Button("Left");
		moveToAstronaut = new Button("MoveToAstronaut");
		//Button South
		heal = new Button("Heal");
		playNPause = new Button("PlayNPause");
		
		//Set button style East
		setButStyle(contract);
		setButStyle(down);
		setButStyle(right);
		setButStyle(moveToAlien);
		setButStyle(score);
		score.getAllStyles().setPadding(10, 10, 3, 3); //Score is bigger then the rest.
		//Set button style West
		setButStyle(expand);
		setButStyle(up);
		setButStyle(left);
		setButStyle(moveToAstronaut);
		//Set button style South
		setButStyle(playNPause);
		//Heal button is unique (white bg & blue border & blue font)
		heal.getAllStyles().setBgTransparency(255);
		heal.getAllStyles().setFgColor(ColorUtil.BLUE); //White text
		heal.getAllStyles().setBgColor(ColorUtil.WHITE);  //Blue background
		heal.getAllStyles().setPadding(5,5,3,3);   //heal size Top,Bottom,Left,Right
		heal.getAllStyles().setBorder(Border.createLineBorder(4,ColorUtil.BLUE)); //Set Border blue

		//Toolbar
		Toolbar toolbar = new Toolbar();
		setToolbar(toolbar);
		toolbar.setTitle("Space Fights Game");
		
		//Containers
		Container eastCt = new Container();
		Container westCt = new Container();
		Container southCt = new Container();
		//Set container layouts
		eastCt.setLayout(new BoxLayout(BoxLayout.Y_AXIS));
		westCt.setLayout(new BoxLayout(BoxLayout.Y_AXIS));
		southCt.setLayout(new FlowLayout(Component.CENTER));
		//Create container borders
		eastCt.getAllStyles().setPadding(Component.TOP, 150);
		westCt.getAllStyles().setPadding(Component.TOP, 150); //150 pixels from component top.
		eastCt.getAllStyles().setBorder(Border.createLineBorder(2, ColorUtil.BLACK)); //Thickness, Color
		westCt.getAllStyles().setBorder(Border.createLineBorder(2, ColorUtil.BLACK));
		southCt.getAllStyles().setBorder(Border.createLineBorder(2, ColorUtil.BLACK));
		
		//Add button to East container
		eastCt.add(contract);
		eastCt.add(down);
		eastCt.add(right);
		//eastCt.add(moveToAlien);
		eastCt.add(score);
		//Add button to West container
		westCt.add(expand);
		westCt.add(up);
		westCt.add(left);
		//westCt.add(moveToAstronaut);
		//Add button to south container
		//southCt.add(heal);
		southCt.add(playNPause);
		
		//Mapview
		mv = new MapView();
		mv.getAllStyles().setBorder(Border.createLineBorder(2, ColorUtil.BLACK));
		//Scoreview
		sv = new ScoreView();
		
		//Set main container layout
		bl = new BorderLayout();
		setLayout(bl);
		
		//Add containers to main (border) layout.
		add(BorderLayout.EAST, eastCt);
		add(BorderLayout.WEST, westCt);
		add(BorderLayout.SOUTH, southCt);
		add(BorderLayout.NORTH, sv);
		add(BorderLayout.CENTER, mv);
		
		//Game world initialize
		gw = new GameWorld();
		gw.addObserver(mv);
		gw.addObserver(sv);
		
		//Commands
		expandc = new ExpandCommand(gw);
		contractc = new ContractCommand(gw);
		scorec = new ScoreCommand(gw);
		rightc = new RightCommand(gw);
		leftc = new LeftCommand(gw);
		upc = new UpCommand(gw);
		downc = new DownCommand(gw);
		//astroc = new AstronautJumpCommand(gw);
		//alienc = new AlienJumpCommand(gw);
		healc = new HealCommand(gw);
		playNPausec = new PlayNPauseCommand(gw, this);
		exitc = new ExitCommand();
		soundc = new SoundCommand(gw);
		aboutc = new AboutCommand();
		helpc = new HelpCommand();
		
		//Sidemenu CheckBox
		//CheckBox soundCheck = new CheckBox("Sound");
		smSound = new Button("Sound");
		smSound.setCommand(soundc);
		setButStyle(smSound);	
		soundc.putClientProperty("SideComponent", smSound);
		//Sidemenu about button set up
		smAbout = new Button("About");
		smAbout.setCommand(aboutc);
		setButStyle(smAbout);
		aboutc.putClientProperty("SideComponent", smAbout);
		//Sidemenu exit button set up
		smExit = new Button("Exit");
		smExit.setCommand(exitc);
		setButStyle(smExit);
		exitc.putClientProperty("SideComponent", smExit);
		
		//Add commands to toolbar.
		toolbar.addCommandToSideMenu(soundc);
		toolbar.addCommandToSideMenu(aboutc);
		toolbar.addCommandToSideMenu(exitc);
		toolbar.addCommandToRightBar(helpc);
		//Add commands to buttons.
		expand.setCommand(expandc);
		contract.setCommand(contractc);
		score.setCommand(scorec);
		right.setCommand(rightc);
		left.setCommand(leftc);
		up.setCommand(upc);
		down.setCommand(downc);
		moveToAstronaut.setCommand(astroc);
		moveToAlien.setCommand(alienc);
		heal.setCommand(healc);
		playNPause.setCommand(playNPausec);
		//Add commands to key bindings.
		addKeyListener('e', expandc);
		addKeyListener('c', contractc);
		addKeyListener('r', scorec);
		addKeyListener('d', rightc);
		addKeyListener('a', leftc);
		addKeyListener('w', upc);
		addKeyListener('s', downc);
		addKeyListener('x', exitc);
		//addKeyListener('o', astroc);
		//addKeyListener('a', alienc);

		//Put all buttons, commands, and keys in a list for enabling/disabling
		makeList();
		
		this.show();
		
		//Set boundaries for objects
		height = Math.round(mv.getWinWidth()); //bl.getCenter().getHeight();
		width = Math.round(mv.getWinHeight()); //bl.getCenter().getWidth();
		gw.setX(height);
		gw.setY(width);
		gw.init();
		
		//Get a new instance of timer
		timer =  new UITimer(this);	
		timer.schedule(elaTime, true, this);
	}
	
	//Add buttons, commands, and keys for enabling and disabling
	public void makeList(){
		//Buttons
		buttonList.add(contract);
		buttonList.add(down);
		buttonList.add(right);
		buttonList.add(score);
		buttonList.add(expand);
		buttonList.add(up);
		buttonList.add(left);
		//buttonList.add(moveToAstronaut);
		//buttonList.add(moveToAlien);
		
		//Commands
		commandList.add(contractc);
		commandList.add(rightc);
		commandList.add(scorec);
		commandList.add(downc);
		commandList.add(expandc);
		commandList.add(upc);
		commandList.add(leftc);
		//commandList.add(astroc);
		//commandList.add(alienc);
		
		//Key listeners
		keyList.add((int) 'c');
		keyList.add((int) 'd');
		keyList.add((int) 'r');
		keyList.add((int) 's');
		keyList.add((int) 'e');
		keyList.add((int) 'w');
		keyList.add((int) 'a');
		//keyList.add((int) 'o');
	}
	
	//Set button style
	public void setButStyle(Button button){
		button.getAllStyles().setBgTransparency(255);
		button.getAllStyles().setFgColor(ColorUtil.WHITE); //White text
		button.getAllStyles().setBgColor(ColorUtil.BLUE);  //Blue background
		button.getAllStyles().setPadding(5,5,3,3);   //Button size Top,Bottom,Left,Right
	}
	
	//Returns timer in game
	public UITimer getTimer(){
		return timer;
	}
	//Return elapse time
	public int getElaTime(){
		return elaTime;
	}
	
	//Change mode handling
	public void mode(){
		//Update Command name to play/pause
		playNPause.setCommand(playNPausec);
		
		//Pause pressed command is now named play, pause
		if(playNPause.getCommand().getCommandName().equals("Play")){
			//Enable heal when in pause
			//heal.setEnabled(true);
			//healc.setEnabled(true);
			//Disable button/command/style
			for(int i=0; i<buttonList.size(); i++){
				buttonList.get(i).setEnabled(false);
				commandList.get(i).setEnabled(false);
				removeKeyListener(keyList.get(i), commandList.get(i));
				buttonList.get(i).getDisabledStyle().setBgColor(ColorUtil.GRAY);
			}	
			//Disable side menu button/style
			for(int i=0; i<sideMenuList.size(); i++){
				sideMenuList.get(i).setEnabled(false);
				sideMenuList.get(i).getDisabledStyle().setBgColor(ColorUtil.GRAY);
			}
			//Cancel timer and tell gameworld to change mode
			timer.cancel();
			gw.gameWorldMode(playNPause.getCommand().getCommandName());
		} else if(playNPause.getCommand().getCommandName().equals("Pause") && heal.isEnabled()){ // heal.isEnable() check is to stop not needed enabling of enable mode buttons(etc)
			//Disable heal in play
			//heal.setEnabled(false);
			//healc.setEnabled(false);
			//heal.getDisabledStyle().setBgColor(ColorUtil.GRAY);
			//Enable button/command
			for(int i=0; i<buttonList.size(); i++){
				buttonList.get(i).setEnabled(true);
				commandList.get(i).setEnabled(true);
				addKeyListener(keyList.get(i), commandList.get(i));
			}
			 //Enable sidemenu button
			for(int i=0; i<sideMenuList.size(); i++){
				sideMenuList.get(i).setEnabled(true);
				//No need to enable command again, already done above
			}	
		}	
	}
	
	//Collision Detection
	public void colDect(){
		IIterator iter = gw.getGameWorldIterator();
		GameObject go1;
		GameObject go2;
		
		while(iter.hasNext()){
			go1 = (GameObject) iter.getNext();
			if(go1 instanceof ICollider){
				ICollider curObj = (ICollider) go1;
				IIterator iter2 = gw.getGameWorldIterator();
				
				while(iter2.hasNext()){
					go2 = (GameObject) iter2.getNext();
					if(go2 instanceof ICollider){ //Check if instance of ICollider
						ICollider otherObj = (ICollider) go2;
						if(curObj != otherObj){ //Check if not itself
							//Objects collided and not in each others collided list
							if(curObj.collidesWith(otherObj) && ((Opponents) curObj).getList().contains(otherObj) == false 
										&& ((Opponents) otherObj).getList().contains(curObj) == false){
								
								//Handle collision
								curObj.handleCollision(otherObj);
								//If aliens collide make spawn alien
								if(go1 instanceof Alien && go2 instanceof Alien){ 
									if(((Alien) go1).newAlienCheck()) { //Check if collision happened
										Alien newAlien = gw.newAlien((Alien) go1);
										
										if(newAlien != null){ //If maxAlien not reached them spawn new one
											//Add to collision lists
											newAlien.addColList(curObj);
											newAlien.addColList(otherObj);
											((Opponents) curObj).addColList(newAlien);
											((Opponents) otherObj).addColList(newAlien);
											//Reset indicator
											((Alien) go1).setNewAlienCheck(false);
										}
									}
									//Alien & astronaut collision
								} else if(go1 instanceof Astronaut && go2 instanceof Alien || go2 instanceof Astronaut && go1 instanceof Alien
										&& ((Opponents) curObj).getList().contains(otherObj) == false && ((Opponents) otherObj).getList().contains(curObj) == false){
									
									//Check if they are fighting
									//Check which is astronaut
									if(go1 instanceof Astronaut && ((Astronaut) go1).fightCheck()){
										gw.fight((Astronaut) go1);
										//Reset Indicator
										((Astronaut) go1).setFightCheck(false);
									} else if(go2 instanceof Astronaut && ((Astronaut) go2).fightCheck()){
										gw.fight((Astronaut) go2);
										((Astronaut) go2).setFightCheck(false);
									}
								}
								//Add each other to their collision list
								((Opponents) curObj).addColList(otherObj);
								((Opponents) otherObj).addColList(curObj);
								//Remove each other from list when no longer colliding
							} else if(curObj.collidesWith(otherObj) == false && ((Opponents) curObj).getList().contains(otherObj)
									&& ((Opponents) otherObj).getList().contains(curObj)){
								
								((Opponents) curObj).removeColList(otherObj);
								((Opponents) otherObj).removeColList(curObj);
							}
						}
					}
				}
			}
		}	
	}
	
	//Timer
	public void run() {
		//Update modes
		mode();
		//Move objects
		gw.tick(elaTime);
		//Dectect Collisions
		colDect();
		//Repaint
		mv.repaint();
	}
		
}
