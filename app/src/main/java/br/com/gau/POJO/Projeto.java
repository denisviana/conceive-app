package br.com.gau.POJO;

import android.graphics.Color;

import java.io.Serializable;
import java.util.Random;

/**
 * Created by Denis Viana on 29/11/2016.
 */
public class Projeto implements Serializable{

    private String nome_projeto;
    private String data_inicio;
    private String nome_cliente;
    private String etapa_atual;
    private int cor;
    private Random randColor = new Random();

    private String[] cores = {"#9C27B0","#673AB7","#009688","#795548","#607D8B","#1B5E20","#B71C1C","#880E4F"};

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

    public void setCor(int cor) {
        this.cor = cor;
    }

    public String getCor(){
        //String cor = Integer.parseInt(myString.replaceFirst("#", ""), 16)

        return cores[randColor.nextInt(cores.length)];
    }


}
