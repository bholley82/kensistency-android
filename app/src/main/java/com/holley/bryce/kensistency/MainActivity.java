package com.holley.bryce.kensistency;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.ListFragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View.OnClickListener;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    // viewpager globals
    static final int NUM_ITEMS = 3;
    static final int NUM_HOME = 0;
    static final int NUM_TRICKLISTS = 1;
    static final int NUM_FRIENDS = 2;
    MyAdapter myAdapter;
    ViewPager myPager;

    // notifications drawer globals
    private String[] mNotifications = {"notification 0","notification 1"};
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // toolbar_top initialization
        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar_top);
        //myToolbar.setLogo(R.drawable.ic_launcher); super sweet main logo thingy.
        setSupportActionBar(myToolbar);
        ActionBar myActionBar = getSupportActionBar();
        myActionBar.setDisplayShowTitleEnabled(false);
        myActionBar.setDisplayHomeAsUpEnabled(true);

        // pager initialization
        myAdapter = new MyAdapter(getSupportFragmentManager());
        myPager = (ViewPager)findViewById(R.id.pager);
        myPager.setAdapter(myAdapter);

        // drawer initialization
        //mNotifications = getResources().getStringArray({}); //TODO how to get notifications?
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.drawer_notifications);
        mDrawerList.setAdapter(new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, mNotifications));
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

        // assign the bottom_toolbar buttons to viewpager fragments and drawer.
        Button button = (Button)findViewById(R.id.button_bottom_home);
        button.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                myPager.setCurrentItem(NUM_HOME);
            }
        });
        button = (Button)findViewById(R.id.button_bottom_trickLists);
        button.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                myPager.setCurrentItem(NUM_TRICKLISTS);
            }
        });
        button = (Button)findViewById(R.id.button_bottom_friends);
        button.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                myPager.setCurrentItem(NUM_FRIENDS);
            }
        });
        button = (Button)findViewById(R.id.button_bottom_notifications); //TODO... why doesnt this work..?
        button.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                mDrawerLayout.openDrawer(mDrawerList);
            }
        });

    }

    // toolbar_top gets inflated from toolbar_top
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toolbar_top, menu);
        return true;
    }

    // toolbar_top on select thingy
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.action_settings:
                // go to settings
                Toast.makeText(getApplicationContext(), "going to settings", Toast.LENGTH_SHORT).show();
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

    public static class MyAdapter extends FragmentPagerAdapter{
        public MyAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return NUM_ITEMS;
        }

        @Override
        public Fragment getItem(int position) {
            switch(position){
                case NUM_HOME:
                    return new HomeFragment();

                case NUM_TRICKLISTS:
                    return new TrickListsFragment();

                case NUM_FRIENDS:
                    return new TrickListsFragment();

                // this shouldn't be reachable, but lets send them home just in case;
                default:
                    return new HomeFragment();
            }
        }
    }

    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView parent, View view, int position, long id) {
            selectItem(position);//TODO could also pass view in if appropriate
        }
    }

    private void selectItem(int position) {
        //TODO go to the proper thingy in the viewpager
        mDrawerList.setItemChecked(position, true);
        Toast.makeText(getApplicationContext(),"you clicked notification " + position, Toast.LENGTH_SHORT).show();
        mDrawerLayout.closeDrawer(mDrawerList);
    }

    public static class HomeFragment extends Fragment {
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
            return inflater.inflate(R.layout.fragment_home, container, false);
        }
    }

    //TODO possibly make a base class that will be inherited by trick, trickList, friend, etc
    public static class TrickListsFragment extends ListFragment {
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
            return inflater.inflate(R.layout.fragment_trick_lists, container, false);
        }

        @Override
        public void onActivityCreated(Bundle savedInstanceState) {
            super.onActivityCreated(savedInstanceState);
            setListAdapter(new ArrayAdapter<String>(getActivity(),
                    android.R.layout.simple_list_item_1, new String[]{"trick1", "trick2"}));
        }
    }
}