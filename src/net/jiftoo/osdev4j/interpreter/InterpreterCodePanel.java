package net.jiftoo.osdev4j.interpreter;

import java.awt.Dimension;

import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.SyntaxConstants;
import org.fife.ui.rtextarea.RTextScrollPane;

public class InterpreterCodePanel extends RTextScrollPane {
	
	private RSyntaxTextArea textArea;
	
	public InterpreterCodePanel(InterpreterFrame frame) {
		textArea = new RSyntaxTextArea();
		textArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_LUA);
		
		setViewportView(textArea);
		
		setPreferredSize(new Dimension(400, 300));
	}
	
	public void changeLanguage(String syntaxConstant) {
		textArea.setSyntaxEditingStyle(syntaxConstant);
	}
	
}