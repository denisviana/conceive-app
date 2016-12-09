package br.com.conseive.POJO;

import java.io.Serializable;
import java.util.Random;

import io.realm.RealmObject;

/**
 * Created by Denis Viana on 29/11/2016.
 */
public class Projeto extends RealmObject implements Serializable{

    private int id;
    private String nome_projeto;
    private String data_inicio;
    private String nome_cliente;
    private String etapa_atual;
    private String caminho;
    private int cor;



    public String getNome_projeto() {
        return nome_projeto;
    }

    public void setNome_projeto(String nome_projeto) {
        this.nome_projeto = nome_projeto;
    }

    public String getData_inicio() {
        return data_inicio;
    }

    public void setData_inicio(String data_inicio) {
        this.data_inicio = data_inicio;
    }

    public String getNome_cliente() {
        return nome_cliente;
    }

    public void setNome_cliente(String nome_cliente) {
        this.nome_cliente = nome_cliente;
    }

    public String getEtapa_atual() {
        return etapa_atual;
    }

    public void setEtapa_atual(String etapa_atual) {
        this.etapa_atual = etapa_atual;
    }

    public String getCaminho() {
        return caminho;
    }

    public void setCaminho(String caminho) {
        this.caminho = caminho;
    }




}
