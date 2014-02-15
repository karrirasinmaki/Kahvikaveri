package fi.raka.test.utils;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class Assert {
	
	/**
	 * Check if objects are same
	 * @param expected
	 * @param actual
	 */
	public static void same(Object expected, Object actual) {
		assertThat(actual, equalTo(expected));
	}
	
	/**
	 * Check if objects are NOT same
	 * @param expected
	 * @param actual
	 */
	public static void notsame(Object expected, Object actual) {
		assertThat(actual, not(equalTo(expected)));
	}
	
	public static void notnull(Object o) {
		assertThat(o, notNullValue());
	}

}
