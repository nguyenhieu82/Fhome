package vn.itplus.AD0318E.Fhome.rest;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RestClient {

    public static final String BASE_API = "https://api.androidhive.info/";
    public static Retrofit retrofit;

    public static APIs getRestClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_API)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit.create(APIs.class);
    }
}
