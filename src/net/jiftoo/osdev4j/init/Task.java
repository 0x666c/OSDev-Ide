package net.jiftoo.osdev4j.init;

import javax.swing.SwingUtilities;

public class Task {

	private final String info;
	private final Runnable task;

	public Task(String info, boolean awt, Runnable task) {
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

	public void run() {
		task.run();
	}

	public String info() {
		return info;
	}

}