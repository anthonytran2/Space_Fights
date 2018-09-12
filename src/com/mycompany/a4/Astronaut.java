package com.mycompany.a4;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Graphics;
import com.codename1.ui.Transform;
import com.codename1.ui.Transform.NotInvertibleException;
import com.codename1.ui.geom.Point;
import com.codename1.ui.geom.Point2D;

//Astronaut - Stuck in space waiting to be saved while fighting off aliens.
public class Astronaut extends Opponents implements ISelectable{
	//private Sound fightSound = new Sound("FightSound.wav");
	private boolean inFight = false;
	
	//Health
	private int maxHealth = 5;
	private int minHealth = 0;
	private int health = maxHealth;
	
	//Speed
	private int cnst = 3;
	private int speed = (health*cnst);

	
	//Use to show color of degrading health
	private int colorHealth = 1; //First hit (1) turns yellow, (0) was initialized to green
	private static int[] r = {10,234,250,250,230,125};  //Green,Yellow,Orange,Red,Pink,Purple = health: 5,4,3,2,1
	private static int[] g = {166,252,173,76,128,12};	//EX: r[0],g[0],b[0] is first initial color
	private static int[] b = {20,63,7,7,250,148};
	
	//Indicate if astronaut is selected
	private boolean selected = false;
	
	
	private Point lowerLeftInLocalSpace;
	
	//Initialize
	public Astronaut(){
		super( ColorUtil.rgb(r[0], g[0], b[0]),5); //color (initialize to green), speed initialize to maxHealth = 5.
		lowerLeftInLocalSpace = new Point(-getSize()/2,-getSize()/2);
	}
	
	//Used in Heal (Healed to original state)
	public void orgiState(){
		setHealth(maxHealth);
		setSpeed(health);
		colorHealth = 1; //next start 1, which is yellow
		setColor(r[0], g[0], b[0]);
	}
	
	//Return true if in fight with alien
	public boolean fightCheck(){
		return inFight;
	}
	//Set fight check indicator
	public void setFightCheck(boolean val){
		inFight = val;
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
		
		boolean col = false;
		if( (thisR < otherL || thisL > otherR) || (thisTop < otherBot || otherTop < thisBot)){
			col = false;
		} else {
			col = true;
		}
		return col;
	}

	//Handle Collision
	public void handleCollision(ICollider otherObject) {
		if(otherObject instanceof Alien) {
			setFightCheck(true);	//Indicate fight with alien
		}
	}
	
	
	
	//Draw astronaut as triangle
	public void draw(Graphics g, Point pCmpRelPrnt, Point pCmpRelScrn){
		int pnts = 3;
		int[] xPnts = new int[pnts];
		int[] yPnts = new int[pnts];
		//The tip of triangle
		xPnts[0] = pCmpRelPrnt.getX()+(int)(getLocation().getX()); yPnts[0] = pCmpRelPrnt.getY()+(int)(getLocation().getY()+getSize()/2);
		//The left/right base points of triangle
		xPnts[1] = pCmpRelPrnt.getX()+(int)(getLocation().getX()-getSize()/2); yPnts[1] = pCmpRelPrnt.getY()+(int)(getLocation().getY()-getSize()/2);
		xPnts[2] = pCmpRelPrnt.getX()+(int)(getLocation().getX()+getSize()/2); yPnts[2] = pCmpRelPrnt.getY()+(int)(getLocation().getY()-getSize()/2);
		
		g.setColor(this.getColor());
		
		Transform gXform = Transform.makeIdentity();
		g.getTransform(gXform);
		Transform gOrigXform = gXform.copy();
		gXform.translate(pCmpRelScrn.getX(), pCmpRelScrn.getY());
		gXform.translate(this.getTranslate().getTranslateX(),this.getTranslate().getTranslateY());
		//gXform.rotate((float) Math.toRadians(50), 0, 0);
		//gXform.concatenate(this.getRotate());
		gXform.translate(-pCmpRelScrn.getX(), -pCmpRelScrn.getY());
		gXform.setTransform(gXform);
		
		//Draw filled triangle if selected object is selected.
		if(isSelected())
			g.fillPolygon(xPnts, yPnts, pnts);
		else	
			g.drawPolygon(xPnts, yPnts, pnts);
		
		g.setTransform(gOrigXform);
	}

	//Move astronaut
	@Override
	public void move(int elaTime) {
		super.move(elaTime); 
	}
	
	//Return size of astronaut
	@Override
	public int getSize(){
		return super.getSize();
	}
	
	//Return astronaut color
	@Override
	public int getColor(){ //Defines own color to be changed later.
		return super.getColor();
	}
	
	//Return astronaut location
	@Override
	public Point2D getLocation(){
		return super.getLocation();
	}
	
	//Set astronaut location
	@Override
	public boolean setLocation(double x, double y){
		return super.setLocation(x, y);
	}
	
	//Set astronaut color
	@Override
	public void setColor(int r, int g, int b){
		super.setColor(r, g, b);
	}
	
	//Fade astronaut color
	public void fadeColor(){ // Fade color by fadeInte 
		if(colorHealth < r.length)
			super.setColor(r[colorHealth], g[colorHealth], b[colorHealth]);
		colorHealth++;
	}
	
	//Get astronaut speed
	@Override
	public int getSpeed(){
		return speed;		
	}
	
	//Set astronaut speed 
	public int setSpeed(int ht){
		return speed = ht * cnst;	//Speed = health * constant
	}
	
	//Return astronaut direction
	@Override
	public int getDirection(){
		return super.getDirection();		
	}
	
	//Set astronaut direction
	@Override
	public void setDirection(int dir){
		super.setDirection(dir);
	}
	
	//Return astronaut max health
	public int getMaxHealth(){
		return maxHealth;
	}
	
	//Return astronaut minimum health
	public int getMinHealth(){
		return minHealth;
	}
	
	//Return astronaut health
	public int getHealth(){
		return health;
	}
	
	//Set astronaut health
	public void setHealth(int num){	//Set health, checks for bounds
		if(num >= minHealth && num <= maxHealth){
			health = num;
		} else {
			System.out.println("Health entered out of bounds 0-5");
		}
	}
	
	//Print astronaut data
	@Override
	public String toString(){
		return "Astronaut: " + "location=" + Math.round(getLocation().getX()*10)/10 +","+ Math.round(getLocation().getY()*10)/10 
				+ " color=[" + ColorUtil.red(getColor()) + ","+ ColorUtil.green(getColor()) + "," + ColorUtil.blue(getColor())+ "]" 
				+ " size=" + getSize() + " speed=" + speed + " direction=" + getDirection() + " health=" + health;
	}

	//Set selected indicator for pointer click
	public void setSelected(boolean val) {
		selected = val;
	}
	//Return selected indicator
	public boolean isSelected() {
		return selected;
	}
	//Returns true if pointer is in the astronaut
	public boolean contains(float[] fPtr) {
	/*	int px = pPtrRelPrnt.getX();
		int py = pPtrRelPrnt.getY();
		int xLoc = pCmpRelPrnt.getX() + (int)(getLocation().getX()) ;
		int yLoc = pCmpRelPrnt.getY() + (int)(getLocation().getY());
		
		if((px >= xLoc-getSize()/2) && (px <= xLoc+getSize()/2) && (py >= yLoc-getSize()/2) && (py <= yLoc+getSize()/2))
			return true;
		else
			return false;
	*/
		
		Transform concatLTs = Transform.makeIdentity();
		//concatLTs.translate(this.getTranslate().getTranslateX(), this.getTranslate().getTranslateY());
		Transform inverseConcatLTs = Transform.makeIdentity();
		try {
			concatLTs.getInverse(inverseConcatLTs);
		} catch(NotInvertibleException e){
			System.out.println("Non invertible xform!");
		}
		inverseConcatLTs.transformPoint(fPtr,fPtr);
		
		int px = (int) fPtr[0];
		int py = (int) fPtr[1];
		int xLoc = lowerLeftInLocalSpace.getX();
		int yLoc = lowerLeftInLocalSpace.getY();
		
		System.out.println(px + " " + py );//+ " " + xLoc + " " + yLoc + " / " + (xLoc-getSize()/2) + " " + (xLoc+getSize()/2) + " " + (yLoc-getSize()/2) + " " + (yLoc+getSize()/2));
 
		if((px >= xLoc) && (px <= xLoc+getSize()) && (py >= yLoc) && (py <= yLoc+getSize()))
			return true;
		else
			return false;
	}

}
