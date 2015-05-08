package DB;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBase_Helper extends SQLiteOpenHelper {

	private static final int DATABASE_VERSION = 11;

	private static final String DATABASE_NAME = "my_data";

	private static final String TABLE_CONTACTS = "contacts";

	private static final String KEY_ID = "id";
	private static final String KEY_NAME = "Name";
	private static final String KEY_TYPE = "group_name";
	private static final String KEY_CONTACTED = "contacted";
	private static final String KEY_INTERVAL = "interval_type";
	private static final String KEY_INTERVAL_TIME = "interval_time";
	private static final String KEY_EMAIL = "email";
	private static final String KEY_NUMBER = "Mobile_number";

	private static final String TABLE_CONTACTS1 = "contacts_group";

	private static final String KEY_ID1 = "id";
	private static final String KEY_GROUP_NAME = "group_name";
	private static final String KEY_GROUP_TIME = "Group_time";

	public DataBase_Helper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub

		String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_CONTACTS + "("
				+ KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT UNIQUE,"
				+ KEY_TYPE + " TEXT," + KEY_CONTACTED + " TEXT," + KEY_INTERVAL
				+ " TEXT," + KEY_EMAIL + " TEXT," + KEY_NUMBER + " TEXT,"
				+ KEY_INTERVAL_TIME + " TEXT" + ")";
		db.execSQL(CREATE_CONTACTS_TABLE);

		String CREATE_CONTACTS_TABLE_2 = "CREATE TABLE " + TABLE_CONTACTS1
				+ "(" + KEY_ID1 + " INTEGER PRIMARY KEY," + KEY_GROUP_NAME
				+ " TEXT," + KEY_GROUP_TIME + " TEXT" + ")";
		db.execSQL(CREATE_CONTACTS_TABLE_2);

	}

	public void addGropu(Contact contact) {
		SQLiteDatabase db = this.getWritableDatabase();
		long rows = 0;
		ContentValues values = new ContentValues();
		values.put(KEY_TYPE, contact.getType());

		values.put(KEY_GROUP_TIME, contact.getGroup_time());
		// Inserting Row
		db.insert(TABLE_CONTACTS1, null, values);

		db.close();

	}

	public void addContact(Contact contact) {
		SQLiteDatabase db = this.getWritableDatabase();
		long rows = 0;
		ContentValues values = new ContentValues();
		values.put(KEY_NAME, contact.getName());
		values.put(KEY_TYPE, contact.getType());
		values.put(KEY_CONTACTED, contact.getContacted());
		values.put(KEY_INTERVAL, contact.getInterval_type());
		values.put(KEY_EMAIL, contact.getEmail());
		values.put(KEY_NUMBER, contact.getContact_number());
		values.put(KEY_INTERVAL_TIME, contact.getInterval_time());

		// Inserting Row
		db.insert(TABLE_CONTACTS, null, values);

		db.close();

	}

	public List<Contact> getAllGroup() {
		List<Contact> contactList = new ArrayList<Contact>();
		// Select All Query
		String selectQuery = "SELECT  * FROM " + TABLE_CONTACTS1;

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				Contact contact = new Contact();
				contact.setId(Integer.parseInt(cursor.getString(0)));

				contact.setType(cursor.getString(1));
				contact.setGroup_time(cursor.getString(2));

				// Adding contact to list
				contactList.add(contact);
			} while (cursor.moveToNext());
		}

		// return contact list
		return contactList;
	}

	public List<Contact> getAllContacts() {
		List<Contact> contactList = new ArrayList<Contact>();
		// Select All Query
		String selectQuery = "SELECT  * FROM " + TABLE_CONTACTS;

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				Contact contact = new Contact();
				contact.setId(Integer.parseInt(cursor.getString(0)));
				contact.setName(cursor.getString(1));
				contact.setType(cursor.getString(2));
				contact.setContacted(cursor.getString(3));
				contact.setInterval_type(cursor.getString(4));
				contact.setEmail(cursor.getString(5));
				contact.setContact_number(cursor.getString(6));
				contact.setInterval_time(cursor.getString(7));

				// Adding contact to list
				contactList.add(contact);
			} while (cursor.moveToNext());
		}

		// return contact list
		return contactList;
	}

	public int updateContact(Contact contact) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_ID, contact.getId());
		values.put(KEY_INTERVAL_TIME, contact.getInterval_time());

		// updating row
		return db.update(TABLE_CONTACTS, values, KEY_ID + " = ?",
				new String[] { String.valueOf(contact.getId()) });
	}

	public int updateContact_new(Contact contact) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_ID, contact.getId());
		values.put(KEY_TYPE, contact.getType());
		values.put(KEY_INTERVAL_TIME, contact.getInterval_time());

		// updating row
		return db.update(TABLE_CONTACTS, values, KEY_ID + " = ?",
				new String[] { String.valueOf(contact.getId()) });
	}

	public int updateContact_new1(Contact contact) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_ID, contact.getId());
		values.put(KEY_TYPE, contact.getType());

		// updating row
		return db.update(TABLE_CONTACTS, values, KEY_ID + " = ?",
				new String[] { String.valueOf(contact.getId()) });
	}

	public int updateGroup(Contact contact) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_ID1, contact.getId());
		values.put(KEY_GROUP_NAME, contact.getType());
		values.put(KEY_GROUP_TIME, contact.getGroup_time());

		// updating row
		return db.update(TABLE_CONTACTS1, values, KEY_ID1 + " = ?",
				new String[] { String.valueOf(contact.getId()) });
	}

	// Deleting single contact
	public void deleteContact(Contact contact) {
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(TABLE_CONTACTS, KEY_ID + " = ?",
				new String[] { String.valueOf(contact.getId()) });
		db.close();
	}

	public void deletegroup(Contact contact) {
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(TABLE_CONTACTS1, KEY_ID1 + " = ?",
				new String[] { String.valueOf(contact.getId()) });
		db.close();
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS1);

		// Create tables again
		onCreate(db);
	}

}
