package jk.tracker.widgets;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.util.Collections;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.UIManager;
import javax.swing.WindowConstants;

import jk.tracker.Workflow;
import jk.tracker.comparators.ShowComparator;
import jk.tracker.core.Show;
import jk.tracker.handlers.ChangeProfileListener;
import jk.tracker.handlers.ConfigureListener;
import jk.tracker.handlers.ExitListener;
import jk.tracker.handlers.ManageShowsListener;
import jk.tracker.handlers.UpdateListener;
import jk.tracker.utils.ProfileUtil;
import jk.tracker.utils.TrackerXML;

public class Tracker extends JFrame {

	private static final long serialVersionUID = 1L;
	
	private static Tracker instance;
	private static Thread saveInBackground;
	
	public static Tracker instance()
	{
		return instance;
	}
	
	private JTabbedPane tabs;
	private JMenuBar menuBar;

	public Tracker() throws Exception
	{
		instance = this;
		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		this.setTitle("Episode Tracker");
		this.setSize(new Dimension(500, 500));
		this.setLocationRelativeTo(null);
		this.setBackground(new Color(255, 255, 255));
		
		addMenuBar();

		BorderLayout layout = new BorderLayout();
		this.setLayout(layout);
		
		tabs = new JTabbedPane();
		updateTabMain();
		updateTabUnseen();
		
		this.add(tabs);
		layout.addLayoutComponent(tabs, BorderLayout.CENTER);
		
		saveInBackground = new Thread(new TrackerXML());
		saveInBackground.start();
	}
	
	public synchronized void updatePanels()
	{
		int index = tabs.getSelectedIndex();
		if(index == -1)
			return;
		
		switch(index)
		{
			case 0:
			{
				updateTabUnseen();
				return;
			}
			case 1:
			{
				updateTabMain();
				return;
			}
		}
	}
	
	private void addMenuBar()
	{
		menuBar = new JMenuBar();
		
		//menu tracker
		JMenu menuTracker = new JMenu("Tracker");
		menuBar.add(menuTracker);
		
		//menu profile
		JMenu menuProfile = new JMenu("Profile");
		menuBar.add(menuProfile);

		//item change profile
		if(Workflow.hasMoreProfiles())
		{
			JMenuItem itemChangeProfile = new JMenuItem("Change profile");
			itemChangeProfile.addActionListener(new ChangeProfileListener());
			menuTracker.add(itemChangeProfile);
			menuTracker.addSeparator();
		}

		//item exit
		JMenuItem itemExit = new JMenuItem("Exit");
		itemExit.addActionListener(new ExitListener());
		menuTracker.add(itemExit);

		//item update
		JMenuItem itemUpdate = new JMenuItem("Update");
		itemUpdate.addActionListener(new UpdateListener());
		menuProfile.add(itemUpdate);
		menuProfile.addSeparator();

		//item manage shows
		JMenuItem itemManageShows = new JMenuItem("Manage shows");
		itemManageShows.addActionListener(new ManageShowsListener());
		menuProfile.add(itemManageShows);

		//item configure
		JMenuItem itemConfigure = new JMenuItem("Configure");
		itemConfigure.addActionListener(new ConfigureListener());
		menuProfile.add(itemConfigure);
		
		this.setJMenuBar(menuBar);
	}
	
	private void updateTabMain()
	{
		int index = tabs.indexOfTab("Main");
		if(index != -1)
			tabs.remove(index);
		else
			index = tabs.getTabCount();
			
		JPanel contents = new JPanel();
		JScrollPane scroller = new JScrollPane(contents);
		scroller.getVerticalScrollBar().setUnitIncrement(16);
				
		contents.setLayout(new BoxLayout(contents, BoxLayout.Y_AXIS));
		
		Collections.sort(Workflow.getProfile().getShow(), new ShowComparator());
		for(Show show : Workflow.getProfile().getShow())
		{
			if(!show.isHidden())
				contents.add(new ShowPanel(show, false));
		}
		
		tabs.insertTab("Main", null, scroller, null, index);
	}
	
	private void updateTabUnseen()
	{
		int index = tabs.indexOfTab("Unseen");
		if(index != -1)
			tabs.remove(index);
		else
			index = tabs.getTabCount();
		
		JPanel contents = new JPanel();
		JScrollPane scroller = new JScrollPane(contents);
		scroller.getVerticalScrollBar().setUnitIncrement(16);
				
		contents.setLayout(new BoxLayout(contents, BoxLayout.Y_AXIS));
		
		Collections.sort(Workflow.getProfile().getShow(), new ShowComparator());
		for(Show show : Workflow.getProfile().getShow())
		{
			if(!show.isHidden() && ProfileUtil.getUnseenEpisodesCount(show) != 0)
				contents.add(new ShowPanel(show, true));
		}
		
		tabs.insertTab("Unseen", null, scroller, null, index);
	}
	
	@Override
	public void dispose()
	{
		if(saveInBackground.isAlive())
			saveInBackground.interrupt();
		
		super.dispose();
	}
}
