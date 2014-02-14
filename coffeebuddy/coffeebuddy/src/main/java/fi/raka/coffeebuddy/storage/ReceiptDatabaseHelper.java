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
	
	public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "CoffeeBuddy.db";
    
	private static final String TYPE_TEXT = " TEXT";
	private static final String TYPE_REAL = " REAL";
	private static final String TYPE_INTEGER = " INTEGER";
	private static final String COMMA_SEP = ",";
	private static final String FOREIGN_KEY = "FOREIGN KEY";
	private static final String REFERENCES = " REFERENCES ";
	private static final String SQL_CREATE_ENTRIES =
	    "CREATE TABLE " + ReceiptEntry.TABLE_NAME + " (" +
		    ReceiptEntry._ID + TYPE_INTEGER + " PRIMARY KEY AUTOINCREMENT," +
		    ReceiptEntry.COLUMN_NAME_TITLE + TYPE_TEXT + COMMA_SEP +
		    ReceiptEntry.COLUMN_NAME_WATER_AMOUNT + TYPE_REAL + COMMA_SEP +
		    ReceiptEntry.COLUMN_NAME_WATER_TEMPERATURE + TYPE_REAL + COMMA_SEP +
		    ReceiptEntry.COLUMN_NAME_COFFEE_AMOUNT + TYPE_REAL + COMMA_SEP +
		    ReceiptEntry.COLUMN_NAME_DESCRIPTION + TYPE_TEXT +
	    " );" +
		// receipt - tag helper table    
		"CREATE TABLE " + ReceiptEntry.TAGS_TABLE_NAME + " (" +
		    ReceiptEntry._ID + TYPE_INTEGER + " PRIMARY KEY AUTOINCREMENT," +
		    ReceiptEntry.RECEIPT_COLUMN_NAME_ID + TYPE_INTEGER + COMMA_SEP +
		    ReceiptEntry.TAGS_COLUMN_NAME_TAG_NAME + TYPE_TEXT + COMMA_SEP +
		    FOREIGN_KEY + "(" + ReceiptEntry.RECEIPT_COLUMN_NAME_ID + ")" + REFERENCES + ReceiptEntry.TABLE_NAME + "(" + ReceiptEntry._ID + ")" +
	    " );";

	private static final String SQL_DELETE_ENTRIES =
	    "DROP TABLE IF EXISTS " + ReceiptEntry.TABLE_NAME;
	
	public ReceiptDatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(SQL_CREATE_ENTRIES);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
        Log.d("DATABASE_UPGRADE", "DONE");
	}
	
	
	/**
	 * This class has most common database methods
	 */
	public static class Utils {
		/**
		 * @param db where to look from
		 * @param table name of table
		 * @param id of entry
		 * @return true if entry found, false otherwise
		 */
		public static boolean existsInDatabase(SQLiteDatabase db, String table, int id) {
			Cursor c = db.query(table, new String[] {ReceiptEntry._ID}, ReceiptEntry._ID + "=?", new String[] { ""+id }, null, null, null, "1");
			return c.moveToFirst();
		}
		/**
		 * Saves entry to database. Updates row if entry with given id exists. Inserts new row otherwise
		 * @param db to store to
		 * @param table name of table
		 * @param values to be stored
		 * @param id of entry (if exists)
		 * @return stored entry id
		 */
		public static Integer saveToDB(SQLiteDatabase db, String table, ContentValues values, int id) {	
			Integer out = id;
			if(!existsInDatabase(db, table, id)) {
				out = (int) db.insert(table, null, values);
			}
			else {
				db.update(table, values, ReceiptEntry._ID + "=?", new String[] { ""+id });
			}
			db.close();
			return out;
		}
	}

}
