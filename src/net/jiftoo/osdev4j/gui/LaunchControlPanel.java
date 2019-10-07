package net.jiftoo.osdev4j.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import net.jiftoo.osdev4j.icons.Icons;

public class LaunchControlPanel extends JPanel {
	
	private final CodeEditorPanel codeEditor;
	
	public LaunchControlPanel(CodeEditorPanel cep) {
		this.codeEditor = cep;
		
		setPreferredSize(new Dimension(0, 30));
		setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5/2));
		setBorder(new LineBorder(Color.BLACK));
		setAlignmentX(LEFT_ALIGNMENT);
		setAlignmentY(TOP_ALIGNMENT);
		
		JButton compile = new JButton();
//		compile.setBorder(new EmptyBorder(0,0,0,0));
		compile.setFocusable(false);
		compile.setPreferredSize(new Dimension(25,25));
		try {
			compile.setIcon(Icons.file);
		} catch (Exception e) {
			e.printStackTrace();
		}
		add(compile);
		
	}
	
}