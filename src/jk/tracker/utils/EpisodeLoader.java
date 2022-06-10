package jk.tracker.utils;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

public class EpisodeLoader {

	private static final String REGEX = "^.*(.S)[0-9][0-9](E)[0-9][0-9](-[0-9][0-9])?[.].*[%FORMATS%]$";
	
	public static List<File> getEpisodes(File root, String formats)
	{
		String regex = getREGEX(formats);
		
		List<File> files = new LinkedList<File>();
		
		loadEpisodes(root, regex, files);
		
		return files;
	}
	
	private static String getREGEX(String formats)
	{
		return REGEX.replace("%FORMATS%", formats);
	}
	
	private static void loadEpisodes(File root, String regex, List<File> files)
	{
		if(root.isFile() && root.getName().matches(regex))
		{
			files.add(root);
		}
		else if(root.isDirectory())
		{
			for(File file : root.listFiles())
				loadEpisodes(file, regex, files);
		}
	}
}
