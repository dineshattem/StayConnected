package com.hrd.stayconnect;

import java.util.ArrayList;
import java.util.List;

import DB.Contact;
import DB.DataBase_Helper;
import Utility.Const;
import Utility.SwipeDismissListViewTouchListener;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

public class MyGroupsActivity extends Activity {
	ListView lv_groups;
	ImageView iv_add;
	DataBase_Helper dbHelper;
	List<Contact> contact_list;
	List<Contact> model1;
	ArrayList<String> myGroups;
	ArrayAdapter<String> adapter;
	static boolean isSwipeEnable = true;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_my_groups);
		dbHelper = new DataBase_Helper(MyGroupsActivity.this);
		lv_groups = (ListView) findViewById(R.id.lv_groups);
		iv_add = (ImageView) findViewById(R.id.iv_add);
		contact_list = dbHelper.getAllGroup();
		myGroups = new ArrayList<String>();
		for (Contact c : contact_list) {
			myGroups.add(c.getType());
		}

		adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, android.R.id.text1,
				myGroups);
		adapter.setNotifyOnChange(true);
		adapter.notifyDataSetChanged();
		// Assign adapter to ListView
		lv_groups.setAdapter(adapter);

		lv_groups.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				String name = (String) parent.getItemAtPosition(position);
				System.out.println("NAEM" + name);

				Const.GROUPNAME = name;

				Intent i = new Intent(MyGroupsActivity.this, GroupDetails.class);

				startActivity(i);

			}
		});

		SwipeDismissListViewTouchListener touchListener = new SwipeDismissListViewTouchListener(
				lv_groups,
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
									MyGroupsActivity.this);

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

											dbHelper = new DataBase_Helper(
													getApplicationContext());
											model1 = dbHelper.getAllGroup();

											for (Contact cn : model1) {

												if (myGroups.get(position)
														.equals(cn.getType())) {

													int id = cn.getId();

													dbHelper.deletegroup(new Contact(
															id));
												}
											}

											myGroups.remove(myGroups
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

		lv_groups.setOnTouchListener(touchListener);
		// Setting this scroll listener is required to ensure that during
		// ListView scrolling,
		// we don't look for swipes.
		lv_groups.setOnScrollListener(touchListener.makeScrollListener());

	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();

		Intent i = new Intent(MyGroupsActivity.this, HomeActivity.class);
		i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(i);
	}

}
