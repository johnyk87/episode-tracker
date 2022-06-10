package jk.tracker;

import java.awt.Component;
import java.util.List;

import javax.swing.JOptionPane;

import jk.tracker.core.Profile;
import jk.tracker.handlers.ProfileLoader;
import jk.tracker.utils.TrackerXML;
import jk.tracker.widgets.Config;
import jk.tracker.widgets.Loading;
import jk.tracker.widgets.ProfileChooser;
import jk.tracker.widgets.ShowManager;
import jk.tracker.widgets.Tracker;

public class Workflow {
	
	private static ProfileChooser _chooser;
	private static Loading _loading;
	private static Tracker _tracker;
	
	private static Profile _profile;
	private static boolean _hasMoreProfiles;

	public static void main(String[] args) throws Exception
	{
		loadChooser(TrackerXML.loadAllProfiles());
	}
	
	public static synchronized void loadChooser(List<Profile> profiles) throws Exception
	{
		_hasMoreProfiles = profiles.size() > 1;
		
		if(_tracker != null)
		{
			_tracker.dispose();
			_tracker = null;
		}
		
		if(profiles.size() == 1)
		{
			loadProfile(profiles.get(0).getName());
		}
		else
		{
			_chooser = new ProfileChooser(profiles);
			_chooser.setVisible(true);
		}
	}
	
	public static synchronized void loadProfile(String name) throws Exception
	{
		if(_chooser != null)
		{
			_chooser.dispose();
			_chooser = null;
		}
		
		if(_tracker != null)
		{
			_tracker.dispose();
			_tracker = null;
		}

		showLoading();
		new Thread(new ProfileLoader(name, false)).start();
	}
	
	public static synchronized void showLoading() throws Exception
	{
		if(_tracker != null)
			_tracker.setEnabled(false);
		
		_loading = new Loading();
		_loading.setVisible(true);
	}
	
	public static synchronized void loadTracker(Profile profile) throws Exception
	{
		if(_loading != null)
		{
			_loading.dispose();
			_loading = null;
		}
		
		if(_tracker != null)
		{
			_tracker.dispose();
			_tracker = null;
		}
		
		_profile = profile;
		
		_tracker = new Tracker();
		_tracker.setVisible(true);
	}
	
	public static synchronized void showConfig() throws Exception
	{
		if(_tracker != null)
			_tracker.setEnabled(false);
		
		 new Config(_profile).setVisible(true);
	}
	
	public static synchronized void showManager() throws Exception
	{
		if(_tracker != null)
			_tracker.setEnabled(false);
		
		 new ShowManager(_profile).setVisible(true);
	}
	
	public static synchronized Profile getProfile()
	{
		return _profile;
	}
	
	public static synchronized boolean hasMoreProfiles()
	{
		return _hasMoreProfiles;
	}

	public static synchronized void exit()
	{
		if(_chooser != null)
		{
			_chooser.dispose();
			_chooser = null;
		}
		
		if(_loading != null)
		{
			_loading.dispose();
			_loading = null;
		}
		
		if(_tracker != null)
		{
			_tracker.dispose();
			_tracker = null;
		}
	}
	
	public static synchronized void showMessage(String message)
	{
		Component parent = null;
		
		if (_loading != null)
		{
			parent = _loading;
		}
		else if (_chooser != null)
		{
			parent = _chooser;
		}
		else if (_tracker != null)
		{
			parent = _tracker;
		} 
		
		JOptionPane.showMessageDialog(parent, message);
	}
}
