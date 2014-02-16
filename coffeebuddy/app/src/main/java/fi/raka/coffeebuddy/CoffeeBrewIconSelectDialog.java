package fi.raka.coffeebuddy;

import java.util.zip.Inflater;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class CoffeeBrewIconSelectDialog extends DialogFragment {

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {		
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		
		LayoutInflater inflater = getActivity().getLayoutInflater();
		ViewGroup view = (ViewGroup) inflater.inflate(R.layout.icon_select_view, null);
		
		builder.setView(view);
		
		return builder.create();
	}
	
}
