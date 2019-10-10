package net.jiftoo.osdev4j.init.plimpl;

import net.jiftoo.osdev4j.init.Task;
import net.jiftoo.osdev4j.init.TaskPipeline;

public class LoadingPipeline extends TaskPipeline {
	
	private LaunchSplashScreen splashScreen;
	
	public void execute() {
		splashScreen = new LaunchSplashScreen(tasks.size());
		splashScreen.show();

		new Thread(() -> {
			for (Task task : tasks) {
				splashScreen.started(task.info());
				task.run();
				splashScreen.finished();
			}
		}).start();

		splashScreen.await();
	}
	
}