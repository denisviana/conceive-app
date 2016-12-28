package br.com.conceive.dao;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import br.com.conceive.POJO.Arquiteto;
import br.com.conceive.retrofit.RetrofitInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Denis Viana on 26/12/2016.
 */

public class RetrofitWebService extends AppCompatActivity {

    public static final String TAG = "Response WebService: ";
    public static final int NO_CONTENT = 204;
    public static final int OK = 200;
    public static final int FORBBIDEN = 403;
    public static final int NOT_MODIFIED = 304;

    protected Retrofit retrofit;
    protected Context context;

    public RetrofitWebService(){
        this.retrofit = new Retrofit.Builder()
                .baseUrl(RetrofitInterface.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public RetrofitInterface getRetrofitInterface(){

        return retrofit.create(RetrofitInterface.class) ;
    }



}
