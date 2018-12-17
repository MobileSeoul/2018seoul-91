package youthshelter.youth.co.kr.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Address;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import youthshelter.youth.co.kr.R;
import youthshelter.youth.co.kr.Util.GeocodeUtil;
import youthshelter.youth.co.kr.Util.GpsInfo;
import youthshelter.youth.co.kr.adapter.AddressListViewAdapter;
import youthshelter.youth.co.kr.item.ListViewItem;
import youthshelter.youth.co.kr.task.AddressAddTask;

public class SetMapActivity extends AppCompatActivity {
    static final String[] LIST_MENU = {"LIST1", "LIST2", "LIST3", "LIST4", "LIST5", "LIST6", "LIST7", "LIST8", "LIST9"};
    private EditText editText_address;
    private ImageButton findbtn;

    private ListView listview;
    private DisplayMetrics metrics;
    private AddressListViewAdapter adapter = new AddressListViewAdapter();
    private GeocodeUtil geocodeUtil;

    private String context = Context.LOCATION_SERVICE;
    private LocationManager locationManager;
    private GpsInfo gps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setmap);
        locationManager = (LocationManager)getSystemService(context);
        geocodeUtil = new GeocodeUtil(this);
        listview = (ListView) findViewById(R.id.listview1);
        listview.setAdapter(adapter);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ListViewItem listViewItem = (ListViewItem) listview.getAdapter().getItem(position);
                String roadAddress = listViewItem.getRoadAddress();
                String title = listViewItem.getTitle();

                ArrayList<GeocodeUtil.GeoLocation> geoLocations = geocodeUtil.getGeoLocationListUsingAddress(roadAddress);
                if (geoLocations.size() < 1) {
                    geoLocations = geocodeUtil.getGeoLocationListUsingAddress(title);
                }
                Log.i("", geoLocations.get(0).getLatitude() + " : Latitute,==" + geoLocations.get(0).getLongitude());
                Intent intent = new Intent();
                intent.putExtra("title",title);
                intent.putExtra("lat", geoLocations.get(0).getLatitude());
                intent.putExtra("lon", geoLocations.get(0).getLongitude());

                setResult(RESULT_OK, intent);
                Toast.makeText(view.getContext(),title,Toast.LENGTH_LONG);
                finish();
            }
        });
        Resources resources = this.getResources();
        metrics = resources.getDisplayMetrics();

        ActionBar actionBar = getSupportActionBar();
        actionBar.setElevation(0);
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setCustomView(R.layout.action_bar_setmap);
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#ffffff")));
        View view = actionBar.getCustomView();

        ImageButton imageButton = (ImageButton) view.findViewById(R.id.action_bar_setmap_back);

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // activity for result
            }
        });

        findbtn = (ImageButton) findViewById(R.id.findBtn);
        editText_address = (EditText) findViewById(R.id.edittext_address);

        editText_address.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                switch (i) {
                    case EditorInfo.IME_ACTION_SEARCH:
                        findbtn.callOnClick();
                        break;
                    default:
                        return false;
                }
                return true;
            }
        });

        findbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(editText_address.getWindowToken(), 0);

                adapter.clear();
                //adapter.notifyDataSetChanged();

                new AddressAddTask().execute(editText_address, adapter, metrics, listview);

            }
        });

        Button nowFind = (Button) findViewById(R.id.now_location);

        nowFind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                /*if(!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                    alertCheckGPS();
                }*/

                //MainActivity.checkLocationPermission(view.getContext(), SetMapActivity.this);
                gps = new GpsInfo(view.getContext());

                if (gps.isGetLocation()) {

                    double latitude = gps.getLatitude();
                    double longitude = gps.getLongitude();

                    Intent intent = new Intent();
                    ArrayList<Address> titles = geocodeUtil.getAddressListUsingGeolocation(new GeocodeUtil.GeoLocation(latitude,longitude));
                    if(titles.size() != 0) {// 37.60277220158049 ,127.0976283341229
                        Address address =  titles.get(0);
                        if(address!=null) {
                            if(address.getThoroughfare()== null){
                                Toast.makeText(view.getContext(),"현재 정확한 위치를 찾을 수 없습니다.",Toast.LENGTH_LONG);
                                intent.putExtra("title", address.getAddressLine(0).split(" ")[1]);
                            }else {
                                intent.putExtra("title", address.getThoroughfare());
                            }
                            intent.putExtra("lat", latitude);
                            intent.putExtra("lon", longitude);
                            Log.i("yeah", titles.get(0).toString());
                            setResult(RESULT_OK, intent);

                            finish();
                        }else{
                            Toast.makeText(view.getContext(),"현재 위치를 찾을 수 없습니다.",Toast.LENGTH_LONG);
                        }
                    }else{
                        Toast.makeText(view.getContext(),"현재 위치를 찾을 수 없습니다.",Toast.LENGTH_LONG);
                    }
                    Log.i("lat",latitude+" ,"+longitude);
                } else {
                    // GPS 를 사용할수 없으므로
                    gps.showSettingsAlert();
                }
            }
        });
    }

    private void alertCheckGPS() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("GPS기능이 꺼져있습니다. GPS기능을 켜시겠습니까?")
                .setCancelable(false)
                .setPositiveButton("허용",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                moveConfigGPS();
                            }
                        })
                .setNegativeButton("거부",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    // GPS 설정화면으로 이동
    private void moveConfigGPS() {
        Intent gpsOptionsIntent = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        startActivity(gpsOptionsIntent);
    }

}