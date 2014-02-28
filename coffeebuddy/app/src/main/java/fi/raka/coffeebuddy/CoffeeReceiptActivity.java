/**
 * Shows single receipt. Allows editing and using CoffeeWizard.
 */

package fi.raka.coffeebuddy;

import fi.raka.coffeebuddy.CoffeeBrewIconSelectDialog.OnReturnDataListener;
import fi.raka.coffeebuddy.logic.CoffeeReceipt;
import fi.raka.coffeebuddy.logic.CoffeeWizard;
import fi.raka.coffeebuddy.logic.Tag;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.EditText;
import android.widget.Button;
import android.widget.ImageButton;

public class CoffeeReceiptActivity extends MyActivity {
		
	private CoffeeReceipt coffeeReceipt;
	private EditText titleEditText, coffeeAmountPicker, waterAmountPicker, waterTemperaturePicker, descriptionEditText, 
					newTagEditText;
	private ImageButton methodImageButton;
	private TagListArrayAdapter adapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Intent intent = getIntent();
		
		setContentView(R.layout.coffee_receipt_view);
		
		coffeeReceipt = CoffeeReceipt.load(intent.getIntExtra("CoffeeReceiptId", -1), this);
		initLayout();
		fillLayout();
	}
	
	private void setMethodImageButtonResId(int resId) {
		methodImageButton.setImageResource(resId);
		methodImageButton.setTag(resId);
	}
	
	/**
	 * Init layout. Set variables, adapters, clickEvents
	 */
	private void initLayout() {
		titleEditText = (EditText) findViewById(R.id.titleEditText);
		coffeeAmountPicker = (EditText) findViewById(R.id.coffeeAmountPicker);
		waterAmountPicker = (EditText) findViewById(R.id.waterAmountPicker);
		waterTemperaturePicker = (EditText) findViewById(R.id.waterTemperaturePicker);
		descriptionEditText = (EditText) findViewById(R.id.descriptionEditText);
		
		methodImageButton = (ImageButton) findViewById(R.id.methodImageButton);
		
		initTagListView();
		
		// Method selection image button
		methodImageButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				final CoffeeBrewIconSelectDialog dialog = new CoffeeBrewIconSelectDialog();
				dialog.setOnReturnDataListener(new OnReturnDataListener() {
					public void onEvent(Integer drawableResId) {
						setMethodImageButtonResId(drawableResId);
						dialog.dismiss();
					}
				});
				dialog.show(getFragmentManager(), "Select");
			}
		});
		
		// Acrid button
		((Button) findViewById(R.id.tasteButton)).setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				updateCoffeeReceipt();
				coffeeReceipt = CoffeeWizard.getBetterCoffee(coffeeReceipt, 1.2, 1.0);
				fillLayout();
			}
		});
		
		// Save button
		((Button) findViewById(R.id.saveButton)).setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				updateCoffeeReceipt();
				coffeeReceipt.save(getApplicationContext());
				setResult(Activity.RESULT_OK);
				finish();
			}
		});
		
		// Delete button
		((Button) findViewById(R.id.delButton)).setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				coffeeReceipt.delete(getApplicationContext());
				setResult(Activity.RESULT_OK);
				finish();
			}
		});
		
	}
	
	/**
	 * Updates coffeeReceipt with form data
	 */
	private void updateCoffeeReceipt() {
		coffeeReceipt.setTitle( getValueString(titleEditText) )
					.setCoffeeAmount( getValueDouble(coffeeAmountPicker) )
					.setWaterAmount( getValueDouble(waterAmountPicker) )
					.setWaterTemperature( getValueDouble(waterTemperaturePicker) )
					.setDescription( descriptionEditText.getText().toString() )
					.setTags( adapter.getValues() )
					.setMethodImageId((Integer) methodImageButton.getTag());
	}
	
	/**
	 * Fills layout with current CoffeeReceipt data.
	 */
	private void fillLayout() {
		titleEditText.setText( coffeeReceipt.getTitle() );
		coffeeAmountPicker.setText( ""+coffeeReceipt.getCoffeeAmount() );
		waterAmountPicker.setText( ""+coffeeReceipt.getWaterAmount() );
		waterTemperaturePicker.setText( ""+coffeeReceipt.getWaterTemperature() );
		descriptionEditText.setText( coffeeReceipt.getDescription() );
		
		setMethodImageButtonResId( coffeeReceipt.getMethodImageId() );
	}
	
	/**
	 * @param editText
	 * @return EditText's text value
	 */
	private String getValueString(EditText editText) {
		return editText.getText().toString();
	}
	/**
	 * @param editText
	 * @return EditText's parsed double value
	 */
	private double getValueDouble(EditText editText) {
		return Double.parseDouble(editText.getText().toString());
	}
	
	/**
	 * Init tag list view. Set adapter to view and set click event listeners
	 */
	private void initTagListView() {
		adapter = new TagListArrayAdapter(this, coffeeReceipt.getTags());
		adapter.setNotifyOnChange(true);
		ListLayout listView = (ListLayout) findViewById(R.id.tagListView);
		listView.setAdapter(adapter);
		
		newTagEditText = (EditText) findViewById(R.id.newTagEditText);
		((Button) findViewById(R.id.addNewTagButton)).setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				adapter.add( new Tag( getValueString(newTagEditText), coffeeReceipt.getId() ) );
				adapter.notifyDataSetChanged();
				newTagEditText.setText("");
			}
		});
	}
	
}
