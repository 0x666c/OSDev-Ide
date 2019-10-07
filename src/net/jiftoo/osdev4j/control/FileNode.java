package net.jiftoo.osdev4j.control;

import java.io.File;

public final class FileNode {

	private final File f;

	public FileNode(final File f) {
		this.f = f;
	}

	public String toString() {
		final String n = f.getName();
		if ("".equals(n))
			return f.getAbsolutePath();
		return n;
	}

	public File getFile() {
		return f;
	}

}