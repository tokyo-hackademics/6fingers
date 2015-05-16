package com.fingers.six.elarm.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.fingers.six.elarm.R;
import com.fingers.six.elarm.common.AlarmTime;
import com.fingers.six.elarm.sidebar.NavigationItem;

import java.util.ArrayList;

/**
 * Created by PhanVanTrung on 2015/05/16.
 */
public class AlarmTimeAdapter extends BaseAdapter {
    Context mcontext;
    ArrayList<AlarmTime> malarmTimes;

    public AlarmTimeAdapter(Context context, ArrayList<AlarmTime> alarmTimes) {
        mcontext = context;
        malarmTimes = alarmTimes;
    }

    @Override
    public int getCount() {
        return malarmTimes.size();
    }

    @Override
    public Object getItem(int position) {
        return malarmTimes.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mcontext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.alarmtime_item, null);
        } else {
            view = convertView;
        }

        TextView titleView = (TextView) view.findViewById(R.id.title);
       // Switch aSwitch = (Switch) view.findViewById(R.id.switch);

        titleView.setText("               "+malarmTimes.get(position).getTime() + "            " + malarmTimes.get(position).getTime_status());
       // aSwitch.setChecked(true);
        return view;
    }
}