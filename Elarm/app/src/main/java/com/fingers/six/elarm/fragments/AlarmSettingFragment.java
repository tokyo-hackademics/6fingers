package com.fingers.six.elarm.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowInsets;
import android.widget.Button;
import android.widget.TimePicker;

import com.fingers.six.elarm.ElarmActivity;
import com.fingers.six.elarm.R;
import com.fingers.six.elarm.common.AlarmHandler;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by PhanVanTrung on 2015/05/17.
 */
public class AlarmSettingFragment extends Activity {

    private  int mhour;
    private  int mminute;
    private  String time_status;

    public void onCreate(Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //View view = inflater.inflate(R.layout.fragment_setting_alarm, container, false);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_setting_alarm);

        Button btnsave = (Button)findViewById(R.id.btnSave);
        TimePicker timePicker = (TimePicker)findViewById(R.id.timePicker);

        // Default values
        Date date = new Date();   // given date
        Calendar calendar = GregorianCalendar.getInstance(); // creates a new calendar instance
        mhour = calendar.get(Calendar.HOUR);
        mminute = calendar.get(Calendar.MINUTE);
        time_status = (mhour < 12) ? "AM" : "PM";

        timePicker.setOnTimeChangedListener((new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                mminute = minute;
                mhour = hourOfDay;
                time_status = (mhour < 12) ? "AM" : "PM";
                Log.d("LOG","Alarm Time is "+Integer.toString(mhour)+":"+Integer.toString(mminute)+time_status);
            }
        }));
        btnsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String  time =  Integer.toString(mhour)+":"+Integer.toString(mminute);
                (new AlarmHandler(getApplicationContext(),"alarm_db")).addAlarmTime(time, time_status);
                Log.d("SAVE", "Saved alarm time is " + Integer.toString(mhour) + ":" + Integer.toString(mminute) + time_status);
                finish();
            }
        });
    }
}
