package fi.raka.coffeebuddy.test;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;
import static fi.raka.test.utils.Assert.*;
import fi.raka.coffeebuddy.logic.CoffeeReceipt;
import fi.raka.coffeebuddy.logic.CoffeeWizard;

@RunWith(RobolectricTestRunner.class)
public class CoffeeWizardTest {
	
	private CoffeeReceipt cr;
	
	@Before
	public void setup() throws Exception {
		cr = new CoffeeReceipt();
	}
	
	@Test
	public void testAcridTemperatureRemains() {
		cr.setWaterAmount(2).setCoffeeAmount(10).setWaterTemperature(94);
		cr = CoffeeWizard.getBetterCoffee(cr, 1.2, 1);
		same(94.0, cr.getWaterTemperature());
	}

	@Test
	public void testAcridWaterAmountRemains() {
		cr.setWaterAmount(2).setCoffeeAmount(10).setWaterTemperature(94);
		cr = CoffeeWizard.getBetterCoffee(cr, 1.2, 1);
		same(2.0, cr.getWaterAmount());
	}

	@Test
	public void testAcridCoffeeAmountChanges() {
		cr.setWaterAmount(2).setCoffeeAmount(10).setWaterTemperature(94);
		cr = CoffeeWizard.getBetterCoffee(cr, 1.2, 1);
		notsame(10.0, cr.getCoffeeAmount());
	}

	@Test
	public void testAcridCoffeeAmountOptimal() {
		cr.setWaterAmount(2).setCoffeeAmount(10).setWaterTemperature(94);
		cr = CoffeeWizard.getBetterCoffee(cr, 1.2, 1);
		same(12.0, cr.getCoffeeAmount());
	}
	
	@Test
	public void testRightImprovementWhenTooLessCoffee() {
		cr.setCoffeeAmount(4).setWaterAmount(1).setWaterTemperature(97);
		cr = CoffeeWizard.getBetterCoffee(cr, 1.2, 1);
		same( 6.0, cr.getCoffeeAmount() );
	}

}
