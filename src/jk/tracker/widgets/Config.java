package jk.tracker.widgets;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.WindowConstants;

import jk.tracker.core.Profile;
import jk.tracker.handlers.ConfigClosedListener;

public class Config extends JFrame {

	private static final long serialVersionUID = 1L;

	public Config(Profile profile) throws Exception
	{
		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		this.setTitle("Profile configuration");
		this.setSize(new Dimension(480, 270));
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.setBackground(new Color(255, 255, 255));
		
		JPanel contents = new JPanel();
		contents.setLayout(null);
		
		JLabel lblName = new JLabel("Name");
		lblName.setBounds(10, 10, 150, 20);
		JTextField txtName = new JTextField(profile.getName());
		txtName.setBounds(170, 10, 300, 20);
		txtName.setEditable(false);
		lblName.setLabelFor(txtName);
		
		JLabel lblFormats = new JLabel("Formats");
		lblFormats.setBounds(10, 40, 150, 20);
		JTextField txtFormats = new JTextField(profile.getFormats());
		txtFormats.setBounds(170, 40, 300, 20);
		lblFormats.setLabelFor(txtFormats);
		
		JLabel lblFolders = new JLabel("Folders");
		lblFolders.setBounds(10, 70, 150, 20);
		JTextArea txtFolders = new JTextArea(profile.getFolders());		
		txtFolders.setBounds(170, 70, 300, 100);
		lblFolders.setLabelFor(txtFolders);
		
		JCheckBox ckbAutotrack = new JCheckBox("Track new shows", profile.isAutotrack());
		ckbAutotrack.setBounds(10, 180, 150, 20);
		
		JCheckBox ckbAutoUpdate = new JCheckBox("Update on load", profile.isAutoupdate());
		ckbAutoUpdate.setBounds(10, 210, 150, 20);

		contents.add(lblName);
		contents.add(txtName);
		contents.add(lblFormats);
		contents.add(txtFormats);
		contents.add(lblFolders);
		contents.add(txtFolders);
		contents.add(ckbAutotrack);
		contents.add(ckbAutoUpdate);

		this.addWindowListener(new ConfigClosedListener(profile, txtFormats, txtFolders, ckbAutotrack, ckbAutoUpdate));
		this.add(contents);
	}
}
