/**
 * Coffee receipt. Holds info about single receipt.
 */

package fi.raka.coffeebuddy.logic;

import java.util.ArrayList;
import java.util.Date;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;
import fi.raka.coffeebuddy.storage.ReceiptDatabaseHelper;
import fi.raka.coffeebuddy.storage.Saveable;
import fi.raka.coffeebuddy.storage.ReceiptContract.ReceiptEntry;

public class CoffeeReceipt implements CListItem, Saveable {
    
	private String id;
	private Integer _id = null; // Database id
    private String title;
    private double waterAmount = 0.0;
    private double waterTemperature = 0.0;
    private double coffeeAmount = 0.0;
    private String desription;
    private ArrayList<Tag> tags;
    
    /**
     * Create new CoffeeReceipt.
     */
    public CoffeeReceipt() {
    	setId( "" + new Date().getTime() );
    	tags = new ArrayList<Tag>();
    }
    
    /**
     * 
     * @param id of the CoffeeReceipt
     * @param context
     * @return new CoffeeReceipt with loaded data or just with given id if data not found
     */
    public static CoffeeReceipt load(String id, Context context) {
    	if( id == null || id.length() <= 0 ) return new CoffeeReceipt();
    	return new CoffeeReceipt().setId(id).load(context);
    }
    
    /**
     * 
     * @param context
     * @return ArrayList<CListItem> filled with CoffeeReceipts
     */
    public static ArrayList<CListItem> loadAll(Context context) {
    	ArrayList<CListItem> list = new ArrayList<CListItem>();

    	SQLiteDatabase db = getDbHelper(context).getReadableDatabase();
		Cursor c = loadCursorData(db, null);
		c.moveToFirst();
		while(c.moveToNext() ) {
			CoffeeReceipt coffeeReceipt = new CoffeeReceipt();
			coffeeReceipt.initWithCursorData(c);
			list.add(coffeeReceipt);
		}
		db.close();
		
    	return list;
    }
    
    /**
     * Init current receipt with Cursor data
     * @param c Cursor
     */
    private void initWithCursorData(Cursor c) {
    	_id = getIntColumn(BaseColumns._ID, c);
    	setId( getStringColumn(ReceiptEntry.COLUMN_NAME_ID, c) );
    	setTitle( getStringColumn(ReceiptEntry.COLUMN_NAME_TITLE, c) );
    	setWaterAmount( getDoubleColumn(ReceiptEntry.COLUMN_NAME_WATER_AMOUNT, c) );
    	setWaterTemperature( getDoubleColumn(ReceiptEntry.COLUMN_NAME_WATER_TEMPERATURE, c) );
    	setCoffeeAmount( getDoubleColumn(ReceiptEntry.COLUMN_NAME_COFFEE_AMOUNT, c) );
    	setDescription( getStringColumn(ReceiptEntry.COLUMN_NAME_DESCRIPTION, c) );
    }
    
    @Override
    public String toString() {
        return getTitle() +" | "+ getWaterAmount() +" | "+ 
               getWaterTemperature() +" | "+ getCoffeeAmount();
    }
    
    /* Setters */
    public CoffeeReceipt setId(String id) {
    	this.id = id;
    	return this;
    }
    public CoffeeReceipt setTitle(String title) {
        this.title = title;
    	return this;
    }
    public CoffeeReceipt setWaterAmount(double amount) {
        this.waterAmount = amount;
    	return this;
    }
    public CoffeeReceipt setWaterTemperature(double temperature) {
        this.waterTemperature = temperature;
    	return this;
    }
    public CoffeeReceipt setCoffeeAmount(double amount) {
        this.coffeeAmount = amount;
    	return this;
    }
    public CoffeeReceipt setDescription(String description) {
    	this.desription = description;
    	return this;
    }

    /* Getters */
    public String getId() {
    	return id;
    }
    public String getTitle() {
        return title;
    }
    public double getWaterAmount() {
        return waterAmount;
    }
    public double getWaterTemperature() {
        return waterTemperature;
    }
    public double getCoffeeAmount() {
        return coffeeAmount;
    }
    public String getDescription() {
    	return desription;
    }
    
    
    /* Tag list manipulating */
    /**
     * Add tag to tags list.
     * @param tagName
     * @return this
     */
    public CoffeeReceipt addTag(String tagName) {
    	tags.add( new Tag(tagName) );
    	return this;
    }
    /**
     * Removes tag with name tagName
     * @param tagName
     * @return
     */
    public CoffeeReceipt removeTag(String tagName) {
    	int index = findTag(tagName);
    	if(index >= 0) tags.remove( index );
    	return this;
    }
    public ArrayList<Tag> getTags() {
    	return tags;
    }
    /**
     * Get tag by name.
     * @param tagName
     * @return Tag, null if not found
     */
    public Tag getTag(String tagName) {
    	return tags.get( findTag(tagName) );
    }
    /**
     * Find tag position in tags list with given name
     * @param tagName
     * @return Index, -1 if not found
     */
    public int findTag(String tagName) {
    	int i = 0;
    	for(Tag t : tags) {
    		if( t.getName().equals(tagName) ) return i;
    		i++;
    	}
    	return -1;
    }
    
    /* Save and load */
    private static ReceiptDatabaseHelper getDbHelper(Context context) {
    	return new ReceiptDatabaseHelper(context);
    }
    
    private ContentValues getValues() {
    	// Create a new map of values, where column names are the keys
		ContentValues values = new ContentValues();
		// if(_id != null) values.put(ReceiptEntry._ID, _id);
		values.put( ReceiptEntry.COLUMN_NAME_ID, getId() );
		values.put( ReceiptEntry.COLUMN_NAME_TITLE, getTitle() );
		values.put( ReceiptEntry.COLUMN_NAME_WATER_AMOUNT, getWaterAmount() );
		values.put( ReceiptEntry.COLUMN_NAME_WATER_TEMPERATURE, getWaterTemperature() );
		values.put( ReceiptEntry.COLUMN_NAME_COFFEE_AMOUNT, getCoffeeAmount() );
		values.put( ReceiptEntry.COLUMN_NAME_DESCRIPTION, getDescription() );
		
		return values;
    }
    
    private static Cursor loadCursorData(SQLiteDatabase db, String id) {		
		// Define a projection that specifies which columns from the database
		// you will actually use after this query.
		String[] projection = {
			BaseColumns._ID,
			ReceiptEntry.COLUMN_NAME_ID,
			ReceiptEntry.COLUMN_NAME_TITLE,
			ReceiptEntry.COLUMN_NAME_WATER_AMOUNT,
			ReceiptEntry.COLUMN_NAME_WATER_TEMPERATURE,
			ReceiptEntry.COLUMN_NAME_COFFEE_AMOUNT,
			ReceiptEntry.COLUMN_NAME_DESCRIPTION
		};
		
		// How you want the results sorted in the resulting Cursor
		String sortOrder = ReceiptEntry.COLUMN_NAME_TITLE + " ASC";
		
		String selection;
		String[] selectionArgs;
		if(id == null) {
			selection = null;
			selectionArgs = null;
		}
		else {
			selection = ReceiptEntry.COLUMN_NAME_ID + "=?";
			selectionArgs = new String[]{ id };
		}
		
		Cursor c = db.query(
			ReceiptEntry.TABLE_NAME,  		          // The table to query
			projection,                               // The columns to return
			selection,                                // The columns for the WHERE clause
			selectionArgs,	                          // The values for the WHERE clause
			null,                                     // don't group the rows
			null,                                     // don't filter by row groups
			sortOrder                                 // The sort order
		);
		
		return c;
    }
    
    private String getStringColumn(String columnName, Cursor c) {
    	return c.getString( c.getColumnIndexOrThrow(columnName) );
    }
    
    private double getDoubleColumn(String columnName, Cursor c) {
    	return c.getDouble( c.getColumnIndexOrThrow(columnName) );
    }
    
    private int getIntColumn(String columnName, Cursor c) {
    	return c.getInt( c.getColumnIndexOrThrow(columnName) );
    }
    
    private boolean existsInDatabase(SQLiteDatabase db) {
    	Cursor c = loadCursorData(db, getId());
    	return c.moveToFirst();
    }
    public boolean existsInDatabase(Context context) {
    	SQLiteDatabase db = getDbHelper(context).getReadableDatabase();
    	return existsInDatabase(db);
    }
    
    /**
     * Save item to database.
     * @param context Context
     */
	public String saveToDB(Context context) {
		SQLiteDatabase db = getDbHelper(context).getWritableDatabase();
		
		if(!existsInDatabase(db)) {
			db.insert(ReceiptEntry.TABLE_NAME, null, getValues());
		}
		else {
			db.update(ReceiptEntry.TABLE_NAME, getValues(), ReceiptEntry.COLUMN_NAME_ID + "=?", new String[] {getId()});
		}
		db.close();
		return getId();
	}
    /**
     * Save item to database.
     * @param context Context
     */
	public String save(Context context) {
		return saveToDB(context);
	}
	
	/**
	 * Load item data from database.
	 * @param context Context
	 */
	public void loadFromDB(Context context) {
		if(getId() == null) throw new IllegalStateException("Entry not found.");

    	SQLiteDatabase db = getDbHelper(context).getReadableDatabase();
		Cursor c = loadCursorData(db, getId());
		c.moveToFirst();
		initWithCursorData(c);
		db.close();
	}
	/**
	 * Load item data from database.
	 * @param context Context
	 */
	public CoffeeReceipt load(Context context) {
		loadFromDB(context);
		return this;
	}
    
}
