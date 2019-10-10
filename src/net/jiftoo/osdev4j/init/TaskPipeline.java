package net.jiftoo.osdev4j.init;

import java.util.LinkedList;
import java.util.Queue;

import net.jiftoo.osdev4j.init.plimpl.LoadingPipeline;
import net.jiftoo.osdev4j.init.plimpl.ProgramPipeline;

public abstract class TaskPipeline {
	
	private static LoadingPipeline lPipeline;
	private static ProgramPipeline pPipeline;
	
	public static LoadingPipeline loading() {
		if (lPipeline == null) {
			lPipeline = new LoadingPipeline();
		}
		return lPipeline;
	}
	
	public static ProgramPipeline app() {
		if (pPipeline == null) {
			pPipeline = new ProgramPipeline();
		}
		return pPipeline;
	}
	
	/////////////////////////////
	
	protected final Queue<Task> tasks = new LinkedList<>();
	
	// Crutch: can queue task when already running, but who cares
	public void queueTask(Task r) {
		tasks.add(r);
	}
	
	// Blocks
	public abstract void execute();
	
}