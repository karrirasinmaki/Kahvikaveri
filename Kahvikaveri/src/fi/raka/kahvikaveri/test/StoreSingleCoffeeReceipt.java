/**
 * Tests single CoffeeReceipt saving and loading
 */


package fi.raka.kahvikaveri.test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import android.test.InstrumentationTestCase;
import android.util.Log;
import fi.raka.kahvikaveri.logic.CoffeeReceipt;

public class StoreSingleCoffeeReceipt extends InstrumentationTestCase {
	
	private CoffeeReceipt cr;
	private CoffeeReceipt cr2;
	private String crId;

	@Before
	public void setUp() throws Exception {
		cr = new CoffeeReceipt();
		cr.setTitle("Paulig");
		cr.setWaterAmount(1.2);
		cr.setWaterTemperature(98.3);
		cr.setCoffeeAmount(12.4);
		crId = cr.save( getInstrumentation().getContext() );
		
		cr2 = CoffeeReceipt.load(crId, getInstrumentation().getContext() );
	}

	@After
	public void tearDown() throws Exception {
	}
	
	@Test
	public void testCoffeeReceiptInit() {		
		assertEquals("Paulig | 1.2 | 98.3 | 12.4", cr.toString() );
	}
	
	@Test
	public void testSave() {
		assertNotNull( crId );
	}
	
	@Test
	public void testLoad() {
		Log.d("LOADED_RECEIPT_ID", cr2.id);
		Log.d("LOADED_RECEIPT", cr2.toString());
		assertNotNull(cr2);
	}

	@Test
	public void testSaveAndLoadTitleRight() {
		assertEquals(cr.getTitle(), cr2.getTitle());
	}
	
	@Test
	public void testSaveAndLoadWaterAmountRight() {
		assertEquals(cr.getWaterAmount(), cr2.getWaterAmount());
	}
	
	@Test
	public void testSaveAndLoadWaterTemperatureRight() {
		assertEquals(cr.getWaterAmount(), cr2.getWaterAmount());
	}
	
	@Test
	public void testSaveAndLoadCoffeeAmountRight() {
		assertEquals(cr.getCoffeeAmount(), cr2.getCoffeeAmount());
	}
	
	@Test
	public void testSaveAndLoad() {
		Log.d("CR_TO_STRING", cr.toString());
		Log.d("CR2_TO_STRING", cr2.toString());
		assertEquals(cr.toString(), cr2.toString());
	}

}
