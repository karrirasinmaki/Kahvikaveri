package fi.raka.coffeebuddy;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

public class SearchBox extends LinearLayout {
	
	private OnSearchListener onSearchListener;
	public EditText editText;
	public Button button;
	
	public SearchBox(Context context) {
		super(context);
		init(context);
	}
	public SearchBox(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}
	
	/**
	 * Init view
	 * @param context
	 */
	private void init(Context context) {
		setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
		
		editText = new EditText(context);
		editText.setHint(R.string.search_hint);
		LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, 1f);
		addView(editText, layoutParams);
		
		button = new Button(context);
		button.setText(R.string.search);
		button.setClickable(true);
		button.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT));
		button.setOnClickListener(onSearchButtonClickListener);
		addView(button);
	}
	
	/**
	 * Set new OnSearchListener. Listener is fired when user clicks search-button
	 * @param listener OnSearchListener
	 */
	public void setOnSearchListener(OnSearchListener listener) {
		onSearchListener = listener;
	}
	
	/**
	 * Detects when search-button is clicked
	 */
	private OnClickListener onSearchButtonClickListener = new OnClickListener() {
		public void onClick(View v) {
			if(onSearchListener != null) onSearchListener.onEvent(getRootView(), editText.getText().toString());
		}
	};

	/**
	 * Interface for OnSearchListener
	 */
	public interface OnSearchListener {
		/**
		 * @param v SearchBox
		 * @param value of search input
		 */
		public void onEvent(View v, String value);
	}
}

