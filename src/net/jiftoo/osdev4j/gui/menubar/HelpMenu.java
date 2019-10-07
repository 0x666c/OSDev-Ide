package net.jiftoo.osdev4j.gui.menubar;

import java.awt.Dimension;

import javax.swing.JMenu;
import javax.swing.JMenuItem;

import net.jiftoo.osdev4j.interpreter.InterpreterFrame;

public class HelpMenu extends JMenu {
	
	private JMenuItem interpreter;
	private JMenuItem calculator;
	private JMenuItem about;
	
	public HelpMenu() {
		setText("Help");
		setBorder(null);
		
		interpreter = add("Interpreter");
		interpreter.setBorder(null);
		interpreter.setPreferredSize(new Dimension(160, 20)); // Only setting prefSize and minSize of the first element is enough
		interpreter.setMinimumSize(new Dimension(160, 20));
		interpreter.addActionListener(ev -> {
			InterpreterFrame.get().show();
		});
		
		// TODO: Implement a calculator
		calculator = add("Calculator");
		calculator.setBorder(null);
		
		addSeparator();
		
		about = add("About");
		about.setBorder(null);
	}
	
}