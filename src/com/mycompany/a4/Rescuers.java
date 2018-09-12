package com.mycompany.a4;

//Defines duties of rescuers
public abstract class Rescuers extends GameObject implements Iguidable{
	//Initialize
	public Rescuers(int size, int color){
		super(size,color);	//Size and color from extended subclass
	}
}
