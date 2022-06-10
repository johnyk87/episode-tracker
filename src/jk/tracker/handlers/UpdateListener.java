package jk.tracker.handlers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import jk.tracker.Workflow;

public class UpdateListener implements ActionListener {
	
	public UpdateListener()
	{
	}

	@Override
	public void actionPerformed(ActionEvent arg0)
	{
		try
		{
			String name = Workflow.getProfile().getName();
			new Thread(new ProfileLoader(name, true)).start();
			Workflow.showLoading();
		}
		catch (Exception e)
		{
			e.printStackTrace();
			Workflow.showMessage("Error updating: " + e.getMessage());
		}
	}
}
