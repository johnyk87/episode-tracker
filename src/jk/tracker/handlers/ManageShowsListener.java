package jk.tracker.handlers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import jk.tracker.Workflow;

public class ManageShowsListener implements ActionListener {
	
	public ManageShowsListener()
	{
	}

	@Override
	public void actionPerformed(ActionEvent arg0)
	{
		try
		{
			Workflow.showManager();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			Workflow.showMessage("Error loading show editor: " + e.getMessage());
		}
	}
}
