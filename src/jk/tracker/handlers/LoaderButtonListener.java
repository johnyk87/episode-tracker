package jk.tracker.handlers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JList;

import jk.tracker.Workflow;

public class LoaderButtonListener implements ActionListener {

	private JList lstProfiles;
	
	public LoaderButtonListener(JList lstProfiles)
	{
		this.lstProfiles = lstProfiles;
	}

	@Override
	public void actionPerformed(ActionEvent arg0)
	{
		try
		{
			String name = (String) lstProfiles.getSelectedValue();
			
			if(name == null)
				return;
			
			Workflow.loadProfile(name);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			Workflow.showMessage("Error loading profile: " + e.getMessage());
		}
	}
}
