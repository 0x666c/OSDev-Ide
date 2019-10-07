package net.jiftoo.osdev4j.gui;

import java.awt.Component;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import net.jiftoo.osdev4j.gui.menubar.EditMenu;
import net.jiftoo.osdev4j.gui.menubar.FileMenu;
import net.jiftoo.osdev4j.gui.menubar.HelpMenu;

// TODO: Make JMenuBar staic and shit
public class MenuBar extends JMenuBar {
	
	public FileMenu file;
	public EditMenu edit;
	public HelpMenu help;
	
	// LMAO GITHUB
	public MenuBar() { // TODO: Start making
		setBorder(null);
		
		file = new FileMenu();
		add(file);
		
		edit = new EditMenu();
		add(edit);
		
		help = new HelpMenu();
		add(help);
		
		// Add stub listeners
		Component[] cpns = getComponents();
		for (Component c : cpns) {
			if (c instanceof JMenu) {
				Component[] ca = ((JMenu) c).getMenuComponents();
				for (Component cc : ca) {
					if (cc instanceof JMenuItem) {
						if (((JMenuItem) cc).getActionListeners().length == 0) {
							((JMenuItem) cc).addActionListener(ev -> JOptionPane.showMessageDialog(SwingUtilities.getWindowAncestor(this), "Not implemented yet :P", "Info", JOptionPane.INFORMATION_MESSAGE));
						}
					}
				}
			}
		}
	}
	
}