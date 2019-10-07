package net.jiftoo.osdev4j.init;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.CountDownLatch;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.LineBorder;

import net.jiftoo.osdev4j.icons.Icons;

/**
 * Shows splash screen until CountDownLatch value is greater than zero
 */
public class LaunchSplashScreen {

	// Indicates whether the splash screen is still active
	private CountDownLatch latch;
	private int taskAmount;
	
	private JFrame splashScreen;
	private JProgressBar progressBar;
	
	public LaunchSplashScreen(int taskCount) {
		taskAmount = taskCount;
		latch = new CountDownLatch(taskCount);
		
		splashScreen = new JFrame();
		JPanel cp = new JPanel();
		cp.setBorder(null);
		cp.setLayout(new BorderLayout());
		splashScreen.setContentPane(cp);
		splashScreen.setAlwaysOnTop(true);
		splashScreen.setSize(350, 200);
		splashScreen.setLocationRelativeTo(null);
		splashScreen.setUndecorated(true);
		
		// Will exit without saving anything
		splashScreen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		progressBar = new JProgressBar(0, taskCount);
		progressBar.setValue(0);
		progressBar.setPreferredSize(new Dimension(0, 25));
		progressBar.setFont(new Font("Dialog", Font.PLAIN, 12));
		progressBar.setBorder(new LineBorder(new Color(0xffe65f32), 3));
		progressBar.setStringPainted(true);
		progressBar.setForeground(new Color(0xffe65f32));
		
		l = new JLabel("", SwingConstants.CENTER);
		l.setBorder(new LineBorder(new Color(0xffe65f32)));
		l.setIconTextGap(0);
		l.setFont(new Font(Font.DIALOG, Font.BOLD, 42));
		splashScreen.add(l, BorderLayout.CENTER);
		
		splashScreen.add(progressBar, BorderLayout.SOUTH);
	}
	
	private final JLabel l;
	
	public void show() {
		try {
			SwingUtilities.invokeAndWait(() -> {
				splashScreen.setVisible(true);
				l.setIcon(new ImageIcon(Icons.logo.getImage().getScaledInstance(l.getWidth(), (int)(splashScreen.getHeight()*1.2), Image.SCALE_SMOOTH)));
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void await() {
		try {
			latch.await();
			
			SwingUtilities.invokeAndWait(() -> {
				splashScreen.setVisible(false);
				splashScreen.dispose();
			});
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1); // Bad
		}
	}
	
	public void started(String info) {
		progressBar.setValue((int) (taskAmount - latch.getCount()));
		progressBar.setString("Loading: " + info);
	}
	
	public void finished() {
		latch.countDown();
	}
	
	public static class LTask {
		private final String info;
		private final Runnable task;
		
		public LTask(String info, boolean awt, Runnable task) {
			this.info = info;
			this.task = awt ? new Runnable() {
				public void run() {
					try {
						SwingUtilities.invokeAndWait(task::run);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			} : task;
		}
		public void run() {task.run();}
		public String info() {return info;}
	}
}