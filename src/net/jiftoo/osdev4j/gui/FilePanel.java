package net.jiftoo.osdev4j.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.Icon;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreePath;

import net.jiftoo.osdev4j.control.Controller;
import net.jiftoo.osdev4j.control.FileNode;
import net.jiftoo.osdev4j.icons.Icons;

/**
 * Contains file tree and probably something else i haven't came up with yet
 */
public class FilePanel extends JPanel {
	
	private JTree tree;
	private JProgressBar parsingProgress;
	
	public FilePanel(CodeEditorPanel cep) {
		setLayout(new BorderLayout());
		setBorder(new CompoundBorder(new EmptyBorder(2,2,2,0), new LineBorder(Color.BLACK, 1)));
		
		// ProgressBar text color
		UIManager.put("ProgressBar.selectionForeground", Color.black); // Outside
		UIManager.put("ProgressBar.selectionBackground", Color.black); // Inside
		parsingProgress = new JProgressBar(0,1);
		parsingProgress.setBorder(null);
		parsingProgress.setVisible(false);
		parsingProgress.setForeground(new Color(0xADFF2F));
		parsingProgress.setStringPainted(true);
		add(parsingProgress, BorderLayout.NORTH);
		
		////////// Tree //////////
		tree = new JTree((Object[])null);
		
		tree.setPreferredSize(new Dimension(200, 600));
		tree.setMinimumSize(new Dimension(100, 600));
		tree.setBorder(new EmptyBorder(5,5,5,5));
		
		DefaultTreeCellRenderer r = (DefaultTreeCellRenderer)tree.getCellRenderer();
		r.setLeafIcon(Icons.file);
		r.setClosedIcon(Icons.folderClosed);
		r.setOpenIcon(Icons.folderOpen);
		
		tree.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if(!SwingUtilities.isLeftMouseButton(e))
					return;
				
				int selRow = tree.getRowForLocation(e.getX(), e.getY());
				TreePath selPath = tree.getPathForLocation(e.getX(), e.getY());
				if (selRow != -1) {
					if (e.getClickCount() == 2) {
						Controller.openFile(((FileNode)((DefaultMutableTreeNode)selPath.getLastPathComponent()).getUserObject()).getFile().getAbsolutePath());
					}
				}
			}
		});
		
		final JScrollPane sc = new JScrollPane(tree);
		sc.setBorder(null);
		
		add(sc, BorderLayout.CENTER);
	}
	
	public JTree getTree() {
		return tree;
	}
	
	public JProgressBar getParsingProgressBar() {
		return parsingProgress;
	}
	
}