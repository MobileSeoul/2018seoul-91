package youthshelter.youth.co.kr.activity;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.Window;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import youthshelter.youth.co.kr.data.YouthCenter;
import youthshelter.youth.co.kr.fragment.IntroFragment;
import youthshelter.youth.co.kr.R;


public class IntroActivity extends AppCompatActivity {
    DatabaseReference mRootRef;
    DatabaseReference mPostReference;
    ArrayList<YouthCenter> youthCenters = new ArrayList<>();
    boolean networkState;
    private boolean checkNetwokState() {
        try{
            ConnectivityManager manager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mobile = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
            NetworkInfo wifi = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            NetworkInfo lte_4g = manager.getNetworkInfo(ConnectivityManager.TYPE_WIMAX);
            boolean blte_4g = false;
            if(lte_4g != null)
                blte_4g = lte_4g.isConnected();

            if(mobile != null){
                if(mobile.isConnected()){
                    return true;
                } else {
                    if(wifi != null){
                        if(wifi.isConnected()){
                            return true;
                        } else {
                            if(blte_4g) {
                                return true;
                            } else
                                return false;
                        }
                    }
                }
            } else {
                if(wifi != null){
                    if(wifi.isConnected()){
                        return true;
                    } else {
                        if(blte_4g) {
                            return true;
                        } else
                            return false;
                    }
                } else {
                    if(blte_4g) {
                        return true;
                    } else
                        return false;
                }
            }
        } catch(Exception e){
            // try{} 블럭 안에서 예외(Exception)가 발생할경우 이 문제를 처리할 코드 작성
            return false;
        }
        return false;
    }

    public void showDialog()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("네트워크 연결 상태를 확인 한 후, 다시 시도해주세요.");
        builder.setPositiveButton("다시시도",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        networkState = checkNetwokState();
                        if(!networkState){//네트워크가 안 될 때
                            showDialog();
                        }
                        else {
                            firebaseDatabaseInit();
                        }
                    }
                });
        builder.show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_intro);
        setFragment(new IntroFragment());
        networkState = checkNetwokState();
        if(!networkState){//네트워크가 안 될 때
           showDialog();
        }
        else {
            firebaseDatabaseInit();
        }
    }
    public void firebaseDatabaseInit(){
        mRootRef = FirebaseDatabase.getInstance().getReference();
        mPostReference = mRootRef.child("center");
        mPostReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //Log.i("ttttt", dataSnapshot.getKey() + dataSnapshot.getChildrenCount() + "@@");
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    //Log.i("ttttt", snapshot.getKey() + "!!");
                    YouthCenter center = snapshot.getValue(YouthCenter.class);
                    youthCenters.add(center);
                }
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.putExtra("centers", youthCenters);
                startActivity(intent);
                overridePendingTransition(0, 0);
                finish();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    public void setFragment(Fragment fragment) {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.Fragment, fragment);
        fragmentTransaction.commit();
    }
}