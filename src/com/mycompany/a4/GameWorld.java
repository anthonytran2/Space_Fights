package com.mycompany.a4;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Random;

import com.codename1.ui.Command;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.TextArea;
import com.codename1.ui.geom.Point2D;

public class GameWorld extends Observable{ 
	//Game object collection
	private GameObjectCollection gameObjects = new GameObjectCollection();
	//Max objects
	private int maxAstro = 4;
	private int maxSpaceship = 1;
	private int maxAlien = 30;
	private int initAlienCnt = 3;
	//Location object counts
	private int astroOut = maxAstro;
	private int astroIn = 0;
	private int alienOut = initAlienCnt;
	private int alienIn = 0;
	//Score count
	private int score = 0;
	
	//Sound Flag 0 = off/1 = on
	private boolean soundFlag = true;
	//Door Sound, New alien sound, fight sound, bg sound.
	private Sound doorSound = new Sound("DoorSound.wav");
	private Sound newAlienSound = new Sound("NewAlienSound.wav");
	private Sound fightSound = new Sound("FightSound.wav");
	private BGSound bgSound = new BGSound("BGSound.wav");
	
	//Game mode, start in play
	private String gameMode = "Play";
	//Use for printing state values each tick
	private int tickCounter = 0;
	//The amount to move by for door and spaceship user movement
	private int moveVal = 25;
	//Boundaries... updated when component show()
	private int maxX = 1024;
	private int maxY = 768;
	
	
	
	//initiate world with game Objects
	public void init(){
		add("spaceship", maxSpaceship);
		add("alien", initAlienCnt);
		add("astro", maxAstro);
		//Play bg sound when started.
		bgSound.play();
	}

	//Width
	public void setX(int x){
		maxX = x;
	}
	//Height
	public void setY(int y){
		maxY = y;
	}
	
	//Return game mode as String "Play/Pause"
	public String gameMode(){
		return gameMode;
	}
	//Handle mode change 
	public void gameWorldMode(String name){
		//Pause pressed, button name is now play, handle pause
		if(name.equals("Play")){
			gameMode = "Pause";
			bgSound.pause();
		} else if(name.equals("Pause")){ //Play pressed, button name is pause, handle play;
			gameMode = "Play";
			bgSound.play();
		}
	}
	
	//Notify observers of GameWorld change
	public void notifyObs(){
		this.setChanged();
		this.notifyObservers(this);
	}
	
	//Returns iterator for game objects
	public IIterator getGameWorldIterator(){
		return gameObjects.getIterator();
	}
	
	//Adds to GameObjects ArrayList depending on object type 
	//and also accepts the number of objects added
	public GameObject add(String name, int num){
		for(int i=0; i<num; i++){
			if(name.equals("spaceship")){
				GameObject ss = (Spaceship.getSpaceship());
				//Set boundaries for spaceship
				ss.setMaxX(maxX);
				ss.setMaxY(maxY);
				gameObjects.add(ss); //Singleton-only 1 spaceship allowed.
			} else if(name.equals("alien")){
				Alien a = new Alien(false);
				//Set object boundaries
				a.setMaxX(maxX);
				a.setMaxY(maxY);
				gameObjects.add(a); // false, not a spawn of collision
			} else if(name.equals("astro")){
				//Set object boundaries
				Astronaut ao = new Astronaut();
				ao.setMaxX(maxX);
				ao.setMaxY(maxY);
				gameObjects.add(ao);
			}
		}
		
		//Notify observers
		notifyObs();
		
		IIterator go = gameObjects.getIterator();
		return (GameObject) go.get(go.size()-1);
	}
	
	//'a' - Spaceship jumps to alien.
	public void jumpAlien(){
		ArrayList <Alien> alienList = new ArrayList<Alien>();
        Random rand = new Random();
		int randNum;
		
		//Copy alien objects to array list for random picking
		IIterator objsCollection = gameObjects.getIterator(); //Get GameObjectCollection Iterator
		GameObject go;			//GameObject access
		while(objsCollection.hasNext()){
        	 go = (GameObject) objsCollection.getNext();
	         if (go instanceof Alien) { 
                 Alien astro = (Alien) go;
                 alienList.add(astro);     
	         } 
		}
		
		if(alienList.size()>0){
	        //Randomize alien choice, 0 to total alien in world.
	        randNum = 0 + rand.nextInt(alienList.size());    
	        //Spaceship jump to random alien.
			( (Spaceship) (objsCollection.get(0))).jumpToLocation(alienList.get(randNum).getLocation());		
		} else {
			System.out.println("No more aliens");
		}
		
		//Notify observers
		notifyObs();
	}
	
	//'o' - Spaceship jumps to astronaut.
	public void jumpAstro(){
		ArrayList <Astronaut> astroList = new ArrayList<Astronaut>();
        Random rand = new Random();
		int randNum;
		
		//Copy astro object to array list for random picking
		IIterator objsCollection = gameObjects.getIterator();
		GameObject go;
		while(objsCollection.hasNext()){
        	 go = (GameObject) objsCollection.getNext();
	         if (go instanceof Astronaut) { 
                 Astronaut astro = (Astronaut) go;
                 astroList.add(astro);     
          } 
		}

		if(astroList.size()>0){
	        //Randomize astro choice, 0 to total astros.
	        randNum = 0 + rand.nextInt(astroList.size());

	        //Spaceship jump to random astro.
			( (Spaceship) (objsCollection.get(0))).jumpToLocation(astroList.get(randNum).getLocation());
		} else {
			System.out.println("No more astronauts");
		}
		
		//Notify observers
		notifyObs();
	}
	
	//'r' - Move right.
	public void moveR(){
		IIterator objsCollection = gameObjects.getIterator();
		((Spaceship) objsCollection.get(0)).moveRight(moveVal);
		//Notify observers
		notifyObs();
	}
	
	//'l' - Move left.
	public void moveL(){
		IIterator objsCollection = gameObjects.getIterator();
		((Spaceship) objsCollection.get(0)).moveLeft(moveVal);
		//Notify observers
		notifyObs();
	}

	//'u' Move up.
	public void moveU(){
		IIterator objsCollection = gameObjects.getIterator();
		((Spaceship) objsCollection.get(0)).moveUp(moveVal);
		//Notify observers
		notifyObs();
	}
	
	//'d' Move down.
	public void moveD(){
		IIterator objsCollection = gameObjects.getIterator();
		((Spaceship) objsCollection.get(0)).moveDown(moveVal);
		//Notify observers
		notifyObs();
	}
	
	
	//'e' - Expand spaceship door
	public void expand(){
		IIterator objsCollection = gameObjects.getIterator();
		Spaceship ss = ((Spaceship) (objsCollection.get(0)));
		ss.setSize(ss.getSize()+moveVal);	
		//Notify observers
		notifyObs();
	}
	
	//'c' - Contract spaceship door
	public void contract(){
		IIterator objsCollection = gameObjects.getIterator();
		Spaceship ss = ((Spaceship) (objsCollection.get(0)));
		ss.setSize(ss.getSize()-moveVal);	
		//Notify observers
		notifyObs();
	}
	
	//'t' - tick game clock
	public void tick(int elaTimer){
		System.out.println("Tick: " + tickCounter++);
		IIterator objsCollection = gameObjects.getIterator();
		GameObject go;
		
		//Display end msg and final score and then exit
		if(astroIn==maxAstro){
			Command cOk = new Command("Ok");
			Command cCancel = new Command("Cancel");
			Command[] cmds = new Command[] {cOk, cCancel};
				
			TextArea ta = new TextArea("Score: " + score);
			ta.setEditable(false);
			Command c = Dialog.show("Game Over", ta, cmds);
			if(c == cOk)
				Display.getInstance().exitApplication();
		}
		
		//Move objects
		while(objsCollection.hasNext()){
       	 	go = (GameObject) objsCollection.getNext();
            if (go instanceof Imoveable) { 
                Imoveable mObj = (Imoveable) go;
                if( mObj instanceof Astronaut ){ //Check if astro health is 0
                	if( ((Astronaut) mObj).getHealth() > 0 ){
                		mObj.move(elaTimer); 
                	}
                } else {
            		mObj.move(elaTimer); 
                }
            } 
		}
		
		//Notify observers
		notifyObs();
	}
	
	//'s' - Opens spaceship door
	public void openDoor(){
		IIterator objsCollection = gameObjects.getIterator();
		GameObject go;
		Spaceship ss = ((Spaceship) (objsCollection.get(0)));
		
		//Specify spaceship bound from center point and size
		double ssLeftBound = (ss.getLocation()).getX() - (ss.getSize()/2);
		double ssRightBound = (ss.getLocation()).getX() + (ss.getSize()/2);
		double ssUpperBound = (ss.getLocation()).getY() + (ss.getSize()/2);
		double ssLowerBound = (ss.getLocation()).getY() - (ss.getSize()/2);
		
		doorSound.play();
		
		//Check all game objects for spaceship entry
		while(objsCollection.hasNext()){
       	 	go = (GameObject) objsCollection.getNext();
			if (go instanceof Astronaut) { 
            	Astronaut astro = (Astronaut) go;
                Point2D astroLoc = astro.getLocation();
                if( (astroLoc.getX() >= ssLeftBound) && (astroLoc.getX() <= ssRightBound)	//Inside spaceship bounds
            		   && (astroLoc.getY() <= ssUpperBound) && (astroLoc.getY() >= ssLowerBound) ) {
                
                	//Astronaut saved! Max 10 pts per astronaut. Health degrades as they fight
                	//Update Game values
            	    score = score + (10 - (astro.getMaxHealth() - astro.getHealth()));
        		    astroOut--;
        		    astroIn++;   
                    objsCollection.remove();
                }   
            } else if (go instanceof Alien) { 
                Alien alien = (Alien) go;
                Point2D alienLoc = alien.getLocation();
                if( (alienLoc.getX() >= ssLeftBound) && (alienLoc.getX() <= ssRightBound)
             		   && (alienLoc.getY() <= ssUpperBound) && (alienLoc.getY() >= ssLowerBound) ){
             	   
                    //Alien sneaked in! Minus 10 per alien
                	//Update game values
             	    score = score - 10;
             	    alienOut--;
             	    alienIn++;
             	    objsCollection.remove();
                } 
            }
		}
		
		//Notify observers
		notifyObs();
	}
	
	//'w' - Collision of two aliens. New one is spawned near a random one. Returns null if maxAlien hit.
	public Alien newAlien(Alien parentAlien){
		//spawn in random of 2 locations.
		int spawnLoc = -50;
		Random rand = new Random();
		int randNum = 0 + rand.nextInt(2);
		//If 0 then change between -spawnLoc,-spawnLoc and +spawnLoc,+spawnLoc
		if(randNum == 0){
			spawnLoc = spawnLoc * -1;
		}
		
		Alien spawnAlien = null;
		//If max aliens not reached then add new alien
		if(alienOut < maxAlien){
			spawnAlien = new Alien(true); 
	        spawnAlien.setLocation(parentAlien.getLocation().getX()+spawnLoc, parentAlien.getLocation().getY()+spawnLoc);
	      //Set object boundaries
	        spawnAlien.setMaxX(maxX);
	        spawnAlien.setMaxY(maxY);
	        gameObjects.add(spawnAlien);
	        alienOut++;
			newAlienSound.play();
	        
			//Notify observers
			notifyObs();
		}
		return spawnAlien;
	}
	
	//'f' - Alien and astro collied and fight
	public void fight(Astronaut astro){
		if(astro.getHealth() > 0){
	        astro.setHealth(astro.getHealth()-1);
	        astro.setSpeed(astro.getHealth());
	        astro.fadeColor();
	        fightSound.play();
		}
		
		//Notify observers
		notifyObs();
	}
	
	//'p' - print game state values
	public void stateValues(){
		System.out.println("Score:"+ score + " Astronaut In:" + astroIn + " Aliens In:"+ alienIn + " Astronaut Out:" + astroOut + " Alien Out:" + alienOut);
	}
	
	//'m' - Print all game objects data
	public void map(){
		String s ="";
		IIterator objsCollection = gameObjects.getIterator();
		GameObject go;
		
		while(objsCollection.hasNext()){
      	 	go = (GameObject) objsCollection.getNext();
        	if (go instanceof Alien) { 
            	s = ((Alien) go).toString();
            } else if(go instanceof Astronaut){
            	s = ((Astronaut) go).toString();
            } else if(go instanceof Spaceship){
            	s = ((Spaceship) go).toString();	
            }
        	System.out.println(s);
        }
        System.out.println("\n");
	}
	
	//Changes sound flag, mute sound when off and unmute when on.
	public void sound(){
		//Sound on
		if(soundFlag == false){
			soundFlag = true;
			bgSound.unmute();
			doorSound.unmute();
			fightSound.unmute();
			newAlienSound.unmute();
			
		} else { //Sound off
			soundFlag = false;
			bgSound.mute();
			doorSound.mute();
			fightSound.mute();
			newAlienSound.mute();
		}
		//Notify observers
		notifyObs();
	}
	
	//Returns sound flag
	public boolean getSound(){
		return soundFlag;
	}
	
	//Scoreview pass setChange for use in Game class.
	@Override
	public void setChanged(){
		super.setChanged();
	}
	
	//ScoreView access to this variables.
	public int getAstroOut(){
		return astroOut;
	}
	
	public int getAstroIn(){
		return astroIn;
	}
	
	public int getAlienOut(){
		return alienOut;
	}
	
	public int getAlienIn(){
		return alienIn;
	}
	
	public int getScore(){
		return score;
	}
	
}
