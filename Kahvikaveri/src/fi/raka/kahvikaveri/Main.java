package fi.raka.kahvikaveri;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

import fi.raka.kahvikaveri.logic.CListItem;
import fi.raka.kahvikaveri.logic.CoffeeReceipt;

public class Main extends Activity {
	
	private ArrayList<CListItem> listItems;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        listItems = new ArrayList<CListItem>();
        listItems.add( new CoffeeReceipt("Paulig") );
        listItems.add( new CoffeeReceipt("Mokka") );
        createList();
        
        // On click new receipt
        Button newReceipt = (Button) findViewById(R.id.newReceipt);
        newReceipt.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
		        listItems.add( new CoffeeReceipt("Katriina") );
		        createList();
			}
		});
    }
    
    private void createList() {
        ListView listView = (ListView) findViewById(R.id.coffeeReceiptList);
        ReceiptArrayAdapter adapter = new ReceiptArrayAdapter(this, listItems);
        listView.setAdapter( adapter );
    }
}
