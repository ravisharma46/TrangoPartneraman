package com.naruto.trangopartner.main_homepage;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ImageView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.naruto.trangopartner.R;
import com.naruto.trangopartner.SOSActivity;
import com.naruto.trangopartner.main_homepage.expandable_navigation_menu.ExpandableListAdapter;
import com.naruto.trangopartner.main_homepage.expandable_navigation_menu.MenuModel;
import com.naruto.trangopartner.main_homepage.fragments.MainHomepageContentFragment;
import com.naruto.trangopartner.main_homepage.fragments.MyTripsFragment;
import com.naruto.trangopartner.main_homepage.fragments.ProfileFragment;
import com.naruto.trangopartner.main_homepage.fragments.TransactionHistoryFragment;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import io.chirp.connect.ChirpConnect;
import io.chirp.connect.models.ChirpError;
import io.chirp.connect.interfaces.ConnectEventListener;

public class MainHomepageActivity extends AppCompatActivity implements MainHomepageContentFragment.OnFragmentInteractionListener,
        MyTripsFragment.OnFragmentInteractionListener, TransactionHistoryFragment.OnFragmentInteractionListener,
        ProfileFragment.OnFragmentInteractionListener{

    private Toolbar toolbar;

    ExpandableListAdapter expandableListAdapter;
    ExpandableListView expandableListView;
    List<MenuModel> headerList = new ArrayList<>();
    HashMap<MenuModel, List<MenuModel>> childList = new HashMap<>();

    //Global declaration for chirp
    private ChirpConnect chirp;

    private static final int RESULT_REQUEST_RECORD_AUDIO = 1;
    String CHIRP_APP_KEY = "2f7fFfEba47eeD56b6aec03C8";
    String CHIRP_APP_SECRET = "A7EA91FA7B4126dad3F8593d32Da5add8ccEAbbb3daEC9e0bE";
    String CHIRP_APP_CONFIG = "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";
    private int chirpState = 1;


//    private int lastExpandedPosition = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_homepage);

        toolbar = findViewById(R.id.main_home_toolbar_widget);
        toolbar.setTitle("Trango Partner");
        setSupportActionBar(toolbar);

        replaceFragment(MainHomepageContentFragment.newInstance(), "MainHomepageContent");

        setBottomNavigation();
        setNavigationDrawer();

        //use of chirp global declaration
        chirp = new ChirpConnect(this, CHIRP_APP_KEY, CHIRP_APP_SECRET);
        ChirpError error = chirp.setConfig(CHIRP_APP_CONFIG);
        if (error.getCode() == 0) {
            Log.v("ChirpSDK: ", "Configured ChirpSDK "+chirp.getVersion());
            chirp.setListener(chirpEventListener);
        } else {
            Log.e("ChirpError: ", error.getMessage());
        }

    }

    @Override
    protected void onResume() {
        super.onResume();

//        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
//            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.RECORD_AUDIO}, RESULT_REQUEST_RECORD_AUDIO);
//        }
//        else {
            // Start ChirpSDK sender and receiver, if no arguments are passed both sender and receiver are started
            ChirpError error = chirp.start(true, true);
            if (error.getCode() > 0) {
                Log.e("ChirpError: ", error.getMessage());
            } else {
                Log.v("ChirpSDK: ", "Started ChirpSDK");
            }
//        }
    }

    ConnectEventListener chirpEventListener = new ConnectEventListener() {
        @Override
        public void onSent(@NotNull byte[] bytes, int i) {

        }

        @Override
        public void onSending(@NotNull byte[] bytes, int i) {
            Log.v("onSending", new String(bytes));
        }

        @Override
        public void onReceived(@Nullable byte[] bytes, int i) {
            if (bytes != null) {
                String identifier = new String(bytes);
                Log.v("ChirpSDK: ", "Received " + identifier);
                showDialogBox(identifier);
            } else {
                Log.e("ChirpError: ", "Decode failed");
            }
        }

        @Override
        public void onReceiving(int i) {
            Log.v("onReceiving", i+ " - ");
        }

        @Override
        public void onStateChanged(int i, int i1) {

        }

        @Override
        public void onSystemVolumeChanged(float v, float v1) {

        }
    };

    public void showDialogBox(String qrCode) {
        new AlertDialog.Builder(this)
                .setTitle("QR Code Received")
                .setMessage("User Checked In")

                // Specifying a listener allows you to take an action before dismissing the dialog.
                // The dialog is automatically dismissed when a dialog button is clicked.
                .setPositiveButton("OK", (dialog, which) -> {
                    // Continue
                    dialog.dismiss();
                })

                // A null listener allows the button to dismiss the dialog and take no further action.
                .setNegativeButton("Cancel", null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    @Override
    protected void onPause() {
        super.onPause();
        chirp.stop();
    }

    private  void setBottomNavigation(){

        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = item -> {
        switch (item.getItemId()) {
            case R.id.navigation_home:
                if (!(getSupportFragmentManager().findFragmentById(R.id.home_fragment_container)
                        instanceof MainHomepageContentFragment)) {
                    toolbar.setTitle("Trango Partner");
                    delay(MainHomepageContentFragment.newInstance(), "MainHomepageContent");
                }
                return true;
            case R.id.navigation_trips:
                toolbar.setTitle("Trips Received");
                delay(MyTripsFragment.newInstance(), "MyTrips");
                return true;
            case R.id.navigation_wallet:
                toolbar.setTitle("Transaction History");
                delay(TransactionHistoryFragment.newInstance(), "TransactionHistory");
                return true;
            case R.id.navigation_profile:
                toolbar.setTitle("Profile");
                delay(ProfileFragment.newInstance(), "Profile");
                return true;
        }
        return false;
    };

    private void setNavigationDrawer(){

        expandableListView = findViewById(R.id.expandableListView);
        prepareMenuData();
        populateExpandableList();

        final DrawerLayout drawer = findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(

                this, drawer, toolbar, R.string.open_navigation_drawer, R.string.close_navigation_drawer);
        drawer.addDrawerListener(toggle);

        toggle.setDrawerIndicatorEnabled(false);

        toggle.setToolbarNavigationClickListener(v -> {
            if (drawer.isDrawerVisible(GravityCompat.START)) {
                drawer.closeDrawer(GravityCompat.START);
            } else {
                drawer.openDrawer(GravityCompat.START);
            }
        });

        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(menuItem -> onNavigationItemSelected());

        // Set Navigation Header
        View headerView      =  navigationView.getHeaderView(0);

        ImageView iv_profile =  headerView.findViewById(R.id.profile_pic);
//        TextView tv_name     =  headerView.findViewById(R.id.name);
//        TextView tv_mobile   =  headerView.findViewById(R.id.mobile);

        // need to set a profile pic -
        iv_profile.setOnClickListener(v -> {
            //startActivity(new Intent(AppHomePage.this,Profile.class));
            drawer.closeDrawer(GravityCompat.START);
        });
        //tv_name.setText(mUserInfo.get(LoginSessionManager.NAME));
        //tv_mobile.setText(mUserInfo.get(LoginSessionManager.MOBILE));

    }

    private void prepareMenuData() {

        MenuModel menuModel = new MenuModel(1,"Home",false,true,R.drawable.ic_home);
        headerList.add(menuModel);
        if (!menuModel.isHasChildren()) {
            childList.put(menuModel, null);
        }

        menuModel = new MenuModel(2,"Profile",false,true,R.drawable.ic_profile);
        headerList.add(menuModel);
        if (!menuModel.isHasChildren()) {
            childList.put(menuModel, null);
        }

        menuModel = new MenuModel(3,"My Trips",false,true,R.drawable.ic_trips);
        headerList.add(menuModel);
        if (!menuModel.isHasChildren()) {
            childList.put(menuModel, null);
        }

        menuModel = new MenuModel(4,"Transaction History",false,true,R.drawable.ic_wallet);
        headerList.add(menuModel);
        if (!menuModel.isHasChildren()) {
            childList.put(menuModel, null);
        }

        menuModel = new MenuModel(5,"Notifications",false,true,R.drawable.active_dots);
        headerList.add(menuModel);
        if (!menuModel.isHasChildren()) {
            childList.put(menuModel, null);
        }

        menuModel = new MenuModel(6,"About Us",false,true,R.drawable.active_dots);
        headerList.add(menuModel);
        if (!menuModel.isHasChildren()) {
            childList.put(menuModel, null);
        }

        menuModel = new MenuModel(7,"SOS",false,true,R.drawable.active_dots);
        headerList.add(menuModel);
        if (!menuModel.isHasChildren()) {
            childList.put(menuModel, null);
        }

        menuModel = new MenuModel(8,"",false,false, 0);  //Blank Space
        headerList.add(menuModel);
        if (!menuModel.isHasChildren()) {
            childList.put(menuModel, null);
        }

        menuModel = new MenuModel(9,"Logout",false,true,R.drawable.active_dots);
        headerList.add(menuModel);
        if (!menuModel.isHasChildren()) {
            childList.put(menuModel, null);
        }

    }

    private void populateExpandableList() {

        expandableListAdapter = new ExpandableListAdapter(this, headerList, childList);

        ViewGroup footerView = (ViewGroup) getLayoutInflater().inflate(R.layout.home_page_expandable_view_footer, expandableListView, false);
        expandableListView.addFooterView(footerView);

        expandableListView.setAdapter(expandableListAdapter);

        expandableListView.setOnGroupClickListener((parent, v, groupPosition, id) -> {

//            DrawerLayout drawer = findViewById(R.id.drawer_layout);
            MenuModel menuModel = headerList.get(groupPosition);
            int id_int = (int) id;

            if (menuModel.isGroup()) {

                if (!menuModel.isHasChildren()) {
                    switch (id_int) {
                        case 0:
                            if (!(getSupportFragmentManager().findFragmentById(R.id.home_fragment_container)
                                    instanceof MainHomepageContentFragment)) {
                                toolbar.setTitle("Trango Partner");
                                delay(MainHomepageContentFragment.newInstance(), "MainHomepageContent");
                            }
                            break;
                        case 1:
                            toolbar.setTitle("Profile");
                            delay(ProfileFragment.newInstance(), "Profile");
                            break;
                        case 2:
                            toolbar.setTitle("Trips Received");
                            delay(MyTripsFragment.newInstance(), "MyTrips");
                            break;
                        case 3:
                            toolbar.setTitle("Transaction History");
                            delay(TransactionHistoryFragment.newInstance(), "TransactionHistory");
                            break;
                        case 4:
                        case 7:
                            break;
                        case 5:
//                            delay(new AboutUs());
                            break;
                        case 6:
                            delay(new SOSActivity());
                            break;
                        case 8:
                            // mSession.logoutUser();
                            finish();
                            break;
                    }
                    onBackPressed();
                }
            }

            return false;
        });

    }

    @Override
    public void onBackPressed() {
//        mMenuMyVehicleLayout.setVisibility(View.GONE);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);

        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
            finish();
        }
    }

    public boolean onNavigationItemSelected() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void replaceFragment(Fragment fragment, String fragmentTag) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.home_fragment_container, fragment);
//        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        if (!fragmentTag.equals("MainHomepageContent")) {
            ft.addToBackStack(fragmentTag);
        }
        ft.commit();
    }

    public void delay(Fragment fragment, String fragmentTag){
        Handler handler = new Handler();

        handler.postDelayed(() -> replaceFragment(fragment, fragmentTag), 250);
    }

    public void delay(Activity activity){
        Handler handler = new Handler();

        handler.postDelayed(() -> {
            Intent intent = new Intent(this, activity.getClass());
            startActivity(intent);

        }, 250);
    }

    @Override
    public void onFragmentInteraction(View view) {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        chirp.stop();
        try {
            chirp.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void toggleChirp(View view) {
        if(chirpState == 0) {
            chirpState = 1;
            // Start ChirpSDK sender and receiver, if no arguments are passed both sender and receiver are started
            ChirpError error = chirp.start(true, true);
            if (error.getCode() > 0) {
                Log.e("ChirpError: ", error.getMessage());
            } else {
                Log.v("ChirpSDK: ", "Started ChirpSDK");
            }
        } else {
            chirpState = 0;
            chirp.stop();
        }
    }
}
