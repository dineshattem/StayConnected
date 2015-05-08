package Utility;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.provider.ContactsContract;
import android.provider.Settings;

@SuppressLint("SimpleDateFormat")
public class Utils {

	public static String getDeviceId(Context context) {
		String AndroidId = Settings.Secure.getString(
				context.getContentResolver(), Settings.Secure.ANDROID_ID);
		// Log.print("getDeviceId", AndroidId);

		return AndroidId;
	}

	public static Date convertStringToDate(String strDate, String parseFormat) {
		try {
			return new SimpleDateFormat(parseFormat).parse(strDate);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static String getDateFromat(String _Date,
			java.text.DateFormat dateFormat) {
		return dateFormat
				.format(Utils.convertStringToDate(_Date, "yyyy-MM-dd"));
	}

	public static boolean isOnline(Context context) {

		try {
			ConnectivityManager cm = (ConnectivityManager) context
					.getSystemService(Context.CONNECTIVITY_SERVICE);

			if (cm != null) {
				return cm.getActiveNetworkInfo().isConnected();
			} else {

				return false;

			}
		} catch (Exception e) {
			return false;
		}
	}

	// reading contacts from cotactbook..!!!
	public static void readContacts(final Context context) {
		StringBuffer sb = new StringBuffer();
		Const.allContacts.clear();
		ContentResolver cr = context.getContentResolver();
		Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI, null,
				null, null, null);
		String phone = null;
		String emailType = null;
		String emailContact = null;
		if (cur.getCount() > 0) {
			while (cur.moveToNext()) {
				String id = cur.getString(cur
						.getColumnIndex(ContactsContract.Contacts._ID));
				String name = cur
						.getString(cur
								.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
			
				if (Integer
						.parseInt(cur.getString(cur
								.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0) {
					System.out.println("name : " + name + ", ID : " + id);

					// Const.allContacts.add(name);

					System.out.println("MYCONTACT "
							+ Const.allContacts.toString());

					sb.append("\n Contact Name:" + name);
					Cursor pCur = cr.query(
							ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
							null,
							ContactsContract.CommonDataKinds.Phone.CONTACT_ID
									+ " = ?", new String[] { id }, null);
					while (pCur.moveToNext()) {
						phone = pCur
								.getString(pCur
										.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
						
						String call_duration = cur
								.getString(cur
										.getColumnIndex(ContactsContract.Contacts.LAST_TIME_CONTACTED));

						SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
						String dateString = formatter.format(new Date((long) Double
								.parseDouble(call_duration)));
						System.out.println("Current Status " + dateString);
						
						sb.append("\n Phone number:" + phone);
						System.out.println("phone" + phone);
						HashMap<String, String> contactmap = new HashMap<String, String>();
						contactmap.put("name", name);
						contactmap.put("num", phone);
						contactmap.put("contacted",dateString);
						Const.allContacts.add(contactmap);
					}
					pCur.close();

					Cursor emailCur = cr.query(
							ContactsContract.CommonDataKinds.Email.CONTENT_URI,
							null,
							ContactsContract.CommonDataKinds.Email.CONTACT_ID
									+ " = ?", new String[] { id }, null);

				}

			}
		}

	}

}
