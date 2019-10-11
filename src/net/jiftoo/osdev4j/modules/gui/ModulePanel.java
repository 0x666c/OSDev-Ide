package net.jiftoo.osdev4j.modules.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.ButtonModel;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import net.jiftoo.osdev4j.gui.CodeEditorPanel;
import net.jiftoo.osdev4j.gui.CoolButton;
import net.jiftoo.osdev4j.icons.Icons;

public class ModulePanel extends JPanel {
	
	private final CodeEditorPanel codeEditor;
	
	public ModulePanel(CodeEditorPanel cep) {
		this.codeEditor = cep;
		
		setPreferredSize(new Dimension(0, 31));
		setLayout(new FlowLayout(FlowLayout.LEFT, 5, 2));
		setBorder(new CompoundBorder(new EmptyBorder(0,2,0,2), new LineBorder(Color.BLACK)));
		setAlignmentX(LEFT_ALIGNMENT);
		setAlignmentY(TOP_ALIGNMENT);
		
///////////////
		
		// TODO: Use ModuleManager instead to load modules and add corresponding buttons dynamically
		CoolButton compile = new CoolButton(Icons.compile);
		compile.setPreferredSize(new Dimension(25,25));
		add(compile);
		
		CoolButton compileandrun = new CoolButton(Icons.compilerun);
		compileandrun.setPreferredSize(new Dimension(25,25));
		add(compileandrun);
		
		CoolButton run = new CoolButton(Icons.run);
		run.setPreferredSize(new Dimension(25,25));
		add(run);
		
///////////////
	}
	
}