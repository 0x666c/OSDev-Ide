package net.jiftoo.osdev4j.icons;

import java.awt.Image;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;

public class Icons {
	
	public static Icon file;
	public static Icon folderOpen, folder;
	public static Icon asm, c, cpp, h;
	public static Icon run, compile, compilerun;
	
	public static ImageIcon logo;
	
	// Everything is hardcoded
	public static final void load() {
		try {
			file = loadIcon("file.png", 16);
			folderOpen = loadIcon("folder_open.png", 16);
			folder = loadIcon("folder.png", 16);
			
			asm = loadIcon("asm.png", 16);
			c = loadIcon("c.png", 16);
			cpp = loadIcon("cpp.png", 14, 16);
			h = loadIcon("h.png", 16);
			
			run = loadIcon("run.png", 16);
			compile = loadIcon("compile.png", 16);
			compilerun = loadIcon("compilerun.png", 16);
			
			logo = loadImage("logo.png");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static final ImageIcon loadIcon(String resource, int dim) throws Exception {
		return new ImageIcon(new ImageIcon(ImageIO.read(Icons.class.getResourceAsStream(resource))).getImage().getScaledInstance(dim, dim, Image.SCALE_SMOOTH));
	}
	
	private static final ImageIcon loadIcon(String resource, int dim1, int dim2) throws Exception {
		return new ImageIcon(new ImageIcon(ImageIO.read(Icons.class.getResourceAsStream(resource))).getImage().getScaledInstance(dim1, dim2, Image.SCALE_SMOOTH));
	}
	
	private static final ImageIcon loadImage(String resource) throws Exception {
		return new ImageIcon(ImageIO.read(Icons.class.getResourceAsStream(resource)));
	}
	
}