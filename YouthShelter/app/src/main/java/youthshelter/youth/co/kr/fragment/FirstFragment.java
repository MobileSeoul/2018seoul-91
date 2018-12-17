package youthshelter.youth.co.kr.fragment;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import youthshelter.youth.co.kr.R;
import youthshelter.youth.co.kr.activity.SetMapActivity;
import youthshelter.youth.co.kr.adapter.FirstFragmentCenterRecyclerAdapter;
import youthshelter.youth.co.kr.components.SearchCustomDialog;
import youthshelter.youth.co.kr.components.SortCustomDialog;
import youthshelter.youth.co.kr.data.YouthCenter;

public class FirstFragment extends Fragment {
    private RecyclerView firstRecyclerView;
    private ArrayList<YouthCenter> youthCenters;
    private ArrayList<YouthCenter> filteringCenters;
    private ArrayList<YouthCenter> tempCenters;

    private FirstFragmentCenterRecyclerAdapter adapter;
    private boolean isFiltering = false;
    private TextView positionTextView;
    private ImageView search_ImageView;
    private ImageView sort_ImageView;
    private SearchCustomDialog searchCustomDialog;
    private SortCustomDialog sortCustomDialog;
    private String title;

    private final int SET_MAP = 0;
    private double myLat = 0;
    private double myLon = 0;

    public FirstFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_first, container, false);
        firstRecyclerView = (RecyclerView) view.findViewById(R.id.firstRecyclerView);
        search_ImageView = (ImageView) getActivity().findViewById(R.id.search_ImageView);
        sort_ImageView = (ImageView) getActivity().findViewById(R.id.sort_ImageView);
        positionTextView = (TextView) getActivity().findViewById(R.id.position);
        return view;
    }

    public double DistanceByDegree(double _latitude1, double _longitude1, double _latitude2, double _longitude2) {
        Location startPos = new Location("PointA");
        Location endPos = new Location("PointB");

        startPos.setLatitude(_latitude1);
        startPos.setLongitude(_longitude1);
        endPos.setLatitude(_latitude2);
        endPos.setLongitude(_longitude2);

        double distance = startPos.distanceTo(endPos);
        distance = distance / 1000;


        return Double.parseDouble(String.format("%.2f", distance));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        //Log.i("ttttttt", requestCode + " " + resultCode + " " + data.getIntExtra("index", 0) + " " + data.getIntExtra("count", 0));
        if (requestCode == 3000) {
            if (resultCode == 100) {
                int index = data.getIntExtra("index", 0);

                tempCenters.get(index).setLike(data.getIntExtra("count", 0));


                adapter.notifyItemChanged(index);
            }
        } else if (requestCode == SET_MAP) {
            if (resultCode == -1) {
                title = data.getStringExtra("title");
                myLat = data.getDoubleExtra("lat", 0);
                myLon = data.getDoubleExtra("lon", 0);
                if(title !=null && myLat !=0 && myLon != 0) {
                    positionTextView.setText(title);
                    for (YouthCenter youthCenter_data : tempCenters) {
                        youthCenter_data.setDistance(DistanceByDegree(myLat, myLon, youthCenter_data.getLatitude(), youthCenter_data.getLongitude()));
                        youthCenter_data.setCalcDistance(true);
                        //Log.e("Distance",""+DistanceByDegree(myLat,myLon, youthCenter_data.getLatitude(),youthCenter_data.getLongitude())+" km");
                    }
                    adapter.notifyDataSetChanged();
                }
            }
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        positionTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), SetMapActivity.class);
                startActivityForResult(intent, SET_MAP);
                getActivity().overridePendingTransition(0, 0);
            }
        });

        searchCustomDialog = new SearchCustomDialog(getContext());
        sortCustomDialog = new SortCustomDialog(getContext());
        sortCustomDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                int data = sortCustomDialog.getAddCategoryStr();
                switch (data) {
                    case 0:
                        sortByDistance(tempCenters);
                        break;
                    case 1:
                        sortByLike(tempCenters);
                        break;
                    case 2:
                        sortByName(tempCenters);
                        break;
                }

            }
        });
        searchCustomDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                String text = searchCustomDialog.getAddCategoryStr();
                setArrayBySearch(text);
            }
        });

        search_ImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchCustomDialog.clear();
                searchCustomDialog.show();
                InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
            }
        });
        sort_ImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sortCustomDialog.show();
            }
        });

        filteringCenters = new ArrayList<>();
        tempCenters = youthCenters = (ArrayList<YouthCenter>) getArguments().getSerializable("centers");
        firstRecyclerView.setOverScrollMode(View.OVER_SCROLL_NEVER);
        firstRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new FirstFragmentCenterRecyclerAdapter(this);
        firstRecyclerView.setAdapter(adapter);


        //sortByName(youthCenters);
        adapter.setItem(tempCenters);
        //setArrayBySearch("도봉");
    }

    public void setArrayBySearch(String text) {
        if (text == null || youthCenters == null || youthCenters.size() == 0)
            return;
        else if(text.trim().equals("")){
            isFiltering = false;
            tempCenters = youthCenters;
            adapter.setItem(tempCenters);
            return;
        }
        filteringCenters.clear();
        isFiltering = true;
        tempCenters = filteringCenters;
        for (YouthCenter youthCenter : youthCenters) {
            if (youthCenter.getName().contains(text)) {
                filteringCenters.add(youthCenter);
            }
        }
        adapter.setItem(filteringCenters);
    }

    public void sortByName(ArrayList<YouthCenter> youthCenters) {
        Collections.sort(youthCenters, new Comparator<YouthCenter>() {
            public int compare(YouthCenter obj1, YouthCenter obj2) {
                // ## Ascending order
                return obj1.getName().compareToIgnoreCase(obj2.getName());
            }
        });
        adapter.notifyDataSetChanged();
    }

    public void sortByDistance(ArrayList<YouthCenter> youthCenters) {
        Collections.sort(youthCenters, new Comparator<YouthCenter>() {
            public int compare(YouthCenter obj1, YouthCenter obj2) {
                // ## Ascending order
                return Double.valueOf(obj1.getDistance()).compareTo(obj2.getDistance());
            }
        });
        adapter.notifyDataSetChanged();
    }

    public void sortByLike(ArrayList<YouthCenter> youthCenters) {
        Collections.sort(youthCenters, new Comparator<YouthCenter>() {
            public int compare(YouthCenter obj1, YouthCenter obj2) {
                // ## Ascending order
                return Integer.valueOf(obj2.getLike()).compareTo(obj1.getLike());
            }
        });
        adapter.notifyDataSetChanged();
    }
}
