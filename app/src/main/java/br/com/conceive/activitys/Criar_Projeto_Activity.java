package br.com.conceive.activitys;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.drive.Drive;
import com.google.android.gms.drive.DriveFolder;
import com.google.android.gms.drive.DriveId;
import com.google.android.gms.drive.MetadataChangeSet;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import br.com.conceive.POJO.Arquiteto;
import br.com.conceive.POJO.Projeto;
import br.com.conceive.R;
import br.com.conceive.dao.ArquitetoDAO;
import br.com.conceive.dao.RetrofitWebService;
import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by Denis Viana on 29/11/2016.
 */

public class Criar_Projeto_Activity extends RetrofitWebService implements View.OnClickListener,
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener{

    private final String TAG = "Criar Projeto Activity";
    private EditText edit_nome_projeto;
    private Button bt_add_projeto;
    private String root_sd;
    private Button bt_data_inicio;
    static final int DATE_DIALOG_ID = 0;
    private Date data_inicio;
    private GoogleApiClient mGoogleApiClient;
    private GoogleSignInAccount account;
    private Arquiteto arquiteto;
    private ArquitetoDAO arquitetoDAO;

    @Override
    protected void onResume() {
        super.onResume();

        if(mGoogleApiClient!=null){
            mGoogleApiClient.connect(GoogleApiClient.SIGN_IN_MODE_OPTIONAL);
        }
    }

    @Override
    protected void onPause() {
        if(mGoogleApiClient!=null){
            mGoogleApiClient.disconnect();
        }
        super.onPause();
    }


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

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.server_client_id))
                .requestScopes(new Scope(Scopes.DRIVE_APPFOLDER), new Scope(Scopes.DRIVE_FILE))
                .requestProfile()
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(Auth.GOOGLE_SIGN_IN_API,gso)
                .addApi(Drive.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();

        mGoogleApiClient.connect(GoogleApiClient.SIGN_IN_MODE_OPTIONAL);

        bt_data_inicio.setOnClickListener(this);
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
                    String data = String.valueOf(dia)+"/"+String.valueOf(mes+1)+"/"+String.valueOf(ano);
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

                if(mGoogleApiClient.isConnected() && arquiteto!=null){
                    DriveId sFolderId = DriveId.decodeFromString(arquiteto.getId_pasta_drive());
                    DriveFolder folder = Drive.DriveApi.getFolder(mGoogleApiClient, sFolderId);
                    MetadataChangeSet changeSet = new MetadataChangeSet.Builder()
                            .setTitle(edit_nome_projeto.getText().toString()).build();
                    folder.createFolder(mGoogleApiClient, changeSet).setResultCallback(folderCreatedCallback);
                }

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

    ResultCallback<DriveFolder.DriveFolderResult> folderCreatedCallback = new
            ResultCallback<DriveFolder.DriveFolderResult>() {
                @Override
                public void onResult(DriveFolder.DriveFolderResult result) {
                    if (!result.getStatus().isSuccess()) {
                        Log.i(TAG,"Problem while trying to create a folder");
                        return;
                    }
                    Log.i(TAG,"Subpasta criada com sucesso");
                }
            };

    @Override
    public void onConnected(@Nullable Bundle bundle) {

       Auth.GoogleSignInApi.silentSignIn(mGoogleApiClient).setResultCallback(new ResultCallback<GoogleSignInResult>() {
           @Override
           public void onResult(@NonNull GoogleSignInResult googleSignInResult) {
               Log.i(TAG,"Conectado");
               account = googleSignInResult.getSignInAccount();
               Log.i(TAG,"Usuário logado id: "+account.getId());
               arquitetoDAO = new ArquitetoDAO(Criar_Projeto_Activity.this);
               arquiteto = arquitetoDAO.buscaArquiteto(account.getId());



           }
       });
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
