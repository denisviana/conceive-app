package br.com.conceive.activitys;

import android.Manifest;
import android.content.Intent;
import android.content.IntentSender;
import android.content.IntentSender.SendIntentException;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.drive.Drive;
import com.google.android.gms.drive.DriveFile;
import com.google.android.gms.drive.DriveFolder;
import com.google.android.gms.drive.DriveId;
import com.google.android.gms.drive.MetadataChangeSet;

import java.io.File;
import java.util.ArrayList;

import br.com.conceive.POJO.Item_DashBoard;
import br.com.conceive.R;
import br.com.conceive.adapter.Adapter_Dashboard;


public class Dashboard_Activity extends AppCompatActivity
        implements GoogleApiClient.OnConnectionFailedListener, GoogleApiClient.ConnectionCallbacks {

    private ListView list_dashboard;
    private Adapter_Dashboard adapter;
    private final int PERMISSION_WRITE_STORAGE = 0;
    private GoogleApiClient mGoogleApiClient;
    private GoogleSignInOptions gso;
    private static final String TAG = "Google Drive Activity";
    private static final int REQUEST_CODE_RESOLUTION = 1;
    private static final  int REQUEST_CODE_OPENER = 2;
    private boolean fileOperation = false;
    private DriveId mFileId;
    public DriveFile file;




    @Override
    protected void onStart() {
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .addOnConnectionFailedListener(this)
                .build();

        mGoogleApiClient.connect();
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();

        solicitarPermissaoCriarPasta();

        if(mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addApi(Drive.API)
                    .addScope(Drive.SCOPE_FILE)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .build();
        }
        mGoogleApiClient.connect();

        MetadataChangeSet changeSet = new MetadataChangeSet.Builder()
                .setTitle("Conceive").build();
        Drive.DriveApi.getRootFolder(mGoogleApiClient).createFolder(
                mGoogleApiClient,changeSet
        ).setResultCallback(folderCreateCallback);

        if(!new File(Environment.getExternalStorageDirectory()+"/gau").exists()){
            new File(Environment.getExternalStorageDirectory()+"/gau").mkdir();
        }

    }

    @Override
    protected void onStop() {
        super.onStop();
        if(mGoogleApiClient != null){
            mGoogleApiClient.disconnect();
        }
        super.onPause();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        setTitle("Dashboard");
        initViews();


        ArrayList<Item_DashBoard> lista_itens = new ArrayList<>();

        Item_DashBoard item = new Item_DashBoard();
        item.setItemnome("Projetos");
        item.setCor(R.color.colorProjetos);
        lista_itens.add(item);

        item = new Item_DashBoard();
        item.setItemnome("Meu Portfólio");
        item.setCor(R.color.colorPortfolio);
        lista_itens.add(item);

        item = new Item_DashBoard();
        item.setItemnome("Acervo de Referências");
        item.setCor(R.color.colorAcervo);
        lista_itens.add(item);

        item = new Item_DashBoard();
        item.setItemnome("Configurações");
        item.setCor(R.color.colorConfig);
        lista_itens.add(item);

        adapter = new Adapter_Dashboard(this,lista_itens);

        list_dashboard.setAdapter(adapter);

        list_dashboard.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent;
                switch(i){
                    case 0:
                        intent = new Intent(Dashboard_Activity.this,Projetos_Activity.class);
                        startActivity(intent);
                        break;
                    case 1:
                        intent = new Intent(Dashboard_Activity.this,Meu_Portfolio_Activity.class);
                        startActivity(intent);
                        break;
                    case 2:
                        intent = new Intent(Dashboard_Activity.this,Acervo_Referecencias_Activity.class);
                        startActivity(intent);
                        break;
                }

            }
        });



    }

    private void signOut() {
        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {
                        SharedPreferences sharedPreferences = getSharedPreferences("APP", MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putBoolean("isLogin", false);
                        editor.commit();
                        finish();
                    }
                });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_home,menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId() == R.id.sign_out){
            signOut();
            Intent intent = new Intent(Dashboard_Activity.this, Login_Activity.class);
            intent.putExtra("signout",true);
            startActivity(intent);
        }

        return false;
    }



    private void solicitarPermissaoCriarPasta(){
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED){
            if(ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.WRITE_EXTERNAL_STORAGE)){

            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},PERMISSION_WRITE_STORAGE);
            }
        } return;
    }


    private void initViews(){
        list_dashboard = (ListView) findViewById(R.id.dashboard_list);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch(requestCode){
            case PERMISSION_WRITE_STORAGE: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    new File(Environment.getExternalStorageDirectory()+"/gau").mkdir();
                } else {

                }
                return;
            }
        }
    }


    @Override
    public void onConnected(@Nullable Bundle bundle) {
        Toast.makeText(getApplicationContext(),"Connected",Toast.LENGTH_LONG).show();
        MetadataChangeSet changeSet = new MetadataChangeSet.Builder()
                .setTitle("Conceive").build();
        Drive.DriveApi.getRootFolder(mGoogleApiClient).createFolder(
                mGoogleApiClient,changeSet
        ).setResultCallback(folderCreateCallback);



    }

    ResultCallback<DriveFolder.DriveFolderResult> folderCreateCallback = new ResultCallback<DriveFolder.DriveFolderResult>() {
        @Override
        public void onResult(@NonNull DriveFolder.DriveFolderResult driveFolderResult) {
            if(!driveFolderResult.getStatus().isSuccess()){
                Toast.makeText(getApplicationContext(), "Error while trying to create the folder", Toast.LENGTH_LONG).show();
            }
            Toast.makeText(getApplicationContext(), "Created a folder: " + driveFolderResult.getDriveFolder().getDriveId(), Toast.LENGTH_LONG).show();
        }
    };

    @Override
    public void onConnectionSuspended(int i) {
        Log.i(TAG,"GoogleApiClient connection suspended");
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
        }catch (SendIntentException e){
            Log.e(TAG,"Exception while starting resolution activity",e);
        }
    }
}
