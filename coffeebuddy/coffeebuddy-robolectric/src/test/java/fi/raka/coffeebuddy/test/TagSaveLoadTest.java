package fi.raka.coffeebuddy.test;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;

import android.content.Context;
import fi.raka.coffeebuddy.logic.CoffeeReceipt;
import fi.raka.coffeebuddy.logic.Tag;
import fi.raka.coffeebuddy.storage.ReceiptContract.ReceiptEntry;
import fi.raka.coffeebuddy.storage.ReceiptDatabaseHelper.DBUtils;
import fi.raka.test.utils.RobolectricMavenTestRunner;
import static fi.raka.test.utils.Assert.*;

@RunWith(RobolectricMavenTestRunner.class)
public class TagSaveLoadTest {
	
	private Context context;
	private CoffeeReceipt coffeeReceipt;

	@Before
	public void setup() {
		context = Robolectric.getShadowApplication().getApplicationContext();
		coffeeReceipt = new CoffeeReceipt();
		coffeeReceipt.setTitle("Paulig").save(context);
	}
	
	@Test
	public void testTagNameInit() {
		Tag tag = new Tag("Tummapaahto", coffeeReceipt.getId());
		same( "Tummapaahto", tag.getName() );
	}
	
	@Test
	public void testTagRefIdInit() {
		Tag tag = new Tag("Tummapaahto", coffeeReceipt.getId());
		same( coffeeReceipt.getId(), tag.getReferenceId() );
	}
	
	@Test
	public void testSaveReturnsId() {
		Tag sampleTag = new Tag("Tummapaahto", coffeeReceipt.getId());
		Integer sampleTagId = sampleTag.saveToDB(context);
		
		System.out.println("THE SAVED TAG ID IS " + sampleTagId);
		
		notnull( sampleTagId );
	}
	
	@Test
	public void testSaveLoadSingleTag() {
		Tag tag = new Tag("Tummapaahto", coffeeReceipt.getId());
		Integer id = tag.saveToDB(context);
		
		Tag newTag = new Tag().setId(id);
		newTag.loadFromDB(context);

		same( tag.getName(), newTag.getName() );
	}
	
}
