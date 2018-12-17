package youthshelter.youth.co.kr.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;

import java.util.ArrayList;

import youthshelter.youth.co.kr.R;
import youthshelter.youth.co.kr.adapter.ImageViewPagerAdapter;
import youthshelter.youth.co.kr.components.CircleAnimIndicator;
import youthshelter.youth.co.kr.data.YouthCenter;

public class DetailActivity extends AppCompatActivity {
    private ViewPager viewPager;
    private YouthCenter center;

    private CircleAnimIndicator circleAnimIndicator;

    private TextView shelter_info_detail_TextView;
    private TextView shelter_location_detail_TextView;
    private TextView shelter_name_detail_TextView;
    private TextView shelter_tel_detail_TextView;
    private TextView shelter_website_detail_TextView;
    private TextView shelter_like_detail_TextView;

    private LinearLayout shelter_tel_detail_LinearLayout;
    private LinearLayout shelter_website_detail_LinearLayout;
    private LinearLayout culture_like_detail_LinearLayout;
    private LinearLayout shelter_map_detail_LinearLayout;

    private Button shelter_map_detail_Button;

    DatabaseReference mRootRef;
    DatabaseReference mPostReference;

    int count = 0;

    @Override
    public void onBackPressed() {

        Intent intent = new Intent();
        intent.putExtra("index", getIntent().getIntExtra("index",0));
        intent.putExtra("count", count);
        //Log.i("tttttttttttt",getIntent().getIntExtra("index",0) + " " + count + "!!!!");
        setResult(100, intent);

        finish();
        super.onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        mRootRef = FirebaseDatabase.getInstance().getReference();
        mPostReference = mRootRef.child("center");

        center = (YouthCenter) getIntent().getSerializableExtra("center");
        Log.i("tttttt", "~~" + center.toString());
        shelter_info_detail_TextView = (TextView) findViewById(R.id.shelter_info_detail_TextView);
        shelter_location_detail_TextView = (TextView) findViewById(R.id.shelter_location_detail_TextView);
        shelter_name_detail_TextView = (TextView) findViewById(R.id.shelter_name_detail_TextView);
        shelter_tel_detail_TextView = (TextView) findViewById(R.id.shelter_tel_detail_TextView);
        shelter_website_detail_TextView = (TextView) findViewById(R.id.shelter_website_detail_TextView);
        shelter_like_detail_TextView = (TextView) findViewById(R.id.shelter_like_detail_TextView);

        shelter_tel_detail_LinearLayout = (LinearLayout) findViewById(R.id.shelter_tel_detail_LinearLayout);
        shelter_website_detail_LinearLayout = (LinearLayout) findViewById(R.id.shelter_website_detail_LinearLayout);
        culture_like_detail_LinearLayout = (LinearLayout) findViewById(R.id.culture_like_detail_LinearLayout);

        shelter_map_detail_LinearLayout = (LinearLayout) findViewById(R.id.shelter_map_detail_LinearLayout);
        shelter_map_detail_LinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                        Uri.parse("geo:" + center.getLatitude() + "," + center.getLongitude() + "?q=" + center.getLatitude() + "," + center.getLongitude() + "(" + center.getName() + ")"));
                startActivity(intent);
            }
        });

        culture_like_detail_LinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shelter_like_detail_TextView.setText(Integer.parseInt(shelter_like_detail_TextView.getText().toString()) + 1 + "");
                mPostReference.orderByChild("name").equalTo(center.getName()).addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                        mPostReference.child(dataSnapshot.getKey()).child("like").runTransaction(new Transaction.Handler() {
                            @Override
                            public Transaction.Result doTransaction(MutableData mutableData) {

                                count = mutableData.getValue(Integer.class) + 1;
                                shelter_like_detail_TextView.setText(Integer.toString(count));
                                mutableData.setValue(count);

                                return Transaction.success(mutableData);
                            }

                            @Override
                            public void onComplete(
                                    DatabaseError databaseError, boolean committed, DataSnapshot dataSnapshot) {
                                System.out.println("Transaction completed");


                            }
                        });


                    }

                    @Override
                    public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                        int like = dataSnapshot.child("like").getValue(Integer.class);
                        shelter_like_detail_TextView.setText(Integer.toString(like));
                    }

                    @Override
                    public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

                    }

                    @Override
                    public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });


        shelter_name_detail_TextView.setText(center.getName());
        shelter_location_detail_TextView.setText(center.getAddress());
        shelter_tel_detail_TextView.setText(center.getPhone());
        shelter_like_detail_TextView.setText(Integer.toString(center.getLike()));

        String info = "※소개\n" + center.getIntroduction() + "\n\n※정보\n" + center.getBonus() + "\n\n※평일\n" + center.getWeekday() + "\n\n※토요일\n" + center.getSaturday() + "\n\n※일요일\n" + center.getSunday();
        shelter_info_detail_TextView.setText(info);
        shelter_tel_detail_LinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + shelter_tel_detail_TextView.getText()));
                startActivity(intent);
            }
        });

        shelter_website_detail_LinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (center == null || center.getHomepage() == null) {
                    Toast.makeText(getApplicationContext(), "웹사이트를 지원하지 않는 장소 입니다.", Toast.LENGTH_SHORT).show();
                } else
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(center.getHomepage())));
            }
        });

        viewPager = (ViewPager) findViewById(R.id.imageViewPager);
        viewPager.setOffscreenPageLimit(10);
        circleAnimIndicator = (CircleAnimIndicator) findViewById(R.id.circleAnimIndicator);

        initIndicaotor(center.getCount());

        ImageViewPagerAdapter viewPagerAdapter = new ImageViewPagerAdapter(getSupportFragmentManager(), this, center.getImage(), center.getCount(), center.getFormat());
        viewPager.setAdapter(viewPagerAdapter);
        viewPager.addOnPageChangeListener(mOnPageChangeListener);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void initIndicaotor(int size) {

        //원사이의 간격
        circleAnimIndicator.setItemMargin(15);
        //애니메이션 속도
        circleAnimIndicator.setAnimDuration(300);
        //indecator 생성
        circleAnimIndicator.createDotPanel(size, R.drawable.indicator_non, R.drawable.indicator_on);
    }

    /**
     * ViewPager 전환시 호출되는 메서드
     */
    private ViewPager.OnPageChangeListener mOnPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        }

        @Override
        public void onPageSelected(int position) {
            circleAnimIndicator.selectDot(position);
        }

        @Override
        public void onPageScrollStateChanged(int state) {
        }
    };

}
