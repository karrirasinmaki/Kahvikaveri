package fi.raka.coffeebuddy.storage;

import android.provider.BaseColumns;

public final class ReceiptContract {

	public ReceiptContract() {}
	
	public static abstract class ReceiptEntry implements BaseColumns {
		public static final String TABLE_NAME = "receipts",
								   COLUMN_NAME_ID = "receiptid",
								   COLUMN_NAME_TITLE = "title",
								   COLUMN_NAME_WATER_AMOUNT = "wateramount",
								   COLUMN_NAME_WATER_TEMPERATURE = "watertemperature",
								   COLUMN_NAME_COFFEE_AMOUNT = "coffeeamount";
	}
}
