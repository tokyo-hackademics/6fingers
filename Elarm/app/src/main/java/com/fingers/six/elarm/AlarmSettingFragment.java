package com.fingers.six.elarm;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by PhanVanTrung on 2015/05/17.
 */
public class AlarmSettingFragment extends Fragment {
    public AlarmSettingFragment() {
        // Required empty public constructor
    }


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_setting_alarm, container, false);

        return view;
    }
}
