package fi.raka.coffeebuddy.test;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;

import fi.raka.coffeebuddy.MainActivity;
import static fi.raka.test.utils.Assert.*;

@RunWith(RobolectricTestRunner.class)
public class MainActivityTest {
	
	private MainActivity mainActivity;
	
	@Before
	public void setUp() {
		mainActivity = Robolectric.buildActivity(MainActivity.class).create().visible().get();
	}
	
	@Test
	public void testActivityStart() {
		MainActivity activity = Robolectric.buildActivity(MainActivity.class).create().visible().get();
		notnull(activity);
	}

}
