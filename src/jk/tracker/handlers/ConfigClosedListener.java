package jk.tracker.handlers;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JCheckBox;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import jk.tracker.Workflow;
import jk.tracker.core.Profile;
import jk.tracker.utils.TrackerXML;

public class ConfigClosedListener implements WindowListener {
	
	private Profile profile;
	private JTextField formats;
	private JTextArea folders;
	private JCheckBox autotrack;
	private JCheckBox autoupdate;
	
	public ConfigClosedListener(Profile profile, JTextField formats, JTextArea folders, JCheckBox autotrack, JCheckBox autoupdate)
	{
		this.profile = profile;
		this.formats = formats;
		this.folders = folders;
		this.autotrack = autotrack;
		this.autoupdate = autoupdate;
	}

	@Override
	public void windowClosed(WindowEvent arg0)
	{
		try
		{
			profile.setFormats(formats.getText().trim());
			profile.setFolders(folders.getText().trim());
			profile.setAutotrack(autotrack.isSelected());
			profile.setAutoupdate(autoupdate.isSelected());
			
			TrackerXML.saveProfile();
			
			Workflow.loadTracker(profile);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			Workflow.showMessage("Error saving configurations: " + e.getMessage());
		}
	}

	@Override
	public void windowActivated(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosing(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeactivated(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeiconified(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowIconified(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowOpened(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
