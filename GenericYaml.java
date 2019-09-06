package leviathanyaml;

import java.io.*;
import java.util.*;

public class GenericYaml {
	
	File ymlfile;
	String[][] entry;
	FileReader fr;
	BufferedReader buffr;
	FileWriter fw;
	BufferedWriter buffw;
	
	public GenericYaml(File f) {
		ymlfile = f;
		readAllLines();
	}
	
	public String getKey(String key) {
		try {
			if (entry != null) {
				for (int i = 0; i < entry[0].length; i++) {
					if (entry[0][i].trim().equals(key.trim())) {
						return entry[1][i];
					}
				}
			}
			throw new Exception("Whoops! Error in function getKey(): String[][] entry is null.");
		} catch (Exception e) {}
		return null;
	}
	
	public String setKey(String key, String value) {
		try {
			if (entry != null) {
				for (int i = 0; i < entry[0].length; i++) {
					if (entry[0][i].trim().equals(key.trim())) {
						entry[1][i] = value;
					}
				}
			}
			throw new Exception("Whoops! Error in function setKey(): String[][] entry is null.");
		} catch (Exception e) {}
		return null;
	}
	
	void appendLine(String key, String value) {
		try {
			fw = new FileWriter(ymlfile, true);
			buffw = new BufferedWriter(fw);
			String newline = key + ": " + value + "\n";
			buffw.write(newline);
			buffw.close();
		} catch (Exception e) { System.out.println(e.toString()); }
	}
	
	public String[][] readAllLines() {
		List<String> keys = new ArrayList<String>();
		List<String> values = new ArrayList<String>();
		try {
			fr = new FileReader(ymlfile);
			buffr = new BufferedReader(fr);
			String line = "";
			while (line != null) {
				line = buffr.readLine();
				keys.add(line.trim().substring(0, line.lastIndexOf(":")));
				values.add(line.trim().substring(line.lastIndexOf(":") + 2, line.length()));
			}
			if (keys.isEmpty()) {
				return null;
			}
			buffr.close();
		} catch (Exception e) { System.out.println(e.toString()); }
		String[] a1 = keys.toArray(new String[keys.size()]);
		String[] a2 = values.toArray(new String[values.size()]);
		entry = new String[a1.length][a2.length];
		System.arraycopy(new String[][] { a1, a2 }, 0, entry, 0, entry.length);
		return entry;
	}
	
	public void clearFile() {
		try {
			fw = new FileWriter(ymlfile);
			buffw = new BufferedWriter(fw);
			buffw.write("");
			buffw.close();
		} catch (Exception e) { System.out.println(e.toString()); }
	}
	
	public void saveEntry(String[][] entry) {
		clearFile();
		if (entry != null) {
			for (int i = 0; i < entry[0].length; i++) {
				appendLine(entry[0][i], entry[1][i]);
			}
		}
	}
}