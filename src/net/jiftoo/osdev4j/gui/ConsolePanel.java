package net.jiftoo.osdev4j.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.SwingUtilities;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.text.AttributeSet;
import javax.swing.text.Document;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;

/**
 * Console and clear buttons
 */
public class ConsolePanel extends JPanel {
	
	private JTextPane outputPane = new JTextPane();
	
	public ConsolePanel() {
		setLayout(new BorderLayout());
		setBorder(new CompoundBorder(new EmptyBorder(0,0,2,2), new LineBorder(Color.BLACK, 1)));
		
		outputPane.setEditable(false);
		outputPane.setFont(new Font("Courier New", Font.PLAIN, 13));
		
		final JScrollPane sc = new JScrollPane(outputPane);
		sc.setBorder(new EmptyBorder(1,1,1,1));
		sc.getVerticalScrollBar().setPreferredSize(new Dimension(12, 0));
		sc.getVerticalScrollBar().setUnitIncrement(4);
		sc.getHorizontalScrollBar().setPreferredSize(new Dimension(0, 12));
		add(sc, BorderLayout.CENTER);
		
		// Hook to stdout and stderr
		
		final PrintStream STDOUT = System.out;
		System.setOut(new PrintStream(new PaneOutputStream(STDOUT)));
		
		final PrintStream STDERR = System.err;
		System.setErr(new PrintStream(new PaneErrorStream(STDERR)));
	}
	
	public void puts(String str) {
		printText(str+"\n", null);
	}
	
	public void puts(String str, Color c) {
		printText(str+"\n", newStyle(null, StyleConstants.Foreground, c));
	}
	
	
	
	private class PaneOutputStream extends OutputStream {
		private final PrintStream STD;
		public PaneOutputStream(PrintStream std) {
			this.STD = std;
		}
		public void write(byte[] buffer, int offset, int length) throws IOException {
			final String text = new String(buffer, offset, length);
			
			printText(text, null);
			STD.write(buffer, offset, length);
		}
		
		@Override
		public void write(int b) throws IOException {
			write(new byte[] { (byte) b }, 0, 1);
		}
	}
	
	
	
	private class PaneErrorStream extends OutputStream {
		private final PrintStream STD;
		public PaneErrorStream(PrintStream std) {
			this.STD = std;
		}
		public void write(byte[] buffer, int offset, int length) throws IOException {
			final String text = new String(buffer, offset, length);
			
			printText(text, ERROR);
			STD.write(buffer, offset, length);
		}
		
		private AttributeSet ERROR;
		{
			ERROR = newStyle(null, StyleConstants.Foreground, new Color(255, 64, 64, 255));
		}
		
		@Override
		public void write(int b) throws IOException {
			write(new byte[] { (byte) b }, 0, 1);
		}
	}
	
	
	private static final AttributeSet newStyle(AttributeSet base, Object a, Object b) {
		StyleContext c = StyleContext.getDefaultStyleContext();
		return c.addAttribute(base==null?c.getEmptySet():base, a, b);
	}
	
	private void printText(final String text, AttributeSet as) {
		SwingUtilities.invokeLater(() -> {
			Document doc = outputPane.getDocument();
			try {
				doc.insertString(doc.getLength(), text, as);
			} catch (Exception e) {
				e.printStackTrace();
				System.exit(-1);
			}
		});
	}
}