package com.mycompany.a4;

public interface IIterator {
	public boolean hasNext();
	public Object getNext();
	public void remove();
	public Object get(int i);
	public int size();
}
