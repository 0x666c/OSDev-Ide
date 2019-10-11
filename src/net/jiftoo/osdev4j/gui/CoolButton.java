package net.jiftoo.osdev4j.gui;

import java.awt.Color;

import javax.swing.ButtonModel;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.UIManager;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class CoolButton extends JButton {
	
	public CoolButton() {
		setFocusPainted(false);
		setContentAreaFilled(false);
		setOpaque(true);
		
		getModel().addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				ButtonModel model = (ButtonModel)e.getSource();
				
				setBackground(UIManager.getColor("Button.background"));
				
				if(model.isRollover()) {
					setBackground(UIManager.getColor("inactiveCaption"));
				} if(model.isRollover() && model.isArmed()) {
					setBackground(UIManager.getColor("activeCaption"));
				}
			}
		});
	}
	
	public CoolButton(Icon icon) {
		this();
		try {
			setIcon(icon);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}