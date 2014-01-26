package fi.raka.kahvikaveri;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import fi.raka.kahvikaveri.logic.CListItem;

public class ReceiptArrayAdapter extends ArrayAdapter<CListItem> {
    
    private final Context context;
    private final ArrayList<CListItem> values;
    private final static int LAYOUT_ID = R.layout.coffee_receipt_list_item;
    
    public ReceiptArrayAdapter(Context context, ArrayList<CListItem> values) {
        super(context, LAYOUT_ID, values);
        this.context = context;
        this.values = values;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) 
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(LAYOUT_ID, parent, false);
        
        TextView titleView = (TextView) rowView.findViewById( R.id.title );
        titleView.setText( values.get(position).getTitle() );
        
        return rowView;
    }
    
}
