package com.example.androidfinal;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;
import android.widget.Switch;


public class OptionsPicker extends DialogFragment implements DialogInterface.OnClickListener {
    private static final String sFragmentTag = "OptionsPicker";
    private static View mView; 
    
    public static OptionsPicker createAndShow(FragmentManager manager) {
        Fragment frag = manager.findFragmentByTag(sFragmentTag);
        if (null != frag) {
            // Already have an instance of this dialog, don't make another
            return (OptionsPicker) frag;
        }
        OptionsPicker df = new OptionsPicker();
        df.show(manager, sFragmentTag);
        return df;
    }

    public static boolean isActive(FragmentManager manager) {
        return manager.findFragmentByTag(sFragmentTag) != null;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        //Use our best friend LayoutInflater to inflate the date picker
        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.options_picker, (ViewGroup) getView(), false);
        mView = v;
        //Create a dialog with a done button
        AlertDialog dialog = new AlertDialog.Builder(getActivity())
        .setPositiveButton("Done", this)
        .create();

       
        //Put the view inside the dialog
        dialog.setView(v);
        dialog.setTitle("Select Study Options"); //Use a string from resources
        //Make the dialog close when you touch outside of it
        dialog.setCanceledOnTouchOutside(true);
        return dialog;
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        if (which == Dialog.BUTTON_POSITIVE) {
        	Boolean chain = false;
        	Boolean wifi = false;
        	Boolean events = false;
        	Boolean coffee = false;
        	Boolean food = false;
        	Integer price = 1;
        	String atmosphere = "cozy";
        	Integer rating = 1;
          
        	Switch chainSwitch = (Switch) mView.findViewById(R.id.chainOption);
        	Switch wifiSwitch = (Switch) mView.findViewById(R.id.wifiOption);
        	Switch eventsSwitch = (Switch) mView.findViewById(R.id.eventsOption);
        	Switch coffeeSwitch = (Switch) mView.findViewById(R.id.coffeeOption);
        	Switch foodSwitch = (Switch) mView.findViewById(R.id.foodOption);
        	Spinner priceSpinner = (Spinner) mView.findViewById(R.id.priceOption);
        	Spinner atmosphereSpinner = (Spinner) mView.findViewById(R.id.atmosphereOption);
        	Spinner ratingSpinner = (Spinner) mView.findViewById(R.id.ratingOption);
        	
        	chain = chainSwitch.isChecked();
        	wifi = wifiSwitch.isChecked();
        	events = eventsSwitch.isChecked();
        	coffee = coffeeSwitch.isChecked();
        	food = foodSwitch.isChecked();
        	price = priceSpinner.getSelectedItemPosition() + 1; 
        	rating = ratingSpinner.getSelectedItemPosition() + 1;
        	atmosphere = atmosphereSpinner.getSelectedItem().toString();
            
        	
        	
            //getActivity() will get the activity hosting this fragment. In this case we assume
            //its CalendarActivity and explicitly cast it. This could be bad if we're wrong
            ((MainActivity)getActivity()).onOptionsSelected(chain,wifi,events,coffee,food,price,atmosphere,rating);
        }
        
    }
}

