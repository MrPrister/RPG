package game.actor.attribute;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import main.SavePart;

public class Attribute {

	private SavePart save;
	
	// min and max base values
	private int maxValue;
	private int minValue;
	
	private String name;
	private int baseValue;					// the value of the attribute without any bonuses or multipliers
	
	// need new classes for timed
	private ArrayList<Integer> bonus;		// number
	private ArrayList<Integer> multiplier;	// percentage
	
	// effects the min and max values
	private ArrayList<Integer> maxBonus;		// number
	private ArrayList<Integer> maxMultiplier;	// percentage
	private ArrayList<Integer> minBonus;		// number
	private ArrayList<Integer> minMultiplier;	// percentage
	
	public Attribute(SavePart data) {
		save = data;
		
		name = data.get("name");
		maxValue = Integer.parseInt(data.get("maxValue"));
		minValue = Integer.parseInt(data.get("minValue"));
		baseValue = Integer.parseInt(data.get("baseValue"));
		
		bonus = new ArrayList<Integer>();
		multiplier = new ArrayList<Integer>();
		
		SavePart bonusData = data.getSubset("bonus");
		bonus = loadIntArrayList(bonusData);
		
		SavePart multiplierData = data.getSubset("multiplier");
		multiplier = loadIntArrayList(multiplierData);
		
		SavePart minBonusData = data.getSubset("minBonus");
		minBonus = loadIntArrayList(minBonusData);
		
		SavePart minMultiplierData = data.getSubset("minMultiplier");
		minMultiplier = loadIntArrayList(minMultiplierData);
		
		SavePart maxBonusData = data.getSubset("maxBonus");
		maxBonus = loadIntArrayList(maxBonusData);
		
		SavePart maxMultiplierData = data.getSubset("maxMultiplier");
		maxMultiplier = loadIntArrayList(maxMultiplierData);
		
	}
	
	private ArrayList<Integer> loadIntArrayList(SavePart subset) {
		ArrayList<Integer> array = new ArrayList<Integer>();
		
		HashMap<String, String> hashmap = subset.getData();
		
		for (Entry<String, String> entry : hashmap.entrySet()) {
			String value = entry.getValue();
			array.add(Integer.parseInt(value));
		}
		
		return array;
	}
	
	public Attribute(String name, int maxValue, int minValue, int baseValue) {
		save = new SavePart();
		
		this.name = name;
		this.maxValue = maxValue;
		this.minValue = minValue;
		this.baseValue = baseValue;
		
		bonus = new ArrayList<Integer>();
		multiplier = new ArrayList<Integer>();
		
		minBonus = new ArrayList<Integer>();
		minMultiplier = new ArrayList<Integer>();
		
		maxBonus = new ArrayList<Integer>();
		maxMultiplier = new ArrayList<Integer>();
		
	}
	
	public SavePart save() {
		save.clear();
		
		save.put("name", name);
		save.put("maxValue", maxValue);
		save.put("minValue", minValue);
		save.put("baseValue", baseValue);
		
		save.putSubset("bonus", intArrayListToSavePart(bonus));
		save.putSubset("multiplier", intArrayListToSavePart(multiplier));
		save.putSubset("minBonus", intArrayListToSavePart(minBonus));
		save.putSubset("minMultiplier", intArrayListToSavePart(minMultiplier));
		save.putSubset("maxBonus", intArrayListToSavePart(maxBonus));
		save.putSubset("maxMultiplier", intArrayListToSavePart(maxMultiplier));
		
		return save;
	}
	
	/**
	 * turn an array list containing integers into a save part 
	 * @param list
	 * @return
	 */
	private SavePart intArrayListToSavePart(ArrayList<Integer> list) {
		SavePart savePart = new SavePart();
		
		int i = 0;
		
		if(list != null) {
			for (Iterator<Integer> iterator = list.iterator(); iterator.hasNext();) {
				int value = (int) iterator.next();
				savePart.put(String.valueOf(i), value);
				i++;
			}
		}
		return savePart;
	}
	
	/**
	 * return the name of this attribute
	 * @return
	 */
	public String getName() {
		return name;
	}
	/**
	 * get the value of the max limit without bonuses
	 * @return
	 */
	public int getRawMax() {
		return maxValue;
	}
	/**
	 * set the value of the max limit
	 * @param max
	 */
	public void setMax(int max) {
		maxValue = max;
	}
	/**
	 * get the value of the min limit without bonuses
	 * @return
	 */
	public int getRawMin() {
		return minValue;
	}
	/**
	 * set the value of the min limit
	 * @param min
	 */
	public void setMin(int min) {
		minValue = min;
	}
	
	/**
	 * get the value of the max limit
	 * @return
	 */
	public int getMax(boolean bonusesFirst) {
		int value = maxValue;
		int totalBonus = getMaxBonus();
		int totalMultiplier = getMaxMultiplier();
		
		if(bonusesFirst) {
			value += totalBonus;
			if(totalMultiplier != 0) {
				value *= totalMultiplier;
			}
		} else {
			if(totalMultiplier != 0) {
				value *= totalMultiplier;
			}
			value += totalBonus;
		}
		
		return value;
	}
	/**
	 * get the value of the min limit
	 * @return
	 */
	public int getMin(boolean bonusesFirst) {
		int value = minValue;
		int totalBonus = getMinBonus();
		int totalMultiplier = getMinMultiplier();
		
		if(bonusesFirst) {
			value += totalBonus;
			if(totalMultiplier != 0) {
				value *= totalMultiplier;
			}
		} else {
			if(totalMultiplier != 0) {
				value *= totalMultiplier;
			}
			value += totalBonus;
		}
		
		return value;
	}
	
	/**
	 * add a bonus to this attribute
	 * @param bonus
	 */
	public void addBonus(int bonus) {
		this.bonus.add(bonus);
	}
	/**
	 * add a multiplier to this attribute
	 * @param multiplier
	 */
	public void addMultiplier(int multiplier) {
		this.multiplier.add(multiplier);
	}
	/**
	 * remove a bonus from this attribute
	 * @param bonus
	 */
	public void removeBonus(int bonus) {
		this.bonus.remove(new Integer(bonus));
	}
	/**
	 * remove a multiplier from this attribute
	 * @param multiplier
	 */
	public void removeMultiplier(int multiplier) {
		this.multiplier.remove(new Integer(multiplier));
	}
	/**
	 * add a bonus to this attribute
	 * @param bonus
	 */
	public void addMaxBonus(int bonus) {
		this.maxBonus.add(bonus);
	}
	/**
	 * add a multiplier to this attribute
	 * @param multiplier
	 */
	public void addMaxMultiplier(int multiplier) {
		this.maxMultiplier.add(multiplier);
	}
	/**
	 * remove a bonus from this attribute
	 * @param bonus
	 */
	public void removeMaxBonus(int bonus) {
		this.maxBonus.remove(new Integer(bonus));
	}
	/**
	 * remove a multiplier from this attribute
	 * @param multiplier
	 */
	public void removeMaxMultiplier(int multiplier) {
		this.maxMultiplier.remove(new Integer(multiplier));
	}
	
	/**
	 * add a bonus to this attribute
	 * @param bonus
	 */
	public void addMinBonus(int bonus) {
		this.minBonus.add(bonus);
	}
	/**
	 * add a multiplier to this attribute
	 * @param multiplier
	 */
	public void addMinMultiplier(int multiplier) {
		this.minMultiplier.add(multiplier);
	}
	/**
	 * remove a bonus from this attribute
	 * @param bonus
	 */
	public void removeMinBonus(int bonus) {
		this.minBonus.remove(new Integer(bonus));
	}
	/**
	 * remove a multiplier from this attribute
	 * @param multiplier
	 */
	public void removeMinMultiplier(int multiplier) {
		this.minMultiplier.remove(new Integer(multiplier));
	}
	
	/**
	 * add to the base value
	 * @param value
	 */
	public void addToBase(int value) {
		int newBase = baseValue + value;
		int minValue = getMin(true);
		int maxValue = getMax(true);
		
		if(newBase < minValue) {
			this.baseValue = minValue;
			return;
		}
		if(newBase > maxValue) {
			this.baseValue = maxValue;
			return;
		}
		
		this.baseValue = newBase;
	}

	/**
	 * get the value of the base - without bonuses or multipliers
	 * @return
	 */
	public int getBaseValue() {
		return baseValue;
	}
	
	/**
	 * set the value of the base
	 * @param value
	 */
	public void setBase(int value) {
		setBase(value, true);
	}
	
	/**
	 * set the value of the base with consideration of the max and min limits
	 * @param value
	 * @param limit
	 */
	public void setBase(int value, boolean limit) {
		this.baseValue = value;
		
		if(limit) {
			if(baseValue > getMax(true)) {
				baseValue = getMax(true);
			}
			if(baseValue < getMin(true)) {
				baseValue = getMin(true);
			}
		}
	}
	
	/**
	 * get the total of the bonuses on this attribute
	 * @return
	 */
	public int getBonus() {
		int toReturn = 0;
		
		for (Iterator<Integer> iterator = bonus.iterator(); iterator.hasNext();) {
			int value = (int) iterator.next();
			
			toReturn += value;
		}
		
		return toReturn;
	}
	
	/**
	 * get the total of the multipliers on this attribute
	 * @return
	 */
	public int getMultiplier() {
		int toReturn = 0;
		
		for (Iterator<Integer> iterator = multiplier.iterator(); iterator.hasNext();) {
			int value = (int) iterator.next();
			
			toReturn *= value;
		}
		
		return toReturn;
	}
	
	/**
	 * get the total of the max limit bonuses on this attribute
	 * @return
	 */
	public int getMaxBonus() {
		int toReturn = 0;
		
		for (Iterator<Integer> iterator = maxBonus.iterator(); iterator.hasNext();) {
			int value = (int) iterator.next();
			
			toReturn += value;
		}
		
		return toReturn;
	}
	
	/**
	 * get the total of the max multipliers on this attribute
	 * @return
	 */
	public int getMaxMultiplier() {
		int toReturn = 0;
		
		for (Iterator<Integer> iterator = maxMultiplier.iterator(); iterator.hasNext();) {
			int value = (int) iterator.next();
			
			toReturn *= value;
		}
		
		return toReturn;
	}
	
	/**
	 * get the total of the min limit bonuses on this attribute
	 * @return
	 */
	public int getMinBonus() {
		int toReturn = 0;
		
		for (Iterator<Integer> iterator = minBonus.iterator(); iterator.hasNext();) {
			int value = (int) iterator.next();
			
			toReturn += value;
		}
		
		return toReturn;
	}
	
	/**
	 * get the total of the min limit multipliers on this attribute
	 * @return
	 */
	public int getMinMultiplier() {
		int toReturn = 0;
		
		for (Iterator<Integer> iterator = minMultiplier.iterator(); iterator.hasNext();) {
			int value = (int) iterator.next();
			
			toReturn *= value;
		}
		
		return toReturn;
	}
	
	/**
	 * return the total of the attribute with multipliers and bonuses added
	 * @param multipliersFirst calculate multipliers first
	 * @return
	 */
	public int getValue(boolean bonusesFirst) {
		int value = getBaseValue();
		int minValue = getMin(true);
		int maxValue = getMax(true);
		
		int totalBonus = getBonus();
		int totalMultiplier = getMultiplier(); 
		
		if(bonusesFirst) {
			value += totalBonus;
			if(totalMultiplier != 0) {
				value *= totalMultiplier;
			}
		} else {
			if(totalMultiplier != 0) {
				value *= totalMultiplier;
			}
			value += totalBonus;
		}
		
		if(value < minValue) {
			return minValue;
		}
		if(value > maxValue) {
			return maxValue;
		}
		
		return value;
	}
	
	/**
	 * return the total of the attribute with multipliers and bonuses added
	 * (add bonuses first)
	 * @return
	 */
	public int getValue() {
		return getValue(true);
	}
	
}
