package br.com.conceive.retrofit;

import java.util.List;

import br.com.conceive.POJO.Arquiteto;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by Denis Viana on 22/12/2016.
 */

public interface RetrofitInterface {

    String BASE_URL = "http://13.67.177.142:8080/WebService/rest/";

    @GET("arquiteto/listaTodos")
    Call<List<Arquiteto>> listaArquitetos(@Header("token") String token);

    @GET("arquiteto/buscaporID/{id}")
    Call<Arquiteto> buscaArquitetoPorID(@Header("token") String token, @Path("id") String idArquiteto);

    @POST("arquiteto/cadastrar")
    Call<Integer> cadastrarArquiteto(@Header("token") String token, @Body Arquiteto arquiteto);


}
