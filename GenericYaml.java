package leviathanyaml;

import java.io.*;
import java.util.*;

public class GenericYaml {
	
	public String[][] en;
	
	File ymlfile;
	FileReader fr;
	BufferedReader buffr;
	FileWriter fw;
	BufferedWriter buffw;
	
	public GenericYaml(File f) {
		ymlfile = f;
		if (!ymlfile.exists()) {
			clearFile();
		}
	}
	
	public void initEntry(int len) {
		en = new String[2][len];
	}
	
	public String getKey(String key) {
		try {
			readAllLines();
			return en[1][YamlUtil.getKey(key, en)];
		} catch (Exception e) { e.printStackTrace(); }
		return null;
	}
	
	public void addValue(String key, String value, int index) {
		en[0][index] = key;
		en[1][index] = value;
	}
	
	public String[][] readAllLines() {
		List<String> keys = new ArrayList<String>();
		List<String> values = new ArrayList<String>();
		try {
			if (ymlfile.exists()) {
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
				String[] a1 = keys.toArray(new String[keys.size()]);
				String[] a2 = values.toArray(new String[values.size()]);
				en = new String[a1.length][a2.length];
				System.arraycopy(new String[][] { a1, a2 }, 0, en, 0, en.length);
			}
		} catch (Exception e) { e.printStackTrace(); }
		return en;
	}
	
	public void clearFile() {
		try {
			fw = new FileWriter(ymlfile);
			buffw = new BufferedWriter(fw);
			buffw.write("");
			buffw.close();
		} catch (Exception e) { e.printStackTrace(); }
	}
	
	public void writeAllLines() {
		clearFile();
		if (en != null) {
			for (int i = 0; i < en[0].length; i++) {
				appendLine(en[0][i], en[1][i]);
			}
		}
	}
	
	void appendLine(String key, String value) {
		try {
			fw = new FileWriter(ymlfile, true);
			buffw = new BufferedWriter(fw);
			String newline = key + ": " + value + "\n";
			buffw.write(newline);
			buffw.close();
		} catch (Exception e) { e.printStackTrace(); }
	}
}