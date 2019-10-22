package com.sullivan.editor;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class EditorModel {
	public void writeToFile(File file, String text) {
		try {
			Files.write(file.toPath(), text.getBytes());
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public String readFromFile(File file) {
		String content;
		try {
			content = new String(Files.readAllBytes(file.toPath()));
		} catch(IOException e) {
			e.printStackTrace();
			content = "";
		}
		return content;
	}
}
