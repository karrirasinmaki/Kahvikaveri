package fi.raka.coffeebuddy;

import android.content.Context;
import android.database.DataSetObserver;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.LinearLayout;

public class ListLayout extends LinearLayout {
	
	private Adapter adapter;

	public ListLayout(Context context) {
		super(context);
	}
	public ListLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	public ListLayout(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}
	
	public void setAdapter(Adapter adapter) {
		this.adapter = adapter;
		initAdapter();
		drawChilds();
	}
	public Adapter getAdapter() {
		return adapter;
	}
	
	/**
	 * Registers new adapter dataSetObserver
	 */
	private void initAdapter() {
		adapter.registerDataSetObserver(dataSetObserver);
	}
	
    /**
     * Add all childs (every val in values) to parentView
     */
    private void drawChilds() {
		for(int i=0, l=adapter.getCount(); i<l; ++i) {
			addView( adapter.getView(i, null, this), i );
		}
    }
    
    /**
     * Observers dataset chages in adapter
     */
    private DataSetObserver dataSetObserver = new DataSetObserver() {
    	@Override
    	public void onChanged() {
    		super.onChanged();
    		removeAllViews();
        	drawChilds();
    	}
	};

}
