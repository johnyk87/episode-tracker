package jk.tracker.utils;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

import jk.tracker.Workflow;
import jk.tracker.core.Episode;
import jk.tracker.core.Profile;
import jk.tracker.core.Season;
import jk.tracker.core.Show;

public class ProfileUtil {
	
	private static final String DEFAULT_FOLDERS = ".";
	private static final String DEFAULT_FORMATS = "mkv|rmvb|avi";
	private static final boolean DEFAULT_AUTOTRACK = true;

	private static final boolean DEFAULT_HIDDEN = false;

	private static final boolean DEFAULT_SEEN = false;
	
	public static Show getShow(Profile profile, String name)
	{
		for(Show show : profile.getShow())
		{
			if(show.getName().equalsIgnoreCase(name))
				return show;
		}
		
		return null;
	}
	
	public static Season getSeason(Show show, String name)
	{
		for(Season season : show.getSeason())
		{
			if(season.getName().equalsIgnoreCase(name))
				return season;
		}
		
		return null;
	}
	
	public static Episode getEpisode(Season season, String filename)
	{
		for(Episode episode : season.getEpisode())
		{
			if(episode.getFilename().equalsIgnoreCase(filename))
				return episode;
		}
		
		return null;
	}
	
	public static Episode getEpisode(Profile profile, String filename)
	{
		String showName = NameUtils.getShowName(filename);
		String seasonName = NameUtils.getSeasonName(filename);
		
		Show show = getShow(profile, showName);
		if(show == null) return null;
		Season season = getSeason(show, seasonName);
		if(season == null) return null;
		return getEpisode(season, filename);
	}
	
	public static boolean hasEpisode(Profile profile, String filename)
	{
		return getEpisode(profile, filename) != null;
	}
	
	public static Profile newProfile(String name)
	{
		Profile profile = new Profile();
		
		profile.setName(name);
		profile.setFolders(DEFAULT_FOLDERS);
		profile.setFormats(DEFAULT_FORMATS);
		profile.setAutotrack(DEFAULT_AUTOTRACK);
		
		return profile;
	}
	
	public static Show newShow(String name)
	{
		Show show = new Show();
		
		show.setName(name);
		show.setHidden(DEFAULT_HIDDEN);
		
		return show;
	}
	
	public static Season newSeason(String name)
	{
		Season season = new Season();
		
		season.setName(name);
		
		return season;
	}
	
	public static Episode newEpisode(String filename)
	{
		Episode episode = new Episode();
		
		episode.setFilename(filename);
		episode.setName(NameUtils.getEpisodeName(filename));
		episode.setNumber(NameUtils.getEpisodeNumber(filename));
		episode.setSeen(DEFAULT_SEEN);
		
		return episode;		
	}
	
	public static void addEpisode(Profile profile, String filename)
	{
		String showName = NameUtils.getShowName(filename);
		Show show = getShow(profile, showName);
		if(show == null)
		{
			show = newShow(showName);
			show.setHidden(!profile.isAutotrack());
			profile.getShow().add(show);
		}
		String seasonName = NameUtils.getSeasonName(filename);
		Season season = getSeason(show, seasonName);
		if(season == null)
		{
			season = newSeason(seasonName);
			show.getSeason().add(season);
		}
		Episode episode = newEpisode(filename);
		season.getEpisode().add(episode);
	}
	
	public static Profile updateProfile(String name)
	{
		Profile profile = TrackerXML.loadProfile(name);
				
		String folderConfig = profile.getFolders();
		String[] folders = folderConfig.split(";");
		
		for(String folder : folders)
		{
			try
			{
				File dir = new File(folder);
				if(!dir.exists())
					continue;
				
				List<File> files = EpisodeLoader.getEpisodes(dir, profile.getFormats());
				
				for(File file : files)
				{
					if(!ProfileUtil.hasEpisode(profile, file.getName()))
						ProfileUtil.addEpisode(profile, file.getName());
				}
			}
			catch(Exception e)
			{
				e.printStackTrace();
				Workflow.showMessage("Error updating profile: " + e.getMessage());
			}
		}

		TrackerXML.saveProfile();
		
		return profile;
	}
	
	public static List<Episode> getUnseenEpisodes(Season season)
	{
		List<Episode> unseen = new LinkedList<Episode>();
		
		for(Episode episode : season.getEpisode())
			if(!episode.isSeen())
				unseen.add(episode);
		
		return unseen;
	}
	
	public static int getTotalEpisodesCount(Season season)
	{
		return season.getEpisode().size();
	}
	
	public static int getSeenEpisodesCount(Season season)
	{
		int count = 0;
		for(Episode episode : season.getEpisode())
			if(episode.isSeen())
				count++;
		
		return count;
	}
	
	public static int getUnseenEpisodesCount(Season season)
	{
		int count = 0;
		for(Episode episode : season.getEpisode())
			if(!episode.isSeen())
				count++;
		
		return count;
	}
	
	public static int getTotalEpisodesCount(Show show)
	{
		int count = 0;
		for(Season season : show.getSeason())
			count += getTotalEpisodesCount(season);
		
		return count;
	}
	
	public static int getSeenEpisodesCount(Show show)
	{
		int count = 0;
		for(Season season : show.getSeason())
			count += getSeenEpisodesCount(season);
		
		return count;
	}
	
	public static int getUnseenEpisodesCount(Show show)
	{
		int count = 0;
		for(Season season : show.getSeason())
			count += getUnseenEpisodesCount(season);
		
		return count;
	}
}
