package jk.tracker.widgets;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.Collections;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.swing.SwingConstants;

import jk.tracker.comparators.SeasonComparator;
import jk.tracker.core.Season;
import jk.tracker.core.Show;
import jk.tracker.handlers.ToggleListener;
import jk.tracker.utils.DisplayUtils;
import jk.tracker.utils.ProfileUtil;

public class ShowPanel extends JPanel {
	
	private static final long serialVersionUID = 1L;
		
	public ShowPanel(Show show, boolean unseenOnly)
	{
		BorderLayout layout = new BorderLayout();
		this.setLayout(layout);

		JLabel space = new JLabel(" ");
		space.setPreferredSize(new Dimension(20, 1));

		JToggleButton btnToggle = new JToggleButton(show.getName(), false);
		btnToggle.setHorizontalAlignment(SwingConstants.LEFT);
		JPanel seasons = new JPanel();
		seasons.setVisible(false);
		seasons.setLayout(new BoxLayout(seasons, BoxLayout.Y_AXIS));

		btnToggle.addActionListener(new ToggleListener(seasons));

		this.add(btnToggle);
		this.add(space);
		this.add(seasons);
		layout.addLayoutComponent(btnToggle, BorderLayout.PAGE_START);
		layout.addLayoutComponent(space, BorderLayout.LINE_START);
		layout.addLayoutComponent(seasons, BorderLayout.CENTER);

		Collections.sort(show.getSeason(), new SeasonComparator());
		int total = 0;
		int seen = 0;
		for(Season season : show.getSeason())
		{	
			int seasonTotal = ProfileUtil.getTotalEpisodesCount(season);
			int seasonSeen = ProfileUtil.getSeenEpisodesCount(season);
			
			total += seasonTotal;
			seen += seasonSeen;
			
			if (!unseenOnly || seen < total)
				seasons.add(new SeasonPanel(btnToggle, season, unseenOnly));
		}
		
		btnToggle.setText(show.getName() + " [" + seen + " of " + total + "]");
		
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
