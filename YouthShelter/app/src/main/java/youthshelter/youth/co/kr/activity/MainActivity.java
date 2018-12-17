package youthshelter.youth.co.kr.activity;


import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;

import youthshelter.youth.co.kr.GlideApp;
import youthshelter.youth.co.kr.adapter.ViewPagerAdapter;
import youthshelter.youth.co.kr.R;
import youthshelter.youth.co.kr.components.CustomViewPager;
import youthshelter.youth.co.kr.data.YouthCenter;


public class MainActivity extends AppCompatActivity {
    private ViewPagerAdapter viewPagerAdapter;
    private CustomViewPager viewPager;

    private MessageHandler messageHandler;
    //private ActionBar



    ArrayList<YouthCenter> youthCenters;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        checkLocationPermission(this, MainActivity.this);
        youthCenters = (ArrayList<YouthCenter>) getIntent().getSerializableExtra("centers");
        Log.i("tttttt", youthCenters.size() + "");

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.action_bar_main);
        View view = getSupportActionBar().getCustomView();

        messageHandler = new MessageHandler();
        viewPager = (CustomViewPager) findViewById(R.id.main_viewPager);
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), this, messageHandler, youthCenters);
        viewPager.setAdapter(viewPagerAdapter);
        viewPager.setSwipeEnabled(false);


        TabLayout tabLayout = (TabLayout) findViewById(R.id.main_tabLayout);
        tabLayout.setupWithViewPager(viewPager);
        //GlideApp.with(this).load(R.drawable.shelter_tab).centerCrop().placeholder(R.drawable.noimage).thumbnail(0.5f).diskCacheStrategy(DiskCacheStrategy.ALL).into(holder.shelter_image_ImageView);
        //GlideApp.with(this).load(R.drawable.culture_tab).centerCrop().placeholder(R.drawable.noimage).thumbnail(0.5f).diskCacheStrategy(DiskCacheStrategy.ALL).into(holder.shelter_image_ImageView);
        tabLayout.getTabAt(0).setCustomView(R.layout.tab_button_layout);
        tabLayout.getTabAt(1).setCustomView(R.layout.tab_button_layout2);

    }

    class MessageHandler extends Handler {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            switch (msg.what) {

            }
        }

    }

    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;

    public static boolean checkLocationPermission(Context context, final Activity activity) {
        if (ContextCompat.checkSelfPermission(context,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {
                ActivityCompat.requestPermissions(activity,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(activity,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
            return false;
        } else {
            return true;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // location-related task you need to do.
                    if (ContextCompat.checkSelfPermission(this,
                            Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {

                        //Request location updates:
                        //locationManager.requestLocationUpdates(provider, 400, 1, this);
                    }
                    ContextCompat.checkSelfPermission(this,
                            Manifest.permission.ACCESS_COARSE_LOCATION);


                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.

                }
                return;
            }

        }
    }

}
