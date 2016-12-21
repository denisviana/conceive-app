package br.com.conceive.POJO;

import java.io.Serializable;
import java.util.Date;

import io.realm.RealmList;
import io.realm.RealmObject;

/**
 * Created by Denis Viana on 29/11/2016.
 */
public class Projeto extends RealmObject implements Serializable{

    private int id;
    private String nome_projeto;
    private Date data_inicio;
    private String nome_cliente;
    private String etapa_atual;
    private String caminho;
    private RealmList<Etapa_Projeto> etapas;

    public String getNome_projeto() {
        return nome_projeto;
    }

    public void setNome_projeto(String nome_projeto) {
        this.nome_projeto = nome_projeto;
    }

    public Date getData_inicio() {
        return data_inicio;
    }

    public void setData_inicio(Date data_inicio) {
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public RealmList<Etapa_Projeto> getEtapas() {
        return etapas;
    }

    public void setEtapas(RealmList<Etapa_Projeto> etapas) {
        this.etapas = etapas;
    }



}
