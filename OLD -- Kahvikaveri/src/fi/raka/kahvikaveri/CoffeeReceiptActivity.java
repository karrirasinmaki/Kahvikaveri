package fi.raka.kahvikaveri;

import fi.raka.kahvikaveri.logic.CoffeeReceipt;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.Button;

public class CoffeeReceiptActivity extends MyActivity {
		
	private CoffeeReceipt coffeeReceipt;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Intent intent = getIntent();
		
		setContentView(R.layout.coffee_receipt_view);
		initTopBar();
		
		coffeeReceipt = CoffeeReceipt.load(intent.getStringExtra("CoffeeReceiptId"), this);
		fillLayout();
		
		// Save button
		((Button) findViewById(R.id.saveButton)).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				coffeeReceipt.save(CoffeeReceiptActivity.this);
				finish();
			}
		});
	}
	
	private void fillLayout() {
		((TextView) findViewById(R.id.title)).setText(coffeeReceipt.getTitle() );
		((EditText) findViewById(R.id.coffeeAmountPicker)).setText(""+coffeeReceipt.getCoffeeAmount() );
		((EditText) findViewById(R.id.waterAmountPicker)).setText(""+coffeeReceipt.getWaterAmount() );
		((EditText) findViewById(R.id.waterTemperaturePicker)).setText(""+coffeeReceipt.getWaterTemperature() );
	}
}
