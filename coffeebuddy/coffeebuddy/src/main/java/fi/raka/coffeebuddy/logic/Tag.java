/**
 * Tag.
 */

package fi.raka.coffeebuddy.logic;

import android.content.ContentValues;
import android.content.Context;
import fi.raka.coffeebuddy.storage.Saveable;
import fi.raka.coffeebuddy.storage.ReceiptContract.ReceiptEntry;

public class Tag implements Saveable {
	
	private String name;
	private Integer referenceId;
	
	public Tag(String name, Integer referenceId) {
		setName(name);
	}
	
	/* Getters */
	public String getName() {
		return name;
	}
	public Integer getReferenceId() {
		return referenceId;
	}
	
	/* Setters */
	public Tag setName(String name) {
		this.name = name;
		return this;
	}
	public Tag setReferenceId(Integer referenceId) {
		this.referenceId = referenceId;
		return this;
	}
	
    private ContentValues getValues() {
    	// Create a new map of values, where column names are the keys
		ContentValues values = new ContentValues();
		values.put( ReceiptEntry._ID, getReferenceId() );
		values.put( ReceiptEntry.TAGS_COLUMN_NAME_TAG_NAME, getName() );
		
		return values;
    }

	@Override
	public Integer saveToDB(Context context) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void loadFromDB(Context context) {
		// TODO Auto-generated method stub
		
	}
	
}
