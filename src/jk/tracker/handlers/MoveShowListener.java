package jk.tracker.handlers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collections;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JList;

import jk.tracker.Workflow;
import jk.tracker.comparators.ShowComparator;
import jk.tracker.core.Show;
import jk.tracker.utils.ShowWrapper;

public class MoveShowListener implements ActionListener {
	
	private JList from;
	private JList to;
	
	public MoveShowListener(JList from, JList to)
	{
		this.from = from;
		this.to = to;
	}

	@Override
	public void actionPerformed(ActionEvent arg0)
	{
		try
		{
			ShowWrapper moved = null;
			if((moved = (ShowWrapper) from.getSelectedValue()) == null)
			{
				System.out.println("nothing selected");
				return;
			}
			
			DefaultListModel fromList = (DefaultListModel) from.getModel();
			DefaultListModel toList = (DefaultListModel) to.getModel();
			
			toList.addElement(moved);
			fromList.removeElement(moved);
			List<Show> list = new LinkedList<Show>();
			Enumeration<?> elems = toList.elements();
			while(elems.hasMoreElements())
				list.add(((ShowWrapper)elems.nextElement()).getShow());
			
			Collections.sort(list, new ShowComparator());
			toList.removeAllElements();
			for(Show show : list)
				toList.addElement(new ShowWrapper(show));
			
			from.invalidate();
			to.invalidate();
		}
		catch (Exception e)
		{
			e.printStackTrace();
			Workflow.showMessage("Error moving show: " + e.getMessage());
		}
	}
}
