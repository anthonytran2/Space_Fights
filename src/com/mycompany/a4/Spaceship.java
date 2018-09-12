package com.mycompany.a4;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Graphics;
import com.codename1.ui.Transform;
import com.codename1.ui.geom.Point;
import com.codename1.ui.geom.Point2D;

//Spaceship - user controlled, the hero!
public class Spaceship extends Rescuers{
	//private Point2D location = super.getLocation();	//Get location from parent class
	private int maxSize = 1025;	//Max size for spaceship 50-1024 inclusive
	private int minSize = 50;
	private static Spaceship theSpaceship;
	
	//Initialize
	private Spaceship(){
		//Size (initialize to 100),Color (initialize to white)
		super(100, ColorUtil.rgb(255,255,255)); 
	}
	
	public static Spaceship getSpaceship(){
		if(theSpaceship == null)
			theSpaceship = new Spaceship();
		return theSpaceship;		
	}
	

	
	//Draw spaceship as square
	public void draw(Graphics g, Point pCmpRelPrnt, Point pCmpRelScrn){ //Check double?
		//int x = (int) (this.getLocation().getX()-this.getSize()/2);
		//int y = (int) (this.getLocation().getY()-this.getSize()/2);
		
		g.setColor(this.getColor());
		
		Transform gXform = Transform.makeIdentity();
		g.getTransform(gXform);
		Transform gOrigXform = gXform.copy();
		gXform.translate(pCmpRelScrn.getX(), pCmpRelScrn.getY());
		gXform.translate(this.getTranslate().getTranslateX()-this.getSize()/2, this.getTranslate().getTranslateY()-this.getSize()/2);
		//gXform.concatenate(this.getRotate());
		gXform.translate(-pCmpRelScrn.getX(), -pCmpRelScrn.getY());
		g.setTransform(gXform);
		
		g.drawRect((pCmpRelPrnt.getX()), (pCmpRelPrnt.getY()), getSize(), getSize());
		g.setTransform(gOrigXform);
	}

	//Set size for spaceship
	@Override
	public void setSize(int num) {
		if(num >= minSize && num < maxSize){ //Checks for over and under sizing
			super.setSize(num);
		} else {
			System.out.println("Size of spaceship must be in bound 50 - 1024");
		}
	}

	//Return size for spaceship
	@Override
	public int getSize(){
		return super.getSize();
	}
	
	//Return color for spaceship
	@Override
	public int getColor(){
		return super.getColor();
	}
	
	//Spaceship setColor is not allowed and is empty
	@Override
	public void setColor(int r, int g, int b){ }
	
	//Return location for spaceship
	@Override
	public Point2D getLocation(){
		return super.getLocation();
	}
	
	//Set location for spaceship
	@Override
	public boolean setLocation(double x, double y){
		return super.setLocation(x, y);
	}

	//Spaceship move left
	public void moveLeft(int moveVal) {
		super.setLocation(getLocation().getX()-moveVal, getLocation().getY());
	}

	//Spaceship move right
	public void moveRight(int moveVal) {
		super.setLocation(getLocation().getX()+moveVal, getLocation().getY());
	}

	//Spaceship move up
	public void moveUp(int moveVal) {
		super.setLocation(getLocation().getX(), getLocation().getY()+moveVal);
	}

	//Spaceship move down
	public void moveDown(int moveVal) {
		super.setLocation(getLocation().getX(), getLocation().getY()-moveVal);
	}

	//Space ship jump to location of alien or astronaut
	public void jumpToLocation(Point2D loc) {
		// TODO Auto-generated method stub
		super.setLocation(loc.getX(), loc.getY());
	}
	
	//Print Spaceship data
	@Override
	public String toString(){
		return "Spaceship: " + "location=" + Math.round(this.getLocation().getX()*10)/10 +","+ Math.round(this.getLocation().getY()*10)/10 
				+ " color=[" + ColorUtil.red(getColor()) + ","+ ColorUtil.green(getColor()) + "," + ColorUtil.blue(getColor())+ "]" 
				+ " size=" + getSize();
	}

}
