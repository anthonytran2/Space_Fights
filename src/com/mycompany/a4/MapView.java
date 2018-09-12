package com.mycompany.a4;

import java.util.Observable;
import java.util.Observer;

import com.codename1.ui.Container;
import com.codename1.ui.Graphics;
import com.codename1.ui.Transform;
import com.codename1.ui.Transform.NotInvertibleException;
import com.codename1.ui.geom.Point;

public class MapView extends Container implements Observer {
	private GameWorld gameworld;
	private Transform worldToND, ndToDisplay, theVTM;
	private float winLeft, winRight, winBottom, winTop;
	private float winWidth;
	private float winHeight;
	private Point pPrevDragLoc = new Point(-1,-1);
	
	public MapView(){
		winLeft = 0;
		winBottom = 0;
		winRight = 1492/2;
		winTop = 1214/2;
		winWidth = winRight - winLeft;
		winHeight = winTop - winBottom;
	}
	
	//Update map on console
	public void update(Observable o, Object arg) {
		gameworld = (GameWorld) arg;
		gameworld.map();
		this.repaint();
	}
	
	public float getWinWidth(){
		return winWidth;
	}
	
	public float getWinHeight(){
		return winHeight;
	}
	
	@Override
	public void paint(Graphics g){
		super.paint(g);
		IIterator gameObjects = gameworld.getGameWorldIterator();
		GameObject go;
		Point pCmpRelPrnt = new Point(this.getX(),this.getY());
		Point pCmpRelScrn = new Point(this.getAbsoluteX(),this.getAbsoluteY());
	
		//Check
		//winWidth = this.getWidth() - winLeft;
		//winHeight = this.getHeight() - winBottom;
		
		worldToND = buildWorldToNDXform(winWidth, winHeight, winLeft, winBottom);
		ndToDisplay = buildNDToDisplayXform(this.getWidth(), this.getHeight());
		theVTM = ndToDisplay.copy();
		theVTM.concatenate(worldToND);
		
		Transform gXform = Transform.makeIdentity();
		g.getTransform(gXform);
		gXform.translate(getAbsoluteX(), getAbsoluteY());
		gXform.concatenate(theVTM);
		gXform.translate(-getAbsoluteX(), -getAbsoluteY());
		g.setTransform(gXform);
		
		
		while(gameObjects.hasNext()){
			go = (GameObject) gameObjects.getNext();
			//If in play mode then unselect all ISelectables
			if(go instanceof ISelectable && gameworld.gameMode().equals("Play"))
				((ISelectable) go).setSelected(false);
			
			//Draw each object
			go.draw(g, pCmpRelPrnt, pCmpRelScrn);
		}
		
		g.resetAffine();
	}
	
	private Transform buildWorldToNDXform(float winWidth, float winHeight, float winLeft, float winBottom){
		Transform tmpXform = Transform.makeIdentity();
		tmpXform.scale(1/winWidth, 1/winHeight);
		tmpXform.translate(-winLeft, -winBottom);
		return tmpXform;
	}
	
	private Transform buildNDToDisplayXform(float displayWidth, float displayHeight){
		Transform tmpXform = Transform.makeIdentity();
		tmpXform.translate(0, displayHeight);
		tmpXform.scale(displayWidth, -displayHeight);
		return tmpXform;
	}

	public void zoom(float factor) {
		//positive factor would zoom in (make the worldWin smaller), suggested value is 0.05f
		//negative factor would zoom out (make the worldWin larger), suggested value is -0.05f
		//...[calculate winWidth and winHeight]
		float newWinLeft = winLeft + winWidth*factor;
		float newWinRight = winRight - winWidth*factor;
		float newWinTop = winTop - winHeight*factor;
		float newWinBottom = winBottom + winHeight*factor;
		float newWinHeight = newWinTop - newWinBottom;
		float newWinWidth = newWinRight - newWinLeft;
		//in CN1 do not use world window dimensions greater than 1000!!!
		if (newWinWidth <= 1000 && newWinHeight <= 1000 && newWinWidth > 0 && newWinHeight > 0 ){
			winLeft = newWinLeft;
			winRight = newWinRight;
			winTop = newWinTop;
			winBottom = newWinBottom;
		} else {
			System.out.println("Cannot zoom further!");
		}
		this.repaint();
	}
	
	public void panHorizontal(double delta) {
		//positive delta would pan right (image would shift left), suggested value is 5
		//negative delta would pan left (image would shift right), suggested value is -5
		winLeft += delta;
		winRight += delta;
		this.repaint();
	}
	
	public void panVertical(double delta) {
		//positive delta would pan up (image would shift down), suggested value is 5
		//negative delta would pan down (image would shift up), suggested value is -5
		winBottom += delta;
		winTop += delta;
		this.repaint();
	}
	
	@Override
	public boolean pinch(float scale){
		if(scale < 1.0){
			//Zooming Out: two fingers come closer together (on actual device), right mouse
			//click + drag towards the top left corner of screen (on simulator)
			zoom(-0.05f);
		}else if(scale>1.0){
			//Zooming In: two fingers go away from each other (on actual device), right mouse
			//click + drag away from the top left corner of screen (on simulator)
			zoom(0.05f);
		}
		return true;
	}
	
	@Override
	public void pointerPressed(int x, int y){
		int onlySelected = 0;
		x = x - getParent().getAbsoluteX();
		y = y - getParent().getAbsoluteY();
		float[] fPtr= new float[]{x,y};
		
		//If game mode is Pause then ISelectable objects are selectable
		if(gameworld.gameMode().equals("Pause")){ 
			/*//Pointer relative to screen
			Point pPtrRelPrnt = new Point(x,y);
			*///Component relative to parent container
			//Point pCmpRelPrnt = new Point(getX(),getY());
			
			IIterator gameObjects = gameworld.getGameWorldIterator();
			GameObject go;
			
			Transform inverseVTM = Transform.makeIdentity();
			try {
				theVTM.getInverse(inverseVTM);
			} catch (NotInvertibleException e) {
				System.out.println("Not invertible xform!");
			}
			inverseVTM.transformPoint(fPtr,fPtr);
			
			while(gameObjects.hasNext()){
				go = (GameObject) gameObjects.getNext();
				/*if(go instanceof ISelectable && ((ISelectable) go).contains(pPtrRelPrnt, pCmpRelPrnt) && onlySelected == 0){ //onlySelected only allows one if in the same spot
					((ISelectable) go).setSelected(true);
					onlySelected = 1;
				} else if(go instanceof ISelectable && !((ISelectable) go).contains(pPtrRelPrnt, pCmpRelPrnt)){ //Unselect all other ISelectables not in pointer 
					((ISelectable) go).setSelected(false);
				}*/
				if(go instanceof ISelectable && ((ISelectable) go).contains(fPtr) && onlySelected == 0){ //onlySelected only allows one if in the same spot
					((ISelectable) go).setSelected(true);
					onlySelected = 1;
				} else if(go instanceof ISelectable && !((ISelectable) go).contains(fPtr)){ //Unselect all other ISelectables not in pointer 
					((ISelectable) go).setSelected(false);
				}
				
			}
			repaint();
		}
	}
	
	@Override
	public void pointerDragged(int x, int y){
		if (pPrevDragLoc.getX() != -1){
			if (pPrevDragLoc.getX() < x)
				panHorizontal(5);
			else if (pPrevDragLoc.getX() > x)
				panHorizontal(-5);
			if (pPrevDragLoc.getY() < y)
				panVertical(-5);
			else if (pPrevDragLoc.getY() > y)
				panVertical(5);
		}
		pPrevDragLoc.setX(x);
		pPrevDragLoc.setY(y);
	}

}
