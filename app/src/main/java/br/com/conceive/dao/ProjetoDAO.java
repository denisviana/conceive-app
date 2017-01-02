package br.com.conceive.dao;

import android.content.Context;

import br.com.conceive.POJO.Arquiteto;
import br.com.conceive.POJO.Projeto;
import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.exceptions.RealmException;

/**
 * Created by denis on 27/12/2016.
 */

public class ProjetoDAO {

    private Context context;
    private Realm realm;

    public ProjetoDAO(Context context){
        this.context = context;
        Realm.init(context);
        realm = Realm.getDefaultInstance();
    }

    public boolean novoProjeto(Projeto projeto){
        try{
            realm.beginTransaction();
            realm.copyToRealmOrUpdate(projeto);
            realm.commitTransaction();
            realm.close();
            return true;
        } catch (RealmException e){
            e.printStackTrace();
            return false;
        }
    }

    public boolean atualizarProjeto(Projeto projeto){
        try{
            realm.beginTransaction();
            realm.copyToRealmOrUpdate(projeto);
            realm.commitTransaction();
            realm.close();
            return true;
        } catch (RealmException e){
            e.printStackTrace();
            return false;
        }
    }

    public Projeto buscaProjeto(String id){
        Projeto projeto = realm.where(Projeto.class)
                .equalTo("id", id)
                .findFirst();
        return projeto;
    }

    public boolean ProjetoExiste(String id){
        try{
            RealmResults<Projeto> projetos = realm.where(Projeto.class)
                    .equalTo("id", id)
                    .findAll();
            if(projetos.size()>0){
                return true;
            } else {
                return false;
            }
        }catch (RealmException e){
            e.printStackTrace();
            return false;
        }
    }
}
