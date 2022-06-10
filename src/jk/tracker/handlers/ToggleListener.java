package jk.tracker.handlers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.JToggleButton;

public class ToggleListener implements ActionListener {
	
	private JPanel panel;
	
	public ToggleListener(JPanel panel)
	{
		this.panel = panel;
	}

	public void actionPerformed(ActionEvent arg0)
	{
		panel.setVisible(((JToggleButton) arg0.getSource()).isSelected());
	}
}
