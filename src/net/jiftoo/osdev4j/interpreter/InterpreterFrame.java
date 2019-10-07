package net.jiftoo.osdev4j.interpreter;

import java.awt.BorderLayout;

import javax.swing.JFrame;

import net.jiftoo.osdev4j.control.Controller;

public class InterpreterFrame extends JFrame {
	
	private static volatile InterpreterFrame interpreterFrame;
	
	public static InterpreterFrame get() {
		if(interpreterFrame == null)
			interpreterFrame = new InterpreterFrame();
		return interpreterFrame;
	}
	
	//////////////////////////////////////////
	
	public InterpreterCodePanel codePanel;
	public InterpreterControlsPanel controlsPanel;
	
	public InterpreterFrame() {
		setTitle("Script");
		setAlwaysOnTop(true);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLayout(new BorderLayout());
		
		codePanel = new InterpreterCodePanel(this);
		add(codePanel, BorderLayout.CENTER);
		
		controlsPanel = new InterpreterControlsPanel(this);
		add(controlsPanel, BorderLayout.NORTH);
		
		pack();
		setLocationRelativeTo(Controller.getMainFrame());
	}
	
	public void open() {
		setVisible(true);
	}
	
	public void close() {
		dispose();
		interpreterFrame = null;
	}
	
}