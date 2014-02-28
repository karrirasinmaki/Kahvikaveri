package fi.raka.coffeebuddy;

import fi.raka.coffeebuddy.R.id;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;

public class CoffeeBrewIconSelectDialog extends DialogFragment {
	
	private OnReturnDataListener onReturnDataListener;
	private Context context;
	
	private int[] resIds = {
		R.drawable.aeropress,
		R.drawable.chemex,
		R.drawable.coffee_maker,
		R.drawable.mocca_master,
		R.drawable.espresso,
		R.drawable.french_press,
		R.drawable.manual
	};
	
	public CoffeeBrewIconSelectDialog() {}
	

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {		
		context = getActivity().getApplicationContext();
		
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		
		LayoutInflater inflater = getActivity().getLayoutInflater();
		ViewGroup view = (ViewGroup) inflater.inflate(R.layout.icon_select_view, null);
		
		LinearLayout[] rows = {
			(LinearLayout) view.findViewById( R.id.firstRow ),
			(LinearLayout) view.findViewById( R.id.secondRow )
		};
		for(int i=0, l=resIds.length; i<l; ++i) {
			rows[i % rows.length].addView(createImageButton(resIds[i]));
		}
		
		builder.setView(view);		
		return builder.create();
	}
/*
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		context = inflater.getContext();
		
		ViewGroup view = (ViewGroup) inflater.inflate(R.layout.icon_select_view, null);
		
		LinearLayout[] rows = {
			(LinearLayout) view.findViewById( R.id.firstRow ),
			(LinearLayout) view.findViewById( R.id.secondRow )
		};
		for(int i=0, l=resIds.length; i<l; ++i) {
			rows[i % rows.length].addView(createImageButton(resIds[i]));
		}
		
		return view;
	}
	*/
	
	/**
	 * Creates new ImageButton and sets OnClickListener
	 * @param resId of drawable
	 * @return new ImageButton
	 */
	private ImageButton createImageButton(int resId) {
		ImageButton imageButton = new ImageButton(context);
		imageButton.setImageResource(resId);
		imageButton.setTag(resId);
		imageButton.setOnClickListener(onClickBrewIconListener);
		return imageButton;
	}
	
	/**
	 * On brew icon click listener
	 */
	View.OnClickListener onClickBrewIconListener = new View.OnClickListener() {
		public void onClick(View v) {
			brewIconClick(v);
		}
	};
	
	/**
	 * On brew icon click
	 * @param v View clicked
	 */
	public void brewIconClick(View v) {
		if(onReturnDataListener != null) {
			onReturnDataListener.onEvent( (Integer) v.getTag() );
		}
	}
	
	/**
	 * Sets OnReturnDataListener
	 * @param listener
	 */
	public void setOnReturnDataListener(OnReturnDataListener listener) {
		onReturnDataListener = listener;
	}
	
	/**
	 * OnReturnDataListener
	 */
	public interface OnReturnDataListener {
		/**
		 * Fired on event
		 * @param drawableResId drawable resId
		 */
		public void onEvent(Integer drawableResId);
	}
	
}
