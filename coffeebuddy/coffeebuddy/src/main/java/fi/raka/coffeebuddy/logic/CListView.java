package fi.raka.coffeebuddy.logic;

import java.util.ArrayList;

public class CListView {
    
    private final ArrayList<CListItem> items;
    
    public CListView() {
        items = new ArrayList<CListItem>();
    }
            
    public void addItem(CListItem item) {
        items.add(item);
    }
    
    @Override
    public String toString() {
        String tmp = "";
        for(CListItem item : items) {
            tmp += item.toString() + "\n";
        }
        return tmp;
    }
}
