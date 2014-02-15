package fi.raka.coffeebuddy.test;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import static fi.raka.test.utils.Assert.*;
import fi.raka.coffeebuddy.logic.CoffeeReceipt;

@RunWith(RobolectricTestRunner.class)
public class ReceiptTagTest {

	private CoffeeReceipt cr;
	
	@Before
	public void setUp() throws Exception {
		cr = new CoffeeReceipt();
	}

	@Test
	public void testAddTagSizeRight() {
		cr.addTag("Eka");
		cr.addTag("Toka");
		same(2, cr.getTags().size());
	}

	@Test
	public void testAddTagNameRight() {
		cr.addTag("Moro");
		same("Moro", cr.getTags().get(0).getName() );
	}

	@Test
	public void testFindTag() {
		cr.addTag("Testi");
		same( "Testi", cr.getTag("Testi").getName() );
	}

	@Test
	public void testRemoveTag() {
		cr.addTag("Tagi");
		cr.removeTag("Tagi");
		same( -1, cr.findTag("Tagi") );
	}
	
}
