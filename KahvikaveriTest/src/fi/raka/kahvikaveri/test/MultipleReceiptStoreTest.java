package fi.raka.kahvikaveri.test;

import java.util.ArrayList;

import fi.raka.kahvikaveri.logic.CListItem;
import fi.raka.kahvikaveri.logic.CoffeeReceipt;
import fi.raka.kahvikaveri.storage.ReceiptContract.ReceiptEntry;
import fi.raka.kahvikaveri.storage.ReceiptDatabaseHelper;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.test.AndroidTestCase;
import android.util.Log;

public class MultipleReceiptStoreTest extends AndroidTestCase {

	SQLiteDatabase db;
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		// Clear db
		ReceiptDatabaseHelper dbHelper = new ReceiptDatabaseHelper(getContext());
		db = dbHelper.getWritableDatabase();
		db.delete(ReceiptEntry.TABLE_NAME, null, null);
		
		for(int i=10; i>=0; --i) {
			CoffeeReceipt c = new CoffeeReceipt();
			c.setTitle("Title-"+i);
			c.save(getContext());
		}
	}
	
	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
		
		db.close();
	}
	
	public void testDataInDatabase() {
		Cursor c = db.rawQuery("SELECT * FROM "+ReceiptEntry.TABLE_NAME, null);
		c.moveToFirst();
		String output = "";
		while(c.moveToNext()) {
			String[] cnames = c.getColumnNames();
			for(int i=0, l=cnames.length; i<l; ++i) {
				output += c.getString( c.getColumnIndex(cnames[i]) );
			}
		}
		Log.d("DATABASE_AS_STRING", output);
		assertEquals(true, output.length() > 0);
	}
	
	public void testLoadAll() {
		ArrayList<CListItem> a = CoffeeReceipt.loadAll(getContext());
		assertEquals(10, a.size());
	}
	
}
