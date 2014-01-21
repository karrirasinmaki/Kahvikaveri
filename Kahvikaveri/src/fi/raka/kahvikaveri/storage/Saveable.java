package fi.raka.kahvikaveri.storage;

import android.content.Context;

public interface Saveable {
	/**
	 * Saves object to storage
	 * @param context
	 * @return String id
	 */
	String save(Context context);
	void load(Context context);
}
