package fi.raka.kahvikaveri.logic;


import java.util.Date;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import fi.raka.kahvikaveri.ReceiptWizard;
import fi.raka.kahvikaveri.storage.ReceiptContract.ReceiptEntry;
import fi.raka.kahvikaveri.storage.ReceiptDatabaseHelper;
import fi.raka.kahvikaveri.storage.Saveable;

public class CoffeeReceipt implements CListItem, Saveable {
    
	public String id;
    private String title;
    private double waterAmount, 
                waterTemperature, 
                coffeeAmount;
    
    public CoffeeReceipt() {
    }
    public CoffeeReceipt(String id) {
    	this.id = id;
    }
    
    public static CoffeeReceipt load(String id, Context context) {
    	CoffeeReceipt newCoffeeReceipt =  new CoffeeReceipt(id);
    	newCoffeeReceipt.load(context);
    	return newCoffeeReceipt;
    }
    
    @Override
    public String toString() {
        return getTitle() +" | "+ getWaterAmount() +" | "+ 
               getWaterTemperature() +" | "+ getCoffeeAmount();
    }
    
    /* Setters */
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
    
    // Save and load
    
    private ReceiptDatabaseHelper getDbHelper(Context context) {
    	return new ReceiptDatabaseHelper(context);
    }
    
    private String getStringColumn(String columnName, Cursor c) {
    	return c.getString( c.getColumnIndexOrThrow(columnName) );
    }
    
    private double getDoubleColumn(String columnName, Cursor c) {
    	return c.getDouble( c.getColumnIndexOrThrow(columnName) );
    }
    
	@Override
	public String save(Context context) {
		SQLiteDatabase db = getDbHelper(context).getWritableDatabase();
		
		String newId = "" + new Date().getTime();
		// Create a new map of values, where column names are the keys
		ContentValues values = new ContentValues();
		values.put(ReceiptEntry.COLUMN_NAME_ID, newId);
		values.put(ReceiptEntry.COLUMN_NAME_TITLE, getTitle() );
		values.put(ReceiptEntry.COLUMN_NAME_WATER_AMOUNT, getWaterAmount() );
		values.put(ReceiptEntry.COLUMN_NAME_WATER_TEMPERATURE, getWaterTemperature() );
		values.put(ReceiptEntry.COLUMN_NAME_COFFEE_AMOUNT, getCoffeeAmount() );
		
		db.insert(ReceiptEntry.TABLE_NAME, null, values);
		db.close();
		return newId;
	}
	
	@Override
	public void load(Context context) {
		if(id == null) throw new IllegalStateException("Entry not found.");
		
		SQLiteDatabase db = getDbHelper(context).getWritableDatabase();
		
		// Define a projection that specifies which columns from the database
		// you will actually use after this query.
		String[] projection = {
			ReceiptEntry.COLUMN_NAME_ID,
			ReceiptEntry.COLUMN_NAME_TITLE,
			ReceiptEntry.COLUMN_NAME_WATER_AMOUNT,
			ReceiptEntry.COLUMN_NAME_WATER_TEMPERATURE,
			ReceiptEntry.COLUMN_NAME_COFFEE_AMOUNT
		};
		
		// How you want the results sorted in the resulting Cursor
		String sortOrder = ReceiptEntry.COLUMN_NAME_ID + " DESC";
		
		String selection = ReceiptEntry.COLUMN_NAME_ID + "=?";
		String[] selectionArgs = { id };
		
		Cursor c = db.query(
			ReceiptEntry.TABLE_NAME,  		          // The table to query
			projection,                               // The columns to return
			selection,                                // The columns for the WHERE clause
			selectionArgs,	                                  // The values for the WHERE clause
			null,                                     // don't group the rows
			null,                                     // don't filter by row groups
			sortOrder                                 // The sort order
		);
		
		c.moveToFirst();

		setTitle( getStringColumn(ReceiptEntry.COLUMN_NAME_TITLE, c) );
		setWaterAmount( getDoubleColumn(ReceiptEntry.COLUMN_NAME_WATER_AMOUNT, c) );
		setWaterTemperature( getDoubleColumn(ReceiptEntry.COLUMN_NAME_WATER_TEMPERATURE, c) );
		setCoffeeAmount( getDoubleColumn(ReceiptEntry.COLUMN_NAME_COFFEE_AMOUNT, c) );
		
		db.close();
	}
    
}
