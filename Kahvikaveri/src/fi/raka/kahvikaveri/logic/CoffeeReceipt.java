package fi.raka.kahvikaveri.logic;


import java.util.ArrayList;
import java.util.Date;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import fi.raka.kahvikaveri.storage.ReceiptContract.ReceiptEntry;
import fi.raka.kahvikaveri.storage.ReceiptDatabaseHelper;
import fi.raka.kahvikaveri.storage.Saveable;

public class CoffeeReceipt implements CListItem, Saveable {
    
	private String id;
	private int _id; // Database id
    private String title;
    private double waterAmount, 
                waterTemperature, 
                coffeeAmount;
    
    public CoffeeReceipt() {
    	setId("" + new Date().getTime());
    }
    public CoffeeReceipt(String id) {
    	setId(id);
    }
    
    /**
     * 
     * @param id of the CoffeeReceipt
     * @param context
     * @return new CoffeeReceipt with loaded data or just with given id if data not found
     */
    public static CoffeeReceipt load(String id, Context context) {
    	CoffeeReceipt newCoffeeReceipt =  new CoffeeReceipt(id);
    	newCoffeeReceipt.load(context);
    	return newCoffeeReceipt;
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
    
    private void initWithCursorData(Cursor c) {
    	_id = getIntColumn(ReceiptEntry._ID, c);
    	setId( getStringColumn(ReceiptEntry.COLUMN_NAME_ID, c) );
    	setTitle( getStringColumn(ReceiptEntry.COLUMN_NAME_TITLE, c) );
    	setWaterAmount( getDoubleColumn(ReceiptEntry.COLUMN_NAME_WATER_AMOUNT, c) );
    	setWaterTemperature( getDoubleColumn(ReceiptEntry.COLUMN_NAME_WATER_TEMPERATURE, c) );
    	setCoffeeAmount( getDoubleColumn(ReceiptEntry.COLUMN_NAME_COFFEE_AMOUNT, c) );
    }
    
    @Override
    public String toString() {
        return getTitle() +" | "+ getWaterAmount() +" | "+ 
               getWaterTemperature() +" | "+ getCoffeeAmount();
    }
    
    /* Setters */
    public void setId(String id) {
    	this.id = id;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public void setWaterAmount(double amount) {
        this.waterAmount = amount;
    }
    public void setWaterTemperature(double temperature) {
        this.waterTemperature = temperature;
    }
    public void setCoffeeAmount(double amount) {
        this.coffeeAmount = amount;
    }

    /* Getters */
    public String getId() {
    	return id;
    }
    @Override
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
    
    /* Save and load */
    
    private static ReceiptDatabaseHelper getDbHelper(Context context) {
    	return new ReceiptDatabaseHelper(context);
    }
    
    private ContentValues getValues() {
    	// Create a new map of values, where column names are the keys
		ContentValues values = new ContentValues();
		values.put(ReceiptEntry._ID, _id);
		values.put(ReceiptEntry.COLUMN_NAME_ID, getId() );
		values.put(ReceiptEntry.COLUMN_NAME_TITLE, getTitle() );
		values.put(ReceiptEntry.COLUMN_NAME_WATER_AMOUNT, getWaterAmount() );
		values.put(ReceiptEntry.COLUMN_NAME_WATER_TEMPERATURE, getWaterTemperature() );
		values.put(ReceiptEntry.COLUMN_NAME_COFFEE_AMOUNT, getCoffeeAmount() );
		
		return values;
    }
    
    private static Cursor loadCursorData(SQLiteDatabase db, String id) {		
		// Define a projection that specifies which columns from the database
		// you will actually use after this query.
		String[] projection = {
			ReceiptEntry._ID,
			ReceiptEntry.COLUMN_NAME_ID,
			ReceiptEntry.COLUMN_NAME_TITLE,
			ReceiptEntry.COLUMN_NAME_WATER_AMOUNT,
			ReceiptEntry.COLUMN_NAME_WATER_TEMPERATURE,
			ReceiptEntry.COLUMN_NAME_COFFEE_AMOUNT
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
    
	@Override
	public String save(Context context) {
		SQLiteDatabase db = getDbHelper(context).getWritableDatabase();

		db.replace(ReceiptEntry.TABLE_NAME, null, getValues());
		db.close();
		return getId();
	}
	
	@Override
	public void load(Context context) {
		if(getId() == null) throw new IllegalStateException("Entry not found.");

    	SQLiteDatabase db = getDbHelper(context).getReadableDatabase();
		Cursor c = loadCursorData(db, getId());
		c.moveToFirst();
		initWithCursorData(c);
		db.close();
	}
    
}
