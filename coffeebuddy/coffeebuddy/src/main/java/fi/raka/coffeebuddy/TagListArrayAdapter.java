/**
 * Tag list view array adapter. Inflates custom list items.
 */

package fi.raka.coffeebuddy;

import java.util.ArrayList;

import fi.raka.coffeebuddy.logic.Tag;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.ImageButton;

public class TagListArrayAdapter extends ArrayAdapter<Tag> {
	
    private Context context;
    private ArrayList<Tag> values;
    private ViewGroup parentView;
    private final static int LAYOUT_ID = R.layout.tag_list_item;
    
    public TagListArrayAdapter(Context context, ArrayList<Tag> values) {
        super(context, LAYOUT_ID, values);
        this.context = context;
        this.values = values;
    }

    public View getView(int position, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) 
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(LAYOUT_ID, parent, false);
        
        TextView titleView = (TextView) rowView.findViewById( R.id.title );
        titleView.setText( values.get(position).getName() );
        
        ImageButton deleteButton = (ImageButton) rowView.findViewById(R.id.deleteButton);
        deleteButton.setTag( new TagPosition(position) );
        deleteButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				values.remove( ((TagPosition) v.getTag()).position );
				notifyDataSetChanged();
			}
		});
        
        return rowView;
    }
    
    private void drawChilds() {
		for(int i=0, l=getCount(); i<l; ++i) {
			parentView.addView(getView(i, parentView), i);
		}
    }
    
    public void addParentView(ViewGroup parent) {
    	parentView = parent;
    	drawChilds();
    }
    
    @Override
    public void notifyDataSetChanged() {
    	parentView.removeAllViews();
    	drawChilds();
    };
    
    class TagPosition {
    	public int position;
    	public TagPosition(int position) { this.position = position; }
    }
    
}