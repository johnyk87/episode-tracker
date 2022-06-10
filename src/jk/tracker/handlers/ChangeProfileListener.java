package jk.tracker.handlers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import jk.tracker.Workflow;
import jk.tracker.utils.TrackerXML;

public class ChangeProfileListener implements ActionListener {
	
	public ChangeProfileListener()
	{
	}

	@Override
	public void actionPerformed(ActionEvent arg0)
	{
		try
		{
			Workflow.loadChooser(TrackerXML.loadAllProfiles());
		}
		catch (Exception e)
		{
			e.printStackTrace();
			Workflow.showMessage("Error loading profile list: " + e.getMessage());
		}
	}
}
