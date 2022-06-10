package jk.tracker.handlers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import jk.tracker.Workflow;

public class ConfigureListener implements ActionListener {
	
	public ConfigureListener()
	{
	}

	@Override
	public void actionPerformed(ActionEvent arg0)
	{
		try
		{
			Workflow.showConfig();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			Workflow.showMessage("Error loading configuration editor: " + e.getMessage());
		}
	}
}
