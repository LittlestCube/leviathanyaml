package leviathanyaml;

import java.io.*;
import java.util.*;

public class GenericYaml {
	
	public String[][] entry;
	
	File ymlfile;
	FileReader fr;
	BufferedReader buffr;
	FileWriter fw;
	BufferedWriter buffw;
	
	public GenericYaml(File f) {
		ymlfile = f;
		if (!ymlfile.exists()) {
			clearFile();
		} else {
			entry = readAllLines();
		}
	}
	
	public String getKey(String key) {
		try {
			readAllLines();
			return entry[1][YamlUtil.getKey(key, entry)];
		} catch (Exception e) { e.printStackTrace(); }
		return null;
	}
	
	public void setKey(String key, String value) {
		try {
			int kline = YamlUtil.getKey(key, entry);
			System.out.println(String.valueOf(kline));
			if (kline == -1) {
				appendItem(key, value);
			} else {
				entry[0][kline] = key;
				entry[1][kline] = value;
			}
		} catch (Exception e) { e.printStackTrace(); }
	}
	
	public void appendItem(String key, String value) {
		List<String> keys = new ArrayList<String>(Arrays.asList(entry[0]));
		List<String> values = new ArrayList<String>(Arrays.asList(entry[1]));
		keys.add(key);
		values.add(value);
		entry[0] = keys.toArray(new String[keys.size()]);
		entry[1] = values.toArray(new String[values.size()]);
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
				entry = new String[a1.length][a2.length];
				System.arraycopy(new String[][] { a1, a2 }, 0, entry, 0, entry.length);
			} else {
				entry = new String[2][0];
			}	
		} catch (Exception e) { e.printStackTrace(); }
		return entry;
	}
	
	public void clearFile() {
		try {
			fw = new FileWriter(ymlfile);
			buffw = new BufferedWriter(fw);
			buffw.write("");
			buffw.close();
		} catch (Exception e) { e.printStackTrace(); }
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