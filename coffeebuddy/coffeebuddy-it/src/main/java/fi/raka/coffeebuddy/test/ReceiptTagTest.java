package fi.raka.coffeebuddy.test;

import fi.raka.coffeebuddy.logic.CoffeeReceipt;
import android.test.AndroidTestCase;

public class ReceiptTagTest extends AndroidTestCase {

	private CoffeeReceipt cr;
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		cr = new CoffeeReceipt();
	}
	
	public void testAddTagSizeRight() {
		cr.addTag("Eka");
		cr.addTag("Toka");
		assertEquals(2, cr.getTags().size());
	}
	
	public void testAddTagNameRight() {
		cr.addTag("Moro");
		assertEquals("Moro", cr.getTags().get(0).getName() );
	}
	
	public void testFindTag() {
		cr.addTag("Testi");
		assertEquals( "Testi", cr.getTag("Testi").getName() );
	}
	
	public void testRemoveTag() {
		cr.addTag("Tagi");
		cr.removeTag("Tagi");
		assertEquals( -1, cr.findTag("Tagi") );
	}
	
}
