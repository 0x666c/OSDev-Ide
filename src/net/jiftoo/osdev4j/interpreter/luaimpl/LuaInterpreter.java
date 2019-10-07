package net.jiftoo.osdev4j.interpreter.luaimpl;

import org.luaj.vm2.Globals;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.compiler.LuaC;
import org.luaj.vm2.lib.Bit32Lib;
import org.luaj.vm2.lib.PackageLib;
import org.luaj.vm2.lib.StringLib;
import org.luaj.vm2.lib.jse.JseBaseLib;
import org.luaj.vm2.lib.jse.JseMathLib;

import net.jiftoo.osdev4j.interpreter.Interpreter;

public class LuaInterpreter extends Interpreter {
	
	private static Globals globals;
	
	public LuaInterpreter() {
		globals = new Globals();
		LuaC.install(globals);
		
		globals.load(new JseBaseLib());
		globals.load(new PackageLib());
		globals.load(new Bit32Lib());
		globals.load(new StringLib());
		globals.load(new JseMathLib());
		
		globals.load(new IdeTable());
	}
	
	public Object exec(String code) {
		try {
			LuaValue chunk = globals.load(code);
			return chunk.call();
		} catch (Exception e) {
			return e.toString();
		}
	}
}