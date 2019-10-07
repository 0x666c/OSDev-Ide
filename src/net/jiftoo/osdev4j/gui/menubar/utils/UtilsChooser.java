package net.jiftoo.osdev4j.gui.menubar.utils;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BoxLayout;
import javax.swing.JDialog;

// Mark as deprecated since i have no plans of implementing utilities
@Deprecated
public class UtilsChooser extends JDialog {
	
	// Crutch: does it need a volatile modifier?
	private volatile static UtilsChooser utilsChooser;
	
	public static UtilsChooser get() {
		if(utilsChooser == null)
			utilsChooser = new UtilsChooser();
		return utilsChooser;
	}
	
	//////////////////////////////////////////
	
	public UtilsChooser() {
		setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				close();
			}
		});
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
	}
	
	// Reset and show
	public void open() {
		new UtilsChooser();
		setVisible(true);
	}
	
	public void close() {
		dispose();
		
		utilsChooser = null;
	}
	
}