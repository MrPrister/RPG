package game.actor.attribute;

import java.util.HashMap;
import java.util.Map.Entry;

import main.SavePart;

public class AttributeManager {

	private SavePart save;
	private HashMap<Integer, Attribute> attributes;
	
	// load the attributes for the attribute manager
	public AttributeManager(SavePart data) {
		save = data;
		attributes = new HashMap<Integer, Attribute>();
		
		load(data);
	}

	// create a new attribute manager
	public AttributeManager() {
		save = new SavePart();
		attributes = new HashMap<Integer, Attribute>();
	}
	
	private void load(SavePart data) {
		// it's possible that no actors are on the level so check if the save part is empty
		if(data != null && !data.empty()) {
			HashMap<String, SavePart> attributeList = data.getSubsets();
			for (Entry<String, SavePart> attributePair : attributeList.entrySet()) {
				int index = Integer.parseInt(attributePair.getKey());
				SavePart attribute = attributePair.getValue();
				
				add(index, new Attribute(attribute));
			}
		}
	}

	public SavePart save() {
		save.clear();
		
		int i = 0;
		
		for (Entry<Integer, Attribute> attributePair : attributes.entrySet()) {
			Attribute attribute = attributePair.getValue();
			
			save.putSubset(String.valueOf(i), attribute.save());
			i++;
		}
		
		return save;
	}
	
	/**
	 * add an attribute, automatically assign a valid index
	 * @param attribute
	 */
	public void add(Attribute attribute) {
		for (int i = 0; i < attributes.size() + 1; i++) {
			if(!attributes.containsKey(i)) {
				// we've found a valid index
				add(i, attribute);
				return;
			}
		}
	}
	
	public void add(int index, Attribute attribute) {
		attributes.put(index, attribute);
	}
	
	/**
	 * remove the attribute with the given index
	 * @param index
	 */
	public boolean remove(int index) {
		if(attributes.containsKey(index)) {
			attributes.remove(index);
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * remove the attribute with the given name
	 * @param name
	 */
	public boolean remove(String name) {
		int indexToRemove = getIndex(name);
		
		if(indexToRemove != -1) {
			return remove(indexToRemove);
		} else {
			return false;
		}
	}
	
	/**
	 * get the index of the attribute with the given name
	 * @param name
	 * @return
	 */
	public int getIndex(String name) {
		for (Entry<Integer, Attribute> attributePair : attributes.entrySet()) {
			Attribute attribute = attributePair.getValue();
			
			if(attribute.getName().equals(name)) {
				return attributePair.getKey();
			}
		}
		
		return -1;
	}
	
	/**
	 * return the value of the attribute with the given index
	 * @param index
	 * @return
	 */
	public int getValue(int index) {
		if(!attributes.containsKey(index)) {
			return 0;
		}
		
		Attribute attribute = attributes.get(index);
		return attribute.getValue();
	}
	
	/**
	 * return the value of the attribute with the given name
	 * @param name
	 * @return
	 */
	public int getValue(String name) {
		return getValue(getIndex(name));
	}
	
	/**
	 * return the attribute for the given index
	 * @param index
	 * @return
	 */
	public Attribute getAttribute(int index) {
		if(!attributes.containsKey(index)) {
			return null;
		}
		
		Attribute attribute = attributes.get(index);
		return attribute;
	}
	
	public Attribute getAttribute(String name) {
		return getAttribute(getIndex(name));
	}
}
