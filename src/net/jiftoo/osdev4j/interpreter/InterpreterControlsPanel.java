package net.jiftoo.osdev4j.interpreter;

import java.awt.Dimension;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.fife.ui.rsyntaxtextarea.SyntaxConstants;

public class InterpreterControlsPanel extends JPanel {
	
	private InterpreterFrame frame;
	private static JComboBox<String> interpreters;
	
	public InterpreterControlsPanel(InterpreterFrame frame) {
		this.frame = frame;
		
		setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
		setPreferredSize(new Dimension(400, 30));
		setBorder(new EmptyBorder(5,5,5,5));
		
		Box b = Box.createHorizontalBox();
		
		interpreters = new JComboBox<>();
		interpreters.setMaximumSize(new Dimension(100,20));
		interpreters.setMinimumSize(new Dimension(100,20));
		interpreters.setFocusable(false);
		interpreters.addItem("lua");
		interpreters.addItem("js");
		interpreters.addItem(loadp ? "python" : "*python");
		interpreters.addActionListener(ev -> {
			String lang = SyntaxConstants.SYNTAX_STYLE_LUA;
			switch ((String) interpreters.getSelectedItem()) {
			case "js":
				lang = SyntaxConstants.SYNTAX_STYLE_JAVASCRIPT;
				break;
			case "python":
				lang = SyntaxConstants.SYNTAX_STYLE_PYTHON;
				break;
			}
			frame.codePanel.changeLanguage(lang);
		});
		
		JButton run = new JButton("Run");
		run.setFocusable(false);
		run.addActionListener(ev -> {
			Interpreter.get(
					(String)interpreters.getSelectedItem()).exec(frame.codePanel.getTextArea().getText());
		});
		b.add(run);
		
		JButton check = new JButton("Check");
		check.setFocusable(false);
		check.setEnabled(false);
		b.add(Box.createHorizontalStrut(5));
		b.add(check);
		
		
		b.add(Box.createHorizontalGlue());
		
		b.add(interpreters);
		
		add(b);
	}
	
	// Crutch: BAD AF
	private static boolean loadp = false;
	public static void pyLoaded() {
		loadp = true;
	}
	
}