package com.mycompany.a4;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Graphics;
import com.codename1.ui.Transform;
import com.codename1.ui.geom.Point;
import com.codename1.ui.geom.Point2D;

//Aliens - unwanted space being!
public class Alien extends Opponents {
	//private Sound fightSound = new Sound("FightSound.wav");
	private boolean newAlien = false;
	private int agingAlien = 0; //Accumulating age
	private int stopAge = 25; //Age to stop, which means its ready to mate
	private boolean babyAlien; //Indicates if alien is created from mating or is initial alien.

	
	//Initialize
	public Alien(boolean val) {
		super( ColorUtil.rgb(125, 12, 148), 5*3); //color(Initialize to red), speed = 5*constant (initialize to 5*1)
		babyAlien = val;
		if(!babyAlien) //If initial alien then allow to mate 
			agingAlien = stopAge;
	}
	
	//Detect collision
	public boolean collidesWith(ICollider otherObject) {
		int thisCenX = (int) getLocation().getX();
		int thisCenY = (int) getLocation().getY();
		int otherCenX = (int) ((GameObject) otherObject).getLocation().getX(); 
		int otherCenY = (int) ((GameObject) otherObject).getLocation().getY();
		
		//This object
		int thisTop = thisCenY + getSize()/2; //Upside down
		int thisBot = thisCenY - getSize()/2;
		int thisL = thisCenX - getSize()/2;
		int thisR = thisCenX + getSize()/2;
		//Other object
		int otherTop = otherCenY + ((GameObject) otherObject).getSize()/2; //Upside down
		int otherBot = otherCenY - ((GameObject) otherObject).getSize()/2;
		int otherL = otherCenX - ((GameObject) otherObject).getSize()/2;
		int otherR = otherCenX + ((GameObject) otherObject).getSize()/2;

		//Collision indicator
		boolean col = false;
		//No Collision
		if( (thisR < otherL || thisL > otherR) || (thisTop < otherBot || otherTop < thisBot)){
			col = false;
		} else {	//Collision
			col = true;
			agingAlien++; //Age alien
		}
		return col;
	}
	
	//Handle collision
	public void handleCollision(ICollider otherObject) {
		if(otherObject instanceof Alien && agingAlien > stopAge){ //alien on alien collision and mature to mate.
			setNewAlienCheck(true);	//Indicate that new Alien spawned from collision
		} else if(otherObject instanceof Astronaut) {	//Indicate alien fought with astronaut
			Astronaut astro = (Astronaut) otherObject;
			astro.setFightCheck(true);
		}
	}
	
	
	//Draw alien as circle
	public void draw(Graphics g, Point pCmpRelPrnt, Point pCmpRelScrn){ //Check width height radius
		//int x = (int) (this.getLocation().getX()-this.getSize()/2);
		//int y = (int) (this.getLocation().getY()-this.getSize()/2);
		
		g.setColor(this.getColor());
		
		Transform gXform = Transform.makeIdentity();
		g.getTransform(gXform);
		Transform gOrigXform = gXform.copy();
		gXform.translate(pCmpRelScrn.getX(), pCmpRelScrn.getY());
		gXform.translate(this.getTranslate().getTranslateX()-this.getSize()/2,this.getTranslate().getTranslateY()-this.getSize()/2);
		gXform.concatenate(this.getRotate());
		gXform.translate(-pCmpRelScrn.getX(), -pCmpRelScrn.getY());
		g.setTransform(gXform);
		
		g.drawArc(pCmpRelPrnt.getX(), pCmpRelPrnt.getY(), this.getSize(), this.getSize(), 0, 360);
		g.setTransform(gOrigXform);
	}
	
	//Return indicator of new alien spawn from collision
	public boolean newAlienCheck(){
		return newAlien;
	}
	
	//Set indicator of new alien spawn from collision
	public void setNewAlienCheck(boolean val){
		newAlien = val;
	}
	
	//Move alien
	@Override
	public void move(int elaTime) {
		super.move(elaTime);
	}

	//Return size of alien
	@Override
	public int getSize() {
		return super.getSize();
	}

	//Return color of alien
	@Override
	public int getColor() {
		return super.getColor();
	}
	
	//Alien setColor is not allowed and is empty
	@Override
	public void setColor(int r, int g, int b){ }
	
	//Return point of alien
	@Override
	public Point2D getLocation(){
		return super.getLocation();
	}
	
	//Set location of alien
	@Override
	public boolean setLocation(double x, double y){
		return super.setLocation(x, y);
	}

	//Return speed of alien
	@Override
	public int getSpeed(){
		return super.getSpeed();
	}
	
	//Get direction of alien
	@Override
	public int getDirection(){
		return super.getDirection();
	}
	
	//Set direction of alien
	@Override
	public void setDirection(int dir){
		super.setDirection(dir);
	}

	//Alien data print
	@Override
	public String toString(){
		return "Alien: " + "location=" + Math.round(getLocation().getX()*10)/10 +","+ Math.round(getLocation().getY()*10)/10 
				+ " color=[" + ColorUtil.red(getColor()) + ","+ ColorUtil.green(getColor()) + "," + ColorUtil.blue(getColor())+ "]" 
				+ " size=" + getSize() + " speed=" + getSpeed() + " direction=" + getDirection();
	}
}
