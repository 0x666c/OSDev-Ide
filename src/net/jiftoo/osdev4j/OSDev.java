package net.jiftoo.osdev4j;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.plaf.basic.BasicSplitPaneDivider;
import javax.swing.plaf.basic.BasicSplitPaneUI;

import net.jiftoo.osdev4j.control.Controller;
import net.jiftoo.osdev4j.gui.BottomPanel;
import net.jiftoo.osdev4j.gui.CodeEditorPanel;
import net.jiftoo.osdev4j.gui.ConsolePanel;
import net.jiftoo.osdev4j.gui.FilePanel;
import net.jiftoo.osdev4j.gui.MenuBar;
import net.jiftoo.osdev4j.icons.Icons;
import net.jiftoo.osdev4j.init.Task;
import net.jiftoo.osdev4j.init.TaskPipeline;
import net.jiftoo.osdev4j.interpreter.Interpreter;
import net.jiftoo.osdev4j.modules.gui.ModulePanel;

public class OSDev {
	
	public static final String VERSION = "0.0.1";
	
	public static void main(String[] args) throws Exception {
		System.setProperty("sun.java2d.noddraw", Boolean.TRUE.toString());
		System.out.println(System.getProperty("java.version"));
		
		// Fix JTabbedPane
		UIManager.getDefaults().put("TabbedPane.contentBorderInsets", new Insets(0,0,0,0));
	    UIManager.getDefaults().put("TabbedPane.tabAreaInsets", new Insets(5,0,0,20));
	    UIManager.getDefaults().put("TabbedPane.tabsOverlapBorder", true);
		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		
	    //////////////////////////////////////
		
		// Don't use the task pipeline since the splashscreen uses icons
	    Icons.load();
	    
	    TaskPipeline pipeline = TaskPipeline.loading();
	    
	    pipeline.queueTask(new Task("gui", true, OSDev::createGUI));
	    pipeline.queueTask(new Task("script-engines", false, Interpreter::init));
		
	    pipeline.execute();
	    
	    //////////////////////////////////////
	    
	    mainFrame.setVisible(true);
		System.out.println("Loaded");
	}
	
	/**
	 * @wbp.parser.entryPoint
	 */
	private static void createGUI() {
		mainFrame = new JFrame("OSDev IDE v" + VERSION);
		
		mainFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		
		///////////////
		JPanel cp = new JPanel();
		cp.setLayout(new BorderLayout());
		cp.setBorder(null);
		mainFrame.setContentPane(cp);
		
		JSplitPane mainSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		mainSplitPane.setContinuousLayout(true);
		mainSplitPane.setDividerSize(5);
		mainSplitPane.setUI(new BasicSplitPaneUI() 
		{
		    @Override
		    public BasicSplitPaneDivider createDefaultDivider() 
		    {
		        return new BasicSplitPaneDivider(this) 
		        {                
		            public void setBorder(Border b) {}

		            @Override
		            public void paint(Graphics g) 
		            {
		                g.setColor(mainSplitPane.getBackground());
		                g.fillRect(0, 10, getSize().width, getSize().height);
		                super.paint(g);
		            }
		        };
		    }
		});
		mainSplitPane.setBorder(null);
		
		/////
		
		CodeEditorPanel codePane = new CodeEditorPanel();
		ConsolePanel consolePane = new ConsolePanel();
		FilePanel filePane = new FilePanel(codePane);
		ModulePanel launchControlPanel = new ModulePanel(codePane);
		
		MenuBar menuBar = new MenuBar();
		BottomPanel bottomPanel = new BottomPanel();
		
		/////
		
		cp.add(launchControlPanel, BorderLayout.NORTH);
		cp.add(bottomPanel, BorderLayout.SOUTH);
		/////
		
		JSplitPane editorPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
		editorPane.setPreferredSize(new Dimension(500, 600));
		editorPane.setUI(new BasicSplitPaneUI() 
		{
		    @Override
		    public BasicSplitPaneDivider createDefaultDivider() 
		    {
		        return new BasicSplitPaneDivider(this) 
		        {                
		            public void setBorder(Border b) {}

		            @Override
		            public void paint(Graphics g) 
		            {
		                g.setColor(editorPane.getBackground());
		                g.fillRect(0, 10, getSize().width, getSize().height);
		                super.paint(g);
		            }
		        };
		    }
		});
//		editorPane.setBorder(new EmptyBorder(0,0,0,0));
		editorPane.setBorder(null);
		editorPane.setTopComponent(codePane);
		editorPane.setBottomComponent(consolePane);
		editorPane.setContinuousLayout(true);
		
		
		mainSplitPane.setRightComponent(editorPane);
		mainSplitPane.setLeftComponent(filePane);
		
		cp.add(mainSplitPane, BorderLayout.CENTER);
		
	// Menu bar
		mainFrame.setJMenuBar(menuBar);
		
	// Init the controller
		Controller.initStuff(menuBar, codePane, filePane, consolePane);
	//
		
		mainFrame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) { Controller.exit(0); }
		});
		
		mainFrame.pack();
		
		mainFrame.setSize(1000, 700);
		mainFrame.setLocationRelativeTo(null);
		
		codePane.focus();
	}
	
	//private static Taskbar tb = Taskbar.getTaskbar();
	private static JFrame mainFrame;
	
	public static void blinkTaskbar() {
		//tb.requestWindowUserAttention(mainFrame);
		// java 8 doesn't support Taskbar
		return;
	}

}