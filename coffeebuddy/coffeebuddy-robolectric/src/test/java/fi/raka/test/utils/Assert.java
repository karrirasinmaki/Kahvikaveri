package fi.raka.test.utils;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class Assert {
	
	public static void same(Object expected, Object actual) {
		assertThat(expected, equalTo(actual));
	}
	
	public static void notsame(Object expected, Object actual) {
		assertThat(expected, not(equalTo(actual)));
	}
	
	public static void notnull(Object o) {
		assertThat(o, notNullValue());
	}

}
