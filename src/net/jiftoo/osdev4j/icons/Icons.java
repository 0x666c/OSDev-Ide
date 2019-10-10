package net.jiftoo.osdev4j.icons;

import java.awt.Image;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;

public class Icons {
	
	public static Icon file;
	public static Icon folderOpen, folderClosed;
	public static ImageIcon logo;
	
	// Everything is hardcoded
	public static final void load() {
		try {
			file = new ImageIcon(new ImageIcon(ImageIO.read(Icons.class.getResourceAsStream("file.png"))).getImage().getScaledInstance(15, 15, Image.SCALE_SMOOTH));
			folderOpen = new ImageIcon(new ImageIcon(ImageIO.read(Icons.class.getResourceAsStream("folder_open.png"))).getImage().getScaledInstance(16, 16, Image.SCALE_SMOOTH));
			folderClosed = new ImageIcon(new ImageIcon(ImageIO.read(Icons.class.getResourceAsStream("folder_closed.png"))).getImage().getScaledInstance(16, 16, Image.SCALE_SMOOTH));
			logo = new ImageIcon(ImageIO.read(Icons.class.getResourceAsStream("logo.png")));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}