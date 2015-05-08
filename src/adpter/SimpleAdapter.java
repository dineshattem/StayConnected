package adpter;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.hrd.stayconnect.R;

public class SimpleAdapter extends BaseAdapter {
	public ArrayList<String> contArray;
	public ArrayList<String> conacted;
	private Context mContext;
	String resnID, reson;
	Intent i;

	public SimpleAdapter(Context paramContext, ArrayList<String> contList,ArrayList<String> conacted) {
		this.mContext = paramContext;
		this.contArray = contList;
		this.conacted = conacted;

	}

	public int getCount() {
		return this.contArray.size();
	}

	public Object getItem(int paramInt) {
		return Integer.valueOf(paramInt);
	}

	public long getItemId(int paramInt) {
		return paramInt;
	}

	public View getView(final int paramInt, View paramView,
			ViewGroup paramViewGroup) {
		LayoutInflater localLayoutInflater = (LayoutInflater) this.mContext
				.getSystemService("layout_inflater");
		Viewholder localViewholder = null;
		if (paramView == null) {
			paramView = localLayoutInflater.inflate(
					R.layout.raw_single_contact, paramViewGroup, false);
			localViewholder = new Viewholder();

			localViewholder.tv_cont = ((TextView) paramView
					.findViewById(R.id.tv_talent));
			
			localViewholder.tv_log = ((TextView) paramView
					.findViewById(R.id.tv_log));

			paramView.setTag(localViewholder);

		} else {

			localViewholder = (Viewholder) paramView.getTag();
		}
		localViewholder.tv_cont.setTextColor(Color.parseColor("#000000"));
		localViewholder.tv_cont.setText(contArray.get(paramInt));
		
		
		if (conacted.get(paramInt).equals("01/01/1970")) {
			
			localViewholder.tv_log.setText("Never Contacted");
		}
		else {
			localViewholder.tv_log.setText(conacted.get(paramInt));
		}

		return paramView;

	}

	static class Viewholder {

		TextView tv_cont;
		TextView tv_log;

	}

}
