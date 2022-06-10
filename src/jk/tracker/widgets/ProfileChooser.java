package jk.tracker.widgets;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.util.List;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;
import javax.swing.WindowConstants;

import jk.tracker.core.Profile;
import jk.tracker.handlers.LoaderButtonListener;
import jk.tracker.handlers.ProfileChooserListener;

public class ProfileChooser extends JFrame {

	private static final long serialVersionUID = 1L;
	
	private JPanel contents;
	
	private JList lstProfiles;
	private JButton btnLoad;

	public ProfileChooser(List<Profile> profiles) throws Exception
	{
		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		this.setTitle("Profile Loader");
		this.setSize(new Dimension(250, 200));
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.setBackground(new Color(255, 255, 255));
				
		contents = new JPanel();
		BorderLayout layout = new BorderLayout();
		contents.setLayout(layout);
		
		lstProfiles = new JList();
		Vector<String> list = new Vector<String>();
		for(Profile profile : profiles)
		{
			list.add(profile.getName());
		}		
		lstProfiles.setListData(list);
		lstProfiles.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
				
		btnLoad = new JButton("Load Profile");
		btnLoad.setEnabled(false);
		
		lstProfiles.addListSelectionListener(new ProfileChooserListener(lstProfiles, btnLoad));
		btnLoad.addActionListener(new LoaderButtonListener(lstProfiles));

		contents.add(lstProfiles);
		contents.add(btnLoad);
		layout.addLayoutComponent(lstProfiles, BorderLayout.CENTER);
		layout.addLayoutComponent(btnLoad, BorderLayout.PAGE_END);
				
		this.add(contents);
	}
}
