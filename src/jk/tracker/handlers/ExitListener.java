package jk.tracker.handlers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import jk.tracker.Workflow;

public class ExitListener implements ActionListener {
	
	public ExitListener()
	{
	}

	@Override
	public void actionPerformed(ActionEvent arg0)
	{
		Workflow.exit();
	}
}
