package fi.raka.coffeebuddy;

import fi.raka.coffeebuddy.logic.CoffeeReceipt;
import fi.raka.coffeebuddy.logic.CoffeeWizard;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.Button;

public class CoffeeReceiptActivity extends MyActivity {
		
	private CoffeeReceipt coffeeReceipt;
	
	private TextView titleView;
	private EditText coffeeAmountPicker, waterAmountPicker, waterTemperaturePicker;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Intent intent = getIntent();
		
		setContentView(R.layout.coffee_receipt_view);
		initTopBar();
		
		coffeeReceipt = CoffeeReceipt.load(intent.getStringExtra("CoffeeReceiptId"), this);
		initLayout();
		fillLayout();
	}
	
	private void initLayout() {
		titleView = (TextView) findViewById(R.id.title);
		coffeeAmountPicker = (EditText) findViewById(R.id.coffeeAmountPicker);
		waterAmountPicker = (EditText) findViewById(R.id.waterAmountPicker);
		waterTemperaturePicker = (EditText) findViewById(R.id.waterTemperaturePicker);
		
		// Acrid button
		((Button) findViewById(R.id.acridButton)).setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				coffeeReceipt = CoffeeWizard.getBetterCoffee(coffeeReceipt, 1.2, 1.0);
				fillLayout();
			}
		});
		
		// Save button
		((Button) findViewById(R.id.saveButton)).setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				coffeeReceipt.setTitle( titleView.getText().toString() )
							.setCoffeeAmount( getValue(coffeeAmountPicker) )
							.setWaterAmount( getValue(waterAmountPicker) )
							.setWaterTemperature( getValue(waterTemperaturePicker) );
				coffeeReceipt.save(CoffeeReceiptActivity.this);
				finish();
			}
		});
	}
	
	private void fillLayout() {
		titleView.setText(coffeeReceipt.getTitle() );
		coffeeAmountPicker.setText(""+coffeeReceipt.getCoffeeAmount() );
		waterAmountPicker.setText(""+coffeeReceipt.getWaterAmount() );
		waterTemperaturePicker.setText(""+coffeeReceipt.getWaterTemperature() );
	}
	
	private double getValue(EditText editText) {
		return Double.parseDouble(editText.getText().toString());
	}
}
