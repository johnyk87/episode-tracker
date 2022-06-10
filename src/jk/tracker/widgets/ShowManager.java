package jk.tracker.widgets;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Collections;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;
import javax.swing.WindowConstants;

import jk.tracker.comparators.ShowComparator;
import jk.tracker.core.Profile;
import jk.tracker.core.Show;
import jk.tracker.handlers.ManagerClosedListener;
import jk.tracker.handlers.MoveShowListener;
import jk.tracker.handlers.ShowListSelectionListener;
import jk.tracker.utils.ShowWrapper;

public class ShowManager extends JFrame {

	private static final long serialVersionUID = 1L;
	
	private JList lstTracked = null;
	private JList lstHidden = null;

	public ShowManager(Profile profile) throws Exception
	{
		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		this.setTitle("Show manager");
		this.setSize(new Dimension(590, 330));
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.setBackground(new Color(255, 255, 255));
		
		JPanel contents = new JPanel();
		contents.setLayout(null);
		
		JLabel lblTracked = new JLabel("Tracked");
		lblTracked.setBounds(10, 10, 150, 20);
		
		JLabel lblHidden = new JLabel("Hidden");
		lblHidden.setBounds(330, 10, 150, 20);

		DefaultListModel tracked = new DefaultListModel();
		DefaultListModel hidden = new DefaultListModel();
		
		Collections.sort(profile.getShow(), new ShowComparator());
		for(Show show : profile.getShow())
			if(!show.isHidden())
				tracked.addElement(new ShowWrapper(show));
			else
				hidden.addElement(new ShowWrapper(show));
		
		lstTracked = new JList(tracked);
		lstTracked.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		lstTracked.setLayoutOrientation(JList.VERTICAL);
		lstTracked.addMouseListener(new MouseAdapter() {
		    public void mouseClicked(MouseEvent evt) {
		    	System.out.println("Tracked: " + evt.getClickCount());
		        if (evt.getClickCount() % 2 == 0) {
		        	new MoveShowListener(lstTracked, lstHidden).actionPerformed(new ActionEvent(evt.getSource(), 0, null));
		        }
		    }
		});
		JScrollPane lstTrackedScroll = new JScrollPane(lstTracked);
		lstTrackedScroll.setBounds(10, 32, 250, 250);
		lblTracked.setLabelFor(lstTrackedScroll);
		
		lstHidden = new JList(hidden);
		lstHidden.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		lstHidden.setLayoutOrientation(JList.VERTICAL);
		lstHidden.addMouseListener(new MouseAdapter() {
		    public void mouseClicked(MouseEvent evt) {
		    	System.out.println("Hidden: " + evt.getClickCount());
		        if (evt.getClickCount() % 2 == 0) {
		        	new MoveShowListener(lstHidden, lstTracked).actionPerformed(new ActionEvent(evt.getSource(), 0, null));
		        }
		    }
		});
		lblHidden.setLabelFor(lstTracked);
		JScrollPane lstHiddenScroll = new JScrollPane(lstHidden);
		lstHiddenScroll.setBounds(330, 32, 250, 250);
		lblTracked.setLabelFor(lstHiddenScroll);
		
		JButton btnTrack = new JButton("<");
		btnTrack.setBounds(270, 127, 50, 20);
		btnTrack.addActionListener(new MoveShowListener(lstHidden, lstTracked));
		btnTrack.setEnabled(lstHidden.getSelectedIndex() != -1);
		lstHidden.addListSelectionListener(new ShowListSelectionListener(lstHidden, btnTrack));
		
		JButton btnHide = new JButton(">");
		btnHide.setBounds(270, 167, 50, 20);
		btnHide.addActionListener(new MoveShowListener(lstTracked, lstHidden));
		btnHide.setEnabled(lstTracked.getSelectedIndex() != -1);
		lstTracked.addListSelectionListener(new ShowListSelectionListener(lstTracked, btnHide));
		
		contents.add(lblTracked);
		contents.add(lblHidden);
		contents.add(lstTrackedScroll);
		contents.add(lstHiddenScroll);
		contents.add(btnTrack);
		contents.add(btnHide);

		this.addWindowListener(new ManagerClosedListener(profile, lstTracked, lstHidden));
		this.add(contents);
	}
}
