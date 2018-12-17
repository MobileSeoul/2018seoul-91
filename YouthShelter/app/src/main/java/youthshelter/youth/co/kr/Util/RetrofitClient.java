package youthshelter.youth.co.kr.Util;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static RetrofitClient mInstance = new RetrofitClient();
    public static RetrofitClient getInstance(){
        return mInstance;
    }

    private RetrofitClient(){}

    Retrofit retrofit = new Retrofit.Builder().baseUrl("http://openapi.seoul.go.kr:8088/587348636d676f3938315347784463/json/SearchConcertDetailService/3/").addConverterFactory(GsonConverterFactory.create()).build();

    RetrofitService service = retrofit.create(RetrofitService.class);

    public RetrofitService getService(){
        return service;
    }
}