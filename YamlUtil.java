package leviathanyaml;

import java.util.*;

public abstract class YamlUtil {
	
	public static int getKey(String key, String[][] entry) {
		try {
			if (entry != null) {
				for (int i = 0; i < entry[0].length; i++) {
					if (entry[0][i].equals(key)) {
						return i;
					}
				}
			}
			throw new Exception("Whoops! Error in function getKey(): String[][] entry is null.");
		} catch (Exception e) {}
		return -1;
	}
	
	public static int getKey(String key, String[][][] entry) {
		try {
			if (entry != null) {
				for (int i = 0; i < entry[0][0].length; i++) {
					if (entry[0][0][i].equals(key)) {
						return i;
					}
				}
			}
			throw new Exception("Whoops! Error in function getKey(): String[][][] entry is null.");
		} catch (Exception e) {}
		return -1;
	}
	
	public static void ensureSize(List<String[][]> arr, int size) {
		while (arr.size() < size) {
			arr.add(null);
		}
	}
	
	public static int[] convertIntegers(List<Integer> integers) {
		int[] ret = new int[integers.size()];
		Iterator<Integer> iterator = integers.iterator();
		for (int i = 0; i < ret.length; i++) {
			ret[i] = iterator.next().intValue();
		}
		return ret;
	}
	
	public static String[] convertEntryToLines(String[][] entry) {
		List<String> arr = new ArrayList<String>();
		for (int i = 0; i < entry[0].length; i++) {
			arr.add(entry[0][i] + ": " + entry[1][i]);
		}
		return arr.toArray(new String[arr.size()]);
	}
	
	public static String[][] convertLinesToEntry(String[] lines) {
		List<String> keys = new ArrayList<String>();
		List<String> values = new ArrayList<String>();
		for (int i = 0; i < lines.length; i++) {
			keys.add(lines[i].trim().substring(0, lines[i].lastIndexOf(":")));
			values.add(lines[i].trim().substring(lines[i].lastIndexOf(":") + 2, lines[i].length()));
		}
		if (keys.isEmpty()) {
			return null;
		}
		String[] a1 = keys.toArray(new String[keys.size()]);
		String[] a2 = values.toArray(new String[values.size()]);
		return new String[][] { a1, a2 };
	}
	
	public static String[][] sortArray(String[][] entry) {
		int j;
		boolean flag = true;
		String temp;
		String[] lines = convertEntryToLines(entry);
		
		while (flag) {
			flag = false;
			for (j = 0; j < lines.length - 1; j++) {
				if (lines[j].compareToIgnoreCase(lines[j + 1]) > 0) {
					temp = lines[j];
					lines[j] = lines[j + 1];
					lines[j + 1] = temp;
					flag = true;
				}
			}
		}
		entry = convertLinesToEntry(lines);
		return entry;
	}
	
	public static String[][][] sortArray(String[][][] entries) {
		int j;
		boolean flag = true;
		String temp;
		String[] lines;
		for (int i = 0; i < entries.length; i++) {
			lines = convertEntryToLines(entries[i]);
			
			while (flag) {
				flag = false;
				for (j = 0; j < lines.length - 1; j++) {
					if (lines[j].compareToIgnoreCase(lines[j + 1]) > 0) {
						temp = lines[j];
						lines[j] = lines[j + 1];
						lines[j + 1] = temp;
						flag = true;
					}
				}
			}
			entries[i] = convertLinesToEntry(lines);
		}
		return entries;
	}
	
	public static String[][] removeItem(String[][] entry, int removeNo) {
		if (entry == null || entry[0].length == 1) {
			try {
				throw new Exception("Whoops! Error in function removeItem(): String[][] entry only has one item, or is null.");
			} catch (Exception e) {}
		} else {
			String[][] newentry;
			newentry = new String[2][entry.length - 1];
			for (int i = 0; i < entry.length - 1; i++) {
				if (i == removeNo) {
					i++;
				}
				newentry[0][i] = entry[0][i];
				newentry[1][i] = entry[1][i];
			}
			return newentry;
		}
		return entry;
	}
	
	public static String[][] appendItem(String key, String value, String[][] entry) {
		List<String> keys = new ArrayList<String>(Arrays.asList(entry[0]));
		List<String> values = new ArrayList<String>(Arrays.asList(entry[1]));
		keys.add(key);
		values.add(value);
		String[] a1 = keys.toArray(new String[keys.size()]);
		String[] a2 = values.toArray(new String[values.size()]);
		return new String[][]{ a1, a2 };
	}
	
	public static String[][] _appendItem(String[][] entry) {
		String[][] newentry;
		if (entry == null) {
			newentry = new String[][] { new String[] { "new" } , new String[] { "item" } };
			return newentry;
		} else {
			newentry = new String[2][entry[0].length + 1];
			for (int i = 0; i < entry[0].length; i++) {
				newentry[0][i] = entry[0][i];
				newentry[1][i] = entry[1][i];
			}
			try {
				newentry[0][newentry[0].length - 1] = "new";
				newentry[1][newentry[0].length - 1] = "item";
			} catch (Exception e) { System.out.println("oops" + e.toString()); }
		}
		return newentry;
	}
}