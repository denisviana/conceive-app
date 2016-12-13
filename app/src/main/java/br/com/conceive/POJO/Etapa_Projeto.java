package br.com.conceive.POJO;

import java.io.Serializable;

import io.realm.RealmObject;

/**
 * Created by Denis Viana on 30/11/2016.
 */

public class Etapa_Projeto extends RealmObject implements Serializable {

    private String nome_etapa;

    public String getNome_etapa() {
        return nome_etapa;
    }

    public void setNome_etapa(String nome_etapa) {
        this.nome_etapa = nome_etapa;
    }
}
