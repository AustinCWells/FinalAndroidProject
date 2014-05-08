package com.example.androidfinal;

import java.util.ArrayList;
import java.util.StringTokenizer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ProgressBar;
import android.widget.TextView;

/** The system calls this to perform work in the UI thread and delivers
	      * the result from doInBackground() */
public class MainActivity extends ActionBarActivity {
	private ArrayList<ExpandListGroup> ExpListItems;
	private ExpandListAdapter ExpAdapter;
	private ExpandableListView ExpandList;
	private ProgressBar progressSpinner;
	Context mContext;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mContext = this;
		ExpListItems = new ArrayList<ExpandListGroup>();
		progressSpinner = (ProgressBar) findViewById(R.id.progressSpinner);
		new loadJobsTask().execute();
		
		Button searchButton = (Button) findViewById(R.id.GO);
		Button allButton = (Button) findViewById(R.id.allButton);
		allButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				MainActivity.this.ExpAdapter.getFilter().filter("all");
			}
		});
		searchButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
					loadOptions();
				
			}
		});
		
		
    }
    
 
	
	private void loadOptions(){
		 OptionsPicker.createAndShow(getFragmentManager());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	public void onOptionsSelected(Boolean chain, Boolean wifi, Boolean events, Boolean coffee, Boolean food, Integer cost, String atmosphere, Integer rating){
		String constraints = chain.toString() + "|" + wifi.toString() + "|" + events.toString() + "|" + food.toString() + "|" + cost.toString() + "|" + atmosphere.toString() + "|" + rating.toString();
		MainActivity.this.ExpAdapter.getFilter().filter(constraints);
	}

	

	private class loadJobsTask extends AsyncTask<String, Void, String> {
	    /** The system calls this to perform work in a worker thread and
	      * delivers it the parameters given to AsyncTask.execute() */
	    protected String doInBackground(String... urls) {
	    	String json = "";
	    	
	    	try {
	    		//get JSON of the possible locations 
	    		json = getJobsString();
				JSONArray locationsArray = new JSONArray(json);
				
				//add locations to array list
				for(int i = 0; i < locationsArray.length(); i++){
					JSONObject location = locationsArray.getJSONObject(i);
					String name = location.getString("name");
			  		String address = location.getString("address");
			  		String phone = location.getString("phone");
			  		String weekday_hours = location.getString("weekday_hours");
			  		String weekend_hours = location.getString("weekend_hours");
			  		Integer rating = location.getInt("rating");
			  		Integer price = location.getInt("price");
			  		Boolean hasCoffee = location.getBoolean("coffee");
			  		Boolean hasFood = location.getBoolean("food");
			  		Boolean isChain = location.getBoolean("chain");
			  		Boolean hasWifi = location.getBoolean("wifi");
			  		Boolean hasEvents = location.getBoolean("events");
			  		String atmosphere = location.getString("atmosphere"); 
			  		Log.i("ACW","Adding: " + name);
			  		ExpListItems.add(new ExpandListGroup(new Location(name,address,phone,weekday_hours,weekend_hours,rating,price,hasCoffee,hasFood,isChain,hasWifi,hasEvents,atmosphere)));
			        
				}
				
			} catch (JSONException e) {
				e.printStackTrace();
			}
	    	return "";
	    }
	    
	    /** The system calls this to perform work in the UI thread and delivers
	      * the result from doInBackground() */
	    protected void onPostExecute(String loginResult) {
	    	ProgressBar progressView = (ProgressBar) findViewById(R.id.progressSpinner);
	    	progressView.setVisibility(View.GONE);
	    	ExpandList = (ExpandableListView) findViewById(R.id.ExpList);
	        ExpAdapter = new ExpandListAdapter(MainActivity.this, ExpListItems);
	        ExpandList.setAdapter(ExpAdapter);
	    }
	    
	    private String getJobsString() {
	    	return "[ { \"name\": \"Crooked Tree Coffeehouse\", \"address\": \"2414 Routh St, Dallas, TX 75201\", \"phone\": \"(214) 952-1142\", \"weekday_hours\": \" 6:00am-11:00pm\", \"weekend_hours\": \"7:00am-12:00am\", \"rating\": 4.5, \"price\": 2, \"coffee\": true, \"food\": true, \"chain\": false, \"wifi\": true, \"events\": true, \"atmosphere\": \"cozy\" }, { \"name\": \"Murray Street Coffee Shop\", \"address\": \"103 MurraySt Dallas TX75226\", \"phone\": \"(214)655-2808\", \"weekday_hours\": \"7: 00am-7: 00pm\", \"weekend_hours\": \"closed\", \"rating\": 4.4, \"price\": 2, \"coffee\": true, \"food\": true, \"chain\": false, \"wifi\": true, \"events\": true, \"atmosphere\": \"modern\" }, { \"name\": \"Klyde Warren Park\", \"address\": \"2012 WoodallRodgersFwy Dallas TX75201\", \"phone\": \"(214)716-4500\", \"weekday_hours\": \"6: 00am-11: 00pm\", \"weekend_hours\": \"6: 00am-11: 00pm\", \"rating\": 4.6, \"price\": 0, \"coffee\": false, \"food\": true, \"chain\": false, \"wifi\": false, \"events\": true, \"atmosphere\": \"outdoors\" }, { \"name\": \"Coffee House Cafe\", \"address\": \"6150 Frankford Rd. Dallas Tx\", \"phone\": \"(469) 248-0554\", \"weekday_hours\": \"7:00 am - 4:00 pm\", \"weekend_hours\": \"7:00 am - 4:00 pm\", \"rating\": 4, \"price\": 2, \"coffee\": true, \"food\": true, \"chain\": false, \"wifi\": true, \"events\": false, \"atmosphere\": \"restaurant\" }, { \"name\": \"Audelia Road Branch Library\", \"address\": \"10045 Audelia Rd Dallas Tx \", \"phone\": \"(214) 670-1350\", \"weekday_hours\": \"12:00pm - 8:00pm\", \"weekend_hours\": \"10:00pm - 6:00pm\", \"rating\": 3.6, \"price\": 0, \"coffee\": false, \"food\": false, \"chain\": false, \"wifi\": true, \"events\": true, \"atmosphere\": \"modern\" }, { \"name\": \"Half Price Books\", \"address\": \"5803 E Northwest Hwy, Dallas, Tx 75231\", \"phone\": \"(214) 379-8000\", \"weekday_hours\": \"9:00am - 11:00pm\", \"weekend_hours\": \"9:00am-11: 00pm\", \"rating\": 4.4, \"price\": 1, \"coffee\": true, \"food\": false, \"chain\": true, \"wifi\": true, \"events\": false, \"atmosphere\": \"busy\" }, { \"name\": \"FlyingHorseCage-LiquidCulture\", \"address\": \"1401ACommerceSt.DallasTx75201\", \"phone\": \"(214)741-1731\", \"weekday_hours\": \"8: 00am-9: 00pm\", \"weekend_hours\": \"7: 00am-8: 00pm\", \"rating\": 4.5, \"price\": 1, \"coffee\": true, \"food\": false, \"chain\": false, \"wifi\": true, \"events\": true, \"atmosphere\": \"cozy\" }, { \"name\": \"OakLawnCoffee\", \"address\": \"2720OakLawnAve\", \"phone\": \"2720OakLawnAve\", \"weekday_hours\": \"6: 00am-9: 00pm\", \"weekend_hours\": \"10: 00am-5: 00pm\", \"rating\": 4.3, \"price\": 1, \"coffee\": true, \"food\": true, \"chain\": false, \"wifi\": true, \"events\": false, \"atmosphere\": \"modern\" }, { \"name\": \"TheWildDetectives\", \"address\": \"314W8thSt.DallasTX75208\", \"phone\": \"(214)942-0108\", \"weekday_hours\": \"9: 00am-12: 00am\", \"weekend_hours\": \"9: 00am-6: 00pm\", \"rating\": 5, \"price\": 1, \"coffee\": true, \"food\": true, \"chain\": false, \"wifi\": true, \"events\": true, \"atmosphere\": \"busy\" }, { \"name\": \"Mudsmith\", \"address\": \"2114GreenvilleAve\", \"phone\": \"(214)370-9535\", \"weekday_hours\": \"7: 00am-9: 00pm\", \"weekend_hours\": \"7: 00am-8: 00pm\", \"rating\": 4.4, \"price\": 2, \"coffee\": true, \"food\": true, \"chain\": true, \"wifi\": true, \"events\": true, \"atmosphere\": \"modern\" }, { \"name\": \"VelvetTaco\", \"address\": \"3012NHendersonAve\", \"phone\": \"(214)823-8358\", \"weekday_hours\": \"7: 00am-11: 00pm\", \"weekend_hours\": \"7: 00am-11: 00pm\", \"rating\": 4, \"price\": 1, \"coffee\": true, \"food\": true, \"chain\": true, \"wifi\": true, \"events\": false, \"atmosphere\": \"busy\" }, { \"name\": \"CafeBrownSugar\", \"address\": \"2625OldDentonRd.\", \"phone\": \"(972)446-6633\", \"weekday_hours\": \"6: 00am-7: 00pm\", \"weekend_hours\": \"6: 00am-5: 00pm\", \"rating\": 3.5, \"price\": 2, \"coffee\": true, \"food\": true, \"chain\": true, \"wifi\": true, \"events\": true, \"atmosphere\": \"restaurant\" }, { \"name\": \"BuzzbrewsKitchen\", \"address\": \"4334LemmonAve\", \"phone\": \"(214)521-4344\", \"weekday_hours\": \"24hours\", \"weekend_hours\": \"24hours\", \"rating\": 3.3, \"price\": 1, \"coffee\": true, \"food\": true, \"chain\": true, \"wifi\": true, \"events\": false, \"atmosphere\": \"busy\" }, { \"name\": \"CrookedTreeCoffeehouse\", \"address\": \"2414RouthSt.\", \"phone\": \"(214)952-1142\", \"weekday_hours\": \"7: 00am-9: 00pm\", \"weekend_hours\": \"7: 00am-9: 00pm\", \"rating\": 5, \"price\": 1, \"coffee\": true, \"food\": false, \"chain\": false, \"wifi\": true, \"events\": false, \"atmosphere\": \"Busy\" }, { \"name\": \"DallasHallLawn\", \"address\": \"6024AirlineRoad\", \"phone\": \"(214)768-2000\", \"weekday_hours\": \"24Hours\", \"weekend_hours\": \"24Hours\", \"rating\": 5, \"price\": 0, \"coffee\": false, \"food\": false, \"chain\": false, \"wifi\": false, \"events\": false, \"atmosphere\": \"outdoors\" }, { \"name\": \"MeadowsSchooloftheArts\", \"address\": \"6101BishopBlvd\", \"phone\": \"(214)768-2000\", \"weekday_hours\": \"24Hours\", \"weekend_hours\": \"24Hours\", \"rating\": 5, \"price\": 0, \"coffee\": true, \"food\": true, \"chain\": false, \"wifi\": true, \"events\": true, \"atmosphere\": \"cozy\" } ]";
	    }
	  
	    
	   
	    
	}
	
	  public class Location {
  		String name;
  		String address;
  		String phone;
  		String weekday_hours;
  		String weekend_hours;
  		Integer rating;
  		Integer price;
  		Boolean hasCoffee;
  		Boolean hasFood;
  		Boolean isChain;
  		Boolean hasWifi;
  		Boolean hasEvents;
  		String atmosphere; 
  	

	  	public Location(String name,
						String address,
						String phone,
						String weekday_hours,
						String weekend_hours,
						Integer rating,
						Integer price,
						Boolean hasCoffee,
						Boolean hasFood,
						Boolean isChain,
						Boolean hasWifi,
						Boolean hasEvents,
						String atmosphere){
		  		this.name = name;
		  		this.address = address;
		  		this.phone = phone;
		  		this.weekday_hours = weekday_hours;
		  		this.weekend_hours = weekend_hours;
		  		this.rating = rating; 
		  		this.price = price;
		  		this.hasCoffee = hasCoffee;
		  		this.hasFood = hasFood;
		  		this.isChain = isChain;
		  		this.hasWifi = hasWifi;
		  		this.hasEvents = hasEvents;
		  		this.atmosphere = atmosphere; 
	  	}
  }
	  
	
		public class ExpandListGroup {
		 
			private String Name;
			private Location location;
			
			ExpandListGroup(Location location){
				this.Name = location.name;
				this.location = location;
			}
			
			public String getName() {
				return Name;
			}
			public void setName(String name) {
				this.Name = name;
			}
			public Location getLocation() {
				return location;
			}
			public void setLocation(Location location) {
				this.location = location;
			}
			
			
		}
		
	
		public class ExpandListAdapter extends BaseExpandableListAdapter implements Filterable {

			private Context context;
			private ArrayList<ExpandListGroup> groups;
			public ExpandListAdapter(Context context, ArrayList<ExpandListGroup> groups) {
				this.context = context;
				this.groups = groups;
			}
			
			public void addItem(Location item, ExpandListGroup group) {
				if (!groups.contains(group)) {
					groups.add(group);
				}
				int index = groups.indexOf(group);
				groups.get(index).setLocation(item);
			}
		
			public Object getChild(int groupPosition, int childPosition) {

				return groups.get(groupPosition).getLocation();
				
			}

			public long getChildId(int groupPosition, int childPosition) {
				// TODO Auto-generated method stub
				return childPosition;
			}

			public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View view,
					ViewGroup parent) {
				Location child = (Location) getChild(groupPosition, childPosition);
				if (view == null) {
					LayoutInflater infalInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
					view = infalInflater.inflate(R.layout.expandlist_child_item, null);
				}
				
				
				
				TextView addressView = (TextView) view.findViewById(R.id.address);
				TextView phoneView = (TextView) view.findViewById(R.id.phone);
				TextView weekdayHoursView = (TextView) view.findViewById(R.id.weekday_hours);
				TextView ratingView = (TextView) view.findViewById(R.id.rating);
				TextView priceView = (TextView) view.findViewById(R.id.price);
				 
				 
				
				addressView.setText(child.address);
				phoneView.setText(child.phone);
				weekdayHoursView.setText(child.weekday_hours);
				ratingView.setText("rating: " + child.rating.toString() + "/5");
				String dollarSigns = ""; 
				for(int i = 0; i < child.price; i++){
					dollarSigns += "$";
				}
				if(child.price == 0 ){
					dollarSigns = "free!";
				}
				priceView.setText("price: " + dollarSigns);
				
				final String statAddress = child.address;
				addressView.setOnClickListener(new View.OnClickListener() {
					
					@Override
					public void onClick(View v) {
						Intent intent = new Intent(android.content.Intent.ACTION_VIEW, 
							    Uri.parse("http://maps.google.com/maps?saddr=&daddr=" + statAddress));
							startActivity(intent);
						
					}
				});
				String posted_by = child.phone;
				final String uri = "tel:" + posted_by.trim() ;
				phoneView.setOnClickListener(new View.OnClickListener() {
					
					@Override
					public void onClick(View v) {
						 Intent intent = new Intent(Intent.ACTION_DIAL);
						 intent.setData(Uri.parse(uri));
						 startActivity(intent);
						
					}
				});
				
				return view;
			}


			public Object getGroup(int groupPosition) {
				// TODO Auto-generated method stub
				return groups.get(groupPosition);
			}

			public int getGroupCount() {
				// TODO Auto-generated method stub
				return groups.size();
			}

			public long getGroupId(int groupPosition) {
				// TODO Auto-generated method stub
				return groupPosition;
			}

			public View getGroupView(int groupPosition, boolean isLastChild, View view,
					ViewGroup parent) {
				ExpandListGroup group = (ExpandListGroup) getGroup(groupPosition);
				if (view == null) {
					LayoutInflater inf = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
					view = inf.inflate(R.layout.expandlist_group_item, null);
				}
				Log.i("ACW", "Group name: " + group.getName());
				TextView groupView = (TextView) view.findViewById(R.id.studyLocationGroup);
				groupView.setText(group.getName());
				// TODO Auto-generated method stub
				return view;
			}

			public boolean hasStableIds() {
				// TODO Auto-generated method stub
				return true;
			}

			public boolean isChildSelectable(int arg0, int arg1) {
				// TODO Auto-generated method stub
				return true;
			}


			@Override
			public int getChildrenCount(int groupPosition) {
				// TODO Auto-generated method stub
				return 1;
			}
			
			@Override
		    public Filter getFilter() {

		        Filter filter = new Filter() {

		            @SuppressWarnings("unchecked")
		            @Override
		            protected void publishResults(CharSequence constraint, FilterResults results) {

		            	 groups = ( ArrayList<ExpandListGroup>) results.values;
		                notifyDataSetChanged();
		            }

		            @Override
		            protected FilterResults performFiltering(CharSequence constraint) {
		            	groups = ExpListItems;
		            	 FilterResults results = new FilterResults();
		            	if(constraint.toString().contains("all")){
		            		 results.count = groups.size();
				             results.values = groups;				             
				             return results;
		            	}
		               
		                ArrayList<ExpandListGroup> FilteredArrayNames = new ArrayList<ExpandListGroup>();

		                // perform a search
		               
		        		StringTokenizer st = new StringTokenizer(constraint.toString(), "|");
		        		Boolean isChain = Boolean.valueOf(st.nextToken()); 
		        		Boolean hasWifi = Boolean.valueOf(st.nextToken());
		        		Boolean hasEvents = Boolean.valueOf(st.nextToken());
		        		Boolean hasFood = Boolean.valueOf(st.nextToken());
		        		Integer cost = Integer.valueOf(st.nextToken()); 
		        		String atmosphere = st.nextToken(); 
		        		Integer rating = Integer.valueOf(st.nextToken());
		                
		                
		                constraint = constraint.toString().toLowerCase();
		                for (int i = 0; i < groups.size(); i++) {
		                	ExpandListGroup groupItem = groups.get(i);
		                	Location location = groupItem.getLocation();
		                    if (location.isChain == isChain && location.hasWifi == hasWifi && location.hasEvents == hasEvents && location.hasFood == hasFood )  {
		                    	
		                    	// auto set if we're ignoring
		                    	if(cost == 0) {
		                    		cost = 5;		
		                    	} else {
		                    		cost -= 1; 
		                    	}
		                    	
		                    	if(rating == 1 ){
		                    		rating = 0; 
		                    	} else {
		                    		rating -= 1; 
		                    	}
		                    	// ignore atmosphere
		                    	if(atmosphere.contains("no preference"))
		                    	{
		                    		if(location.price < cost && location.rating > rating )
		                    			FilteredArrayNames.add(groupItem);
		                    	} else {
		                    		if ( location.price < cost && location.rating > rating && location.atmosphere.contains(atmosphere))
		                    			FilteredArrayNames.add(groupItem);
		                    	}
		                    	
		                    	
		                    }
		                }

		                results.count = FilteredArrayNames.size();
		                results.values = FilteredArrayNames;
		
		                return results;
		            }

				
				
		        };
		        
		        return (android.widget.Filter) filter;
		    }
			
			
		}
		
	

}
