package fi.raka.kahvikaveri;

import fi.raka.kahvikaveri.logic.CoffeeReceipt;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

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
	}
	
	private void fillLayout() {
		((TextView) findViewById(R.id.title)).setText(coffeeReceipt.getTitle() );
	}
}
