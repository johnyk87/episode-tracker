package jk.tracker.comparators;

import java.util.Comparator;

import jk.tracker.core.Show;

public class ShowComparator implements Comparator<Show> {

	@Override
	public int compare(Show arg0, Show arg1)
	{
		return String.CASE_INSENSITIVE_ORDER.compare(arg0.getName(), arg1.getName());
	}

}
