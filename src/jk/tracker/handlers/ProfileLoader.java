package jk.tracker.handlers;

import jk.tracker.Workflow;
import jk.tracker.core.Profile;
import jk.tracker.utils.ProfileUtil;
import jk.tracker.utils.TrackerXML;

public class ProfileLoader implements Runnable {
	
	private String name;
	private boolean request;
	
	public ProfileLoader(String name, boolean request)
	{
		this.name = name;
		this.request = request;
	}

	public void run()
	{
		try
		{
			Profile profile = TrackerXML.loadProfile(name);
			if(request || profile.isAutoupdate())
				profile = ProfileUtil.updateProfile(name);
			
			Workflow.loadTracker(profile);
		} 
		catch (Exception e)
		{
			e.printStackTrace();
			Workflow.showMessage("Error loading profile: " + e.getMessage());
		}
	}
}
