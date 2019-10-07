package net.jiftoo.osdev4j.control;

import java.awt.Color;
import java.io.File;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import org.fife.ui.rsyntaxtextarea.SyntaxConstants;

import net.jiftoo.osdev4j.gui.CodeEditorPanel;
import net.jiftoo.osdev4j.gui.ConsolePanel;
import net.jiftoo.osdev4j.gui.FilePanel;
import net.jiftoo.osdev4j.gui.MenuBar;

/**
 * Static class used to control the whole program
 */
// TODO: Integrate with other classes
public class Controller {
	
	// Should all be initialized before setVisible()
	private static MenuBar menuBar;
	private static CodeEditorPanel codeEditorPanel;
	private static FilePanel filePanel;
	private static ConsolePanel consolePanel;
	
	public static void initStuff(MenuBar mb, CodeEditorPanel cep, FilePanel fp, ConsolePanel cp) {
		menuBar = mb;
		codeEditorPanel = cep;
		filePanel = fp;
		consolePanel = cp;
	}
	
	/////////////////////////////////////////////////////////////////
	
	// TODO: Prompt to save all files on exit
	public static void exit(int code) {
		System.exit(code);
	}
	
	/////////////////////////////////////////////////////////////////
	
	public static void openDirectory(String path) {
		filePanel.getParsingProgressBar().setVisible(true);
		Controller.parseDirectory(path);
	}
	
	// Clear the FilePanel
	public static void closeDirectory() {
		filePanel.getParsingProgressBar().setVisible(false);
		filePanel.getTree().setModel(null);
	}//
	
	/////////////////////////////////////////////////////////////////
	
	// Opens file in the editor
	public static void openFile(String path, String syntax) {
		codeEditorPanel.openNewTab(path, syntax);
	}
	
	public static void openFile(String path) {
		final File file = new File(path);
		
		if(!file.isDirectory()) {
			String extension = file.getName().contains(".") ? file.getName().substring(file.getName().indexOf('.') + 1) : "<none>";
			//System.out.print("Opened file " + file.getName() + " with extension " + extension + ", ");
			if(extension.equalsIgnoreCase("c") || extension.equalsIgnoreCase("h"))
				openFile(path, SyntaxConstants.SYNTAX_STYLE_C);
			else if(extension.equalsIgnoreCase("cpp") || extension.equalsIgnoreCase("hpp"))
				openFile(path, SyntaxConstants.SYNTAX_STYLE_CPLUSPLUS);
			else if(extension.equalsIgnoreCase("asm"))
				openFile(path, SyntaxConstants.SYNTAX_STYLE_ASSEMBLER_X86);
			else
				openFile(path, SyntaxConstants.SYNTAX_STYLE_NONE);
		}
	}
	
	/////////////////////////////////////////////////////////////////
	
	public static void saveCurrentFile() {
		codeEditorPanel.saveCurrentTab();
	}
	
	// 'ident' can be either String (tab name) or int (tab id);
	public static void saveChosenFile(Object ident) {
		if(ident instanceof String)
			throw new UnsupportedOperationException("Tab index by string is noi");
		else if(ident instanceof Integer)
			codeEditorPanel.saveTab((Integer)ident);
		
		throw new IllegalStateException("ident is not a String or an int");
	}
	
	public static void saveAllFiles() {
		codeEditorPanel.saveAllTabs();
	}
	
	/////////////////////////////////////////////////////////////////
	
	public static void refreshCurrentTab() {
		codeEditorPanel.refreshCurrentTab();
	}
	
	// Same as saveChosenFile()
	public static void refreshChosenTab(Object ident) {
		if(ident instanceof String)
			throw new UnsupportedOperationException("Tab index by string is noi");
		else if(ident instanceof Integer)
			codeEditorPanel.refreshTab((Integer)ident);
		
		throw new IllegalStateException("ident is not a String or an int");
	}
	
	public static void refreshAllTabs() {
		codeEditorPanel.refreshAllTabs();
	}
	
	public static void parseDirectory(String path) {
		filePanel
			.getTree()
				.setModel(
						new FileSystemParser(new File(path),
								filePanel.getTree(),
								filePanel.getParsingProgressBar())
						.supplyModelAndBeginParsing());
	}
	
	/////////////////////////////////////////////////////////////////
	// Log only to the console
	
	public static void logToConsole(String text) {
		consolePanel.puts(text);
	}
	
	public static void logToConsole(String text, Color color) {
		consolePanel.puts(text, color);
	}
	
	/////////////////////////////////////////////////////////////////
	
	// FIXME: Fix indo to emptiness when opening file
	public static void undo() {
		codeEditorPanel.getCurrentEditor().editor.undoLastAction();
	}
	
	public static void redo() {
		codeEditorPanel.getCurrentEditor().editor.redoLastAction();
	}
	
	/////////////////////////////////////////////////////////////////
	
	// TODO: Implement
	public static void changeSettings(Preferences newSettings) {
		
	}
	
	/////////////////////////////////////////////////////////////////
	
	private static JFrame rootFrame = null;
	
	public static JFrame getMainFrame() {
		if(rootFrame == null)
			rootFrame = (JFrame)SwingUtilities.getWindowAncestor(codeEditorPanel);
		
		return rootFrame;
	}
	
	/////////////////////////////////////////////////////////////////
	
}