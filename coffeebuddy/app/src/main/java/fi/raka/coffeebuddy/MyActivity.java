/**
 * Activity extension. Injects top bar to view.
 */

package fi.raka.coffeebuddy;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

public class MyActivity extends Activity {

	public static int ALIGN_LEFT = 0;
	public static int ALIGN_RIGHT = 1;
	
	public LinearLayout topBar;
	public LinearLayout topBarLeft;
	public LinearLayout topBarRight;
	
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
		button.setText(text);
		button.setOnClickListener(onClickListener);

		if(align == ALIGN_LEFT) topBarLeft.addView(button);
		else if(align == ALIGN_RIGHT) topBarRight.addView(button);
		
		return button;
	}
	
	/**
	 * Inits content view
	 */
	private void initContentView() {
		contentView = new LinearLayout(this);
		contentView.setOrientation(LinearLayout.VERTICAL);
		contentView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
	}
	
	@Override
	public void setContentView(int layoutResID) {
		initContentView();
		addTopBarTo(contentView);

		FrameLayout mainView = new FrameLayout(this);
		FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, 1);
		layoutParams.setMargins(0, topBar.getHeight(), 0, 0);
		mainView.setLayoutParams(layoutParams);
		
		View view = getLayoutInflater().inflate(layoutResID, null);
		view.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
		mainView.addView(view);
		
		contentView.addView(mainView);
		
		setContentView(contentView);
	}
	
}
