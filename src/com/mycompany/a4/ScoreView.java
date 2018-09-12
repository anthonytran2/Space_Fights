package com.mycompany.a4;

import java.util.Observable;
import java.util.Observer;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Label;
import com.codename1.ui.layouts.FlowLayout;

public class ScoreView extends Container implements Observer{
	private Label scoreLabel = new Label("Total Score:");
	private Label astroRescLabel = new Label("Astronauts Rescued:");
	private Label alienSneakedLabel = new Label("Aliens Sneaked In:");
	private Label astroRemainLabel = new Label("Astronauts Remaining:");
	private Label alienRemainLabel = new Label("Aliens Remaining:");
	private Label soundLabel = new Label("Sound:");
	
	private Label scoreVal = new Label("");
	private Label astroRescVal = new Label("");
	private Label alienSneakedVal = new Label("");
	private Label astroRemainVal = new Label("");
	private Label alienRemainVal = new Label("");
	private Label soundVal = new Label("");
	
	private GameWorld gw;
	private String soundFlag = "";
	
	public ScoreView(){
		
		this.setLayout(new FlowLayout(Component.CENTER));
		
		setLabStyle(scoreVal,"Value");
		setLabStyle(astroRescVal,"Value");
		setLabStyle(alienSneakedVal,"Value");
		setLabStyle(astroRemainVal,"Value");
		setLabStyle(alienRemainVal,"Value");
		setLabStyle(soundVal,"Value");
		
		setLabStyle(scoreLabel,"");
		setLabStyle(astroRescLabel,"");
		setLabStyle(alienSneakedLabel,"");
		setLabStyle(astroRemainLabel,"");
		setLabStyle(alienRemainLabel,"");
		setLabStyle(soundLabel,"");
		
		this.add(scoreLabel);
		this.add(scoreVal);
		this.add(astroRescLabel);
		this.add(astroRescVal);
		this.add(alienSneakedLabel);
		this.add(alienSneakedVal);
		this.add(astroRemainLabel);
		this.add(astroRemainVal);
		this.add(alienRemainLabel);
		this.add(alienRemainVal);
		this.add(soundLabel);
		this.add(soundVal);
	}


	public void update(Observable observable, Object data) {
		gw = (GameWorld) data;
		
		//Update sound flag value
		if(gw.getSound() == false)
			soundFlag = "OFF";
		else 
			soundFlag = "ON";
		
		//Update values
		scoreVal.setText(gw.getScore()+"");
		astroRescVal.setText(gw.getAstroIn()+"");
		alienSneakedVal.setText(gw.getAlienIn()+"");
		astroRemainVal.setText(gw.getAstroOut()+"");
		alienRemainVal.setText(gw.getAlienOut()+"");
		soundVal.setText(soundFlag+"");
		
	}
	
	//Set label style
	public void setLabStyle(Label label, String ver){
		label.getAllStyles().setFgColor(ColorUtil.BLUE);
		//Set padding for values
		if(ver.equals("Value")) {
			label.getAllStyles().setPadding(1, 0, 1, 1);
		} 
	}

}
