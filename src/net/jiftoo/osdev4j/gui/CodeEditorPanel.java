package net.jiftoo.osdev4j.gui;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import net.jiftoo.osdev4j.OSDev;
import net.jiftoo.osdev4j.gui.CodeEditorPanel.JTabbedPaneCloseButton.CloseButtonTab;

/**
 * Contains the text editor and file tabs
 */
// FIXME: Reduce class structure complexity
public class CodeEditorPanel extends JPanel {
	
	private JTabbedPane tabbedPane = new JTabbedPaneCloseButton();
	
	public void focus()
	{
		try {
			((JCodeEditor)tabbedPane.getComponentAt(0)).focus();
		} catch (Exception e) {/* Can be ignored */}
	}
	
	public CodeEditorPanel() {
		setLayout(new BorderLayout());
		setPreferredSize(new Dimension(500, 500));
		setMinimumSize(new Dimension(100, 100));
		
		tabbedPane.setBorder(new EmptyBorder(0,0,0,2));
		add(tabbedPane, BorderLayout.CENTER);
	}
	
	// File - Index
	
	
	// Tab close callback
	public void closeTab(CloseButtonTab comp) {
		System.out.println("Removed " + comp.getToolTipText());
		
		tc.remove(comp.getToolTipText());
	}
	
	public void saveCurrentTab() {
		int index = tabbedPane.getSelectedIndex();
		if (index == -1) // No tabs present
			return;

		saveTab(index);
	}
	
	public void saveAllTabs() {
		for (int i = 0; i < tabbedPane.getTabCount(); i++) {
			saveTab(i);
		}
	}
	
	public void saveTab(int index) {
		CloseButtonTab t = (CloseButtonTab) tabbedPane.getTabComponentAt(index);

		File f = new File(t.getToolTipText());
		try (FileWriter out = new FileWriter(f)) {
			out.write(((JCodeEditor) t.tab).getEditor().getText());
			out.flush();
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(-1);
		}

		t.setUnsaved(false);
	}
	
	
	public void refreshAllTabs() {
		for (int i = 0; i < tabbedPane.getTabCount(); i++) {
			refreshTab(i);
		}
	}
	
	public void refreshCurrentTab() {
		int index = tabbedPane.getSelectedIndex();
		if(index == -1) // No tabs present
			return;
		
		refreshTab(index);
	}
	
	// TODO: Check file hash, so we don't reload a potentially huge file if nothing has changed
	public void refreshTab(int index) {
		CloseButtonTab t = (CloseButtonTab) tabbedPane.getTabComponentAt(index);

		try {
			String fileContent = new String(Files.readAllBytes(Paths
					.get(t.getToolTipText() /* FIXME: Probably need a separate field for file in CloseButtonTab */)));
			System.out.println(
					"Re-Read " + fileContent.length() + " characters from file " + new File(t.getToolTipText()));
			((JCodeEditor) t.tab).getEditor().setText(fileContent);
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(-1);
		}
	}
	
	public JCodeEditor getCurrentEditor() {
		try {
			JCodeEditor c = (JCodeEditor) tabbedPane.getComponentAt(0);
			return c;
		} catch (Exception e) {
			e.printStackTrace(); // TODO: This is ok?
			return null;
		}
	}
	
	// TODO: Implement
	public void renameCurrentTabFile(String newName) {
		throw new UnsupportedOperationException("Not implemented yet");
	}
	
	/*public void openDirectory() {
		throw new UnsupportedOperationException("Not implemented yet");
	}*/

	// Implies that 'file' is a valid text file and not a directory
	public void openNewTab(String filePath, final String language) {
		final File file = new File(filePath);
		
		System.out.println("Set language as " + language);
		
		// Check if tab is already open
		boolean alreadyOpen = false;
		int selectedIndex = -2;
		for(int i = 0; i < tabbedPane.getTabCount(); i++) {
			if(tabbedPane.getToolTipTextAt(i).equals(filePath)) {
				alreadyOpen = true;
				selectedIndex = i;
				break;
			}
		}
		// if open, then select it and return
		if(alreadyOpen) {
			tabbedPane.setSelectedIndex(selectedIndex);
			return;
		}
		
		JCodeEditor codePane = JCodeEditor.createNew();
		codePane.getEditor().setSyntaxEditingStyle(language);
		
		try {
			// Load text TODO: Separate method
			String fileContent = new String(Files.readAllBytes(Paths.get(filePath)));
			System.out.println("Read " + fileContent.length() + " characters from file " + file.getName());
			codePane.getEditor().setText(fileContent);
			// Add the tab already filled with text
			tabbedPane.addTab(file.getName(), null, codePane, filePath);
			
			// Select the newly opened tab
			tabbedPane.setSelectedIndex(tabbedPane.getTabCount() - 1);
			final int sel = tabbedPane.getTabCount() - 1;
			
			// FIXME: Move to better location, preferably inside the JCodeEditor
			codePane.getEditor().getDocument().addDocumentListener(new DocumentListener() {
				public void removeUpdate(DocumentEvent e) {
				}
				public void insertUpdate(DocumentEvent e) {
				}
				public void changedUpdate(DocumentEvent e) {
					((CloseButtonTab)tabbedPane.getTabComponentAt(sel)).setUnsaved(true);
				}
			});
			
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(-1);
		}
		
		System.out.println();
	}
	
	HashMap<String, CloseButtonTab> tc = new HashMap<>();
	
	class JTabbedPaneCloseButton extends JTabbedPane {

		public JTabbedPaneCloseButton() {
			super();
			// TOP, WRAP_TAB_LAYOUT
		}

		/* Override Addtab in order to add the close Button everytime */
		@Override
		public void addTab(String title, Icon icon, Component component, String tip) {
			super.addTab(title, icon, component, tip);
			int count = this.getTabCount() - 1;
			CloseButtonTab t = new CloseButtonTab(component, title, icon, count);
			setTabComponentAt(count, t);
			
			t.setToolTipText(tip);
			tc.put(tip, t);
		}

		@Override
		public void addTab(String title, Icon icon, Component component) {
			addTab(title, icon, component, null);
		}

		@Override
		public void addTab(String title, Component component) {
			addTab(title, null, component);
		}

		/* addTabNoExit */
		public void addTabNoExit(String title, Icon icon, Component component, String tip) {
			super.addTab(title, icon, component, tip);
		}

		public void addTabNoExit(String title, Icon icon, Component component) {
			addTabNoExit(title, icon, component, null);
		}

		public void addTabNoExit(String title, Component component) {
			addTabNoExit(title, null, component);
		}
		
		
		/* Button */
		class CloseButtonTab extends JPanel {
			protected Component tab;

			protected boolean hoverTab = false;
			protected boolean hoverClose = false;
			
			protected int tabIndex;
			
			protected JLabel jLabel;
			
			// Add or remove asterisk from tab name
			public void setUnsaved(boolean unsaved) {
				if(unsaved && jLabel.getText().charAt(0) != '*') {
					jLabel.setText("*" + jLabel.getText());
				} else if(!unsaved) {
					if(jLabel.getText().charAt(0) == '*') {
						jLabel.setText(jLabel.getText().substring(1, jLabel.getText().length()));
					}
				}
			}

			public CloseButtonTab(final Component tab, String title, Icon icon, int index) {
				this.tabIndex = index;
				this.tab = tab;
				
				setFocusable(false);
				setOpaque(false);

				FlowLayout flowLayout = new FlowLayout(FlowLayout.CENTER, 2, 0);
				setLayout(flowLayout);
				jLabel = new JLabel(title);
				setBackground(Color.GRAY);
				jLabel.setIcon(new ImageIcon(OSDev.class.getResource("/org/fife/rsta/ac/java/img/template_obj.gif")));
				jLabel.setIconTextGap(5);
				add(jLabel);
				JLabel button = new JLabel(" ", new PaintedCrossIcon(), 0);

				// Amazing hack smh
				button.setFont(new Font(Font.DIALOG, Font.PLAIN, 10));
				button.setBorder(new EmptyBorder(0, 8, 1, -4));
				//

				button.addMouseListener(new CloseListener(tab));

				// Fixed listener by making mousePressed explicitly switch to 'this' tab
				addMouseListener(new MouseAdapter() {
					@Override
					public void mouseEntered(MouseEvent e) {
						hoverTab = true;
					}

					@Override
					public void mousePressed(MouseEvent e) {
						setSelectedIndex(tabIndex);
					}

					@Override
					public void mouseExited(MouseEvent e) {
						hoverTab = false;
					}
				});
				
				// Too tired to research if it's right of wrong, so this might be buggy as hell. It works tho, but still, 
				addChangeListener(new ChangeListener() {
					 
					public void stateChanged(ChangeEvent e) {
						tabIndex = indexOfTabComponent(CloseButtonTab.this);
					}
					
				});
				
				add(button);
			}
			
			
			class PaintedCrossIcon implements Icon {
				
				private final int size = 6;
				
				@Override
				public void paintIcon(Component c, Graphics gr, int x, int y) {
					if (!hoverTab && (getSelectedIndex() != tabIndex))
						return;
					
					Graphics2D g = (Graphics2D) gr.create();
					JLabel lbl = (JLabel) c;

					g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
					g.setStroke(new BasicStroke(2.88f));
					g.setColor(Color.BLACK);
					g.drawLine(x, y, x + size, y + size);
					g.drawLine(x + size, y, x, y + size);

					g.setStroke(new BasicStroke(2f));
					g.setColor(hoverClose ? Color.RED : Color.WHITE);
					g.drawLine(x, y, x + size, y + size);
					g.drawLine(x + size, y, x, y + size);

					g.dispose();
				}

				@Override
				public int getIconWidth() {
					return size;
				}

				@Override
				public int getIconHeight() {
					return size;
				}
			}
			
			
			/* ClickListener */
			class CloseListener extends MouseAdapter {
				private Component tab;

				public CloseListener(Component tab) {
					this.tab = tab;
				}

				@Override
				public void mouseEntered(MouseEvent e) {
					hoverClose = true;
				}

				@Override
				public void mouseExited(MouseEvent e) {
					hoverClose = false;
				}

				@Override
				public void mouseClicked(MouseEvent e) {
					if (e.getSource() instanceof JLabel) {
						JLabel clickedButton = (JLabel) e.getSource();
						JTabbedPane tabbedPane = (JTabbedPane) clickedButton.getParent().getParent().getParent();
						
						closeTab(CloseButtonTab.this);
						
						tabbedPane.remove(tab);
					}
				}
			}
		}
	}
}