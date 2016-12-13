package br.com.conceive.POJO;

import io.realm.RealmObject;

/**
 * Created by Denis Viana on 09/12/2016.
 */

public class Cliente extends RealmObject{

    private String nome_cliente;
    private String email_cliente;
    private String telefone_cliente;

    public String getNome_cliente() {
        return nome_cliente;
    }

    public void setNome_cliente(String nome_cliente) {
        this.nome_cliente = nome_cliente;
    }

    public String getEmail_cliente() {
        return email_cliente;
    }

    public void setEmail_cliente(String email_cliente) {
        this.email_cliente = email_cliente;
    }

    public String getTelefone_cliente() {
        return telefone_cliente;
    }

    public void setTelefone_cliente(String telefone_cliente) {
        this.telefone_cliente = telefone_cliente;
    }
}
