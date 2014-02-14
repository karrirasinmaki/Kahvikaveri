/**
 * Database helper. Creates tables and updates database if needed.
 */

package fi.raka.coffeebuddy.storage;

import fi.raka.coffeebuddy.storage.ReceiptContract.ReceiptEntry;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class ReceiptDatabaseHelper extends SQLiteOpenHelper {
	
	public static final int DATABASE_VERSION = 5;
    public static final String DATABASE_NAME = "CoffeeBuddy.db";
    
    /* Helper strings */
	private static final String TYPE_TEXT = " TEXT";
	private static final String TYPE_REAL = " REAL";
	private static final String TYPE_INTEGER = " INTEGER";
	private static final String COMMA_SEP = ",";
	private static final String FOREIGN_KEY = "FOREIGN KEY";
	private static final String REFERENCES = " REFERENCES ";
	private static final String DROP_TABLE_IF_EXISTS = "DROP TABLE IF EXISTS ";
	
	/* Table creation */
	private static final String CREATE_RECEIPT_TABLE =
	    "CREATE TABLE " + ReceiptEntry.TABLE_NAME + " (" +
		    ReceiptEntry._ID + TYPE_INTEGER + " PRIMARY KEY AUTOINCREMENT," +
		    ReceiptEntry.COLUMN_NAME_TITLE + TYPE_TEXT + COMMA_SEP +
		    ReceiptEntry.COLUMN_NAME_WATER_AMOUNT + TYPE_REAL + COMMA_SEP +
		    ReceiptEntry.COLUMN_NAME_WATER_TEMPERATURE + TYPE_REAL + COMMA_SEP +
		    ReceiptEntry.COLUMN_NAME_COFFEE_AMOUNT + TYPE_REAL + COMMA_SEP +
		    ReceiptEntry.COLUMN_NAME_DESCRIPTION + TYPE_TEXT +
	    " );";
	private static final String CREATE_TAGS_TABLE =
		// receipt - tag helper table    
		"CREATE TABLE " + ReceiptEntry.TAGS_TABLE_NAME + " (" +
		    ReceiptEntry._ID + TYPE_INTEGER + " PRIMARY KEY AUTOINCREMENT," +
		    ReceiptEntry.RECEIPT_COLUMN_NAME_ID + TYPE_INTEGER + COMMA_SEP +
		    ReceiptEntry.COLUMN_NAME_TITLE + TYPE_TEXT +
		    /* FOREIGN_KEY + "(" + ReceiptEntry.RECEIPT_COLUMN_NAME_ID + ")" + REFERENCES + ReceiptEntry.TABLE_NAME + "(" + ReceiptEntry._ID + ")" + */
	    " );";

	/* Table drop */
	private static final String DROP_RECEIPT_TABLE =
		DROP_TABLE_IF_EXISTS + ReceiptEntry.TABLE_NAME;
	private static final String DROP_TAGS_TABLE =
		DROP_TABLE_IF_EXISTS + ReceiptEntry.TAGS_TABLE_NAME;
	
	public ReceiptDatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(CREATE_RECEIPT_TABLE);
		db.execSQL(CREATE_TAGS_TABLE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(DROP_RECEIPT_TABLE);
        db.execSQL(DROP_TAGS_TABLE);
        onCreate(db);
        Log.d("DATABASE_UPGRADE", "DONE");
	}
	
	
	/**
	 * This class has most common database methods
	 */
	public static class DBUtils {
		
		public static String getStringColumn(String columnName, Cursor c) {
	    	return c.getString( c.getColumnIndexOrThrow(columnName) );
	    }
	    
		public static double getDoubleColumn(String columnName, Cursor c) {
	    	return c.getDouble( c.getColumnIndexOrThrow(columnName) );
	    }
	    
		public static int getIntegerColumn(String columnName, Cursor c) {
	    	return c.getInt( c.getColumnIndexOrThrow(columnName) );
	    }
	    
		/**
		 * @param db where to look from
		 * @param table name of table
		 * @param id of entry
		 * @return true if entry found, false otherwise
		 */
		public static boolean existsInDatabase(SQLiteDatabase db, String table, Integer id) {
			if(id == null) return false;
			
			Cursor c = db.query(table, new String[] {ReceiptEntry._ID}, ReceiptEntry._ID + "=?", new String[] { ""+id }, null, null, null, "1");
			return c.moveToFirst();
		}
		
		/**
		 * Insert new row to db
		 * @param db to insert
		 * @param table name where to insert
		 * @param values ContentValues to insert
		 * @return inserted row _ID
		 */
		public static Integer insertToDB(SQLiteDatabase db, String table, ContentValues values) {	
			return (int) db.insert(table, null, values);
		}
		/**
		 * Saves entry to database. Updates row if entry with given id exists. Inserts new row otherwise
		 * @param db to store to
		 * @param table name of table
		 * @param values to be stored
		 * @param id of entry (if exists)
		 * @return stored entry id
		 */
		public static Integer saveToDB(SQLiteDatabase db, String table, ContentValues values, Integer id) {	
			Integer out = id;
			if(id == null || !existsInDatabase(db, table, id)) {
				out = insertToDB(db, table, values);
			}
			else {
				db.update(table, values, ReceiptEntry._ID + "=?", new String[] { ""+id });
			}
			db.close();
			return out;
		}
		
		/**
	     * Loads data from database and returns it as Cursor
	     * @param db where data is fetched
	     * @param tableName name of table
	     * @param selection The columns for the WHERE clause, =? for values
	     * @param selectionArgs The values for the WHERE clause
	     * @param projection String array of columns to return
	     * @param sortOrder SQL style SORT BY clause, without writing SORT BY
	     * @return Cursor having data of found rows
	     */
	    public static Cursor loadCursorData(SQLiteDatabase db, String tableName, String selection, String[] selectionArgs, String[] projection, String sortOrder) {
	    	return db.query(
	    		tableName,  		              	      // The table to query
				projection,                               // The columns to return
				selection,                                // The columns for the WHERE clause
				selectionArgs,	                          // The values for the WHERE clause
				null,                                     // don't group the rows
				null,                                     // don't filter by row groups
				sortOrder                                 // The sort order
			);
	    }
	    
		/**
	     * Loads data from database and returns it as Cursor
	     * @param db where data is fetched
	     * @param tableName name of table
	     * @param id of CoffeeReceipt to load
	     * @param projection String array of columns to return
	     * @return Cursor having data of found rows
	     */
	    public static Cursor loadCursorDataById(SQLiteDatabase db, String tableName, Integer id, String[] projection) {
	    	return loadCursorData(
	    		db,
	    		tableName,
	    		ReceiptEntry._ID + "=?",
	    		new String[]{ ""+id },
	    		projection,
	    		ReceiptEntry.COLUMN_NAME_TITLE + " ASC"
	    	);
	    }
	}

}
