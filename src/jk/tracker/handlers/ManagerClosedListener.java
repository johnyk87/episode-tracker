package jk.tracker.handlers;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.Collections;

import javax.swing.JList;

import jk.tracker.Workflow;
import jk.tracker.comparators.ShowComparator;
import jk.tracker.core.Profile;
import jk.tracker.core.Show;
import jk.tracker.utils.TrackerXML;
import jk.tracker.utils.ShowWrapper;

public class ManagerClosedListener implements WindowListener {
	
	private Profile profile;
	private JList tracked;
	private JList hidden;
	
	public ManagerClosedListener(Profile profile, JList tracked, JList hidden)
	{
		this.profile = profile;
		this.tracked = tracked;
		this.hidden = hidden;
	}

	@Override
	public void windowClosed(WindowEvent arg0)
	{
		try
		{
			profile.getShow().clear();
			
			for(int i=0; i<tracked.getModel().getSize(); i++)
			{
				Show show = ((ShowWrapper) tracked.getModel().getElementAt(i)).getShow();
				show.setHidden(false);
				profile.getShow().add(show);	
			}
			for(int i=0; i<hidden.getModel().getSize(); i++)
			{
				Show show = ((ShowWrapper) hidden.getModel().getElementAt(i)).getShow();
				show.setHidden(true);
				profile.getShow().add(show);	
			}
			
			Collections.sort(profile.getShow(), new ShowComparator());
			
			TrackerXML.saveProfile();
			
			Workflow.loadTracker(profile);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			Workflow.showMessage("Error saving show configurations: " + e.getMessage());
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
