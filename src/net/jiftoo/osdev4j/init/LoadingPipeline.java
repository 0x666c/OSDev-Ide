package net.jiftoo.osdev4j.init;

import java.util.LinkedList;
import java.util.Queue;
import java.util.function.Consumer;

import net.jiftoo.osdev4j.init.LaunchSplashScreen.LTask;

public class LoadingPipeline {
	
	private final Queue<LTask> tasks = new LinkedList<>();
	private LaunchSplashScreen splashScreen;
	
	public LoadingPipeline() {}
	
	// Crutch: can queue task when running, but who cares
	public void queueTask(LTask r) {
		tasks.add(r);
	}
	
	// Blocks
	public void execute() {
		splashScreen = new LaunchSplashScreen(tasks.size());
		splashScreen.show();
		
		new Thread(() -> {
			for (LTask task : tasks) {
				splashScreen.started(task.info());
				task.run();
				splashScreen.finished();
			}
		}).start();
		
		splashScreen.await();
	}
	
	public static void createAsyncLoadTask(LTask task, Consumer<Long> callback) {
		new Thread(() -> {
			final long l1 = System.nanoTime();
			task.run();
			final long l2 = System.nanoTime();
			callback.accept(l2-l1);
		}).start();
	}
}