/**
 * Holds info about table and column names used in Receipt storing
 */

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
								   COLUMN_NAME_COFFEE_AMOUNT = "coffeeamount",
								   COLUMN_NAME_DESCRIPTION = "description",
								   COLUMN_NAME_METHOD_IMAGE_ID = "methodimageid",
								   
								   RECEIPT_COLUMN_NAME_ID = "receiptid",
								   
								   TAGS_TABLE_NAME = "receipts_tags",
								   TAGS_COLUMN_NAME_TAG_NAME = "tag";
	}
}
