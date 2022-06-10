package jk.tracker.comparators;

import java.util.Comparator;

import jk.tracker.core.Episode;

public class EpisodeComparator implements Comparator<Episode> {

	@Override
	public int compare(Episode arg0, Episode arg1)
	{
		return String.CASE_INSENSITIVE_ORDER.compare(arg0.getFilename(), arg1.getFilename());
	}

}
