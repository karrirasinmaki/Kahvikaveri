/**
 * Tag.
 */

package fi.raka.coffeebuddy.logic;

public class Tag {
	
	private String name;
	
	public Tag(String name) {
		setName(name);
	}
	
	/* Getters */
	public String getName() {
		return name;
	}
	
	/* Setters */
	public Tag setName(String name) {
		this.name = name;
		return this;
	}
	
}
