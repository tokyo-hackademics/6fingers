package com.fingers.six.elarm;

import android.content.Intent;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import android.content.SharedPreferences;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.AdapterView;
import android.view.View;

import com.fingers.six.elarm.common.SwipeGestureFilter;
import com.fingers.six.elarm.common.SwipeGestureFilter.SwipeGestureListener;
import com.fingers.six.elarm.fragments.AlarmFragment;
import com.fingers.six.elarm.fragments.HistoryFragment;
import com.fingers.six.elarm.fragments.HomeFragment;
import com.fingers.six.elarm.fragments.QuestionFragment;
import com.fingers.six.elarm.fragments.QuestionListDetailFragment;
import com.fingers.six.elarm.fragments.SettingsFragment;
import com.fingers.six.elarm.sidebar.NavigationItem;
import com.fingers.six.elarm.sidebar.DrawerListAdapter;

import java.util.ArrayList;


public class ElarmActivity
        extends ActionBarActivity implements HomeFragment.Callbacks, SwipeGestureListener{

    ArrayList<NavigationItem> mnavigationItems = new ArrayList<NavigationItem>();
    private DrawerLayout mDrawerLayout;
    RelativeLayout mDrawerPane;
    ListView mDrawerList;

    // To detect swipe action
    SwipeGestureFilter detector;


    // Manage fragments
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;

    // Fragments
    HomeFragment elarm;
    SettingsFragment settings;
    HistoryFragment history;
    AlarmFragment alarm;
    QuestionFragment question;

    // Manage Preference data by a key-value database
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    // BroadCastReceiver to catch unlock screen event
    BroadcastReceiver broadcastReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_elarm);
        // Detect touched area
        detector = new SwipeGestureFilter(this,this);


        mnavigationItems.add(new NavigationItem("Home", R.mipmap.ic_launcher));
        mnavigationItems.add(new NavigationItem("History", R.mipmap.ic_launcher));
        mnavigationItems.add(new NavigationItem("Alarm", R.mipmap.ic_launcher));
        mnavigationItems.add(new NavigationItem("Setting", R.mipmap.ic_launcher));
        mnavigationItems.add(new NavigationItem("Help", R.mipmap.ic_launcher));

        // DrawerLayout
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);

        // Populate the Navigation Drawer with options
        mDrawerPane = (RelativeLayout) findViewById(R.id.drawerPane);
        mDrawerList = (ListView) findViewById(R.id.navList);
        DrawerListAdapter adapter = new DrawerListAdapter(this, mnavigationItems);
        mDrawerList.setAdapter(adapter);

        // Drawer Item click listeners
        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectItemFromDrawer(position);
            }
        });

        // Manage fragments
        fragmentManager = this.getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();

        elarm = (HomeFragment) fragmentManager.findFragmentByTag("home");
        settings = (SettingsFragment) fragmentManager.findFragmentByTag("settings");
        history = (HistoryFragment) fragmentManager.findFragmentByTag("history");
        alarm = (AlarmFragment) fragmentManager.findFragmentByTag("alarm");
        question = (QuestionFragment)fragmentManager.findFragmentByTag("question");

        if (elarm == null) {
            elarm = new HomeFragment();

            fragmentTransaction.add(R.id.mainContent, elarm, "home");

        }

        if (settings == null) {
            settings = new SettingsFragment();
            fragmentTransaction.add(R.id.mainContent, settings, "settings");
        }

        if (history == null) {
            history = new HistoryFragment();
            fragmentTransaction.add(R.id.mainContent, history, "history");
        }

        if (alarm == null) {
            alarm = new AlarmFragment();
            fragmentTransaction.add(R.id.mainContent, alarm, "alarm");
        }

        if(question == null) {
            question = new QuestionFragment();
            fragmentTransaction.add(R.id.mainContent,question,"question");
        }

        fragmentTransaction.detach(settings);

        fragmentTransaction.detach(history);

        fragmentTransaction.detach(alarm);
        fragmentTransaction.detach(question);

        fragmentTransaction.commit();

        // Preference manager
        sharedPreferences = this.getSharedPreferences("Elarm", MODE_PRIVATE);

        // For first time, we have to define default data
        editor = sharedPreferences.edit();
        if (sharedPreferences.getInt("time_mode", -1) == -1) {
            editor.putInt("time_mode", 2); // no time limit
        }
        if (sharedPreferences.getInt("unlock_or_not", -1) == -1) {
            editor.putInt("unlock_or_not", 0); // show questions after unlock screen
        }
        editor.commit();

        // BroadCastReceiver
        broadcastReceiver =  new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();
                // Detect screen unlocking events
                if(Intent.ACTION_USER_PRESENT.equalsIgnoreCase(action)) {
                    Log.d("MainActivity", "Screen is unlocked");
                    fragmentTransaction = ((ElarmActivity)context).getSupportFragmentManager().beginTransaction();

                    fragmentTransaction.detach(elarm);
                    fragmentTransaction.detach(settings);
                    fragmentTransaction.detach(alarm);
                    fragmentTransaction.attach(question);

                    fragmentTransaction.commitAllowingStateLoss();
                }
            }
        };

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Intent.ACTION_USER_PRESENT);
        registerReceiver(broadcastReceiver, intentFilter);
    }

    /**
     * Called when a particular item from the navigation drawer
     * is selected.
     */

    private void selectItemFromDrawer(int position) {
        mDrawerList.setItemChecked(position, true);

        String title = mnavigationItems.get(position).mTitle;
        setTitle(title);
        Log.d("MainActivity", title);

        fragmentTransaction = fragmentManager.beginTransaction();
        // Event actions
        if ("Home".equalsIgnoreCase(title)) {
            // Detach all presented fragments
            fragmentTransaction.detach(settings);

            fragmentTransaction.detach(history);

            fragmentTransaction.detach(alarm);
            fragmentTransaction.detach(question);

            // Attach elarm
            fragmentTransaction.attach(elarm);
        } else if ("Setting".equalsIgnoreCase(title)) {
            fragmentTransaction.detach(elarm);

            fragmentTransaction.detach(history);

            fragmentTransaction.detach(alarm);
            fragmentTransaction.detach(question);
            //Attach settings
            fragmentTransaction.attach(settings);

        } else if ("History".equalsIgnoreCase(title)) {
            fragmentTransaction.detach(elarm);
            fragmentTransaction.detach(settings);
            fragmentTransaction.detach(alarm);

            fragmentTransaction.attach(history);
//            fragmentTransaction.replace(R.id.mainContent, history);
        }
        if (title.equalsIgnoreCase("Alarm")) {
            fragmentTransaction.detach(elarm);
            fragmentTransaction.detach(settings);
            fragmentTransaction.detach(history);
            fragmentTransaction.detach(question);
            // Attach an alarm view
            fragmentTransaction.attach(alarm);
        }

        fragmentTransaction.commit();

        // Close the drawer
        mDrawerLayout.closeDrawer(mDrawerPane);
    }

    /**
     * To handle events when any radio button in the app is clicked.
     *
     * @param view
     */
    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch (view.getId()) {
            case R.id.setting_time_mode_1:
                if (checked) {
                    // Set time_mode to 0
                    editor.putInt("time_mode", 0);
                }
                break;

            case R.id.setting_time_mode_2:
                if (checked) {
                    editor.putInt("time_mode", 1);
                }
                break;

            case R.id.setting_time_mode_3:
                if (checked) {
                    editor.putInt("time_mode", 2);
                }
                break;

            default:
                break;

        }

        editor.commit();

        Log.d("MainActivity", "Now time_mode = " + sharedPreferences.getInt("time_mode", -1));
    }

    @Override
    public void onItemSelected(String id) {
        // In single-pane mode, simply start the detail activity
        // for the
        Intent detailIntent = new Intent(this, QuestionListDetailActivity.class);
        detailIntent.putExtra(QuestionListDetailFragment.ARG_ITEM_ID, id);
        startActivity(detailIntent);
    }

    /**
     * To handle events when any checkboxes button in the app is clicked.
     *
     * @param view
     */
    public void onCheckboxClicked(View view) {
        // Is the button now checked?
        boolean checked = ((CheckBox) view).isChecked();

        // Check which radio button was clicked
        switch (view.getId()) {
            case R.id.setting_unlock_alarm1:
                if (checked) {
                    // Set time_mode to 0
                    editor.putInt("unlock_or_not", 0);
                } else editor.putInt("unlock_or_not", 1);
                break;

            default:
                break;

        }

        editor.commit();

        Log.d("MainActivity", "Now unlock_or_not = " + sharedPreferences.getInt("unlock_or_not", -1));
    }

    @Override
    public void onSwipe(int direction) {
        String str = "";

        switch (direction) {

            case SwipeGestureFilter.SWIPE_RIGHT : str = "Swipe Right";
                break;
            case SwipeGestureFilter.SWIPE_LEFT :  str = "Swipe Left";
                break;
            case SwipeGestureFilter.SWIPE_DOWN :  str = "Swipe Down";
                break;
            case SwipeGestureFilter.SWIPE_UP :    str = "Swipe Up";
                break;
            default:
                break;

        }

        Log.d("MainActivity", "New action:" + str);
    }

    @Override
    public void onDoubleTap() {
        Log.d("MainActivity", "Double Tap");
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent me){
        // Call onTouchEvent of SimpleGestureFilter class
        detector.onTouchEvent(me);
        return super.dispatchTouchEvent(me);
    }

}
