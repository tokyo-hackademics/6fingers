package com.fingers.six.elarm;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.AdapterView;
import android.view.View;
import android.widget.Toast;

import com.fingers.six.elarm.sidebar.NavigationItem;
import com.fingers.six.elarm.sidebar.DrawerListAdapter;
import java.util.ArrayList;


public class ElarmActivity extends ActionBarActivity {
    ArrayList<NavigationItem> mnavigationItems = new ArrayList<NavigationItem>();
    private DrawerLayout mDrawerLayout;
    RelativeLayout mDrawerPane;
    ListView mDrawerList;

    // Manage fragments
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;

    // Fragments
    ElarmActivityFragment elarm;
    SettingsFragment settings;
    AlarmFragment      alarm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_elarm);

        mnavigationItems.add(new NavigationItem("Home", R.mipmap.ic_launcher));
        mnavigationItems.add(new NavigationItem("History",R.mipmap.ic_launcher));
        mnavigationItems.add(new NavigationItem("Alarm",R.mipmap.ic_launcher));
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

        elarm    = (ElarmActivityFragment)fragmentManager.findFragmentByTag("elarm_main");
        settings = (SettingsFragment)fragmentManager.findFragmentByTag("settings");
        alarm    = (AlarmFragment)fragmentManager.findFragmentByTag("alarm");


        if(elarm == null) {
            elarm = new ElarmActivityFragment();
            fragmentTransaction.add(R.id.mainContent,elarm,"elarm_id");
        }

        if(settings == null) {
            settings = new SettingsFragment();
            fragmentTransaction.add(R.id.mainContent,settings,"settings");
        }

        if(alarm == null) {
            alarm = new AlarmFragment();
            fragmentTransaction.add(R.id.mainContent,alarm,"alarm");
        }

      //  fragmentTransaction.detach(settings);
        fragmentTransaction.commit();
    }
    /**
*   Called when a particular item from the navigation drawer
*   is selected.
*  */
    private void selectItemFromDrawer(int position) {
        mDrawerList.setItemChecked(position, true);

        String title = mnavigationItems.get(position).mTitle;
        setTitle(title);
        Log.d("MainActivity", title);

        fragmentTransaction = fragmentManager.beginTransaction();
        // Event actions
        if("Home".equalsIgnoreCase(title)) {
            // Detach all presented fragments
            fragmentTransaction.detach(settings);
            fragmentTransaction.detach(alarm);

            // Attach elarm
            fragmentTransaction.attach(elarm);
        }
        else if("Setting".equalsIgnoreCase(title)) {
            fragmentTransaction.detach(elarm);
            fragmentTransaction.detach(alarm);
            //Attach settings
            fragmentTransaction.attach(settings);
        }
        if(title.equals("Alarm")) {
            fragmentTransaction.detach(elarm);
            fragmentTransaction.detach(settings);

            fragmentTransaction.attach(alarm);
        }

        fragmentTransaction.commit();

        // Close the drawer
        mDrawerLayout.closeDrawer(mDrawerPane);
//        Toast.makeText(getApplicationContext(), mnavigationItems.get(position).mTitle, Toast.LENGTH_SHORT).show();
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
}
