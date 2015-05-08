package com.hrd.stayconnect;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import DB.Contact;
import DB.DataBase_Helper;
import Utility.Const;
import Utility.Utils;
import adpter.ContactAdapter;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;

public class ContactsActivity extends Activity {

	ListView lv_contacts;
	ContactAdapter adapter;
	LinearLayout slidingmenu;
	EditText et_serach;
	Button btn_done;
	ArrayList<HashMap<String, String>> contactList;
	ArrayList<HashMap<String, String>> searchResults;
	int mSelectedItem;
	DataBase_Helper helper;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_contacts);
		slidingmenu = (LinearLayout) findViewById(R.id.slidingmenu);
		lv_contacts = (ListView) findViewById(R.id.lv_phone_contacts);
		et_serach = (EditText) findViewById(R.id.et_serach);
		btn_done = (Button) findViewById(R.id.btn_done);

		contactList = new ArrayList<HashMap<String, String>>();
		searchResults = new ArrayList<HashMap<String, String>>();
		slidingmenu.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				et_serach.setVisibility(View.VISIBLE);
			}
		});

		new read_contact().execute();

		helper = new DataBase_Helper(getApplicationContext());

		lv_contacts.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int pos,
					long arg3) {
				// TODO Auto-generated method stub
				mSelectedItem = pos;
				adapter.notifyDataSetChanged();

			}

		});
		btn_done.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				if (adapter.getSelectedString().size() > 0) {
					for (int i = 0; i < adapter.getSelectedString().size(); i++) {
						HashMap<String, String> conmap = new HashMap<String, String>();
						conmap.put(
								"name",
								Const.allContacts.get(
										Integer.parseInt(adapter
												.getSelectedString().get(i)))
										.get("name"));
						conmap.put(
								"num",
								Const.allContacts.get(
										Integer.parseInt(adapter
												.getSelectedString().get(i)))
										.get("num"));

						conmap.put(
								"contacted",
								Const.allContacts.get(
										Integer.parseInt(adapter
												.getSelectedString().get(i)))
										.get("contacted"));
						contactList.add(conmap);

						List<Contact> group_time = helper.getAllGroup();

						for (Contact hm : group_time) {

							if (Const.GROUPNAME.equals(hm.getType())) {
								helper.addContact(new Contact(Const.allContacts
										.get(Integer.parseInt(adapter
												.getSelectedString().get(i)))
										.get("name"), Const.GROUPNAME,
										Const.allContacts.get(
												Integer.parseInt(adapter
														.getSelectedString()
														.get(i))).get(
												"contacted"), Const.allContacts
												.get(Integer.parseInt(adapter
														.getSelectedString()
														.get(i))).get("num"),
										hm.getGroup_time()));

								List<Contact> mylist_one = helper
										.getAllContacts();

								for (Contact my : mylist_one) {

									Calendar cal = Calendar.getInstance();

									cal.add(Calendar.SECOND, Integer
											.parseInt(my.getInterval_time()));

									// cal.add(Calendar.SECOND, 30);

									// Create a new PendingIntent and add it to
									// the
									// AlarmManager
									Intent intent = new Intent(
											ContactsActivity.this,
											AlarmReceiverActivity.class);
									intent.putExtra("name", my.getName());

									PendingIntent pendingIntent = PendingIntent
											.getActivity(
													ContactsActivity.this,
													12345,
													intent,
													PendingIntent.FLAG_CANCEL_CURRENT);
									AlarmManager am = (AlarmManager) getSystemService(Activity.ALARM_SERVICE);
									am.set(AlarmManager.RTC_WAKEUP,
											cal.getTimeInMillis(),
											pendingIntent);

								}
							}

						}

					}
				}
				if (contactList.size() > 0) {
					Intent i = new Intent(ContactsActivity.this,
							GroupDetails.class);
					i.putExtra("contacts", contactList);
					System.out
							.println("::::::::::::::SELECTED CONTACT LIST:::::::::::::;"
									+ contactList.toString());
					startActivity(i);
				} else {

					Intent i = new Intent(ContactsActivity.this,
							GroupDetails.class);
					startActivity(i);
				}

				finish();
			}
		});

		et_serach.addTextChangedListener(new TextWatcher() {

			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// get the text in the EditText
				String searchString = et_serach.getText().toString();
				int textLength = searchString.length();

				// clear the initial data set
				searchResults.addAll(Const.allContacts);

				Const.allContacts.clear();

				for (int i = 0; i < searchResults.size(); i++) {

					String playerName = searchResults.get(i).get("name")
							.toString();

					if (textLength <= playerName.length()) {
						// compare the String in EditText with Names in the
						// ArrayList

						if (searchString.equalsIgnoreCase(playerName.substring(
								0, textLength))) {

							Const.allContacts.add(searchResults.get(i));

							System.out.println("MY LIST DATA "
									+ Const.allContacts.toString());

							adapter.notifyDataSetChanged();
						}
					}

				}

			}

			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {

			}

			public void afterTextChanged(Editable s) {

			}
		});

	}

	public class read_contact extends AsyncTask<String, Void, Void> {

		ProgressDialog prog;

		protected void onPreExecute() {
			prog = new ProgressDialog(ContactsActivity.this);
			prog.setMessage("Please Wait...");
			prog.setCanceledOnTouchOutside(isCancelled());
			prog.show();

		}

		@Override
		protected Void doInBackground(String... params) {
			// TODO Auto-generated method stub

			Utils.readContacts(ContactsActivity.this);
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub

			super.onPostExecute(result);
			prog.cancel();

			adapter = new ContactAdapter(ContactsActivity.this,
					Const.allContacts);

			adapter.notifyDataSetChanged();

			lv_contacts.setAdapter(adapter);
		}

	}
}
