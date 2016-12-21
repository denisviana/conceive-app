package br.com.conceive.activitys;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import br.com.conceive.POJO.Projeto;
import br.com.conceive.R;
import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by Denis Viana on 29/11/2016.
 */

public class Criar_Projeto_Activity extends AppCompatActivity implements View.OnClickListener{

    private EditText edit_nome_projeto;
    private Button bt_add_projeto;
    private String root_sd;
    private Button bt_data_inicio;
    static final int DATE_DIALOG_ID = 0;
    private Date data_inicio;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_criar_projeto);

        initViews();
        root_sd = Environment.getExternalStorageDirectory().toString()+"/gau/";

        if(getIntent().getExtras()!=null){
            Projeto projeto = (Projeto) getIntent().getSerializableExtra("projeto");
            setTitle("Editar Projeto");
            edit_nome_projeto.setText(projeto.getNome_projeto());
        }




        bt_add_projeto.setOnClickListener(this);

    }

    private void initViews(){
        edit_nome_projeto = (EditText) findViewById(R.id.edit_nome_projeto);
        bt_add_projeto = (Button) findViewById(R.id.bt_add_projeto);
        bt_data_inicio = (Button) findViewById(R.id.bt_data_inicio_projeto);
    }


    @Override
    protected Dialog onCreateDialog(int id) {
        Calendar calendario = Calendar.getInstance();

        int ano = calendario.get(Calendar.YEAR);
        int mes = calendario.get(Calendar.MONTH);
        int dia = calendario.get(Calendar.DAY_OF_MONTH);



        return  new DatePickerDialog(this, mDateSetListener,ano,mes,dia);
    }

    private DatePickerDialog.OnDateSetListener mDateSetListener =
            new DatePickerDialog.OnDateSetListener(){

                @Override
                public void onDateSet(DatePicker datePicker, int ano, int mes, int dia) {
                    String data = String.valueOf(dia)+"/"+String.valueOf(mes)+"/"+String.valueOf(ano);
                    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                    try {
                        data_inicio = formatter.parse(data);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    bt_data_inicio.setText(data);
                }
            };

    @Override
    public void onClick(View view) {

        if(view == bt_add_projeto){
            if(edit_nome_projeto.getText().length()==0){
                Toast.makeText(Criar_Projeto_Activity.this,"Digite o nome do projeto", Toast.LENGTH_LONG).show();
            }else{
                new File(Environment.getExternalStorageDirectory()+"/gau/"+edit_nome_projeto.getText()).mkdir();
                Realm.init(getApplicationContext());
                RealmConfiguration realmConfg = new RealmConfiguration.Builder().build();

                String nome = edit_nome_projeto.getText().toString();
                Realm realm = Realm.getInstance(realmConfg);
                realm.beginTransaction();
                Projeto projeto = realm.createObject(Projeto.class);
                projeto.setNome_cliente("Denis Viana Costa");
                projeto.setNome_projeto(nome);
                projeto.setCaminho(root_sd+nome);
                projeto.setEtapa_atual("Projeto Executivo");
                projeto.setData_inicio(data_inicio);
                realm.commitTransaction();
                realm.close();
                Toast.makeText(Criar_Projeto_Activity.this,"Projeto criado com sucesso", Toast.LENGTH_LONG).show();
                finish();
            }
        }else if(view == bt_data_inicio){
            showDialog(DATE_DIALOG_ID);
        }

    }
}
