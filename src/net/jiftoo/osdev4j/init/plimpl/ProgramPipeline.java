package net.jiftoo.osdev4j.init.plimpl;

import java.util.function.Consumer;

import net.jiftoo.osdev4j.init.Task;
import net.jiftoo.osdev4j.init.TaskPipeline;

// Continuously executes new tasks
public class ProgramPipeline extends TaskPipeline {
	
	public void execute() {
		// Does nothing in this implementation
		return;
	}
	
	public static void createAsyncLoadTask(Task task, Consumer<Long> timeCallback) {
		new Thread(() -> {
			final long l1 = System.nanoTime();
			task.run();
			final long l2 = System.nanoTime();
			timeCallback.accept(l2-l1);
		}).start();
	}
	
}