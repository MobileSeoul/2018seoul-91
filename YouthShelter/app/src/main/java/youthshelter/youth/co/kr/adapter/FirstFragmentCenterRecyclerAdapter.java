package youthshelter.youth.co.kr.adapter;

import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.logging.Handler;

import youthshelter.youth.co.kr.GlideApp;
import youthshelter.youth.co.kr.R;
import youthshelter.youth.co.kr.activity.DetailActivity;
import youthshelter.youth.co.kr.data.YouthCenter;
import youthshelter.youth.co.kr.fragment.FirstFragment;

public class FirstFragmentCenterRecyclerAdapter extends RecyclerView.Adapter<FirstFragmentCenterRecyclerAdapter.UserViewHolder> {
    private ArrayList<YouthCenter> arrayList;
    private FirstFragment context;
    private Handler handler;
    private FirebaseStorage storage = FirebaseStorage.getInstance();
    public FirstFragmentCenterRecyclerAdapter(FirstFragment context) {
        this.context = context;
        arrayList = new ArrayList<YouthCenter>();
    }
    public void setItem(ArrayList<YouthCenter> centers){
        this.arrayList = centers;
        notifyDataSetChanged();
    }
    class UserViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView shelter_image_ImageView;
        TextView shelter_location_TextView;
        TextView shelter_name_TextView;
        TextView shelter_like_TextView;
        TextView shelter_playTime_TextView;
        CardView cardView;
        TextView shelter_distance_TextView;
        public UserViewHolder(View itemView) {
            super(itemView);
            shelter_image_ImageView = (ImageView) itemView.findViewById(R.id.shelter_image_ImageView);
            shelter_location_TextView = (TextView) itemView.findViewById(R.id.shelter_location_TextView);
            shelter_name_TextView = (TextView) itemView.findViewById(R.id.shelter_name_TextView);
            shelter_like_TextView = (TextView) itemView.findViewById(R.id.shelter_like_TextView);
            shelter_playTime_TextView = (TextView) itemView.findViewById(R.id.shelter_playTime_TextView);
            shelter_distance_TextView = (TextView)  itemView.findViewById(R.id.shelter_distance_TextView);
            cardView = (CardView) itemView.findViewById(R.id.dataCenter_recycler_view);
            cardView.setOnClickListener(this);
           /* cardView.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {

                    int action = event.getAction();
                    Log.e("ttttt",action+" ");
                    if (action == MotionEvent.ACTION_MOVE && v.isPressed()) {
                        cardView.setBackgroundColor(Color.parseColor("#c6c4c4"));
                    }
                    else if(action == MotionEvent.ACTION_UP){
                        v.performClick();
                    }
                    else {

                        cardView.setBackgroundColor(Color.parseColor("#ffffff"));
                    }
                    return true;
                }

            });*/
        }

        @Override
        public void onClick(View v) {
            Log.i("tttttt",arrayList.get(getLayoutPosition()).toString());
            Intent intent = new Intent(context.getContext(),DetailActivity.class);
            intent.putExtra("center",arrayList.get(getLayoutPosition()));
            intent.putExtra("index",getLayoutPosition());
            context.startActivityForResult(intent,3000);
        }
    }

    public void addItem(YouthCenter center) {
        arrayList.add(center);
        notifyItemInserted(arrayList.size()-1);//notifyItemInserted(arrayList.size()-1);
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }
    private String doDayOfWeek() {
        Calendar cal = Calendar.getInstance();
        String strWeek = null;

        int nWeek = cal.get(Calendar.DAY_OF_WEEK);
        if (nWeek == 1) {
            strWeek = "일요일";
        } else if (nWeek == 2) {
            strWeek = "월요";
        } else if (nWeek == 3) {
            strWeek = "화요일";
        } else if (nWeek == 4) {
            strWeek = "수요일";
        } else if (nWeek == 5) {
            strWeek = "목요일";
        } else if (nWeek == 6) {
            strWeek = "금요일";
        } else if (nWeek == 7) {
            strWeek = "토요일";
        }

        return strWeek;
    }

    @Override
    public void onBindViewHolder(UserViewHolder holder, int position) {
        YouthCenter center = arrayList.get(position);

        holder.shelter_location_TextView.setText(center.getAddress());
        holder.shelter_name_TextView.setText(center.getName());
        if(doDayOfWeek().equals("일요일")){
            holder.shelter_playTime_TextView.setText(center.getSunday());
        }
        else if(doDayOfWeek().equals("토요일")){
            holder.shelter_playTime_TextView.setText(center.getSaturday());
        }
        else{
            holder.shelter_playTime_TextView.setText(center.getWeekday());
        }
        if(center.isCalcDistance()) {
            holder.shelter_distance_TextView.setText(Double.toString(center.getDistance()) + "km");
            holder.shelter_distance_TextView.setVisibility(View.VISIBLE);
        }
        holder.shelter_like_TextView.setText(Integer.toString(center.getLike()));
        GlideApp.with(context).load(storage.getReference().child("center").child(center.getImage()+"/0."+center.getFormat())).centerCrop().placeholder(R.drawable.noimage).thumbnail(0.5f).diskCacheStrategy(DiskCacheStrategy.ALL).into(holder.shelter_image_ImageView);

    }

    @Override
    public FirstFragmentCenterRecyclerAdapter.UserViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.data_center_recyclerview, parent, false);
        UserViewHolder userViewHolder = new UserViewHolder(view);
        return userViewHolder;
    }
}
