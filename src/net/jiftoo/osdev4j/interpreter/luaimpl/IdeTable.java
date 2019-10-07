package net.jiftoo.osdev4j.interpreter.luaimpl;

import java.util.Calendar;

import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.TwoArgFunction;
import org.luaj.vm2.lib.ZeroArgFunction;

public class IdeTable extends TwoArgFunction {
	
	public LuaValue call(LuaValue arg1, LuaValue env) {
		LuaTable table = new LuaTable(0, 1);
		
		table.set("time", new time());
		
		env.set("dev", table);
		env.get("package").get("loaded").set("dev", table);
		return table;
	}
	
	private static final class time extends ZeroArgFunction {
		public LuaValue call() {
			final Calendar c = Calendar.getInstance();
			final long nano = System.nanoTime();
			return LuaValue.tableOf(new LuaValue[] {
					// Epoch
					LuaValue.valueOf("enano"), LuaValue.valueOf(nano),
					LuaValue.valueOf("emicro"), LuaValue.valueOf(nano/1000),
					LuaValue.valueOf("emilli"), LuaValue.valueOf((long)(nano/1e+6)),
					// Day
					LuaValue.valueOf("sec"), LuaValue.valueOf(c.get(Calendar.SECOND)),
					LuaValue.valueOf("min"), LuaValue.valueOf(c.get(Calendar.MINUTE)),
					LuaValue.valueOf("hour"), LuaValue.valueOf(c.get(Calendar.HOUR_OF_DAY)),
					LuaValue.valueOf("day"), LuaValue.valueOf(c.get(Calendar.DAY_OF_MONTH)),
					LuaValue.valueOf("week"), LuaValue.valueOf(c.get(Calendar.WEEK_OF_YEAR)),
					LuaValue.valueOf("weekm"), LuaValue.valueOf(c.get(Calendar.WEEK_OF_MONTH)),
					LuaValue.valueOf("month"), LuaValue.valueOf(c.get(Calendar.MONTH)),
					LuaValue.valueOf("year"), LuaValue.valueOf(c.get(Calendar.YEAR)),
					LuaValue.valueOf("era"), LuaValue.valueOf(c.get(Calendar.ERA))});
		}
	}
	
}