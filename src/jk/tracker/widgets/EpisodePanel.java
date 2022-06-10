package jk.tracker.widgets;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.swing.SwingConstants;

import jk.tracker.core.Episode;
import jk.tracker.handlers.EpisodeStateChanged;
import jk.tracker.utils.DisplayUtils;

public class EpisodePanel extends JPanel {
		
	private static final long serialVersionUID = 1L;
		
	public EpisodePanel(JToggleButton btnShow, JToggleButton btnSeason, Episode episode, boolean unseenOnly)
	{
		BorderLayout layout = new BorderLayout();
		this.setLayout(layout);
		
		String text = "[" + episode.getNumber() + "] " + episode.getName();		

		JToggleButton btnToggle = new JToggleButton(text, episode.isSeen());
		if(episode.isSeen())
			btnToggle.setFont(DisplayUtils.getPlainFont());
		else
			btnToggle.setFont(DisplayUtils.getBoldFont());
		
		btnToggle.setHorizontalAlignment(SwingConstants.LEFT);

		btnToggle.addActionListener(new EpisodeStateChanged(btnShow, btnSeason, episode, unseenOnly));
		
		this.add(btnToggle);
		layout.addLayoutComponent(btnToggle, BorderLayout.CENTER);
	}
}
