package jk.tracker.handlers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JToggleButton;

import jk.tracker.core.Episode;
import jk.tracker.utils.DisplayUtils;
import jk.tracker.utils.TrackerXML;
import jk.tracker.widgets.Tracker;

public class EpisodeStateChanged implements ActionListener {
	
	private JToggleButton btnShow;
	private JToggleButton btnSeason;
	private Episode episode;
	private boolean unseenOnly;
	
	public EpisodeStateChanged(JToggleButton btnShow, JToggleButton btnSeason, Episode episode, boolean unseenOnly)
	{
		this.btnShow = btnShow;
		this.btnSeason = btnSeason;
		this.episode = episode;
		this.unseenOnly = unseenOnly;
	}

	@Override
	public void actionPerformed(ActionEvent arg0)
	{
		JToggleButton btnEpisode = (JToggleButton) arg0.getSource();
		episode.setSeen(btnEpisode.isSelected());
		if(episode.isSeen())
		{
			btnEpisode.setFont(DisplayUtils.getPlainFont());
			if(decUnseenEpisodeCount(btnShow) == 0 && unseenOnly)
				btnShow.setVisible(false);
			if(decUnseenEpisodeCount(btnSeason) == 0 && unseenOnly)
				btnSeason.setVisible(false);
			if(unseenOnly)
				btnEpisode.setVisible(false);
		}
		else
		{
			btnEpisode.setFont(DisplayUtils.getBoldFont());
			incUnseenEpisodeCount(btnShow);
			incUnseenEpisodeCount(btnSeason);
		}
		
		TrackerXML.saveProfile();
		Tracker.instance().updatePanels();
	}
	
	private int decUnseenEpisodeCount(JToggleButton btnToggle)
	{
		String text = btnToggle.getText();
		int brackIndex1 = text.lastIndexOf('[');
		int spaceIndex1 = text.indexOf(' ', brackIndex1);
		int spaceIndex2 = text.lastIndexOf(' ');
		int brackIndex2 = text.lastIndexOf(']');
		
		int seen = Integer.parseInt(text.substring(brackIndex1 + 1, spaceIndex1));
		int total = Integer.parseInt(text.substring(spaceIndex2 + 1, brackIndex2));
		
		seen++;
		
		btnToggle.setText(text.substring(0, brackIndex1 + 1) + seen + text.substring(spaceIndex1));

		int unseen = total - seen;
		if(unseen != 0)
		{
			btnToggle.setFont(DisplayUtils.getBoldFont());
		}
		else
		{
			btnToggle.setFont(DisplayUtils.getPlainFont());
		}
		
		return unseen;
	}
	
	private int incUnseenEpisodeCount(JToggleButton btnToggle)
	{
		String text = btnToggle.getText();
		int brackIndex1 = text.lastIndexOf('[');
		int spaceIndex1 = text.indexOf(' ', brackIndex1);
		int spaceIndex2 = text.lastIndexOf(' ');
		int brackIndex2 = text.lastIndexOf(']');
		
		int seen = Integer.parseInt(text.substring(brackIndex1 + 1, spaceIndex1));
		int total = Integer.parseInt(text.substring(spaceIndex2 + 1, brackIndex2));
		
		seen--;
		
		btnToggle.setText(text.substring(0, brackIndex1 + 1) + seen + text.substring(spaceIndex1));

		int unseen = total - seen;
		if(unseen != 0)
		{
			btnToggle.setFont(DisplayUtils.getBoldFont());
		}
		else
		{
			btnToggle.setFont(DisplayUtils.getPlainFont());
		}
		
		return unseen;
	}
}
