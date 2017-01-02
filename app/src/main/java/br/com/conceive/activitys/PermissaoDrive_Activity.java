package br.com.conceive.activitys;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.nfc.Tag;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.drive.Drive;
import com.google.android.gms.drive.DriveFolder;
import com.google.android.gms.drive.MetadataChangeSet;

import br.com.conceive.POJO.Arquiteto;
import br.com.conceive.R;
import br.com.conceive.dao.ArquitetoDAO;
import br.com.conceive.dao.RetrofitWebService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by denis on 24/12/2016.
 */

public class PermissaoDrive_Activity extends RetrofitWebService implements View.OnClickListener, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener{

    private static final int REQUEST_CODE_RESOLUTION = 1;
    private static final  int REQUEST_CODE_OPENER = 2;
    private static final String TAG = "Google Drive Activity";
    private Button bt_conecta_drive;
    private Button bt_conecta_drive_depois;
    private GoogleApiClient mGoogleApiClient;
    private ProgressDialog progressDialog;
    private Arquiteto arquiteto;
    private String token = null;
    private ArquitetoDAO arquitetoDAO;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permissao_google_drive);
        initViews();

        if(getIntent().getExtras()!=null){
            arquiteto = (Arquiteto) getIntent().getSerializableExtra("arquiteto");
            token = getIntent().getStringExtra("token");
        }


        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(Drive.API)
                .addScope(Drive.SCOPE_APPFOLDER)
                .addScope(Drive.SCOPE_FILE)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();

        bt_conecta_drive.setOnClickListener(this);
        bt_conecta_drive_depois.setOnClickListener(this);


    }

    @Override
    protected void onPause() {
        if(mGoogleApiClient!=null){
            mGoogleApiClient.disconnect();
        }
        super.onPause();
    }

    private void initViews(){
        bt_conecta_drive = (Button) findViewById(R.id.bt_conectar_drive);
        bt_conecta_drive_depois = (Button) findViewById(R.id.bt_conectar_drive_depois);
    }


    @Override
    public void onClick(View view) {
        if(view == bt_conecta_drive){

            if(mGoogleApiClient!=null) {
                mGoogleApiClient.connect();
            }

        } else if(view == bt_conecta_drive_depois){
            Intent intent = new Intent(PermissaoDrive_Activity.this,Dashboard_Activity.class);
            startActivity(intent);
            finish();
        }
    }

    private void salvarIdPastaDrive(String DriveFolderID){

        arquiteto.setId_pasta_drive(DriveFolderID);

        Call<Integer> requestArquiteto = getRetrofitInterface().atualizaArquiteto(token,arquiteto);

        requestArquiteto.enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call call, Response response) {
                switch (response.code()){
                    case OK:
                        arquitetoDAO = new ArquitetoDAO(PermissaoDrive_Activity.this);
                        Log.i(TAG,"Atualizado com sucesso no WebService");

                        if(arquitetoDAO.atualizarArquiteto(arquiteto,arquiteto.getId_google())){
                            Log.i(TAG,"Objeto atualizado no banco de dados local");
                        }else
                            Log.i(TAG,"Objeto não atualizado no banco de dados local");

                        break;
                    case FORBBIDEN:
                        Log.i(TAG,"Token de acesso negado");

                        break;
                    case NOT_MODIFIED:
                        Log.i(TAG,"Nada foi modificado");

                        break;
                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {

            }
        });

    }



    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if(mGoogleApiClient.isConnected()){
            Log.i("Api Google","Esta conectado");
            MetadataChangeSet changeSet = new MetadataChangeSet.Builder()
                    .setTitle("Conceive").build();
            Drive.DriveApi.getRootFolder(mGoogleApiClient).createFolder(
                    mGoogleApiClient,changeSet
            ).setResultCallback(folderCreateCallback);

        }  else {
            Log.i("Api Google","Não está conectado");
        }
    }

    ResultCallback<DriveFolder.DriveFolderResult> folderCreateCallback = new ResultCallback<DriveFolder.DriveFolderResult>() {
        @Override
        public void onResult(@NonNull DriveFolder.DriveFolderResult driveFolderResult) {
            if(!driveFolderResult.getStatus().isSuccess()){
                Toast.makeText(getApplicationContext(), "Error while trying to create the folder", Toast.LENGTH_LONG).show();
            }
            Log.i(TAG,"Pasta Criada: " + driveFolderResult.getDriveFolder().getDriveId());
            if(arquiteto!=null && token!=null){
                salvarIdPastaDrive(driveFolderResult.getDriveFolder().getDriveId().toString());
            }
            Intent intent = new Intent(PermissaoDrive_Activity.this,Dashboard_Activity.class);
            startActivity(intent);
            finish();
        }
    };

    @Override
    public void onConnectionSuspended(int i) {
        Log.i("Google Drive","Suspenso");
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.i(TAG,"GoogleApiClient connection failed: "+connectionResult.toString());

        if(!connectionResult.hasResolution()){
            GoogleApiAvailability.getInstance()
                    .getErrorDialog(this, connectionResult.getErrorCode(),0)
                    .show();
        }
        try{
            connectionResult.startResolutionForResult(this,REQUEST_CODE_RESOLUTION);
        }catch (IntentSender.SendIntentException e){
            Log.e(TAG,"Exception while starting resolution activity",e);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case REQUEST_CODE_RESOLUTION:
                if (resultCode == RESULT_OK) {
                    mGoogleApiClient.connect();
                }
                break;
        }
    }
}
