package com.mycompany.a4;

import com.codename1.ui.Graphics;
import com.codename1.ui.geom.Point;

public interface ISelectable {
	public void setSelected(boolean val);
	public boolean isSelected();
	public boolean contains(float[] fPtr);
	public void draw(Graphics g, Point pCmpRelPrnt, Point pCmpRelScrn);
}
