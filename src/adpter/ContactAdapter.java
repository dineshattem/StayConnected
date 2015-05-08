package adpter;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;

import com.hrd.stayconnect.R;

public class ContactAdapter extends BaseAdapter {
	public ArrayList<HashMap<String, String>> contactArray;
	private Context mContext;
	String city_id, city;
	Intent i;
	ArrayList<Boolean> positionArray;
	private static final int ITEM_VIEW_TYPE_ITEM = 0;
	private static final int ITEM_VIEW_TYPE_SEPARATOR = 1;
	String is_suggested;
	ArrayList<String> selectedStrings = new ArrayList<String>();

	public ContactAdapter(Context paramContext,
			ArrayList<HashMap<String, String>> contactList) {
		this.mContext = paramContext;
		this.contactArray = contactList;
		positionArray = new ArrayList<Boolean>(contactList.size());
		for (int i = 0; i < contactList.size(); i++) {
			positionArray.add(false);
		}

	}

	public int getCount() {
		return this.contactArray.size();
	}

	public Object getItem(int paramInt) {
		return Integer.valueOf(paramInt);
	}

	public long getItemId(int paramInt) {
		return paramInt;
	}

	@Override
	public View getView(final int paramInt, View paramView,
			ViewGroup paramViewGroup) {
		LayoutInflater localLayoutInflater = (LayoutInflater) this.mContext
				.getSystemService("layout_inflater");
		Viewholder localViewholder = null;
		if (paramView == null) {
			paramView = localLayoutInflater.inflate(R.layout.raw_contact,
					paramViewGroup, false);
			localViewholder = new Viewholder();

			localViewholder.tv_name = ((TextView) paramView
					.findViewById(R.id.tv_name));
			localViewholder.tv_num = ((TextView) paramView
					.findViewById(R.id.tv_num));

			localViewholder.chk_contact = ((CheckBox) paramView
					.findViewById(R.id.chk_contact));

			paramView.setTag(localViewholder);

		} else {

			localViewholder = (Viewholder) paramView.getTag();
			localViewholder.chk_contact.setOnCheckedChangeListener(null);
		}
		localViewholder.chk_contact.setFocusable(false);
		localViewholder.chk_contact.setChecked(positionArray.get(paramInt));
		localViewholder.tv_name.setText(contactArray.get(paramInt).get("name"));
		localViewholder.tv_num.setText(contactArray.get(paramInt).get("num"));
		localViewholder.chk_contact
				.setOnCheckedChangeListener(new OnCheckedChangeListener() {

					@Override
					public void onCheckedChanged(CompoundButton buttonView,
							boolean isChecked) {
						if (isChecked) {
							selectedStrings.add(paramInt + "");
						    positionArray.set(paramInt, true);

						} else {
							selectedStrings.remove(paramInt + "");
							positionArray.set(paramInt, false);
						}

					}
				});
		return paramView;

	}

	public ArrayList<String> getSelectedString() {
		return selectedStrings;
	}

	static class Viewholder {

		TextView tv_name;
		TextView tv_num;
		CheckBox chk_contact;

	}

}
