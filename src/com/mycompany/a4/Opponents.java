package com.mycompany.a4;

import java.util.ArrayList;
import java.util.Random;

//import com.codename1.ui.Transform;

//Defines values and duties of opponents
public abstract class Opponents extends GameObject implements Imoveable, ICollider{
	private ArrayList<GameObject> colList = new ArrayList<GameObject>();
	
	private int speed;
	private int direction;
	
	//Random direction
	private static Random random = new Random(); 
	private int rndDirX = 0;
	private int rndDirY = 360;
	private int rndDir = (rndDirX+ random.nextInt(rndDirY));
	
	private double dX; 
	private double dY;

	
	//Initialize
	public Opponents(int clr, int spd){
		super((20+random.nextInt(30)), clr); //size (from 20 to 50),color from extended subclass
		speed = spd;
		direction = rndDir;
	}
	
	public void addColList(ICollider obj){
		colList.add((GameObject) obj);
	}
	
	public void removeColList(ICollider obj){
		colList.remove(obj);
	}
	
	public ArrayList<GameObject> getList(){
		return colList;
	}
	
	//Move for opponents
	public void move(int elaTime){
		int randMove = 0 + random.nextInt(2); 	//Add/Sub random number so no moving in straight line.
		dX = Math.cos(90-direction) * (speed) + (elaTime/1000) + randMove;
		dY = Math.sin(90-direction) * (speed) + (elaTime/1000) + randMove;	

		while(!(super.setLocation(this.getLocation().getX()+dX, this.getLocation().getY()+dY))){ //Collision detection correction. 
			direction = (rndDirX+ random.nextInt(rndDirY));
			dX = Math.cos(90-direction) * (speed) + (elaTime/1000) + randMove;
			dY = Math.sin(90-direction) * (speed) + (elaTime/1000) + randMove;
			this.getRotate().rotate(90-direction, 0, 0);
		}
	}
	
	//Opponent setSize is not allowed and is empty
	@Override
	public void setSize(int num){ }
	
	//Set speed for opponents
	public int getSpeed(){
		return speed;
	}
	
	//Return direction for opponents
	public int getDirection(){
		return direction;
	}
	
	//Set direction for opponents
	public void setDirection(int dir){
		direction = dir;
	}
}
