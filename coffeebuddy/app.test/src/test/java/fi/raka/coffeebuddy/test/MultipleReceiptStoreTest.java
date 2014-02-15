package fi.raka.coffeebuddy.test;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;

import static fi.raka.test.utils.Assert.*;
import fi.raka.coffeebuddy.logic.CListItem;
import fi.raka.coffeebuddy.logic.CoffeeReceipt;
import fi.raka.coffeebuddy.storage.ReceiptContract.ReceiptEntry;
import fi.raka.coffeebuddy.storage.ReceiptDatabaseHelper;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

@RunWith(RobolectricTestRunner.class)
public class MultipleReceiptStoreTest {

	SQLiteDatabase db;
	private Context context;
	
	@Before
	public void setUp() throws Exception {
		context = Robolectric.getShadowApplication().getApplicationContext();
		
		// Clear db
		ReceiptDatabaseHelper dbHelper = new ReceiptDatabaseHelper(context);
		db = dbHelper.getWritableDatabase();
		db.delete(ReceiptEntry.TABLE_NAME, null, null);
	}
	
	@After
	public void tearDown() throws Exception {
		db.close();
	}

	/*
	@Test
	public void testDataInDatabase() {
		String output = "";
		Cursor c = db.qu
		if( c.moveToFirst() ) {
			while(c.moveToNext()) {
				String[] cnames = c.getColumnNames();
				for(int i=0, l=cnames.length; i<l; ++i) {
					output += c.getString( c.getColumnIndex(cnames[i]) );
				}
			}
		}
		same(true, output.length() > 0);
	}
	*/

	@Test
	public void testSaveLoadOne() {
		new CoffeeReceipt().setTitle("Title").save(context);

		ArrayList<CListItem> a = CoffeeReceipt.loadAll(context);
		same(1, a.size());
	}
	
	@Test
	public void testLoadAll() {
		new CoffeeReceipt().setTitle("Title-1").save(context);
		new CoffeeReceipt().setTitle("Title-2").save(context);
		new CoffeeReceipt().setTitle("Title-3").save(context);
		new CoffeeReceipt().setTitle("Title-4").save(context);
		new CoffeeReceipt().setTitle("Title-5").save(context);
		
		ArrayList<CListItem> a = CoffeeReceipt.loadAll(context);
		same(5, a.size());
	}
	
	
}
