/**
 * Lists receipts.
 */

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
	private ListView listView;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        listItems = new ArrayList<CListItem>();
        
        initList();
        refreshReceiptList();
        
        // New receipt -button
        Button newReceipt = (Button) findViewById(R.id.newReceipt);
        newReceipt.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				openCoffeeReceiptActivity();
			}
		});
    }
    
    /**
     * Loads all COffeeReceipts and store them to listItems
     */
    private void loadAllCoffeeReceipts() {
    	listItems = CoffeeReceipt.loadAll(getApplicationContext() );
    }
    
    /**
     * Load all CoffeeReceipts and notify adapter about data change to re-draw list
     */
    private void refreshReceiptList() {
    	loadAllCoffeeReceipts();
    	((ReceiptArrayAdapter) listView.getAdapter()).notifyDataSetChanged();
    }
    
    /**
     * Init listView. Attach adapter to it and set click event listeners
     */
    private void initList() {
        listView = (ListView) findViewById(R.id.coffeeReceiptList);
        ReceiptArrayAdapter adapter = new ReceiptArrayAdapter(this, listItems);
        listView.setAdapter( adapter );
        listView.setOnItemClickListener(listViewItemClickListener);
    }
    
    /**
     * Open CoffeeReceipt in CoffeeReceiptActivity.
     * Starts activity for result.
     * @param id of CoffeeReceipt to open
     */
    private void openCoffeeReceiptActivity(Integer id) {
		Intent intent = new Intent(ListActivity.this, CoffeeReceiptActivity.class);
		intent.putExtra("CoffeeReceiptId", id);
		startActivityForResult(intent, 1);
    }
    /**
     * Open CoffeeReceipt in CoffeeReceiptActivity.
     * Starts activity for result.
     * @param coffeeReceipt to open
     */
    private void openCoffeeReceiptActivity(CoffeeReceipt coffeeReceipt) {
    	openCoffeeReceiptActivity( coffeeReceipt.getId() );
    }
    /**
     * Open new, empty CoffeeReceipt in CoffeeReceiptActivity.
     * Starts activity for result.
     */
    private void openCoffeeReceiptActivity() {
    	openCoffeeReceiptActivity( -1 );
    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    	if(requestCode == 1) {
    		if(resultCode == RESULT_OK) {
    			refreshReceiptList();
    		}
    	}
    }
    
    /**
     * List item on click. Opens CoffeeReceipt on click.
     * Calls openCoffeeReceiptActivity on click.
     */
    OnItemClickListener listViewItemClickListener = new OnItemClickListener() {
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			openCoffeeReceiptActivity( (CoffeeReceipt) parent.getItemAtPosition(position) );
		}
	};
}
