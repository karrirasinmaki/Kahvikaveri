package fi.raka.kahvikaveri;

import fi.raka.kahvikaveri.logic.CoffeeReceipt;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class ReceiptWizard extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.receipt_wizard);
		
		Button okButton = (Button) findViewById(R.id.okButton);
		okButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				EditText titleEditText = (EditText) findViewById(R.id.title);
				CoffeeReceipt coffeeReceipt = new CoffeeReceipt();
				coffeeReceipt.setTitle( titleEditText.getText().toString() );
				coffeeReceipt.save(getApplicationContext() );
				
				setResult(RESULT_OK);
				finish();
			}
		});
	}
	
}
