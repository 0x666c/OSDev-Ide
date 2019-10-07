package net.jiftoo.osdev4j.interpreter;

import java.util.HashMap;

import net.jiftoo.osdev4j.init.LoadingPipeline;
import net.jiftoo.osdev4j.init.LaunchSplashScreen.LTask;
import net.jiftoo.osdev4j.interpreter.jsimpl.JSInterpreter;
import net.jiftoo.osdev4j.interpreter.luaimpl.LuaInterpreter;
import net.jiftoo.osdev4j.interpreter.pyimpl.PythonInterpreter;

public abstract class Interpreter {
		
	private static final HashMap<String, Interpreter> availableInterpreters = new HashMap<>();
	public static Interpreter get(String name) {
		return availableInterpreters.get(name);
	}
	
	public static void init() {
		availableInterpreters.put("lua", new LuaInterpreter());
		availableInterpreters.put("js", new JSInterpreter());
		
		LoadingPipeline.createAsyncLoadTask(new LTask("python", false, () -> availableInterpreters.put("python", new PythonInterpreter())), time -> InterpreterControlsPanel.pyLoaded());
	}
	
	/**
	 * @param code
	 * @return value returned by the script
	 */
	public abstract Object exec(String code);
	public volatile boolean available = true;
	
}