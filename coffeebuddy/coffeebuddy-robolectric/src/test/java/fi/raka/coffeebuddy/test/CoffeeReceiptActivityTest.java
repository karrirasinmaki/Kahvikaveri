package fi.raka.coffeebuddy.test;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import fi.raka.coffeebuddy.R;
import fi.raka.coffeebuddy.CoffeeReceiptActivity;
import fi.raka.coffeebuddy.logic.Tag;
import fi.raka.test.utils.RobolectricMavenTestRunner;
import static fi.raka.test.utils.Assert.*;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

@RunWith(RobolectricMavenTestRunner.class)
public class CoffeeReceiptActivityTest {
	
	private CoffeeReceiptActivity mActivity;
	private ListView tagListView;
	private EditText newTagEditText;
	private Button addNewTagButton;
	
	@Before
	public void setUp() throws Exception {
		mActivity = Robolectric.buildActivity(CoffeeReceiptActivity.class).create().visible().get();
		
		tagListView = (ListView) mActivity.findViewById(R.id.tagListView);
		newTagEditText = (EditText) mActivity.findViewById(R.id.newTagEditText);
		addNewTagButton = (Button) mActivity.findViewById(R.id.addNewTagButton);
	}
	
	@Test
	public void testAddTag() {
		newTagEditText.setText("Tag1");
		addNewTagButton.performClick();
		
		Tag gotTag = (Tag) tagListView.getAdapter().getItem(0);
		same( "Tag1", gotTag.getName() );
	}
	
	@Test
	public void testAddMultipleTagsCountOKon2() {		
		newTagEditText.setText("Tag1");
		addNewTagButton.performClick();
		
		newTagEditText.setText("Tag2");
		addNewTagButton.performClick();
		
		same( 2, tagListView.getAdapter().getCount() );
	}

	@Test
	public void testAddMultipleTagsCountOKon10() {
		int count = 0;
		while(count < 9) {
			newTagEditText.setText("Tag" + count);
			addNewTagButton.performClick();
			count++;
		}
		
		same( count, tagListView.getAdapter().getCount() );
	}

	@Test
	public void testAddMultipleTags() {		
		newTagEditText.setText("Tag1");
		addNewTagButton.performClick();
		
		newTagEditText.setText("Tag2");
		addNewTagButton.performClick();
		
		Tag gotTag1 = (Tag) tagListView.getAdapter().getItem(0);
		Tag gotTag2 = (Tag) tagListView.getAdapter().getItem(1);
		same( "Tag1", gotTag1.getName() );
		same( "Tag2", gotTag2.getName() );
	}

}
