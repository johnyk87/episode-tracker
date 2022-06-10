package jk.tracker.comparators;

import java.util.Comparator;

import jk.tracker.core.Season;

public class SeasonComparator implements Comparator<Season> {

	@Override
	public int compare(Season arg0, Season arg1)
	{
		return String.CASE_INSENSITIVE_ORDER.compare(arg0.getName(), arg1.getName());
	}

}
