package net.jiftoo.osdev4j.gui;

import java.awt.Color;
import java.awt.Font;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.util.zip.ZipFile;

import javax.swing.border.LineBorder;

import org.fife.com.swabunga.spell.engine.SpellDictionaryHashMap;
import org.fife.rsta.ac.c.CCompletionProvider;
import org.fife.ui.autocomplete.AutoCompletion;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.SyntaxConstants;
import org.fife.ui.rsyntaxtextarea.Theme;
import org.fife.ui.rsyntaxtextarea.spell.SpellingParser;
import org.fife.ui.rtextarea.RTextScrollPane;

/**
 * Configured code editor
 * Can be created multiple times
 */
public class JCodeEditor extends RTextScrollPane {
	
	public RSyntaxTextArea editor;
	
	public static JCodeEditor createNew() {
		return new JCodeEditor(new RSyntaxTextArea());
	}
	
	public void focus()
	{
		area.requestFocusInWindow();
	}
	
	private final RSyntaxTextArea area;
	
	private JCodeEditor(RSyntaxTextArea editor) {
		super(editor);
		this.editor = editor;
		
		setBorder(new LineBorder(Color.BLACK, 1));
		editor.setBorder(null);
		area = editor;
		
		editor.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_CPLUSPLUS);
		editor.setCodeFoldingEnabled(true);
		editor.setFont(new Font("Courier New", Font.PLAIN, 14));
		editor.setAntiAliasingEnabled(true);
		editor.setAutoIndentEnabled(true);
		
	////////////// Load theme //////////////
		try {
			// Default theme is Monokai
			Theme eclipse = Theme.load(Theme.class.getResourceAsStream("themes/default-alt.xml"));
			eclipse.apply(editor);
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
		
		// Use threading to reduce loading time without any drawbacks
		// TODO: Add a CountDownLatch in every thread to await component initialization right before setVisible(), show a SpalshScreen whilst doing it
		new Thread(() -> {
			
			////////////// Load autocompletion tokens //////////////
			// TODO: Rewrite 'clang.xml' with c++ and g++ tokens that are available in -ffreestanding mode

			CCompletionProvider ccp = new CCompletionProvider();
			// Activate after any character
			ccp.setAutoActivationRules(true, "");

			AutoCompletion ac = new AutoCompletion(ccp);
			ac.setAutoCompleteEnabled(true);
			ac.setAutoActivationEnabled(true);
			ac.setAutoActivationDelay(250);

			// Show extended description
			ac.setShowDescWindow(false);

			ac.install(editor);
			// Allows to change highlighting color
			// SyntaxScheme scheme = editor.getSyntaxScheme();

		////////////// Load spellchecker //////////////

			try {
				SpellDictionaryHashMap dict;
				boolean american = true;

				// Stoopid hack to write classpath file to
				File f = File.createTempFile("jcode", "zip");
				f.deleteOnExit();
				InputStream is = JCodeEditor.class.getResourceAsStream("english_dic.zip");
				OutputStream os = new FileOutputStream(f);
				
				// Crutch: Only java9+
				if(System.getenv("java.version").startsWith("1")) { // Java 8<
					byte[] buffer = new byte[4096];
					int len;
					while ((len = is.read(buffer)) != -1) {
					    os.write(buffer, 0, len);
					}
				} else {
					Method m = is.getClass().getDeclaredMethod("transferTo", OutputStream.class);
					m.invoke(is, os);
//					is.transferTo(os);
				}
				os.flush();
				os.close();
				is.close();

				try (ZipFile zf = new ZipFile(f);) {

					// Words common to American and British English
					InputStream in = zf.getInputStream(zf.getEntry("eng_com.dic"));
					try (BufferedReader r = new BufferedReader(new InputStreamReader(in))) {
						dict = new SpellDictionaryHashMap(r);
					}

					String[] others;
					if (american) {
						others = new String[] { "color", "labeled", "center", "ize", "yze" };
					} else { // British
						others = new String[] { "colour", "labelled", "centre", "ise", "yse" };
					}

					// Load words specific to the English dialect.
					for (String other : others) {
						in = zf.getInputStream(zf.getEntry(other + ".dic"));
						try (BufferedReader r = new BufferedReader(new InputStreamReader(in))) {
							dict.addDictionary(r);
						}
					}
				}

				SpellingParser sp = new SpellingParser(dict);

				sp.setSquiggleUnderlineColor(Color.RED);
				editor.setParserDelay(1000);
				editor.addParser(sp);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}).start();
	}
	
	public RSyntaxTextArea getEditor() {
		return editor;
	}
	
}