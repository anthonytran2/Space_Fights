package com.mycompany.a4;

import java.io.InputStream;

import com.codename1.media.Media;
import com.codename1.media.MediaManager;
import com.codename1.ui.Display;

public class BGSound implements Runnable{
	private Media m;
	private int orgiVol;
	
	public BGSound(String fileName){
		try {
			InputStream is = Display.getInstance().getResourceAsStream(getClass(), "/"+fileName);
			m = MediaManager.createMedia(is, "audio/wav", this);
		} catch (Exception e){
			e.printStackTrace();
		}
		//Save original volume level
		orgiVol = m.getVolume();
	}
	
	public void play(){ m.play();}
	public void pause(){ m.pause();}
	public void mute(){m.setVolume(0);}
	public void unmute(){m.setVolume(orgiVol);}
	
	public void run(){
		m.setTime(0);
		m.play();
	}
}
