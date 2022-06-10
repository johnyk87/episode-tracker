package jk.tracker.handlers;

import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class ShowListSelectionListener implements ListSelectionListener {
	
	private JList lst;
	private JButton btn;
	
	public ShowListSelectionListener(JList lst, JButton btn)
	{
		this.lst = lst;
		this.btn = btn;
	}

	@Override
	public void valueChanged(ListSelectionEvent arg0)
	{
		btn.setEnabled(lst.getSelectedIndex() != -1);			
	}
}
