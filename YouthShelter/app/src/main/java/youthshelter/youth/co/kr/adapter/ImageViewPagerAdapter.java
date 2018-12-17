package youthshelter.youth.co.kr.adapter;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

import youthshelter.youth.co.kr.fragment.ImageFragment;

public class ImageViewPagerAdapter extends FragmentPagerAdapter {
    private Context context;
    private Handler handler;
    private String imageURL;
    private int count;
    private String format;
    public ImageViewPagerAdapter(FragmentManager fm, Context context, String imageURL, int count, String format) {
        super(fm);
        this.context = context;
        this.imageURL = imageURL;
        this.count = count;
        this.format = format;
    }

    @Override
    public Fragment getItem(int position) {
        ImageFragment fragment = new ImageFragment();
        Bundle args = new Bundle();
        args.putString("imageURL", imageURL+"/"+position + "." + format);
        fragment.setArguments(args);
        return fragment;
    }

    @Override // ViewPager의 Page 수
    public int getCount() {
        return count;
    }
}