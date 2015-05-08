package com.hrd.stayconnect;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;

public class SplashActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_splash);

		Thread splashThread = new Thread() {
			public void run() {
				try {
					sleep(3000);
					// Utils.systemUpgrade(SplashActivity.this);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

				Intent intent = null;
				intent = new Intent(SplashActivity.this, HomeActivity.class);
				intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
				startActivity(intent);

				finish();
			}
		};
		splashThread.start();

	}

}
