package leviathanyaml;

import java.io.*;
import java.util.*;
import leviathanyaml.YamlUtil;

public class EntryYaml {
	
	static File ymlfile;
	static FileReader fr;
	static BufferedReader buffr;
	static FileWriter fw;
	static BufferedWriter buffw;
	
	public EntryYaml(File f) {
		try {
			ymlfile = f;
		} catch (Exception e) { System.out.println(e.toString()); }
	}
	
	public String[][] readEntry(int entryNo) {
		String line = "";
		List<String> keys = new ArrayList<String>();
		List<String> values = new ArrayList<String>();
		try {
			fr = new FileReader(ymlfile);
			buffr = new BufferedReader(fr);
			while (true) {
				String lalalalala = buffr.readLine();
				if (lalalalala != null) {
					line = lalalalala;
				} else {
					break;
				}
				if (line.trim().startsWith(Integer.toString(entryNo) + ":")) {
					line = buffr.readLine();
					for (line = line; !line.trim().endsWith(":"); line = buffr.readLine()) {
						if (!line.trim().startsWith("#")) {
							keys.add(line.substring(0, line.lastIndexOf(":")));
							values.add(line.substring(line.lastIndexOf(":") + 2, line.length()));
						}
					}
				}
			}
			if (keys.isEmpty()) {
				return null;
			}
		} catch (Exception e) { System.err.println("Whoops! Error in function readEntry(int entryNo): " + e.toString()); }
		String[] a1 = keys.toArray(new String[keys.size()]);
		String[] a2 = values.toArray(new String[values.size()]);
		String[][] ret = { a1, a2 };
		return ret;
	}
	
	public String[][][] readAllEntries() {
		String[][] arra = { new String[0], new String[0] };
		List<String[][]> entries = new ArrayList<String[][]>();
		for (int entryNo = 0; arra != null; entryNo++) {
			arra = readEntry(entryNo);
			if (arra == null) {
				break;
			}
			YamlUtil.ensureSize(entries, entryNo);
			entries.add(entryNo, arra);
		}
		String[][][] arr = entries.toArray(new String[entries.size()][][]);
		return arr;
	}
	
	public void writeEntry(int entryNo, String[][] entry) {
		String line = "";
		String File = "";
		String readfile = "";
		try {
			fr = new FileReader(ymlfile);
			buffr = new BufferedReader(fr);
			line = buffr.readLine();
			while (line != null) {
				readfile += line + "\n";
				line = buffr.readLine();
			}
			String[] readfilea = readfile.split(System.getProperty("line.separator"));
			for (int i = 0; i < readfilea.length; i++) {
				if (readfilea[i].trim().startsWith(Integer.toString(entryNo) + ":")) {
					i++;
					while (!readfilea[i].trim().endsWith(":")) {
						if (!readfilea[i].trim().startsWith("#")) {
							String currkey = readfilea[i].substring(0, readfilea[i].lastIndexOf(":"));
							readfilea[i] = currkey + ": " + entry[1][YamlUtil.getKey(currkey, entry)];
						}
						i++;
					}
				}
			}
			for (int i = 0; i < readfilea.length; i++) {
				File += readfilea[i] + "\n";
			}
			fw = new FileWriter(ymlfile);
			buffw = new BufferedWriter(fw);
			buffw.write(File, 0, File.length());
			buffw.close();
		} catch (Exception safsdfsdfasdfasdfasdfasdfasdfasdf) { System.err.println("Whoops! Error in function writeEntry(): " + safsdfsdfasdfasdfasdfasdfasdfasdf.toString()); }
	}
	
	public void writeMultipleEntries(int[] entryNo, String[][][] entry) {
		for (int i = 0; i < entryNo.length; i++) {
			writeEntry(entryNo[i], entry[entryNo[i]]);
		}
	}
	
	public void writeAllEntries(String[][][] entry) {
		String line = "";
		String File = "";
		String readfile = "";
		int entryNo = 0;
		try {
			fr = new FileReader(ymlfile);
			buffr = new BufferedReader(fr);
			line = buffr.readLine();
			while (line != null) {
				readfile += line + "\n";
				line = buffr.readLine();
			}
			String[] readfilea = readfile.split(System.getProperty("line.separator"));
			for (int i = 0; i < readfilea.length; i = i) {
				if (readfilea[i].trim().endsWith(":")) {
					entryNo = Integer.parseInt(readfilea[i].trim().substring(0, readfilea[i].lastIndexOf(":")));
					if (i + 1 < readfilea.length) {
						i++;
					} else {
						break;
					}
					while (!readfilea[i].trim().endsWith(":")) {
						if (!readfilea[i].trim().startsWith("#")) {
							String currkey = readfilea[i].substring(0, readfilea[i].lastIndexOf(":"));
							readfilea[i] = currkey + ": " + entry[entryNo][1][YamlUtil.getKey(currkey, entry)];
						}
						if (i + 1 < readfilea.length) {
							i++;
						} else {
							break;
						}
					}
				} else {
					if (i + 1 < readfilea.length) {
						i++;
					} else {
						break;
					}
				}
			}
			for (int i = 0; i < readfilea.length; i++) {
				File += readfilea[i] + "\n";
			}
			fw = new FileWriter(ymlfile);
			buffw = new BufferedWriter(fw);
			buffw.write(File, 0, File.length());
			buffw.close();
		} catch (Exception safsdfsdfasdfasdfasdfasdfasdfasdf) { System.err.println("Whoops! Error in function writeAllEntries(): " + safsdfsdfasdfasdfasdfasdfasdfasdf.toString()); }
	}
	
	public void writeNewEntryYaml(String[][][] entries) {
		try {
			String File = "";
			
			for (int i = 0; i < entries.length; i++) {
				File += i + ":\n";
				for (int j = 0; j < entries[i][0].length; j++) {
					File += "  " + entries[i][0][j] + ": " + entries[i][1][j] + "\n"; 
				}
			}
			fw = new FileWriter(ymlfile);
			buffw = new BufferedWriter(fw);
			buffw.write(File, 0, File.length());
			buffw.close();
		} catch (Exception e) { System.err.println("Whoops! Error in function writeNewEntryYaml(): " + e.toString()); }
	}
}