package net.jiftoo.osdev4j.interpreter;

import java.util.HashMap;

import net.jiftoo.osdev4j.interpreter.jsimpl.JSInterpreter;
import net.jiftoo.osdev4j.interpreter.luaimpl.LuaInterpreter;

public abstract class Interpreter {
		
	private static final HashMap<String, Interpreter> availableInterpreters = new HashMap<>();
	public static Interpreter get(String name) {
		return availableInterpreters.get(name);
	}
	
	public static void init() {
		availableInterpreters.put("lua", new LuaInterpreter());
		availableInterpreters.put("js", new JSInterpreter());
	}
	
	/**
	 * @param code
	 * @return value returned by the script
	 */
	public abstract Object exec(String code);
	public volatile boolean available = true;
	
}