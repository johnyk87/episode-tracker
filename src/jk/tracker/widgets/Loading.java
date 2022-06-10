package jk.tracker.widgets;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.UIManager;
import javax.swing.WindowConstants;

public class Loading extends JFrame {

	private static final long serialVersionUID = 1L;
	
	private JPanel contents;
	private JProgressBar pbLoading;
	
	public Loading() throws Exception
	{
		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

		this.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		this.setTitle("Loading...");
		this.setResizable(false);
		this.setSize(new Dimension(250, 50));
		this.setLocationRelativeTo(null);
		
		contents = new JPanel();
		BorderLayout layout = new BorderLayout();
		contents.setLayout(layout);
				
		pbLoading = new JProgressBar();
		pbLoading.setIndeterminate(true);
		pbLoading.setValue(0);

		contents.add(pbLoading);
		layout.addLayoutComponent(pbLoading, BorderLayout.CENTER);
		
		this.add(contents);
		this.setVisible(true);
		this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
	}
}
