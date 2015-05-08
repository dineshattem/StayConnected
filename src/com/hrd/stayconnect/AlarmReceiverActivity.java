package com.hrd.stayconnect;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;

import DB.Contact;
import DB.DataBase_Helper;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * This activity will be called when the alarm is triggered.
 * 
 * @author Michael Irwin
 */
public class AlarmReceiverActivity extends Activity {
	private MediaPlayer mMediaPlayer;
	TextView tv_remindlbl;
	DataBase_Helper helper;
	String my_number;
	public Vibrator vibrator;

	TextView close;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.alarm);

		close = (TextView) findViewById(R.id.close);
		ImageView stopAlarm = (ImageView) findViewById(R.id.btn_call);
		ImageView btn_msg = (ImageView) findViewById(R.id.btn_msg);
		String name = getIntent().getStringExtra("name");
		String sec = getIntent().getStringExtra("sec");
		tv_remindlbl = (TextView) findViewById(R.id.tv_label);
		tv_remindlbl.setText("Call reminder to " + name);
		blink();
		final View v = new View(AlarmReceiverActivity.this);
		startVibrate(v);
		helper = new DataBase_Helper(getApplicationContext());

		/*
		 * stopAlarm.setOnTouchListener(new OnTouchListener() { public boolean
		 * onTouch(View arg0, MotionEvent arg1) { mMediaPlayer.stop(); finish();
		 * return false; } });
		 */

		List<Contact> list = helper.getAllContacts();

		for (Contact cn : list) {

			if (name.equals(cn.getName())) {

				my_number = cn.getContact_number();

				Calendar cal = Calendar.getInstance();

				System.out.println("MY TIME " + cn.getInterval_time());
				cal.add(Calendar.SECOND,
						Integer.parseInt(cn.getInterval_time()));

				// cal.add(Calendar.SECOND, 30);

				// Create a new PendingIntent and add it to the
				// AlarmManager
				Intent intent = new Intent(this, AlarmReceiverActivity.class);
				intent.putExtra("name", name);

				PendingIntent pendingIntent = PendingIntent.getActivity(this,
						12345, intent, PendingIntent.FLAG_CANCEL_CURRENT);
				AlarmManager am = (AlarmManager) getSystemService(Activity.ALARM_SERVICE);
				am.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(),
						pendingIntent);

			}

		}

		close.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				stopVibrate(v);
				finish();

			}
		});
		stopAlarm.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// calling.......!!!
				stopVibrate(v);
				Intent callIntent = new Intent(Intent.ACTION_CALL);
				callIntent.setData(Uri.parse("tel:" + my_number));
				startActivity(callIntent);
				finish();
			}
		});
		btn_msg.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// message..!!!
				stopVibrate(v);
				Intent smsIntent = new Intent(Intent.ACTION_VIEW);
				smsIntent.setType("vnd.android-dir/mms-sms");
				smsIntent.putExtra("address", my_number);
				smsIntent.putExtra("sms_body", "Body of Message");
				startActivity(smsIntent);

				finish();

			}
		});

		// playSound(this, getAlarmUri());
	}

	private void playSound(Context context, Uri alert) {
		mMediaPlayer = new MediaPlayer();
		try {
			mMediaPlayer.setDataSource(context, alert);
			final AudioManager audioManager = (AudioManager) context
					.getSystemService(Context.AUDIO_SERVICE);
			if (audioManager.getStreamVolume(AudioManager.STREAM_ALARM) != 0) {
				mMediaPlayer.setAudioStreamType(AudioManager.STREAM_ALARM);
				mMediaPlayer.prepare();
				mMediaPlayer.start();
			}
		} catch (IOException e) {
			System.out.println("OOPS");
		}
	}

	// Get an alarm sound. Try for an alarm. If none set, try notification,
	// Otherwise, ringtone.
	private Uri getAlarmUri() {
		Uri alert = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
		if (alert == null) {
			alert = RingtoneManager
					.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
			if (alert == null) {
				alert = RingtoneManager
						.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
			}
		}
		return alert;
	}

	// Blibnking textView..!
	private void blink() {
		final Handler handler = new Handler();
		new Thread(new Runnable() {
			@Override
			public void run() {
				int timeToBlink = 1000; // in milissegunds
				try {
					Thread.sleep(timeToBlink);
				} catch (Exception e) {
				}
				handler.post(new Runnable() {
					@Override
					public void run() {

						if (tv_remindlbl.getVisibility() == View.VISIBLE) {
							tv_remindlbl.setVisibility(View.INVISIBLE);
						} else {
							tv_remindlbl.setVisibility(View.VISIBLE);
						}
						blink();
					}
				});
			}
		}).start();
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		finish();
	}

	public void startVibrate(View v) {
		long pattern[] = { 100, 200, 300, 400, 500 };
		vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
		vibrator.vibrate(pattern, 0);
	}

	public void stopVibrate(View v) {
		vibrator.cancel();
	}
}