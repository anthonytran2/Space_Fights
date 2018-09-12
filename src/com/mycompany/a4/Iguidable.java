package com.mycompany.a4;

import com.codename1.ui.geom.Point2D;

//User guideable objects
public interface Iguidable {
	public void moveLeft(int movVal); //movVal - incr/dcr amount
	public void moveRight(int movVal);
	public void moveUp(int movVal);
	public void moveDown(int movVal);
	public void jumpToLocation(Point2D loc); //loc - location of object
}
