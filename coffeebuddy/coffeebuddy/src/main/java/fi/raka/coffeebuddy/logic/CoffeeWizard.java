package fi.raka.coffeebuddy.logic;

public abstract class CoffeeWizard {
	
	public static double OPTIMAL_COFFEE_AMOUNT_PER_DL = 6.0,
						OPTIMAL_WATER_TEMPERATURE_MIN = 92.0,
						OPTIMAL_WATER_TEMPERATURE_MAX = 98.0;

	/**
	 * 
	 * @param cr CoffeeReceipt that has to be better
	 * @param acrid Acrid taste ratio. 0 == optimal, > 1 == acrid, < 1 something else.
	 * @param flavour Flavour ratio. 0 == optimal, > 1 == too much flavour, < 1 == too less flavour.
	 * @return modified and (hopefully) better CoffeeReceipt
	 */
	public static CoffeeReceipt getBetterCoffee(CoffeeReceipt cr, double acrid, double flavour) {
		// coffee - water ratio | > 1 too much coffee, < 1 too less coffee
		double coffeeWaterRatio = (cr.getCoffeeAmount() / cr.getWaterAmount()) / cr.getCoffeeAmount();
		
		if(acrid > 0) {
			/*
			 * Too less coffee
			 * If coffee amount more than optimal, we don't care,
			 * a bit too much coffee does not make coffee bitter
			 */
			if(coffeeWaterRatio < 1) { 
				cr.setCoffeeAmount( cr.getWaterAmount() * OPTIMAL_COFFEE_AMOUNT_PER_DL );
			}
			
			/*
			 * Water too hot
			 */
			if(cr.getWaterAmount() > OPTIMAL_WATER_TEMPERATURE_MAX) {
				double newWaterTemp = OPTIMAL_WATER_TEMPERATURE_MAX - (OPTIMAL_WATER_TEMPERATURE_MAX - OPTIMAL_WATER_TEMPERATURE_MIN) * (1 - acrid);
				cr.setWaterTemperature( newWaterTemp > OPTIMAL_WATER_TEMPERATURE_MAX ? OPTIMAL_WATER_TEMPERATURE_MAX : newWaterTemp );
			}
		}
		else if(acrid < 0) {
			/*
			 * Water too cold
			 */
			if(cr.getWaterAmount() < OPTIMAL_WATER_TEMPERATURE_MIN) {
				cr.setWaterAmount( OPTIMAL_WATER_TEMPERATURE_MIN + (OPTIMAL_WATER_TEMPERATURE_MAX - OPTIMAL_WATER_TEMPERATURE_MIN) * acrid );
			}
		}
		
		return cr;
	}
	
}
