package net.jiftoo.osdev4j.gui.menubar;

import java.awt.Dimension;

import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

import net.jiftoo.osdev4j.control.Controller;

public class EditMenu extends JMenu {
	
	private JMenuItem undo;
	private JMenuItem redo;
	private JMenuItem settings;
	
	public EditMenu() {
		setText("Edit");
		setBorder(null);
		
		undo = add("Undo");
		undo.setBorder(null);
		undo.setPreferredSize(new Dimension(160, 20)); // Only setting prefSize and minSize of the first element is enough
		undo.setMinimumSize(new Dimension(160, 20));
		undo.setAccelerator(KeyStroke.getKeyStroke("control Z"));
		undo.addActionListener(ev -> {
			Controller.undo();
		});
		
		redo = add("Redo");
		redo.setBorder(null);
		redo.setAccelerator(KeyStroke.getKeyStroke("control Y"));
		redo.addActionListener(ev -> {
			Controller.redo();
		});
		
		addSeparator();
		
		settings = add("Settings");
		settings.setBorder(null);
	}
	
}