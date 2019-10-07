package net.jiftoo.osdev4j.control;

import java.io.File;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JProgressBar;
import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;

import net.jiftoo.osdev4j.OSDev;

public class FileSystemParser {
	
	private final TreeModel suppliedModel;
	private final File root;
	
	private final JTree tree;
	private final JProgressBar parsingProgress;
	
	public FileSystemParser(File root, JTree tree, JProgressBar pp) {
		this.root = root;
		this.tree = tree;
		this.parsingProgress = pp;
		suppliedModel = new DefaultTreeModel(new DefaultMutableTreeNode(new FileNode(root)));
	}
	
	private boolean beganparsingionce = false;
	public TreeModel supplyModelAndBeginParsing() {
		if(beganparsingionce)
			throw new RuntimeException("Cannot reuse " + getClass().getSimpleName());
		beganparsingionce = true;
		
		final CreateChildNodes c = new CreateChildNodes(root, (DefaultMutableTreeNode)suppliedModel.getRoot());
		c.parse();
		
		return suppliedModel;
	}
	
	class CreateChildNodes {

        private DefaultMutableTreeNode root;

        private File fileRoot;

        private CreateChildNodes(File fileRoot, 
                DefaultMutableTreeNode root) {
            this.fileRoot = fileRoot;
            this.root = root;
        }
        
        private double parsingBarCounter = 0.0;
        
        private void parse() {
        	// Update progress bar
        	parsingProgress.setString("Parsing for 0.0");
        	parsingProgress.setValue(0);
        	parsingProgress.setIndeterminate(true);
        	parsingBarCounter = 0.0;
        	
			new Thread(() -> {
				// ProgressBar value updater
				
				Timer tm = new Timer();
	        	tm.scheduleAtFixedRate(new TimerTask() {
					public void run() {
						SwingUtilities.invokeLater(() -> parsingProgress.setString(String.format("Parsing for %.2f", parsingBarCounter)));
						parsingBarCounter += 0.01;
					}
				}, 0, 10);
				
				createChildren(fileRoot, root);
				
				tm.cancel();
				
				System.out.println("Finished parsing in " +String.format("%.2f", parsingBarCounter)+ " ms");
				System.out.println("====================================================================");
				System.out.println();
				
				// Update progress bar
				SwingUtilities.invokeLater(() -> {
					parsingProgress.setString("Finished parsing in " +String.format("%.2f", parsingBarCounter)+ " ms");
					parsingProgress.setIndeterminate(false);
					parsingProgress.setValue(1);
					
					OSDev.blinkTaskbar();
				});
				
			}).start();
		}

		private void createChildren(File fileRoot, DefaultMutableTreeNode node) {

			File[] files = fileRoot.listFiles();

			if (files == null) {
				SwingUtilities.invokeLater(() -> {
					((DefaultTreeModel) tree.getModel()).reload();
				});
				return;
			}

			for (File file : files) {
				DefaultMutableTreeNode childNode = new DefaultMutableTreeNode(new FileNode(file));
				node.add(childNode);
				if (file.isDirectory()) {
					createChildren(file, childNode);
				}
			}

			SwingUtilities.invokeLater(() -> {
				((DefaultTreeModel) tree.getModel()).reload();
			});

		}
	}
}