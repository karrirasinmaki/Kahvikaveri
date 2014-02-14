/**
 * Tag.
 */

package fi.raka.coffeebuddy.logic;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import fi.raka.coffeebuddy.storage.ReceiptDatabaseHelper;
import fi.raka.coffeebuddy.storage.Saveable;
import fi.raka.coffeebuddy.storage.ReceiptContract.ReceiptEntry;
import fi.raka.coffeebuddy.storage.ReceiptDatabaseHelper.DBUtils;

public class Tag implements Saveable {
	
	private Integer id;
	private String name;
	private Integer referenceId;
	
	/**
	 * Create new empty Tag
	 */
	public Tag() {
	}
	/**
	 * Create new Tag and init it with name and referenceId
	 * @param name of Tag
	 * @param referenceId, Id to referenced entry
	 */
	public Tag(String name, Integer referenceId) {
		setName(name);
		setReferenceId(referenceId);
	}
	
	/* Getters */
	public Integer getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	public Integer getReferenceId() {
		return referenceId;
	}
	
	/* Setters */
	public Tag setId(Integer id) {
		this.id = id;
		return this;
	}
	public Tag setName(String name) {
		this.name = name;
		return this;
	}
	public Tag setReferenceId(Integer referenceId) {
		this.referenceId = referenceId;
		return this;
	}
	
	/**
     * Get ContentValues, values that will be stored to database
     * @return ContentValues
     */
    private ContentValues getValues() {
    	// Create a new map of values, where column names are the keys
		ContentValues values = new ContentValues();
		values.put( ReceiptEntry._ID, getId() );
		values.put( ReceiptEntry.RECEIPT_COLUMN_NAME_ID, getReferenceId() );
		values.put( ReceiptEntry.COLUMN_NAME_TITLE, getName() );
		
		return values;
    }
    
    private static String[] getProjection() {
    	return new String[] {
    		ReceiptEntry._ID,
    		ReceiptEntry.RECEIPT_COLUMN_NAME_ID,
    		ReceiptEntry.COLUMN_NAME_TITLE
    	};
    }
    
    private void initWithCursorData(Cursor c) {
    	setId( DBUtils.getIntegerColumn( ReceiptEntry._ID, c ) );
    	setName( DBUtils.getStringColumn( ReceiptEntry.COLUMN_NAME_TITLE, c ) );
    	setReferenceId( DBUtils.getIntegerColumn( ReceiptEntry.RECEIPT_COLUMN_NAME_ID, c ) );
    }

	@Override
	public Integer saveToDB(Context context) {
		SQLiteDatabase db = new ReceiptDatabaseHelper(context).getWritableDatabase();
		setId( DBUtils.saveToDB(db, ReceiptEntry.TAGS_TABLE_NAME, getValues(), getId()) );
		return getId();
	}

	@Override
	public void loadFromDB(Context context) {
		SQLiteDatabase db = new ReceiptDatabaseHelper(context).getReadableDatabase();
		Cursor cursor = DBUtils.loadCursorDataById(db, ReceiptEntry.TAGS_TABLE_NAME, getId(), getProjection());
		if( cursor != null && cursor.moveToFirst() ) {
			initWithCursorData( cursor );
		}
		db.close();
	}
	
	public static ArrayList<Tag> loadAll(Context context, Integer refId) {
    	ArrayList<Tag> list = new ArrayList<Tag>();

    	SQLiteDatabase db = new ReceiptDatabaseHelper(context).getReadableDatabase();
		Cursor c = DBUtils.loadCursorData(
			db,
			ReceiptEntry.TAGS_TABLE_NAME,
			ReceiptEntry.RECEIPT_COLUMN_NAME_ID + "=?",
			new String[] { ""+refId },
			getProjection(),
			null
		);
		c.moveToPosition( -1 );
		while( c.moveToNext() ) {
			Tag tag = new Tag();
			tag.initWithCursorData(c);
			list.add(tag);
		}
		db.close();
		
    	return list;
    }
}
