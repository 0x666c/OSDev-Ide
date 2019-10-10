package net.jiftoo.osdev4j.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.DefaultListCellRenderer;
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

import org.luaj.vm2.ast.Str;

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
		
		CustomListRenderer r = new CustomListRenderer();
		r.setLeafIcon(Icons.file);
		r.setClosedIcon(Icons.folder);
		r.setOpenIcon(Icons.folder);
		tree.setCellRenderer(r);
		
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
	
	private static final class CustomListRenderer extends DefaultTreeCellRenderer {
		
		@Override
		public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded,
				boolean leaf, int row, boolean hasFocus) {
			super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);
			
	        Object nodeObj = ((DefaultMutableTreeNode) value).getUserObject();
	        
	        if(leaf) {
	        	String str = nodeObj.toString();
	        	
	        	if(str.contains(".")) {
	        		String[] ext = nodeObj.toString().split("\\.");
		        	str = ext[ext.length - 1];
	        	} else {
	        		str = "NotAnExtension";
	        	}
	        	
		        switch (str) {
				case "asm":
					setIcon(Icons.asm);
					break;
					
				case "c":
					setIcon(Icons.c);
					break;
					
				case "cpp":
					setIcon(Icons.cpp);
					break;
					
				case "h":
					setIcon(Icons.h);
					break;
					
				default:
					setIcon(Icons.file);
					break;
				}
	        }
	        return this;
		}
		
	}
	
}