/**
 * Lists receipts.
 */

package fi.raka.coffeebuddy;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import fi.raka.coffeebuddy.logic.CListItem;
import fi.raka.coffeebuddy.logic.CoffeeReceipt;

public class ListActivity extends MyActivity {
	
	private ListView listView;
	private ReceiptArrayAdapter listViewAdapter;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        initList();
        refreshReceiptList();
        
        // New receipt -button
        addTopBarButton(R.string.add, 0, 0, new View.OnClickListener() {
			public void onClick(View v) {
				openCoffeeReceiptActivity();
			}
		}, ALIGN_RIGHT);
    }
    
    /**
     * Loads all CoffeeReceipts and store them to listItems
     */
    private void loadAllCoffeeReceipts() {
    	listViewAdapter.clear();
    	listViewAdapter.addAll( CoffeeReceipt.loadAll(getApplicationContext()) );
    }
    
    /**
     * Load all CoffeeReceipts, update and notify adapter about data change to re-draw list
     */
    private void refreshReceiptList() {
    	loadAllCoffeeReceipts();
    	listViewAdapter.notifyDataSetChanged();
    }
    
    /**
     * Init listView. Attach adapter to it and set click event listeners
     */
    private void initList() {
        listView = (ListView) findViewById(R.id.coffeeReceiptList);
        listViewAdapter = new ReceiptArrayAdapter(this, new ArrayList<CListItem>());
        listView.setAdapter( listViewAdapter );
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
