package net.jiftoo.osdev4j.gui.menubar;

import java.awt.Dimension;
import java.awt.event.KeyEvent;

import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

import net.jiftoo.osdev4j.control.Controller;

public class FileMenu extends JMenu {
	
	private JMenuItem newf;
	private JMenuItem open;
	public  JMenuItem save;
	private JMenuItem refresh;
	private JMenuItem rename;
	private JMenuItem exit;
	
	public FileMenu() {
		setText("File");
		setBorder(null);
		
		newf = add("New");
		newf.setBorder(null);
		newf.setPreferredSize(new Dimension(160, 20)); // Only setting prefSize and minSize of the first element is enough
		newf.setMinimumSize(new Dimension(160, 20));
		newf.setAccelerator(KeyStroke.getKeyStroke("control N"));
		
		open = add("Open");
		open.setBorder(null);
		open.setAccelerator(KeyStroke.getKeyStroke("control O"));
		open.addActionListener(ev -> {
			JFileChooser ch = new JFileChooser();
			ch.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			int ex = ch.showOpenDialog(Controller.getMainFrame());
			
			// If successfully selected a directory
			if (ex == JFileChooser.APPROVE_OPTION) {
				String theFile = ch.getSelectedFile().getAbsolutePath();
				System.out.println(theFile);
				Controller.openDirectory(theFile);
			}
		});
		
		save = add("Save");
		save.setBorder(null);
		save.setAccelerator(KeyStroke.getKeyStroke("control S"));
		save.addActionListener(ev -> {
			Controller.saveCurrentFile();
		});
		
		addSeparator();
		
		refresh = add("Refresh");
		refresh.setBorder(null);
		refresh.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F5, KeyEvent.META_DOWN_MASK));
		refresh.addActionListener(ev -> {
			Controller.refreshCurrentTab();
			System.out.println(11);
		});
		
		rename = add("Rename");
		rename.setBorder(null);
		rename.setAccelerator(KeyStroke.getKeyStroke("control O"));
		/*rename.addActionListener(ev -> {
			OSDev.codePane.renameCurrentTabFile();
		});*/
		
		addSeparator();
		
		exit = add("Exit");
		exit.setBorder(null);
		exit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F4, KeyEvent.ALT_DOWN_MASK));
		
	}
	
}