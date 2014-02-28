/**
 * Activity extension. Injects top bar to view.
 */

package fi.raka.coffeebuddy;

import fi.raka.coffeebuddy.SearchBox.OnSearchListener;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class MyActivity extends Activity {

	public static int ALIGN_LEFT = 0;
	public static int ALIGN_RIGHT = 1;
	
	public LinearLayout topBar;
	public LinearLayout topBarLeft;
	public LinearLayout topBarRight;
	
	public RelativeLayout mainView;
	
	public LinearLayout contentView;

	/**
	 * Init top bar
	 */
	public void addTopBarTo(ViewGroup parent) {
		topBar = new LinearLayout(this);
		topBar.setOrientation(LinearLayout.HORIZONTAL);
		topBar.setBackgroundColor(getResources().getColor(R.color.milk_coffee));
		topBar.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
		
		topBarLeft = new LinearLayout(this);
		topBarLeft.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, 1f));

		topBarRight = new LinearLayout(this);
		topBarRight.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
		
		topBar.addView(topBarLeft);
		topBar.addView(topBarRight);
		
		addTopBarButton(R.string.back, R.drawable.ic_navigation_previous_item, 0, onCloseButtonClickListener, ALIGN_LEFT);
		
		parent.addView(topBar);
	}
	
	/**
	 * Listens closeButton click activity. Finishes activity on click.
	 */
	public View.OnClickListener onCloseButtonClickListener = new View.OnClickListener() {
		public void onClick(View v) {
			finish();
		}
	};
	
	/**
	 * Adds new button to top bar
	 * @param text of button
	 * @param leftDrawable of button
	 * @param rightDrawable of button
	 * @param onClickListener of button
	 * @param align | align left or right. ALIGN_LEFT, ALIGN_RIGHT
	 * @return just created button
	 */
	public Button addTopBarButton(int text, int leftDrawable, int rightDrawable, View.OnClickListener onClickListener, int align) {
		Button button = new Button(this);
		button.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT));
		button.setClickable(true);
		button.setBackgroundColor(0);
		button.setTextColor(getResources().getColor(R.color.vienna_coffee));
		
		button.setCompoundDrawablesWithIntrinsicBounds(leftDrawable, 0, rightDrawable, 0);
		if(text == 0) button.setText("");
		else button.setText(text);
		button.setOnClickListener(onClickListener);

		if(align == ALIGN_LEFT) topBarLeft.addView(button);
		else if(align == ALIGN_RIGHT) topBarRight.addView(button);
		
		return button;
	}
	
	/**
	 * Add new SearchBox to parent view
	 * @param onSearchListener fires when search-button is clicked
	 * @param parent view to add SeachBox
	 * @return new SearchBox
	 */
	public SearchBox addSearchView(OnSearchListener onSearchListener, ViewGroup parent) {
		SearchBox searchBox = new SearchBox(this);
		searchBox.setOnSearchListener(onSearchListener);
		searchBox.setBackgroundColor(getResources().getColor(R.color.past_time));
		
		parent.addView(searchBox);
		return searchBox;
	}
	
	/**
	 * Inits content view
	 */
	private void initContentView() {
		contentView = new LinearLayout(this);
		contentView.setOrientation(LinearLayout.VERTICAL);
		contentView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
	}
	
	/**
	 * Overrides normal setContentView by adding topbar to layout
	 */
	@Override
	public void setContentView(int layoutResID) {
		initContentView();
		addTopBarTo(contentView);

		mainView = new RelativeLayout(this);
		View view = getLayoutInflater().inflate(layoutResID, null);
		view.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
		mainView.addView(view);
		
		LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		layoutParams.setMargins(0, topBar.getHeight(), 0, 0);
		mainView.setLayoutParams(layoutParams);
		
		contentView.addView(mainView);
		
		setContentView(contentView);
	}
	
}
