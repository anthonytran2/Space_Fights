package com.mycompany.a4;

import java.util.Random;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Transform;
import com.codename1.ui.geom.Point2D;

//Define base structure of game objects (Rescuers & Opponents)
//Rescuers = spaceship
//Opponents = Alien & Astronauts
public abstract class GameObject implements IDrawable {
	private int size;
	private int color;
	
	//Max and min location requirements
	private double maxX = 746.0;
	private double minX = 0.0;
	private double maxY = 607.0;
	private double minY = 0.0;
	
	private Transform myTranslation = Transform.makeIdentity();
	private Transform myScale = Transform.makeIdentity();
	private Transform myRotate = Transform.makeIdentity();
	
	//Random location
	private Random random = new Random();
	private double randX = minX + (maxX - minX) * random.nextDouble();
	private double randY = minY + (maxY - minY) * random.nextDouble();
	
	//Initialize GameObject
	public GameObject(int s, int c){	//Size and color from extended subclass
		size = s;		
		color = c;
		translate(randX, randY);
	}
	
	public void setMaxX(int x){
		maxX = x;
	}
	public void setMaxY(int y){
		maxY = y;
	}
	
	//Returns Size of game object
	public int getSize(){
		return size;
	}
	
	//Set size of game object
	public void setSize(int num){
		size = num;
		//myScale.scale(x, y);
	}
	
	//Returns color of game object
	public int getColor(){
		return color;
	}
	
	//Set color of game object
	public void setColor(int r, int g, int b){
		color = ColorUtil.rgb(r, g, b);
	}
	
	//Returns location of game object
	public Point2D getLocation(){
		Point2D location = new Point2D(this.getTranslate().getTranslateX(), this.getTranslate().getTranslateY());
		return location;
	}
	
	//Set location of game object
	//Checks for bounds hitting and returns if hit or not.
	public boolean setLocation(double x, double y){
		if((x >= minX && x <= maxX && y >= minY && y <= maxY)){
			x = x - getLocation().getX();
			y = y - getLocation().getY();
			translate(x,y);
			//rotate(45);
			return true;
		} else {
			System.out.println("Redirecting.... Object tried to move out of bound at " + getLocation().getX() + "  "+ getLocation().getY()); //Spaceship included
			return false;
		}
	}
	
	public void translate(double tx, double ty) {
		myTranslation.translate ((float)tx, (float)ty);
	}

	public void scale(double x, double y){
		myScale.scale((float)x,(float)y);
	}
	
	public void rotate(double angle){
		myRotate.rotate((float)angle, 0, 0);
	}
	
	public Transform getTranslate(){
		return myTranslation;
	}
	
	public Transform getScale(){
		return myScale;
	}
	
	public Transform getRotate(){
		return myRotate;
	}
	
}
