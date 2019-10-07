package net.jiftoo.osdev4j.interpreter.pyimpl;

import org.python.antlr.PythonParser.return_stmt_return;

import net.jiftoo.osdev4j.interpreter.Interpreter;

public class PythonInterpreter extends Interpreter {
	
	private org.python.util.PythonInterpreter py;
	
	public PythonInterpreter() {
		py = new org.python.util.PythonInterpreter();
		System.out.println("Python loaded");
	}
	
	public Object exec(String code) {
		try {
			py.exec(code);
			py.cleanup();
		} catch (Exception e) {
			System.err.println(e);
			return 1;
		}
		return 0;
	}

}