package jk.tracker.handlers;

import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class ProfileChooserListener implements ListSelectionListener {
	
	private JList lstProfiles;
	private JButton btnLoad;
	
	public ProfileChooserListener(JList lstProfiles, JButton btnLoad)
	{
		this.lstProfiles = lstProfiles;
		this.btnLoad = btnLoad;
	}

	@Override
	public void valueChanged(ListSelectionEvent arg0)
	{
		String name = (String) lstProfiles.getSelectedValue();
		
		btnLoad.setEnabled(name != null);
	}

}
