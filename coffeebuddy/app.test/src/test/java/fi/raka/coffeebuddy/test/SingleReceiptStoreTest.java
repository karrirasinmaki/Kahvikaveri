package fi.raka.coffeebuddy.test;
/**
 * Tests single CoffeeReceipt saving and loading
 */

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;

import android.content.Context;
import static fi.raka.test.utils.Assert.*;
import fi.raka.coffeebuddy.logic.CoffeeReceipt;
import fi.raka.coffeebuddy.storage.ReceiptDatabaseHelper;
import fi.raka.coffeebuddy.storage.ReceiptContract.ReceiptEntry;

@RunWith(RobolectricTestRunner.class)
public class SingleReceiptStoreTest {
	
	private CoffeeReceipt cr;
	private CoffeeReceipt cr2;
	private Integer crId;
	
	private Context context;
	
	
	@Before
	public void setUp() throws Exception {
		context = Robolectric.getShadowApplication().getApplicationContext();
		
		cr = new fi.raka.coffeebuddy.logic.CoffeeReceipt();
		cr.setTitle("Paulig");
		cr.setWaterAmount(1.2);
		cr.setWaterTemperature(98.3);
		cr.setCoffeeAmount(12.4);
		crId = cr.save( context );
		
		cr2 = CoffeeReceipt.load(crId, context );
	}
	
	@Test
	public void testSave() {
		notnull( crId );
	}

	@Test
	public void testLoad() {
		notnull(cr2);
	}

	@Test
	public void testExists() {
		same( true, cr.existsInDatabase(context) );
	}

	@Test
	public void testNotExists() {
		CoffeeReceipt newCoffeeReceipt = new CoffeeReceipt();
		same( false, newCoffeeReceipt.existsInDatabase(context) );
	}

	@Test
	public void testSaveAndLoadTitleRight() {
		same(cr.getTitle(), cr2.getTitle());
	}

	@Test
	public void testSaveAndLoadWaterAmountRight() {
		same(cr.getWaterAmount(), cr2.getWaterAmount());
	}

	@Test
	public void testSaveAndLoadWaterTemperatureRight() {
		same(cr.getWaterAmount(), cr2.getWaterAmount());
	}

	@Test
	public void testSaveAndLoadCoffeeAmountRight() {
		same(cr.getCoffeeAmount(), cr2.getCoffeeAmount());
	}

	@Test
	public void testSaveAndLoad() {
		same(cr.toString(), cr2.toString());
	}

	@Test
	public void testModifyAndLoadReceiptIdStillSame() {
		cr.setWaterAmount(982);
		same(cr.getId(), cr.save(context) );
	}

	@Test
	public void testModifyAndLoadReceipt() {
		cr2.setWaterAmount(1337);
		Integer id = cr2.save(context);
		CoffeeReceipt newCr = CoffeeReceipt.load(id, context);
		
		same(cr2.toString(), newCr.toString());
	}

	@Test
	public void testSaveLoadTagsToReceipt() {
		cr2.addTag("Tag1");
		cr2.addTag("Tag2");
		cr2.addTag("Tag3");
		Integer id = cr2.save(context);
		
		CoffeeReceipt newCr = CoffeeReceipt.load( id, context );
		same( 3, newCr.getTags().size() );
	}
	
}
