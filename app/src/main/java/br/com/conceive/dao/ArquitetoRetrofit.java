package br.com.conceive.dao;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;

import br.com.conceive.POJO.Arquiteto;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Denis Viana on 26/12/2016.
 */

public class ArquitetoRetrofit extends RetrofitWebService {

    private Arquiteto arquiteto = null;
    private ProgressDialog progress;
    private Context context;
    private int retorno;
    private int id;

    public ArquitetoRetrofit(Context context){
        this.context = context;
        progress = new ProgressDialog(context);
        progress.setTitle("Aguarde...");
        progress.setIndeterminate(true);
    }


    public void requestBuscaArquiteto(String token, String id_google){
        progress.show();

        Call<Arquiteto> requestArquiteto = getRetrofitInterface().buscaArquitetoPorID(token,id_google);

        requestArquiteto.enqueue(new Callback<Arquiteto>() {
            @Override
            public void onResponse(Call<Arquiteto> call, Response<Arquiteto> response) {
                switch(response.code()){
                    case OK:
                        Log.i(TAG,"Sucesso");

                        break;
                    case NO_CONTENT:
                        Log.i(TAG,"Sem Conteúdo");
                        break;
                    case FORBBIDEN:
                        Log.i(TAG,"Validação de token negada");

                        break;
                }
            }

            @Override
            public void onFailure(Call<Arquiteto> call, Throwable t) {
                Log.i(TAG,"Falha na comuninação com o WebService");
            }
        });

        if(progress.isShowing()){
            progress.dismiss();
        }

    }

    public void requestNovoArquiteto(String token, Arquiteto arquiteto){
        retorno = -1;
        progress.show();

        Call<Integer> requestArquiteto = getRetrofitInterface().cadastrarArquiteto(token,arquiteto);

        requestArquiteto.enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                switch(response.code()){
                    case OK:
                        Log.i(TAG,"Sucesso");
                        if(response.body() == -1){
                            retorno = NOT_MODIFIED;
                        } else {
                            retorno = OK;
                            id = response.body();
                        }
                        break;
                    case NO_CONTENT:
                        Log.i(TAG,"Sem Conteúdo");
                        retorno = NO_CONTENT;
                        break;
                    case FORBBIDEN:
                        Log.i(TAG,"Validação de token negada");
                        retorno = FORBBIDEN;
                        break;
                }
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
                Log.i(TAG,"Falha na comuninação com o WebService");
            }
        });

        if(progress.isShowing()){
            progress.dismiss();
        }

    }

    public void requestAtualizaArquiteto(String token, Arquiteto arquiteto){
        retorno = -1;
        progress.show();

        Call<Integer> requestArquiteto = getRetrofitInterface().cadastrarArquiteto(token,arquiteto);

        requestArquiteto.enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                switch(response.code()){
                    case OK:
                        Log.i(TAG,"Sucesso");
                       if(response.body() == -1){
                           retorno = NOT_MODIFIED;
                       } else {
                           retorno = OK;
                       }
                        break;
                    case NO_CONTENT:
                        Log.i(TAG,"Sem Conteúdo");
                        retorno = NO_CONTENT;
                        break;
                    case FORBBIDEN:
                        Log.i(TAG,"Validação de token negada");
                        retorno = FORBBIDEN;
                        break;
                }
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
                Log.i(TAG,"Falha na comuninação com o WebService");
            }
        });

        if(progress.isShowing()){
            progress.dismiss();
        }

    }

    public Arquiteto getArquiteto() {
        return arquiteto;
    }

    public void setArquiteto(Arquiteto arquiteto) {
        this.arquiteto = arquiteto;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRetorno() {
        return retorno;
    }

    public void setRetorno(int retorno) {
        this.retorno = retorno;
    }
}
