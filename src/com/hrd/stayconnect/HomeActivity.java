package com.hrd.stayconnect;

import DB.Contact;
import DB.DataBase_Helper;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class HomeActivity extends Activity implements OnClickListener {
	ImageView btn_crt, btn_mygrp;
	DataBase_Helper helpr;
	int i = 1;
	int i1 = 1;
	TextView tv_int_plus;
	TextView tv_int_val;
	TextView tv_int_minus;
	TextView tv_int_plus1;
	TextView tv_int_val1;
	TextView tv_int_minus1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_home);
		init();
	}

	void init() {
		helpr = new DataBase_Helper(HomeActivity.this);
		btn_crt = (ImageView) findViewById(R.id.btn_crt);
		btn_mygrp = (ImageView) findViewById(R.id.btn_mygrp);
		btn_crt.setOnClickListener(this);
		btn_mygrp.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_crt:
			// create a new group
			AddContactPopUp(HomeActivity.this, "Enter Your Group Name",
					"Create a Group", "CREATE", "CANCEL");
			break;

		case R.id.btn_mygrp:
			// go to my group
			Intent i = new Intent(HomeActivity.this, MyGroupsActivity.class);
			startActivity(i);
			break;

		}
	}

	public void AddContactPopUp(final Context context, String message,
			String title, String leftButton, String RiteButton) {

		final Dialog dialog = new Dialog(context);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(R.layout.popup_alert);
		dialog.setCancelable(true);
		dialog.getWindow().setBackgroundDrawable(
				new ColorDrawable(android.graphics.Color.TRANSPARENT));

		final EditText txt_message = (EditText) dialog
				.findViewById(R.id.txtMessage);
		final Button btn_left = (Button) dialog.findViewById(R.id.btnLeft);
		final Button btn_riButton = (Button) dialog.findViewById(R.id.btnRite);

		tv_int_plus = (TextView) dialog.findViewById(R.id.tv_int_plus);
		tv_int_val = (TextView) dialog.findViewById(R.id.tv_int_val);
		tv_int_minus = (TextView) dialog.findViewById(R.id.tv_int_minus);
		tv_int_plus1 = (TextView) dialog.findViewById(R.id.tv_int_plus1);
		tv_int_val1 = (TextView) dialog.findViewById(R.id.tv_int_val1);
		tv_int_minus1 = (TextView) dialog.findViewById(R.id.tv_int_minus1);

		TextView txtTitle = (TextView) dialog.findViewById(R.id.txtTitle);
		txtTitle.setTypeface(null, Typeface.BOLD);
		txtTitle.setText(title);
		txt_message.setHint(message);
		btn_left.setText(leftButton);
		btn_riButton.setText(RiteButton);

		tv_int_plus.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				System.out.println("MY I+ " + i + "");

				i++;
				if (i >= 59) {
					tv_int_val.setText("59");
					tv_int_plus.setEnabled(false);
				} else {
					tv_int_val.setText(String.valueOf(i));
				}

			}
		});
		tv_int_minus.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				tv_int_plus.setEnabled(true);

				if (i <= 1) {
					tv_int_val.setText("1");

				} else {

					System.out.println("MY I " + i + "");

					i--;
					tv_int_val.setText(String.valueOf(i));
					System.out.println("MY I- " + i + "");
				}
			}
		});
		tv_int_plus1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
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
			}
		});
		tv_int_minus1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
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
			}
		});

		btn_left.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				// saving group name\

				long secnds = getSeconds(i);

				if (txt_message.getText().toString() != null
						&& txt_message.getText().toString() != "") {
					helpr.addGropu(new Contact(
							txt_message.getText().toString(), String
									.valueOf(secnds)));

				} else {
					Toast.makeText(HomeActivity.this,
							"Please enter group name", Toast.LENGTH_SHORT)
							.show();
				}

				Intent i = new Intent(HomeActivity.this, MyGroupsActivity.class);
				startActivity(i);
				dialog.dismiss();

			}
		});
		btn_riButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// cancel
				dialog.dismiss();

			}
		});

		dialog.show();
	}

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
}
