package fi.raka.coffeebuddy;

import fi.raka.coffeebuddy.logic.CoffeeReceipt;
import fi.raka.coffeebuddy.logic.CoffeeWizard;
import fi.raka.coffeebuddy.logic.Tag;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.Button;

public class CoffeeReceiptActivity extends MyActivity {
		
	private CoffeeReceipt coffeeReceipt;
	private EditText titleEditText, coffeeAmountPicker, waterAmountPicker, waterTemperaturePicker, descriptionEditText, 
					newTagEditText;
	private TagListArrayAdapter adapter;
	
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
		titleEditText = (EditText) findViewById(R.id.titleEditText);
		coffeeAmountPicker = (EditText) findViewById(R.id.coffeeAmountPicker);
		waterAmountPicker = (EditText) findViewById(R.id.waterAmountPicker);
		waterTemperaturePicker = (EditText) findViewById(R.id.waterTemperaturePicker);
		descriptionEditText = (EditText) findViewById(R.id.descriptionEditText);
		
		initTagListView();
		
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
				coffeeReceipt.setTitle( getValueString(titleEditText) )
							.setCoffeeAmount( getValueDouble(coffeeAmountPicker) )
							.setWaterAmount( getValueDouble(waterAmountPicker) )
							.setWaterTemperature( getValueDouble(waterTemperaturePicker) )
							.setDescription( descriptionEditText.getText().toString() );
				coffeeReceipt.save(CoffeeReceiptActivity.this);
				finish();
			}
		});
	}
	
	private void fillLayout() {
		titleEditText.setText( coffeeReceipt.getTitle() );
		coffeeAmountPicker.setText( ""+coffeeReceipt.getCoffeeAmount() );
		waterAmountPicker.setText( ""+coffeeReceipt.getWaterAmount() );
		waterTemperaturePicker.setText( ""+coffeeReceipt.getWaterTemperature() );
		descriptionEditText.setText( coffeeReceipt.getDescription() );
	}
	
	private String getValueString(EditText editText) {
		return editText.getText().toString();
	}
	private double getValueDouble(EditText editText) {
		return Double.parseDouble(editText.getText().toString());
	}
	
	private void initTagListView() {
		adapter = new TagListArrayAdapter(this, coffeeReceipt.getTags());
		adapter.setNotifyOnChange(true);
		ListView listView = (ListView) findViewById(R.id.tagListView);
		listView.setAdapter(adapter);
		
		newTagEditText = (EditText) findViewById(R.id.newTagEditText);
		((Button) findViewById(R.id.addNewTagButton)).setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				adapter.add( new Tag( getValueString(newTagEditText) ) );
				adapter.notifyDataSetChanged();
				newTagEditText.setText("");
			}
		});
	}
	
}
