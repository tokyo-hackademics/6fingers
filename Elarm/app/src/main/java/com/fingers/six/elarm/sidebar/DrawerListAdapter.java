package com.fingers.six.elarm.sidebar;

import android.content.Context;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.ImageView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.fingers.six.elarm.R;

import java.util.ArrayList;
/**
 * Created by PhanVanTrung on 2015/05/16.
 */

public class DrawerListAdapter extends BaseAdapter {
    Context mcontext;
    ArrayList<NavigationItem> mnavigationItems;
    public DrawerListAdapter(Context context,ArrayList<NavigationItem> navigationItems){
        mcontext = context;
        mnavigationItems = navigationItems;
    }
    @Override
    public int getCount() {
        return mnavigationItems.size();
    }

    @Override
    public Object getItem(int position) {
        return mnavigationItems.get(position);
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
            view = inflater.inflate(R.layout.drawer_item, null);
        }
        else {
            view = convertView;
        }

        TextView titleView = (TextView) view.findViewById(R.id.title);
        ImageView iconView = (ImageView) view.findViewById(R.id.icon);

        titleView.setText( mnavigationItems.get(position).mTitle );
        iconView.setImageResource(mnavigationItems.get(position).mIcon);

        return view;
    }


}
