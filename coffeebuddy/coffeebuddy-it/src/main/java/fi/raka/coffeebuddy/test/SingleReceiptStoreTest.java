package fi.raka.coffeebuddy.test;
/**
 * Tests single CoffeeReceipt saving and loading
 */

import junit.framework.Test;
import android.util.Log;
import fi.raka.coffeebuddy.logic.CoffeeReceipt;

public class SingleReceiptStoreTest extends android.test.AndroidTestCase {
	
	private CoffeeReceipt cr;
	private CoffeeReceipt cr2;
	private String crId;
	
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		
		cr = new CoffeeReceipt();
		cr.setTitle("Paulig");
		cr.setWaterAmount(1.2);
		cr.setWaterTemperature(98.3);
		cr.setCoffeeAmount(12.4);
		crId = cr.save( getContext() );
		
		cr2 = CoffeeReceipt.load(crId, getContext() );
	}
	
	public void testSave() {
		assertNotNull( crId );
	}
	
	public void testLoad() {
		Log.d("LOADED_RECEIPT_ID", cr2.getId());
		Log.d("LOADED_RECEIPT", cr2.toString());
		assertNotNull(cr2);
	}

	public void testSaveAndLoadTitleRight() {
		assertEquals(cr.getTitle(), cr2.getTitle());
	}
	
	public void testSaveAndLoadWaterAmountRight() {
		assertEquals(cr.getWaterAmount(), cr2.getWaterAmount());
	}
	
	public void testSaveAndLoadWaterTemperatureRight() {
		assertEquals(cr.getWaterAmount(), cr2.getWaterAmount());
	}
	
	public void testSaveAndLoadCoffeeAmountRight() {
		assertEquals(cr.getCoffeeAmount(), cr2.getCoffeeAmount());
	}
	
	public void testSaveAndLoad() {
		Log.d("CR_TO_STRING", cr.toString());
		Log.d("CR2_TO_STRING", cr2.toString());
		assertEquals(cr.toString(), cr2.toString());
	}
	
	public void testModifyAndLoadReceiptIdStillSame() {
		cr.setWaterAmount(982);
		assertEquals(cr.getId(), cr.save(getContext()) );
	}
	
	public void testModifyAndLoadReceipt() {
		cr.setWaterAmount(1337);
		String id = cr.save(getContext());
		CoffeeReceipt newCr = CoffeeReceipt.load(id, getContext());
		
		assertEquals(cr.toString(), newCr.toString());
	}

}
