package youthshelter.youth.co.kr.adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;

import youthshelter.youth.co.kr.GlideApp;
import youthshelter.youth.co.kr.R;
import youthshelter.youth.co.kr.activity.Detail2Activity;
import youthshelter.youth.co.kr.data.CultureData;

public class SecondFragmentRecyclerAdapter extends RecyclerView.Adapter<SecondFragmentRecyclerAdapter.CultureViewHolder> {
    private ArrayList<CultureData> arrayList;
    private Activity context;

    public SecondFragmentRecyclerAdapter(Activity context) {
        this.context = context;
        arrayList = new ArrayList<>();
    }

    class CultureViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView culture_image_ImageView;
        TextView culture_location_TextView;
        TextView culture_name_TextView;
        TextView culture_playTime_TextView;
        CardView cardView;

        public CultureViewHolder(View itemView) {
            super(itemView);
            culture_image_ImageView = (ImageView)itemView.findViewById(R.id.culture_image_ImageView);
            culture_location_TextView = (TextView) itemView.findViewById(R.id.culture_location_TextView);
            culture_name_TextView = (TextView)itemView.findViewById(R.id.culture_name_TextView);
            culture_playTime_TextView = (TextView)itemView.findViewById(R.id.culture_playTime_TextView);
            cardView = (CardView)itemView.findViewById(R.id.culture_recyler_view);
            cardView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(context, Detail2Activity.class);
            intent.putExtra("culture",arrayList.get(getLayoutPosition()));
            context.startActivity(intent);
        }
    }

    public void addItem(CultureData cultureData) {
        arrayList.add(cultureData);
        notifyItemInserted(arrayList.size()-1);//notifyItemInserted(arrayList.size()-1);
    }


    @NonNull
    @Override
    public CultureViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.culture_recyclerview,parent,false);
        CultureViewHolder cultureViewHolder = new CultureViewHolder(view);

        return cultureViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CultureViewHolder holder, int position) {

        CultureData cultureData = arrayList.get(position);
        holder.culture_location_TextView.setText(cultureData.getCultureLocation());
        String text = cultureData.getCultureName().replace("&#39;","").replace("［","[").replace("］","]");
        if(text.charAt(0) == ' ')
            text = text.substring(1);
        holder.culture_name_TextView.setText(text);
        holder.culture_playTime_TextView.setText(cultureData.getStart_date()+" ~ "+cultureData.getEnd_date());
        //Glide.with(context).load(cultureData.getImageName().toLowerCase()).thumbnail(0.1f).into(holder.culture_image_ImageView);
        GlideApp.with(context).load(cultureData.getImageName().toLowerCase()).centerCrop().placeholder(R.drawable.noimage).diskCacheStrategy(DiskCacheStrategy.ALL).thumbnail(0.2f).into(holder.culture_image_ImageView);

        //GlideApp.with(context).load(storage.getReference().child("center").child(center.getImage()+"/0."+center.getFormat())).diskCacheStrategy(DiskCacheStrategy.ALL).thumbnail(0.2f).into(holder.shelter_image_ImageView);



    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }


}