package jk.tracker.utils;

import jk.tracker.core.Show;

public class ShowWrapper {
	
	private Show show;
	
	public ShowWrapper(Show show)
	{
		this.show = show;
	}
	
	public Show getShow()
	{
		return show;
	}
	
	public String toString()
	{
		return show.getName();
	}
}