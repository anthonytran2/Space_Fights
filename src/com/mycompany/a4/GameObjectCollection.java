package com.mycompany.a4;

import java.util.ArrayList;

public class GameObjectCollection implements ICollection {
	private ArrayList<GameObject> gameObjects;
	
	public GameObjectCollection(){
		gameObjects = new ArrayList<GameObject>(); 
	}
	
	public void add(GameObject obj){
		gameObjects.add(obj);
	}
	
	public IIterator getIterator(){
		return new GameObjectArrayListIterator();
	}
	
	private class GameObjectArrayListIterator implements IIterator{
		private int curIdx;
		
		public GameObjectArrayListIterator(){
			curIdx = -1;
		}
		
		public boolean hasNext(){
			if(gameObjects.size() <= 0) return false;
			if(curIdx == gameObjects.size()-1) return false;
			return true;
		}
		
		public Object getNext(){
			curIdx++;
			return gameObjects.get(curIdx);
		}
		
		public void remove(){
			gameObjects.remove(curIdx);
			curIdx--;
		}
		
		public int size(){
			return gameObjects.size();
		}
		
		public Object get(int idx){
			return gameObjects.get(idx);
		}
	}

}
