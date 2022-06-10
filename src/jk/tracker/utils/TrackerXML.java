package jk.tracker.utils;

import java.io.File;
import java.util.List;

import javax.xml.bind.JAXBException;

import jk.tracker.Workflow;
import jk.tracker.core.Profile;
import jk.tracker.core.Tracker;

public class TrackerXML implements Runnable {
	
	private static final String JAXB_CONTEXT = "jk.tracker.core";
	private static final String FILENAME = "./tracker.xml";
	
	private static JAXB<Tracker> jaxb = loadJAXB();
	private static boolean save = false;
	private static Tracker tracker = null;
	
	private static JAXB<Tracker> loadJAXB()
	{
		try
		{
			return new JAXB<Tracker>(JAXB_CONTEXT, new File(FILENAME));
		}
		catch (JAXBException e)
		{
			throw new RuntimeException(e);
		}
	}
	
	private static synchronized Tracker loadTracker()
	{
		try
		{
			if (tracker == null)
			{
				tracker = jaxb.load();
			}
			
			return tracker;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			Workflow.showMessage("Error loading tracker: " + e.getMessage());
			
			return new Tracker();
		}
	}
	
	private static synchronized void saveProfile(Profile profile)
	{
		try
		{
			if (tracker == null) return;

			Profile delete = null;
			
			for(Profile xmlProfile : tracker.getProfile())
			{
				if(xmlProfile.getName().equals(profile.getName()))
				{
					delete = xmlProfile;
					break;
				}
			}
			
			if(delete != null)
				tracker.getProfile().remove(delete);
			
			tracker.getProfile().add(profile);
			
			jaxb.save(tracker);
			
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			Workflow.showMessage("Error saving tracker: " + e.getMessage());
		}
	}
	
	private static Profile getProfile(Tracker tracker, String name)
	{
		for(Profile profile : tracker.getProfile())
			if(profile.getName().equals(name))
				return profile;
		
		Profile profile = new Profile();
		profile.setName(name);
		
		return profile;
	}
	
	public static List<Profile> loadAllProfiles()
	{	
		return loadTracker().getProfile();
	}
	
	public static Profile loadProfile(String name)
	{
		return getProfile(loadTracker(), name);
	}
		
	public static void saveProfile()
	{
		save = true;
	}

	public void run()
	{
		int saveTries = 0;
		while(true)
		{			
			try
			{	
				if (saveTries >= 3)
				{
					Workflow.showMessage("Tracker was not saved");
					save = false;
					saveTries = 0;
				}
				
				if(!save)
				{
					Thread.sleep(1000);
					continue;
				}
			
				saveTries++;
				
				Profile profile = Workflow.getProfile();
				
				saveProfile(profile);
				
				save = false;
				saveTries = 0;
			}
			catch(InterruptedException e)
			{
				return;
			}
			catch(Exception e)
			{
				e.printStackTrace();
				Workflow.showMessage("Error in background process: " + e.getMessage());
			}
		}
	}
}
