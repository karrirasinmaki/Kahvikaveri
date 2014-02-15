/**
 * Activity extension. Injects top bar to view.
 */

package fi.raka.coffeebuddy;

import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewStub;
import android.widget.Button;

public class MyActivity extends Activity {

	/**
	 * Init top bar
	 */
	public void initTopBar() {
		ViewStub stub = (ViewStub) findViewById(R.id.topBar);
		View inflated = stub.inflate();
		
		((Button) inflated.findViewById(R.id.back)).setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				finish();
			}
		});
	}
}
