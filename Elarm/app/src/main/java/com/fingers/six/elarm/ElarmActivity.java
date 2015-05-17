package com.fingers.six.elarm;


import android.content.Intent;
import android.net.Uri;

import android.content.SharedPreferences;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.AdapterView;
import android.view.View;

import com.fingers.six.elarm.sidebar.NavigationItem;
import com.fingers.six.elarm.sidebar.DrawerListAdapter;

import java.util.ArrayList;


public class ElarmActivity
        extends ActionBarActivity
        implements HomeFragment.Callbacks,
        HomeFragment.OnFragmentInteractionListener,
        QuestionListDetailFragment.OnFragmentInteractionListener {

    ArrayList<NavigationItem> mnavigationItems = new ArrayList<NavigationItem>();
    private DrawerLayout mDrawerLayout;
    RelativeLayout mDrawerPane;
    ListView mDrawerList;


    // Manage fragments
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;

    // Fragments
    HomeFragment elarm;
    SettingsFragment settings;
    HistoryFragment history;
    AlarmFragment alarm;

    // Manage Preference data by a key-value database
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_elarm);


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


//                // Test show home fragment:
//                Fragment fragment = new HomeFragment();
//
//                FragmentManager fragmentManager = getSupportFragmentManager();
//                fragmentManager.beginTransaction()
//                        .replace(R.id.mainContent, fragment)
//                        .commit();
//
//                mDrawerList.setItemChecked(position, true);
//                setTitle("Home");
                //               mDrawerLayout.closeDrawer(mDrawerList);
//                mDrawerLayout.closeDrawer(mDrawerList);

                // The actions which are added below this line shouldn't be duplicated
                // with the content in above selectItemFromDrawer(position);
            }
        });

        // Manage fragments
        fragmentManager = this.getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();


        elarm = (HomeFragment) fragmentManager.findFragmentByTag("home");
        settings = (SettingsFragment) fragmentManager.findFragmentByTag("settings");
        history = (HistoryFragment) fragmentManager.findFragmentByTag("history");
        alarm = (AlarmFragment) fragmentManager.findFragmentByTag("alarm");


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

        fragmentTransaction.detach(settings);

        fragmentTransaction.detach(history);

        fragmentTransaction.detach(alarm);

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

            // Attach elarm
            fragmentTransaction.attach(elarm);
        } else if ("Setting".equalsIgnoreCase(title)) {
            fragmentTransaction.detach(elarm);

            fragmentTransaction.detach(history);

            fragmentTransaction.detach(alarm);

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

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_elarm, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }

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
    public void onFragmentInteraction(Uri uri) {

    }
}
