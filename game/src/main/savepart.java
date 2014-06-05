package main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import tools.Globals;

/**
 * parts of the save file
 * @author mattadams
 *
 */
public class SavePart {

	// basic key value pairs
	HashMap<String, String> data;
	
	// more complex key subset pairs
	HashMap<String, SavePart> subset;
	
	/**
	 * load the save part from data from the save file 
	 * @param rawData
	 */
	public SavePart(String rawData) {
		data = new HashMap<String, String>();
		subset = new HashMap<String, SavePart>();
		
		decompile(rawData);
	}
	
	/**
	 * create a new save part
	 */
	public SavePart() {
		data = new HashMap<String, String>();
		subset = new HashMap<String, SavePart>();
	}
	
	/**
	 * split up the raw data into basic key value pairs and subsets
	 * @param rawData
	 */
	public void decompile(String rawData) {
		// break on item delimiters that are not within a subset
		
		if(!rawData.isEmpty()) {
			ArrayList<String> parts = new ArrayList<String>();
			
			int subsetsOpen = 0;
			int pointer = 0;
			String part = "";
			
			// remove the formatting from the file
			rawData = rawData.replace(Globals.newLine, "");
			rawData = rawData.replace(Globals.tab, "");
			
			while(pointer < rawData.length()) {
				String s = rawData.substring(pointer, (pointer + 1));
				
				if(s.equals(Globals.itemDelimiter) && subsetsOpen == 0) {
					parts.add(part);
					part = "";
				} else {
					if(s.equals(Globals.subSetStart)) {
						subsetsOpen++;
					} else if(s.equals(Globals.subSetEnd)) {
						subsetsOpen--;
					}
					
					part = part.concat(s);
				}
				
				pointer++;
			}
			
			// catch the last key value pair as it won't end in a delimiter
			if(part != null && part != "") {
				parts.add(part);
				part = "";
			}
			
			for (Iterator<String> dataStrings = parts.iterator(); dataStrings.hasNext();) {
				String dataString = (String) dataStrings.next();
				
				if(!dataString.isEmpty()) {
					if(dataString.indexOf(Globals.valueDelimiter) != -1) {
						// if there is no value separator (can occur with empty subsets) then skip it
						String key = dataString.substring(0, dataString.indexOf(Globals.valueDelimiter));
						String value = dataString.substring(dataString.indexOf(Globals.valueDelimiter) + Globals.valueDelimiter.length());
						
						//is the value a subset
						if((value.indexOf(Globals.subSetStart) == 0) && (value.lastIndexOf(Globals.subSetEnd) == value.length() - 1)) {
							value = value.substring(1, (value.length() - 1));
							subset.put(key, new SavePart(value));
						} else {
							data.put(key, value);
						}
					}
				}
			}
		}
	}
	
	public String compile() {
		return compile("");
	}
	
	/**
	 * using the current key value pairs in this string construct a new string for the save file
	 * @return
	 */
	public String compile(String tab) {
		
		String compiledString = "";
		
		// compile the basic key value pairs
		for (Map.Entry<String, String> entry : data.entrySet()) {
			if(!compiledString.isEmpty()) {
				compiledString = compiledString.concat(Globals.newLine + tab + Globals.itemDelimiter + Globals.newLine);
	        }
	        
			compiledString = compiledString.concat(tab + entry.getKey() + Globals.valueDelimiter + entry.getValue());
		}
		
		// compile the subsets by calling there compile function
		for (Map.Entry<String, SavePart> entry : subset.entrySet()) {
			//System.out.println("trying to compile a subset [" + entry.getKey() + "]");
			
			if(!compiledString.isEmpty()) {
				compiledString = compiledString.concat(Globals.newLine + tab + Globals.itemDelimiter + Globals.newLine);
	        }
	        
			if(entry.getKey() != null) {
				compiledString = compiledString.concat(tab + entry.getKey() + Globals.valueDelimiter + Globals.subSetStart + Globals.newLine);
				if(entry.getValue() != null) {
					compiledString = compiledString.concat(entry.getValue().compile(tab + Globals.tab));
				}
				compiledString = compiledString.concat(Globals.newLine + tab + Globals.subSetEnd);
			}
		}
		
		return compiledString;
	}

	// basic key value pairs
	public HashMap<String, String> getData() {
		return data;
	}
	
	public String get(String key) {
		return data.get(key);
	}
	
	public String get(String key, String def) {
		return get(key, def, false);
	}
	
	public String get(String key, String def, boolean put) {
		if(exists(key)) {
			return get(key);
		} else {
			if(put) {
				put(key, def);
			}
			
			return def;
		}
	}
	
	public int getInt(String key) {
		return Integer.parseInt(get(key));
	}
	public int getInt(String key, int def) {
		return Integer.parseInt(get(key, String.valueOf(def)));
	}
	
	public boolean getBoolean(String key) {
		return Boolean.parseBoolean(get(key));
	}
	public boolean getBoolean(String key, boolean def) {
		return Boolean.parseBoolean(get(key, String.valueOf(def)));
	}
	
	public Float getFloat(String key) {
		return Float.valueOf(get(key));
	}
	public Float getFloat(String key, float def) {
		return Float.valueOf(get(key, String.valueOf(def)));
	}
	
	public void put(String key, String value) {
		data.put(key, value);
	}
	
	public void put(String key, int value) {
		data.put(key, String.valueOf(value));
	}
	
	public void put(String key, float value) {
		data.put(key, String.valueOf(value));
	}
	
	public void put(String key, boolean value) {
		data.put(key, String.valueOf(value));
	}
	
	public boolean exists(String key) {
		return data.containsKey(key);
	}
	
	
	// subsets
	public HashMap<String, SavePart> getSubsets() {
		return subset;
	}
	
	public SavePart getSubset(String key) {
		return subset.get(key);
	}
	
	public void putSubset(String key, SavePart subset) {
		this.subset.put(key, subset);
	}
	
	public boolean existsSubset(String key) {
		return subset.containsKey(key);
	}
	
	@Override
	public String toString() {
		return compile();
	}

	/**
	 * clear all data from the save part, ready for updating
	 */
	public void clear() {
		data.clear();
		subset.clear();
	}

	/**
	 * return whether the specified subset is empty
	 * @return
	 */
	public boolean emptySubSet(String subsetName) {
		if(subset.containsKey(subsetName)) {
			return true;
		}
		
		return false;
	}
	
	/**
	 * return whether the save part is empty
	 * @return
	 */
	public boolean empty() {
		if(data.isEmpty() && subset.isEmpty()) {
			return true;
		} 
		
		return false;
	}
}
