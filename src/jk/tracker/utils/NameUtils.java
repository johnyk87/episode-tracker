package jk.tracker.utils;

public class NameUtils {
	
	private static final String NAME_SPLIT = "(.S)[0-9][0-9](E)[0-9][0-9](-[0-9][0-9])?[.]";

	public static String getShowName(String filename)
	{
		String[] split = filename.split(NAME_SPLIT);
		
		return split[0].replace('.', ' ');
	}
	
	public static String getEpisodeName(String filename)
	{
		String[] split = filename.split(NAME_SPLIT);
		
		if(split.length < 2)
			return "";
		
		String name = split[1].substring(0, split[1].lastIndexOf("."));
		
		return name.replace('.', ' ');
	}
	
	public static String getFormat(String filename)
	{
		return filename.substring(filename.lastIndexOf('.') + 1);
	}
	
	public static String getSeasonName(String filename)
	{
		int startIndex = getShowName(filename).length() + 1;
		
		return filename.substring(startIndex, startIndex + 3);
	}
	
	public static String getEpisodeNumber(String filename)
	{
		int startIndex = getShowName(filename).length() + 5;
		int endIndex = startIndex + 2;
		
		if(filename.charAt(endIndex) == '-')
			endIndex += 3;
		
		return filename.substring(startIndex, endIndex);
	}
}
