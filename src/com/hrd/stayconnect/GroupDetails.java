package com.hrd.stayconnect;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import DB.Contact;
import DB.DataBase_Helper;
import Utility.Const;
import Utility.SwipeDismissListViewTouchListener;
import adpter.SimpleAdapter;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class GroupDetails extends Activity {
	ImageView iv_add;
	ListView lv_contacts;
	SimpleAdapter adapter;
	ArrayList<HashMap<String, String>> contact_list;
	TextView group_name;
	List<Contact> model1;
	DataBase_Helper helper;
	List<Contact> group_contact;
	ArrayList<String> myGroups_contact;
	ArrayList<String> myGroups_Never_contacted;
	static boolean isSwipeEnable = true;
	Button setting;
	int i = 1;
	int i1 = 1;
	TextView tv_int_plus;
	TextView tv_int_val;
	TextView tv_int_minus;
	TextView tv_int_plus1;
	TextView tv_int_val1;
	TextView tv_int_minus1;
	EditText txt_message;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_group_detail);
		init();

		setting.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				AddContactPopUp(GroupDetails.this, "Enter Your Group Name",
						"Edit Group Reminder", "SAVE", "CANCEL");
			}
		});

		iv_add.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// adding contacts..!!!
				Intent i = new Intent(GroupDetails.this, ContactsActivity.class);
				startActivity(i);
			}
		});
		lv_contacts.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent i = new Intent(GroupDetails.this,
						EditContactActivity.class);
				i.putExtra("name", myGroups_contact.get(position));
				i.putExtra("log", myGroups_Never_contacted.get(position));
				System.out.println("========my selected contact name========="
						+ myGroups_contact.get(position));
				startActivity(i);
			}
		});

		SwipeDismissListViewTouchListener touchListener = new SwipeDismissListViewTouchListener(
				lv_contacts,
				new SwipeDismissListViewTouchListener.DismissCallbacks() {
					@Override
					public boolean canDismiss(int position) {
						System.out.println("Swipe enable status is"
								+ isSwipeEnable);
						return true;
					}

					@Override
					public void onDismiss(ListView lv1,
							int[] reverseSortedPositions) {

						System.out.println("Swipe enable status is"
								+ isSwipeEnable);

						for (final int position : reverseSortedPositions) {

							AlertDialog.Builder alert = new AlertDialog.Builder(
									GroupDetails.this);

							alert.setTitle("Alert");

							alert.setCancelable(false);

							alert.setMessage("Are you sure to delete this Contact?");

							alert.setPositiveButton("Yes",
									new DialogInterface.OnClickListener() {

										@Override
										public void onClick(
												DialogInterface dialog,
												int which) {

											isSwipeEnable = false;
											System.out
													.println("Swipe enable status is"
															+ isSwipeEnable);

											System.out.println("My Title "
													+ myGroups_contact
															.get(position));

											helper = new DataBase_Helper(
													getApplicationContext());
											model1 = helper.getAllContacts();

											for (Contact cn : model1) {

												if (myGroups_contact.get(
														position).equals(
														cn.getName())) {

													int id = cn.getId();

													helper.deleteContact(new Contact(
															id));
												}
											}

											myGroups_contact
													.remove(myGroups_contact
															.get(position));

											adapter.notifyDataSetChanged();

										}
									});

							alert.setNegativeButton("No",
									new DialogInterface.OnClickListener() {

										@Override
										public void onClick(
												DialogInterface dialog,
												int which) {

											System.out
													.println("Swipe enable status is"
															+ isSwipeEnable);

											// TODO Auto-generated method stub
											isSwipeEnable = false;
											dialog.dismiss();

										}
									});

							AlertDialog alert_main = alert.create();

							alert_main.show();

						}

						// lv.setEnabled(true);

					}
				});

		lv_contacts.setOnTouchListener(touchListener);
		// Setting this scroll listener is required to ensure that during
		// ListView scrolling,
		// we don't look for swipes.
		lv_contacts.setOnScrollListener(touchListener.makeScrollListener());

	}

	@SuppressWarnings("unchecked")
	void init() {

		iv_add = (ImageView) findViewById(R.id.iv_add);
		group_name = (TextView) findViewById(R.id.group_name);
		lv_contacts = (ListView) findViewById(R.id.lv_contacts);
		setting = (Button) findViewById(R.id.setting);

		group_name.setText(Const.GROUPNAME);

		myGroups_contact = new ArrayList<String>();
		myGroups_Never_contacted = new ArrayList<String>();

		helper = new DataBase_Helper(getApplicationContext());

		group_contact = helper.getAllContacts();

		for (Contact c : group_contact) {

			if (Const.GROUPNAME.equals(c.getType())) {
				myGroups_contact.add(c.getName());

				myGroups_Never_contacted.add(c.getContacted());

			}

			System.out.println("MYNAME" + c.getName());
		}

		adapter = new SimpleAdapter(getApplicationContext(), myGroups_contact,
				myGroups_Never_contacted);

		lv_contacts.setAdapter(adapter);
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();

		Intent i = new Intent(GroupDetails.this, MyGroupsActivity.class);
		i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(i);
	}

	public void AddContactPopUp(final Context context, String message,
			String title, String leftButton, String RiteButton) {

		final Dialog dialog = new Dialog(context);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(R.layout.popup_alert);
		dialog.setCancelable(true);
		dialog.getWindow().setBackgroundDrawable(
				new ColorDrawable(android.graphics.Color.TRANSPARENT));

		txt_message = (EditText) dialog.findViewById(R.id.txtMessage);

		txt_message.setText(group_name.getText().toString());

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

					System.out.println("MESAGE "
							+ txt_message.getText().toString());

					List<Contact> my_group = helper.getAllGroup();
					List<Contact> my_home = helper.getAllContacts();

					for (Contact gn : my_group) {

						if (group_name.getText().toString()
								.equals(gn.getType())) {

							for (Contact dn : my_home) {

								if (gn.getType().equals(dn.getType())) {

									helper.updateGroup(new Contact(txt_message
											.getText().toString(), gn.getId(),
											String.valueOf(secnds)));

									List<Contact> my_new = helper.getAllGroup();

									for (Contact ne : my_new) {

										if (dn.getInterval_time().equals(
												gn.getGroup_time())) {
											helper.updateContact_new(new Contact(
													ne.getType(), ne
															.getGroup_time(),
													dn.getId()));
										} else {
											helper.updateContact_new1(new Contact(
													dn.getId(), ne.getType()));
										}

									}
								}
							}

						}

					}

				} else {
					Toast.makeText(GroupDetails.this,
							"Please enter group name", Toast.LENGTH_SHORT)
							.show();
				}

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
