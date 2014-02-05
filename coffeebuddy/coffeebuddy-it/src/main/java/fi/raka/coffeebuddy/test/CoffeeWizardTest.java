package fi.raka.coffeebuddy.test;

import fi.raka.coffeebuddy.logic.CoffeeReceipt;
import fi.raka.coffeebuddy.logic.CoffeeWizard;
import android.test.AndroidTestCase;

public class CoffeeWizardTest extends AndroidTestCase {
	
	private CoffeeReceipt cr;
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		
		cr = new CoffeeReceipt();
	}
	
	public void testAcridTemperatureRemains() {
		cr.setWaterAmount(2).setCoffeeAmount(10).setWaterTemperature(94);
		cr = CoffeeWizard.getBetterCoffee(cr, 1.2, 1);
		assertEquals(94.0, cr.getWaterTemperature());
	}
	
	public void testAcridWaterAmountRemains() {
		cr.setWaterAmount(2).setCoffeeAmount(10).setWaterTemperature(94);
		cr = CoffeeWizard.getBetterCoffee(cr, 1.2, 1);
		assertEquals(2.0, cr.getWaterAmount());
	}
	
	public void testAcridCoffeeAmountChanges() {
		cr.setWaterAmount(2).setCoffeeAmount(10).setWaterTemperature(94);
		cr = CoffeeWizard.getBetterCoffee(cr, 1.2, 1);
		assertNotSame(10.0, cr.getCoffeeAmount());
	}
	
	public void testAcridCoffeeAmountOptimal() {
		cr.setWaterAmount(2).setCoffeeAmount(10).setWaterTemperature(94);
		cr = CoffeeWizard.getBetterCoffee(cr, 1.2, 1);
		assertEquals(12.0, cr.getCoffeeAmount());
	}

}
