package jk.tracker.widgets;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.Collections;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.swing.SwingConstants;

import jk.tracker.comparators.EpisodeComparator;
import jk.tracker.core.Episode;
import jk.tracker.core.Season;
import jk.tracker.handlers.ToggleListener;
import jk.tracker.utils.DisplayUtils;

public class SeasonPanel extends JPanel {
	
	private static final long serialVersionUID = 1L;
		
	public SeasonPanel(JToggleButton btnShow, Season season, boolean unseenOnly)
	{
		BorderLayout layout = new BorderLayout();
		this.setLayout(layout);

		JLabel space = new JLabel(" ");
		space.setPreferredSize(new Dimension(20, 1));

		JToggleButton btnToggle = new JToggleButton(season.getName(), false);
		btnToggle.setHorizontalAlignment(SwingConstants.LEFT);
		JPanel episodes = new JPanel();
		episodes.setVisible(false);
		episodes.setLayout(new BoxLayout(episodes, BoxLayout.Y_AXIS));

		btnToggle.addActionListener(new ToggleListener(episodes));

		this.add(btnToggle);
		this.add(space);
		this.add(episodes);
		layout.addLayoutComponent(btnToggle, BorderLayout.PAGE_START);
		layout.addLayoutComponent(space, BorderLayout.LINE_START);
		layout.addLayoutComponent(episodes, BorderLayout.CENTER);

		Collections.sort(season.getEpisode(), new EpisodeComparator());
		int total = 0;
		int seen = 0;
		for(Episode episode : season.getEpisode())
		{
			total++;
			
			boolean showEpisode = true; 
			if (episode.isSeen())
			{
				seen++;
				showEpisode = !unseenOnly;
			}
			
			if (showEpisode)
				episodes.add(new EpisodePanel(btnShow, btnToggle, episode, unseenOnly));
		}

		btnToggle.setText(season.getName() + " [" + seen + " of " + total + "]");
		
		if(seen < total)
		{
			btnToggle.setFont(DisplayUtils.getBoldFont());
		}
		else
		{
			btnToggle.setFont(DisplayUtils.getPlainFont());
		}
	}
}
