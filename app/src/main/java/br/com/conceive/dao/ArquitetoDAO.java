package br.com.conceive.dao;

import android.content.Context;

import br.com.conceive.POJO.Arquiteto;
import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.exceptions.RealmException;

/**
 * Created by Denis Viana on 26/12/2016.
 */

public class ArquitetoDAO  {

    private Context context;
    private Realm realm;

    public ArquitetoDAO(Context context){
        this.context = context;
        Realm.init(context);
        realm = Realm.getDefaultInstance();
    }

    public boolean novoArquiteto(Arquiteto arquiteto){
        try{
            realm.beginTransaction();
            realm.copyToRealmOrUpdate(arquiteto);
            realm.commitTransaction();
            realm.close();
            return true;
        } catch (RealmException e){
            e.printStackTrace();
            return false;
        }
    }

    public boolean atualizarArquiteto(Arquiteto arquiteto){
        try{
            realm.beginTransaction();
            realm.copyToRealmOrUpdate(arquiteto);
            realm.commitTransaction();
            realm.close();
            return true;
        } catch (RealmException e){
            e.printStackTrace();
            return false;
        }
    }

    public Arquiteto buscaArquiteto(String id_google){
        Arquiteto arquiteto = realm.where(Arquiteto.class)
                .equalTo("id_google", id_google)
                .findFirst();
        return arquiteto;
    }

    public boolean arquitetoExiste(String id_google){
        try{
            RealmResults<Arquiteto> arquitetos = realm.where(Arquiteto.class)
                    .equalTo("id_google", id_google)
                    .findAll();
            if(arquitetos.size()>0){
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
