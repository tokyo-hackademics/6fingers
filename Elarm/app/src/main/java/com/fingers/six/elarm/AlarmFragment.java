package com.fingers.six.elarm;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import com.fingers.six.elarm.adapters.AlarmTimeAdapter;
import com.fingers.six.elarm.common.AlarmHandler;
import com.fingers.six.elarm.common.AlarmTime;
import com.fingers.six.elarm.common.MasterDbHandler;

import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by PhanVanTrung on 2015/05/16.
 */
public class AlarmFragment extends Fragment {

      FragmentManager fragmentManager;

    //Views
    private ListView AlarmList;
    AlarmSettingFragment alarmsetting;

    public AlarmFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_alarm, container, false);

        AlarmList  = (ListView)view.findViewById(R.id.AlarmList);
        AlarmHandler alarmHandler = new AlarmHandler(this.getActivity(),"alarm_db");
        ArrayList<AlarmTime> list = alarmHandler.getAllAlarmTime();
        AlarmTimeAdapter adapter = new AlarmTimeAdapter(this.getActivity(),list);
        // Assign adapter to ListView
        AlarmList.setAdapter(adapter);

        Button btnAdd = (Button)view.findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // (new AlarmHandler(getActivity(),"alarm_db")).addAlarmTime("12:00","AM");
                // Manage fragments
//                fragmentManager = getFragmentManager();
//                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//                fragmentTransaction.replace(R.id.main_content,alarmsetting);
//                if(alarmsetting == null) {
//                    alarmsetting = new AlarmSettingFragment();
//                    fragmentTransaction.add(R.id.fragment_sub,alarmsetting,"alarmsetting");
//                }
//
//                fragmentTransaction.commit();
                Intent intent = new Intent(getActivity(),AlarmSettingFragment.class);
                getActivity().startActivity(intent);
            }
        });
        return view;
    }

    public void onButtonPressed(Uri uri) {
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
