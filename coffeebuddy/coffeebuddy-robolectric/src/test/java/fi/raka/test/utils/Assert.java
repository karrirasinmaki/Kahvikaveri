package fi.raka.test.utils;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class Assert {
	
	public static void same(Object expected, Object actual) {
		assertThat(actual, equalTo(expected));
	}
	
	public static void notsame(Object expected, Object actual) {
		assertThat(actual, not(equalTo(expected)));
	}
	
	public static void notnull(Object o) {
		assertThat(o, notNullValue());
	}

}
