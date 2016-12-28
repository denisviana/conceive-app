package br.com.conceive.retrofit;

import java.util.List;

import br.com.conceive.POJO.Arquiteto;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

/**
 * Created by Denis Viana on 22/12/2016.
 */

public interface RetrofitInterface {

    String BASE_URL = "http://192.168.0.183:8081/Conceive_WebService/rest/";

    @GET("arquiteto/listaTodos")
    Call<List<Arquiteto>> listaArquitetos(@Header("token") String token);

    @GET("arquiteto/buscaporID/{id}")
    Call<Arquiteto> buscaArquitetoPorID(@Header("token") String token, @Path("id") String idArquiteto);

    @POST("arquiteto/cadastrar")
    Call<Integer> cadastrarArquiteto(@Header("token") String token, @Body Arquiteto arquiteto);

    @PUT("arquiteto/atualiza")
    Call<Integer> atualizaArquiteto(@Header("token") String token, @Body Arquiteto arquiteto);


}
