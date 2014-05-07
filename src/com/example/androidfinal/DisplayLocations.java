//package com.example.androidfinal;
//
//import java.util.ArrayList;
//
//import android.content.Context;
//import android.os.Bundle;
//import android.support.v7.app.ActionBarActivity;
//import android.view.LayoutInflater;
//import android.view.Menu;
//import android.view.MenuItem;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ArrayAdapter;
//import android.widget.ListView;
//import android.widget.TextView;
//
//public class DisplayLocations extends ActionBarActivity {
//
//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		setContentView(R.layout.activity_display_locations);
//		LocationsAdapter adapter = new LocationsAdapter(this, MainActivity.mLocations);
//		ListView listView = (ListView) findViewById(R.id.myListView); 
//		listView.setAdapter(adapter);
//	}
//
//	@Override
//	public boolean onCreateOptionsMenu(Menu menu) {
//
//		// Inflate the menu; this adds items to the action bar if it is present.
//		getMenuInflater().inflate(R.menu.display_locations, menu);
//		return true;
//	}
//
//	@Override
//	public boolean onOptionsItemSelected(MenuItem item) {
//		// Handle action bar item clicks here. The action bar will
//		// automatically handle clicks on the Home/Up button, so long
//		// as you specify a parent activity in AndroidManifest.xml.
//		int id = item.getItemId();
//		if (id == R.id.action_settings) {
//			return true;
//		}
//		return super.onOptionsItemSelected(item);
//	}
//	
//	
//	public class LocationsAdapter extends ArrayAdapter<MainActivity.location>{
//		private ArrayList<MainActivity.location> locations;
//		private Context context;
//		public LocationsAdapter(Context context, ArrayList<MainActivity.location> locations){
//			
//			super(context, R.layout.location_item, locations); 
//			this.locations = locations;
//			this.context = context; 
//			 
//	
//		}
//		
//		@Override 
//		public View getView(final int position, View convertView, ViewGroup parent) {
//			
//			View v;
//			if (convertView == null) {
//				LayoutInflater inflater = LayoutInflater.from(context); 
//				v = inflater.inflate(R.layout.location_item, null); 
//				
//			} else {
//				v = convertView;
//			}
//			
//			TextView nameView = (TextView) v.findViewById(R.id.name);
//			TextView addressView = (TextView) v.findViewById(R.id.address);
//			TextView phoneView = (TextView) v.findViewById(R.id.phone);
//			TextView weekdayHoursView = (TextView) v.findViewById(R.id.weekday_hours);
//			TextView ratingView = (TextView) v.findViewById(R.id.rating);
//			TextView priceView = (TextView) v.findViewById(R.id.price);
//	
//			 
//			MainActivity.location currentLocation = locations.get(position); 
//			
//			nameView.setText("Name: " + currentLocation.name);
//			addressView.setText("Addres: " + currentLocation.address);
//			phoneView.setText("Phone: " + currentLocation.phone);
//			weekdayHoursView.setText("Weekday Hours: " + currentLocation.weekday_hours);
//			ratingView.setText("Rating: " + currentLocation.rating.toString() + "/5");
//			priceView.setText("Price: " + currentLocation.price.toString() + "/5");
//			
//			
//			
//			
//			return v;
//			
//		}
//	}
//}
