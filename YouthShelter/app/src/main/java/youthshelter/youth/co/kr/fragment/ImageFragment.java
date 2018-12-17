package youthshelter.youth.co.kr.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.firebase.storage.FirebaseStorage;

import youthshelter.youth.co.kr.GlideApp;
import youthshelter.youth.co.kr.R;

public class ImageFragment extends Fragment {
    private ImageView imageView;
    private String imageURL;
    private FirebaseStorage storage = FirebaseStorage.getInstance("gs://youthcenter-856cc.appspot.com");
    public ImageFragment(){

    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_image, container, false);
        imageView = (ImageView)view.findViewById(R.id.image_pager_ImageView);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        imageURL = getArguments().getString("imageURL");
        Log.i("tttttt",imageURL);
        GlideApp.with(this).load(storage.getReference().child("center").child(imageURL)).centerCrop().placeholder(R.drawable.noimage).diskCacheStrategy(DiskCacheStrategy.ALL).thumbnail(0.1f).into(imageView);

        //imageView.setImageResource(imageURL);

    }
}
