package fi.raka.kahvikaveri;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewStub;
import android.widget.ImageButton;

public class MyActivity extends Activity {

	public void initTopBar() {
		ViewStub stub = (ViewStub) findViewById(R.id.topBar);
		View inflated = stub.inflate();
		
		((ImageButton) inflated.findViewById(R.id.back)).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}
}
