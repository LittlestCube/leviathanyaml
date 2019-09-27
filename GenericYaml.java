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
	
	public boolean keyExists(String key) {
		boolean ret = false;
		readAllLines();
		if (YamlUtil.getKey(key, en) != -1) {
			ret = true;
		}
		return ret;
	}
	
	public String getKey(String key) {
		try {
			readAllLines();
			return en[1][YamlUtil.getKey(key, en)];
		} catch (Exception e) { e.printStackTrace(); }
		return null;
	}
	
	public void setKey(String key, String value) {
		try {
			readAllLines();
			if (en == null) {
				en = new String[2][1];
				en[0][0] = key;
				en[1][0] = value;
			} else {
				int kline = YamlUtil.getKey(key, en);
				if (kline != -1) {
					en[0][kline] = key;
					en[1][kline] = value;
				} else {
					appendItem(key, value);
				}
			}
			writeAllLines();
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
					if (line == null) {
						break;
					}
					keys.add(line.substring(0, line.lastIndexOf(":")));
					values.add(line.substring(line.lastIndexOf(":") + 2, line.length()));
				}
				if (keys.isEmpty()) {
					return null;
				}
				buffr.close();
				String[] a1 = keys.toArray(new String[keys.size()]);
				String[] a2 = values.toArray(new String[values.size()]);
				String[][] ent = { a1, a2 };
				en = ent;
			}
		} catch (Exception e) { e.printStackTrace(); }
		return en;
	}
	
	public void writeAllLines() {
		clearFile();
		if (en != null) {
			for (int i = 0; i < en[0].length; i++) {
				appendLine(en[0][i], en[1][i]);
			}
		}
	}
	
	public void appendItem(String key, String value) {
		en = YamlUtil.appendItem(key, value, en);
	}
	
	public void delete() {
		ymlfile.delete();
	}
	
	void setValue(String key, String value) {
		try {
			int kline = YamlUtil.getKey(key, en);
			en[0][kline] = key;
			en[1][kline] = value;
		} catch (Exception e) { e.printStackTrace(); }
	}
	
	void clearFile() {
		try {
			fw = new FileWriter(ymlfile);
			buffw = new BufferedWriter(fw);
			buffw.write("");
			buffw.close();
		} catch (Exception e) { e.printStackTrace(); }
	}
	
	void appendLine(String key, String value) {
		try {
			fw = new FileWriter(ymlfile, true);
			buffw = new BufferedWriter(fw);
			String newline = key + ": " + value + "\n";
			buffw.write(newline);
			buffw.close();
			YamlUtil.sortArray(en);
		} catch (Exception e) { e.printStackTrace(); }
	}
}