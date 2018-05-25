package leviathanyaml;

import java.io.*;
import java.util.*;

public class GenericYaml {
	
	static File ymlfile;
	static FileReader fr;
	static BufferedReader buffr;
	static FileWriter fw;
	static BufferedWriter buffw;
	
	public GenericYaml(File f) {
		ymlfile = f;
	}
	
	void appendLine(String key, String value) {
		try {
			fw = new FileWriter(ymlfile);
			buffw = new BufferedWriter(fw);
			String newline = key + ": " + value + "\n";
			buffw.append(newline);
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
		} catch (Exception e) { System.out.println(e.toString()); }
		String[] a1 = keys.toArray(new String[keys.size()]);
		String[] a2 = values.toArray(new String[values.size()]);
		String[][] ret = { a1, a2 };
		return ret;
	}
	
	public void clearFile() {
		try {
			fw = new FileWriter(ymlfile);
			buffw = new BufferedWriter(fw);
			buffw.write("");
			buffw.close();
		} catch (Exception e) { System.out.println(e.toString()); }
	}
	
	public void writeAllLines(String[][] entry) {
		clearFile();
		if (entry != null) {
			for (int i = 0; i < entry[0].length; i++) {
				appendLine(entry[0][i], entry[1][i]);
			}
		}
	}
}