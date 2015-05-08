package com.hrd.stayconnect;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import DB.Contact;
import DB.DataBase_Helper;
import Utility.Const;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

public class EditContactActivity extends Activity implements OnClickListener {
	TextView tv_contact;
	TextView tv_call;
	TextView tv_int_plus, tv_int_val, tv_int_minus;
	TextView tv_int_nplus, tv_int_nname, tv_int_nminus;
	TextView tv_grpname;
	int intrval_count = 1;
	int intrval_name_count = 1;
	int intrval_typ_count = 1;
	String contact_name;
	int i = 1, i1 = 1;
	TextView tv_cancel;
	TextView tv_log;
	TextView tv_add;
	int days = 1;
	String log;
	String currentDate;

	DataBase_Helper helper;
	TextView tv_int_plus1, tv_int_minus1, tv_int_val1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_edit_contact);
		init();

	}

	// initialize controlls...!!!
	@SuppressLint("SimpleDateFormat")
	void init() {
		log = getIntent().getStringExtra("log");
		tv_log = (TextView) findViewById(R.id.tv_log);
		tv_log.setText(log + "");
		contact_name = getIntent().getStringExtra("name");
		tv_grpname = (TextView) findViewById(R.id.tv_grpname);
		tv_grpname.setText(Const.GROUPNAME);
		tv_add = (TextView) findViewById(R.id.tv_add);
		tv_cancel = (TextView) findViewById(R.id.tv_cancel);
		tv_contact = (TextView) findViewById(R.id.tv_contact);
		tv_call = (TextView) findViewById(R.id.tv_call);
		tv_int_plus = (TextView) findViewById(R.id.tv_int_plus);
		tv_int_val = (TextView) findViewById(R.id.tv_int_val);
		tv_int_minus = (TextView) findViewById(R.id.tv_int_minus);
		tv_int_minus1 = (TextView) findViewById(R.id.tv_int_minus1);

		tv_int_plus1 = (TextView) findViewById(R.id.tv_int_plus1);
		tv_int_val1 = (TextView) findViewById(R.id.tv_int_val1);
		tv_contact.setText(contact_name);
		tv_int_plus.setOnClickListener(this);
		tv_int_minus.setOnClickListener(this);

		tv_add.setOnClickListener(this);
		tv_cancel.setOnClickListener(this);
		tv_int_plus1.setOnClickListener(this);
		tv_int_minus1.setOnClickListener(this);

		helper = new DataBase_Helper(getApplicationContext());
		// get current date..!!!
		Calendar c = Calendar.getInstance();
		System.out.println("Current time => " + c.getTime());

		SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		currentDate = df.format(c.getTime());

		System.out.println("=======current date==========" + currentDate);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tv_int_plus:

			System.out.println("MY I+ " + i + "");

			i++;
			if (i >= 59) {
				tv_int_val.setText("59");
				tv_int_plus.setEnabled(false);
			} else {
				tv_int_val.setText(String.valueOf(i));
			}

			break;
		case R.id.tv_int_minus:
			tv_int_plus.setEnabled(true);

			if (i <= 1) {
				tv_int_val.setText("1");

			} else {

				System.out.println("MY I " + i + "");

				i--;
				tv_int_val.setText(String.valueOf(i));
				System.out.println("MY I- " + i + "");
			}
			break;

		case R.id.tv_int_plus1:

			tv_int_minus1.setEnabled(true);

			System.out.println("MY I1+ " + i1 + "");

			i1++;
			if (i1 >= 4) {
				tv_int_val1.setText("Week");
				tv_int_plus1.setEnabled(false);
			} else {

				if (i1 == 1) {
					tv_int_val1.setText("Minut");
				}
				if (i1 == 2) {
					tv_int_val1.setText("Hour");
				}
				if (i1 == 3) {
					tv_int_val1.setText("Days");
				}

			}

			break;
		case R.id.tv_int_minus1:
			tv_int_plus1.setEnabled(true);

			if (i1 <= 1) {
				tv_int_val1.setText("Minut");
				tv_int_minus1.setEnabled(false);

			} else {

				System.out.println("MY I " + i1 + "");

				i1--;

				if (i1 == 1) {
					tv_int_val1.setText("Minut");
				}
				if (i1 == 2) {
					tv_int_val1.setText("Hour");
				}
				if (i1 == 3) {
					tv_int_val1.setText("Days");
				}

			}
			break;

		case R.id.tv_add:
			// Edited reminder..!!!
			try {
				long secnds = getSeconds(i);

				System.out.println("==============my seconds================="
						+ secnds + "");
				Toast.makeText(EditContactActivity.this,
						"Reminder is added" + "", Toast.LENGTH_SHORT).show();
				// setReminder(20);
				// rough code...!!

				//

				List<Contact> mylist = helper.getAllContacts();

				for (Contact cn : mylist) {
					if (contact_name.equals(cn.getName())) {

						helper.updateContact(new Contact(secnds + "", cn
								.getId()));

						List<Contact> mylist_one = helper.getAllContacts();

						for (Contact my : mylist_one) {

							if (contact_name.equals(my.getName())) {
								Calendar cal = Calendar.getInstance();

								System.out.println("MY TIME "
										+ my.getInterval_time());
								cal.add(Calendar.SECOND,
										Integer.parseInt(my.getInterval_time()));

								// cal.add(Calendar.SECOND, 30);

								// Create a new PendingIntent and add it to the
								// AlarmManager
								Intent intent = new Intent(this,
										AlarmReceiverActivity.class);
								intent.putExtra("name", contact_name);

								PendingIntent pendingIntent = PendingIntent
										.getActivity(
												this,
												12345,
												intent,
												PendingIntent.FLAG_CANCEL_CURRENT);
								AlarmManager am = (AlarmManager) getSystemService(Activity.ALARM_SERVICE);
								am.set(AlarmManager.RTC_WAKEUP,
										cal.getTimeInMillis(), pendingIntent);
							}

						}

					}

				}

			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			finish();
			break;
		case R.id.tv_cancel:
			// back to List..!!!
			finish();
			break;
		}
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub

		Intent i = new Intent(EditContactActivity.this, GroupDetails.class);
		i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(i);
	}

	// Adding days to current date..!!!
	@SuppressLint("SimpleDateFormat")
	String addDay(int add) throws java.text.ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Date dateparsed = sdf.parse(currentDate);
		Calendar c = Calendar.getInstance();
		c.setTime(dateparsed);
		c.add(Calendar.DATE, add); // Adding n number of days
		String output = sdf.format(c.getTime());
		System.out.println(output);
		return output;
	}

	// getseconds..!!!
	long getSeconds(int days) {

		if (tv_int_val1.getText().toString().equals("Days")) {
			days = days * 86400;
		}

		if (tv_int_val1.getText().toString().equals("Hour")) {
			days = days * 3600;
		}
		if (tv_int_val1.getText().toString().equals("Minut")) {
			days = days * 60;
		}
		if (tv_int_val1.getText().toString().equals("Week")) {
			days = days * 14515200;
		}

		return days;

	}

	// set REminder..!!!
	void setReminder(int secs) {

		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.SECOND, (int) secs);

		// Create a new PendingIntent and add it to the AlarmManager
		Intent intent = new Intent(this, AlarmReceiverActivity.class);
		intent.putExtra("sec", secs + "");
		intent.putExtra("name", tv_contact.getText().toString());
		PendingIntent pendingIntent = PendingIntent.getActivity(this, 12345,
				intent, PendingIntent.FLAG_CANCEL_CURRENT);
		AlarmManager am = (AlarmManager) getSystemService(Activity.ALARM_SERVICE);
		am.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), pendingIntent);

	}

}
