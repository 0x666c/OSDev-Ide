package net.jiftoo.osdev4j.control;

/**
 * Singleton used to change the program's behaviour
 */
// TODO: Integrate with other classes
public class Preferences {
	
	// TODO: Need to somehow save/load prefs instead of defaulting each launch
	private static Preferences instance = Preferences.defaults();
	
	public static Preferences get() {
		return instance;
	}
	
	public static void set(Preferences newSettings) {
		instance = newSettings;
	}
	
	private static Preferences defaults() {
		Preferences prefs = new Preferences();
		// p.someSetting = someValue;
		// ...
		return prefs;
	}
	
	/////////////////////////////////////////////////////////////////
	
	private Preferences() {
		// Nothing yet
	}
	
	
	
	
}