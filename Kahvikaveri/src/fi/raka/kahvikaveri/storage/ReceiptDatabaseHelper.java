package fi.raka.kahvikaveri.storage;

import fi.raka.kahvikaveri.storage.ReceiptContract.ReceiptEntry;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class ReceiptDatabaseHelper extends SQLiteOpenHelper {
	
	public static final int DATABASE_VERSION = 4;
    public static final String DATABASE_NAME = "CoffeeBuddy.db";
    
	private static final String TYPE_TEXT = " TEXT";
	private static final String TYPE_REAL = " REAL";
	private static final String COMMA_SEP = ",";
	private static final String SQL_CREATE_ENTRIES =
	    "CREATE TABLE " + ReceiptEntry.TABLE_NAME + " (" +
		    ReceiptEntry._ID + " INTEGER PRIMARY KEY," +
		    ReceiptEntry.COLUMN_NAME_ID + TYPE_TEXT + COMMA_SEP +
		    ReceiptEntry.COLUMN_NAME_TITLE + TYPE_TEXT + COMMA_SEP +
		    ReceiptEntry.COLUMN_NAME_WATER_AMOUNT + TYPE_REAL + COMMA_SEP +
		    ReceiptEntry.COLUMN_NAME_WATER_TEMPERATURE + TYPE_REAL + COMMA_SEP +
		    ReceiptEntry.COLUMN_NAME_COFFEE_AMOUNT + TYPE_REAL +
	    " )";

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
	
	@Override
	public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

}
