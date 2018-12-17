package youthshelter.youth.co.kr.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import youthshelter.youth.co.kr.R;
import youthshelter.youth.co.kr.Util.Response_Culture_Info;
import youthshelter.youth.co.kr.Util.RetrofitClient;
import youthshelter.youth.co.kr.adapter.SecondFragmentRecyclerAdapter;
import youthshelter.youth.co.kr.data.CultureData;

public class SecondFragment extends Fragment {

    private RecyclerView secondRecyclerView;
    ArrayList<CultureData> cultureData = new ArrayList<>();
    ArrayList<CultureData> cultureData2 = new ArrayList<>();

    public SecondFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_second, container, false);

        secondRecyclerView = (RecyclerView) view.findViewById(R.id.secondRecyclerView);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //@@데이터 받기
        Call<Response_Culture_Info.Example> response_culture_infoCall = RetrofitClient.getInstance().getService().GetInfo();
        response_culture_infoCall.enqueue(new Callback<Response_Culture_Info.Example>() {
            @Override
            public void onResponse(Call<Response_Culture_Info.Example> call, Response<Response_Culture_Info.Example> response) {

                for (int i = 0; i < response.body().getResponse_culture_info().getRow().size(); i++) {

                    Log.e("image",""+response.body().getResponse_culture_info().getRow().get(i).getMAIN_IMG());
                    cultureData.add(new CultureData(response.body().getResponse_culture_info().getRow().get(i).getMAIN_IMG(), response.body().getResponse_culture_info().getRow().get(i).getTITLE(),
                            response.body().getResponse_culture_info().getRow().get(i).getGCODE()+" "+response.body().getResponse_culture_info().getRow().get(i).getPLACE(),
                            response.body().getResponse_culture_info().getRow().get(i).getTIME(), response.body().getResponse_culture_info().getRow().get(i).getINQUIRY(),
                            response.body().getResponse_culture_info().getRow().get(i).getORG_LINK(),
                            response.body().getResponse_culture_info().getRow().get(i).getPROGRAM(),
                            response.body().getResponse_culture_info().getRow().get(i).getSTRTDATE(),
                            response.body().getResponse_culture_info().getRow().get(i).getEND_DATE()));
                }

                SecondFragmentRecyclerAdapter adapter = new SecondFragmentRecyclerAdapter(getActivity());
                secondRecyclerView.setAdapter(adapter);

                for (CultureData cultureDatas : cultureData){
                    adapter.addItem(cultureDatas);
                }


            }

            @Override
            public void onFailure(Call<Response_Culture_Info.Example> call, Throwable t) {
                Log.e("onfailure", "" + t.toString());
            }
        });

        secondRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

    }

}