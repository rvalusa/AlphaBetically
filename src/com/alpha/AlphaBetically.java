package com.alpha;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.LinearLayout.LayoutParams;

public class AlphaBetically extends Activity {
	protected static final String TAG = AlphaBetically.class.getName();
	private ArrayList<Listing> programsArrayList;
	private SeparatedListAdapter adapter;
	private ListView programsList;
	
	private ArrayList<Listing> arr_sort = new ArrayList<Listing>();
	private ArrayList<String> keys;
	private LinearLayout atozList;
	
	
	private static DataDBAdapter mDbHelper;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		atozList = (LinearLayout) findViewById(R.id.atozlist);
		ImageView addCustomCard = (ImageView) findViewById(R.id.add_custom_card);
		ImageView back = (ImageView) findViewById(R.id.back);
		
		programsList = (ListView) findViewById(R.id.programsList);
		
	
		
		Bitmap emptyLogo = BitmapFactory.decodeResource(getResources(),R.drawable.logo_placeholder);

		adapter = new SeparatedListAdapter(this);
		ArrayList<Listing> sectionProgramsList = new ArrayList<Listing>();
		keys = new ArrayList<String>();
		
		
		mDbHelper = new DataDBAdapter(this);
		mDbHelper.createDatabase();
		mDbHelper.open();
		programsArrayList = mDbHelper.completeProgramsList(emptyLogo);
		mDbHelper.close();
		
		String keyText = "";
		for (int i = 0; i < programsArrayList.size(); i++) {
			if (i == 0) {
				keyText = programsArrayList.get(i).getName().substring(0, 1);
			}
			if (!keyText.equalsIgnoreCase(programsArrayList.get(i).getName()
					.substring(0, 1))) {
				adapter.addSection(keyText, new ProgramsListAdapter(this,
						R.layout.programs_list_row, sectionProgramsList));
				keys.add(keyText);
				sectionProgramsList = new ArrayList<Listing>();
				sectionProgramsList.add(programsArrayList.get(i));
				keyText = programsArrayList.get(i).getName().substring(0, 1);
			} else {
				sectionProgramsList.add(programsArrayList.get(i));
			}
		}
		// last section
		adapter.addSection(keyText, new ProgramsListAdapter(this,
				R.layout.programs_list_row, sectionProgramsList));
		keys.add(keyText);
		programsList.setAdapter(adapter);

		for(int i=0;i<keys.size();i++)
		{
				TextView keyTextView = new TextView(AlphaBetically.this);
				keyTextView.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, 1f));
				keyTextView.setTextColor(0xFF6A737D);
				keyTextView.setTextSize(10);
				keyTextView.setText(keys.get(i).toUpperCase());
				keyTextView.setTag(keys.get(i).toUpperCase());
				keyTextView.setTypeface(null, Typeface.BOLD);
				keyTextView.setOnClickListener(new View.OnClickListener() {
					
					public void onClick(View v) {
						int selectionPosition = 0;
						for (int i = 0; i < keys.indexOf(v.getTag()); i++) {
							String currentKey = keys.get(i);
							int sectionCount = adapter.sections.get(currentKey)
									.getCount();
							selectionPosition = selectionPosition
									+ sectionCount;
						}
						selectionPosition = selectionPosition
								+ keys.indexOf(v.getTag());
						if (selectionPosition >= 0) {
							programsList.setSelection(selectionPosition);
						} else {
							programsList.setSelection(0);
						}
					}
				});
				atozList.addView(keyTextView);
		}

		
		addCustomCard.setOnClickListener(new View.OnClickListener() {
//			@Override
			public void onClick(View v) {
				
			}
		});

		back.setOnClickListener(new View.OnClickListener() {
//			@Override
			public void onClick(View v) {
				
				overridePendingTransition(R.anim.slide_left, R.anim.hold);
			}
		});
		
		
	}

	private TextWatcher filterTextWatcher = new TextWatcher() {
		public void afterTextChanged(Editable s) {
			
		}

		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {
			
		}

		public void onTextChanged(CharSequence s, int start, int before,
				int count) {
			
			arr_sort.clear();
			
			

			if(arr_sort.size() > 0)
			{
			String keyText = "";
			ArrayList<Listing> sectionProgramsList = new ArrayList<Listing>();
			adapter = new SeparatedListAdapter(AlphaBetically.this);
			Collections.sort(arr_sort);
			keys.clear();
			for (int i = 0; i < arr_sort.size(); i++) {
				if (i == 0) {
					keyText = arr_sort.get(i).getName().substring(0, 1);
				}
				if (!keyText.equalsIgnoreCase(arr_sort.get(i).getName()
						.substring(0, 1))) {
					adapter.addSection(keyText, new ProgramsListAdapter(
							AlphaBetically.this,
							R.layout.programs_list_row, sectionProgramsList));
					keys.add(keyText);
					sectionProgramsList = new ArrayList<Listing>();
					sectionProgramsList.add(arr_sort.get(i));
					keyText = arr_sort.get(i).getName().substring(0, 1);
				} else {
					sectionProgramsList.add(arr_sort.get(i));
				}
			}
			//last records
			adapter.addSection(keyText, new ProgramsListAdapter(AlphaBetically.this, R.layout.programs_list_row, sectionProgramsList));
			keys.add(keyText);

			programsList.setAdapter(adapter);
			atozList.removeAllViews();
			for(int i=0;i<keys.size();i++)
			{
				TextView keyTextView = new TextView(AlphaBetically.this);
				keyTextView.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, 1f));
				keyTextView.setTextColor(0xFF6A737D);
				keyTextView.setTextSize(10);
				keyTextView.setText(keys.get(i).toUpperCase());
				keyTextView.setTag(keys.get(i).toUpperCase());
				keyTextView.setTypeface(null, Typeface.BOLD);
				keyTextView.setOnClickListener(new View.OnClickListener() {
//					@Override
					public void onClick(View v) {
						int selectionPosition = 0;
						for (int i = 0; i < keys.indexOf(v.getTag()); i++) {
							String currentKey = keys.get(i);
							int sectionCount = adapter.sections.get(currentKey)
									.getCount();
							selectionPosition = selectionPosition
									+ sectionCount;
						}
						selectionPosition = selectionPosition
								+ keys.indexOf(v.getTag());
						if (selectionPosition >= 0) {
							programsList.setSelection(selectionPosition);
						} else {
							programsList.setSelection(0);
						}
					}
				});
				atozList.addView(keyTextView);
			}

			}else
			{
				String[] sectionProgramsList = new String[]{"", getResources().getString(R.string.nothingFound), ""};
				programsList.setAdapter(new ArrayAdapter<String>(AlphaBetically.this, R.layout.nothing_found, sectionProgramsList));
				Log.v(TAG, "Nothing found");
				atozList.removeAllViews();
			}
		}
	};

	private class ProgramsListAdapter extends ArrayAdapter<Listing> {

		private List<Listing> items;
		
		public ProgramsListAdapter(Context context, int textViewResourceId,
				List<Listing> items) {
			super(context, textViewResourceId, items);
			this.items = items;
		}

		public int getCount() {
			return items.size();
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View v = convertView;
			if (v == null) {
				LayoutInflater vi = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				v = vi.inflate(R.layout.programs_list_row, null);
			}
			v.setClickable(true);
			final Listing o = items.get(position);
			if (o != null) {
				LinearLayout row = (LinearLayout) v.findViewById(R.id.cardsListRowF);
				TextView programName = (TextView) v
						.findViewById(R.id.programName);
				programName.setText(o.getName());

				//ImageView programLogo = (ImageView) v
				//		.findViewById(R.id.programLogo);
				//programLogo.setImageBitmap(o.getLogoImage());

				v.setTag(o.getId());
				row.setOnClickListener(new View.OnClickListener() {
//					@Override
					public void onClick(View v) {
						
					}
				});
			}
			return v;
		}
	}

}
