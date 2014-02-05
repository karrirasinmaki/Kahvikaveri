package fi.raka.coffeebuddy;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import fi.raka.coffeebuddy.logic.CListItem;
import fi.raka.coffeebuddy.logic.CoffeeReceipt;

public class ListActivity extends Activity {
	private ArrayList<CListItem> listItems;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        refreshReceiptList();
        
        // New receipt -button
        Button newReceipt = (Button) findViewById(R.id.newReceipt);
        newReceipt.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				startActivityForResult(new Intent(ListActivity.this, CoffeeWizardActivity.class), 1);
			}
		});
    }
    
    private void loadAllCoffeeReceipts() {
    	listItems = CoffeeReceipt.loadAll(getApplicationContext() );
    }
    
    private void refreshReceiptList() {
    	loadAllCoffeeReceipts();
        createList();
    }
    
    private void createList() {
        ListView listView = (ListView) findViewById(R.id.coffeeReceiptList);
        ReceiptArrayAdapter adapter = new ReceiptArrayAdapter(this, listItems);
        listView.setAdapter( adapter );
        listView.setOnItemClickListener(listViewItemClickListener);
    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    	if(requestCode == 1) {
    		if(resultCode == RESULT_OK) {
    			refreshReceiptList();
    		}
    	}
    }
    
    OnItemClickListener listViewItemClickListener = new OnItemClickListener() {
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			CoffeeReceipt coffeeReceipt = (CoffeeReceipt) parent.getItemAtPosition(position);
			Intent intent = new Intent(ListActivity.this, CoffeeReceiptActivity.class);
			intent.putExtra("CoffeeReceiptId", coffeeReceipt.getId());
			startActivity(intent);
		}
	};
}
