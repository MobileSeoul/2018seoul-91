package youthshelter.youth.co.kr.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.github.chrisbanes.photoview.PhotoView;

import java.util.ArrayList;

import youthshelter.youth.co.kr.GlideApp;
import youthshelter.youth.co.kr.R;
import youthshelter.youth.co.kr.components.CircleAnimIndicator;
import youthshelter.youth.co.kr.data.CultureData;

public class Detail2Activity extends AppCompatActivity {

    private ViewPager viewPager;
    private CultureData cultureData;

    private ArrayList<String> numberList;

    private CircleAnimIndicator circleAnimIndicator;

    private TextView culture_info_detail_TextView;
    private TextView culture_location_detail_TextView;
    private TextView culture_name_detail_TextView;
    private TextView culture_tel_detail_TextView;
    private TextView culture_website_detail_TextView;

    private ImageView culture_detail_imageview;

    private LinearLayout culture_tel_detail_LinearLayout;
    private LinearLayout culture_website_detail_LinearLayout;

    private PhotoView photoView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail2);

        cultureData = (CultureData)getIntent().getSerializableExtra("culture");

        //culture_detail_imageview = (ImageView)findViewById(R.id.detail_imageview);
        photoView = (PhotoView)findViewById(R.id.detail_imageview);

        culture_info_detail_TextView = (TextView)findViewById(R.id.culture_info_detail_TextView);
        culture_location_detail_TextView = (TextView)findViewById(R.id.culture_location_detail_TextView);
        culture_name_detail_TextView = (TextView)findViewById(R.id.culture_name_detail_TextView);
        culture_tel_detail_TextView = (TextView)findViewById(R.id.culture_tel_detail_TextView);
        culture_website_detail_TextView = (TextView)findViewById(R.id.culture_website_detail_TextView);

        culture_tel_detail_LinearLayout = (LinearLayout)findViewById(R.id.culture_tel_detail_LinearLayout);
        culture_website_detail_LinearLayout = (LinearLayout)findViewById(R.id.culture_website_detail_LinearLayout);

        culture_info_detail_TextView.setText("※소개\n"+ cultureData.getCultureName() + "\n\n※정보\n"+ cultureData.getCultureprogram() + "\n\n※시간\n" + cultureData.getStart_date() + " - " + cultureData.getEnd_date() + "\n" + cultureData.getCulturePlayTime());
        culture_location_detail_TextView.setText(cultureData.getCultureLocation());
        if(cultureData.getCultureTel().length() == 9 || cultureData.getCultureTel().length() == 8)
            culture_tel_detail_TextView.setText("02-" + cultureData.getCultureTel());
        else
            culture_tel_detail_TextView.setText(cultureData.getCultureTel());
//      culture_website_detail_TextView.setText(cultureData.getCultureWebSite());
        culture_name_detail_TextView.setText(cultureData.getCultureName());

        culture_tel_detail_LinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:"+culture_tel_detail_TextView.getText()));
                startActivity(intent);
            }
        });

        culture_website_detail_LinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(cultureData == null || cultureData.getCultureWebSite() == null){
                    Toast.makeText(getApplicationContext(),"웹사이트를 지원하지 않는 장소 입니다.",Toast.LENGTH_SHORT).show();
                }
                else{
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(cultureData.getCultureWebSite())));
                }
            }
        });

        //Glide.with(this).load(cultureData.getImageName().toLowerCase()).thumbnail(0.1f).into(culture_detail_imageview);
        GlideApp.with(this).load(cultureData.getImageName().toLowerCase()).placeholder(R.drawable.noimage).diskCacheStrategy(DiskCacheStrategy.ALL).thumbnail(0.2f).into(photoView);


    }

    private void initIndicaotor(int size) {

        //원사이의 간격
        circleAnimIndicator.setItemMargin(15);
        //애니메이션 속도
        circleAnimIndicator.setAnimDuration(300);
        //indecator 생성
        circleAnimIndicator.createDotPanel(size, R.drawable.indicator_non, R.drawable.indicator_on);
    }

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
