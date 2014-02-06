package fi.raka.coffeebuddy.test;

import fi.raka.coffeebuddy.CoffeeReceiptActivity;
import fi.raka.coffeebuddy.logic.Tag;
import android.annotation.TargetApi;
import android.os.Build;
import android.test.ActivityInstrumentationTestCase2;
import android.test.UiThreadTest;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

@TargetApi(Build.VERSION_CODES.CUPCAKE)
public class CoffeeReceiptActivityTest extends ActivityInstrumentationTestCase2<CoffeeReceiptActivity> {
	
	private CoffeeReceiptActivity mActivity;
	private ListView tagListView;
	private EditText newTagEditText;
	private Button addNewTagButton;

	@TargetApi(Build.VERSION_CODES.FROYO)
	public CoffeeReceiptActivityTest() {
		super(CoffeeReceiptActivity.class);
	}
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		
		mActivity = getActivity();
		
		tagListView = (ListView) mActivity.findViewById(fi.raka.coffeebuddy.R.id.tagListView);
		newTagEditText = (EditText) mActivity.findViewById(fi.raka.coffeebuddy.R.id.newTagEditText);
		addNewTagButton = (Button) mActivity.findViewById(fi.raka.coffeebuddy.R.id.addNewTagButton);
	}
	
	@UiThreadTest
	public void testAddTag() {		
		newTagEditText.setText("Tag1");
		addNewTagButton.performClick();
		
		Tag gotTag = (Tag) tagListView.getAdapter().getItem(0);
		assertEquals( "Tag1", gotTag.getName() );
	}
	
	@UiThreadTest
	public void testAddMultipleTagsCountOKon2() {		
		newTagEditText.setText("Tag1");
		addNewTagButton.performClick();
		
		newTagEditText.setText("Tag2");
		addNewTagButton.performClick();
		
		assertEquals( 2, tagListView.getAdapter().getCount() );
	}
	
	@UiThreadTest
	public void testAddMultipleTagsCountOKon10() {
		int count = 0;
		while(count < 9) {
			newTagEditText.setText("Tag" + count);
			addNewTagButton.performClick();
			count++;
		}
		
		assertEquals( count, tagListView.getAdapter().getCount() );
	}
	
	@UiThreadTest
	public void testAddMultipleTags() {		
		newTagEditText.setText("Tag1");
		addNewTagButton.performClick();
		
		newTagEditText.setText("Tag2");
		addNewTagButton.performClick();
		
		Tag gotTag1 = (Tag) tagListView.getAdapter().getItem(0);
		Tag gotTag2 = (Tag) tagListView.getAdapter().getItem(1);
		assertEquals( "Tag1", gotTag1.getName() );
		assertEquals( "Tag2", gotTag2.getName() );
	}

}
