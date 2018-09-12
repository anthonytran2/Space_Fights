package com.mycompany.a4;

import java.io.InputStream;

import com.codename1.media.Media;
import com.codename1.media.MediaManager;
import com.codename1.ui.Display;

public class Sound {
	private Media m;
	private int origVol;
	
	public Sound(String fileName){
		try {
			InputStream is = Display.getInstance().getResourceAsStream(getClass(), "/"+fileName);
			m = MediaManager.createMedia(is, "audio/wav");
		} catch (Exception e){
			e.printStackTrace();
		}
		//Save original volume level
		origVol = m.getVolume();
	}
	
	public void mute(){m.setVolume(0);}
	public void unmute(){m.setVolume(origVol);}
	public void play(){
		m.setTime(0);
		m.play();
	}

}
