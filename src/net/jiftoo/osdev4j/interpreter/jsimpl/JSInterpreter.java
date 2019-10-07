package net.jiftoo.osdev4j.interpreter.jsimpl;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.tools.shell.Global;

import net.jiftoo.osdev4j.interpreter.Interpreter;

public class JSInterpreter extends Interpreter {
	
	public JSInterpreter() {}
	
	public Object exec(String code) {
		Context c = Context.enter();
		Global g = new Global(c);
		final Object o = c.evaluateString(g, code, "helloWorld.js", 1, null);
		Context.exit();
		return o;
	}
	
}