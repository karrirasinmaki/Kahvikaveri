/**
 * Interface for items that can be saved to long term storage.
 */

package fi.raka.coffeebuddy.storage;

import android.content.Context;

public interface Saveable {
	/**
	 * Saves object to storage
	 * @param context
	 * @return Integer id
	 */
	Integer saveToDB(Context context);
	void loadFromDB(Context context);
}
