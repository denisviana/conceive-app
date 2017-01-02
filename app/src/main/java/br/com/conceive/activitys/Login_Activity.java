package br.com.conceive.activitys;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;


import br.com.conceive.POJO.Arquiteto;
import br.com.conceive.R;
import br.com.conceive.dao.ArquitetoDAO;
import br.com.conceive.dao.ArquitetoRetrofit;
import br.com.conceive.dao.RetrofitWebService;
import io.realm.Realm;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by denis on 04/12/2016.
 */
public class Login_Activity extends RetrofitWebService implements GoogleApiClient.OnConnectionFailedListener,
        View.OnClickListener{

    private final static int RC_SIGN_IN = 777;
    private final static String TAG = "Login Activity: ";

    private EditText edt_email;
    private EditText edt_senha;
    private Button bt_login;
    private TextView cadastrar;
    private GoogleApiClient mGoogleApiClient;
    private SignInButton signInButton;
    private boolean signout = false;
    private ArquitetoDAO arquitetoDAO;
    private Realm realm;
    private ProgressDialog progress;


    @Override
    protected void onResume() {
        super.onResume();
        if(getIntent().getExtras()!=null) {
            signout = getIntent().getExtras().getBoolean("signout");
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();

        Realm.init(this);
        realm = Realm.getDefaultInstance();


        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.server_client_id))
                .requestProfile()
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this,this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();


        signInButton.setSize(SignInButton.SIZE_STANDARD);
        signInButton.setOnClickListener(this);

        progress = new ProgressDialog(this);
        progress.setTitle("Aguarde...");
        progress.setIndeterminate(true);

        cadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login_Activity.this,Cadastrar_Activity.class);
                startActivity(intent);
                finish();
            }
        });



    }


    private boolean salvarLocal(Arquiteto arq){

        arquitetoDAO = new ArquitetoDAO(this);

        if(!arquitetoDAO.arquitetoExiste(arq.getId_google())){
             return arquitetoDAO.novoArquiteto(arq);
        }
        return false;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == RC_SIGN_IN){
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }

    }

    private void handleSignInResult(GoogleSignInResult result) {
        Log.d("TAG", "handleSignInResult: "+ result.isSuccess());

        if(result.isSuccess()){
            GoogleSignInAccount account = result.getSignInAccount();

            Log.i("ID Usuario",account.getId());
            Log.i("Token Usuario",account.getIdToken());

            validaUsuario(account);

        } else {
            SharedPreferences sharedPreferences = getSharedPreferences("APP", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("isLogin", false);
            editor.apply();
        }

    }

    private void revokeAccess() {

        Auth.GoogleSignInApi.revokeAccess(mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {

                    }
                });
    }

    private void validaUsuario(final GoogleSignInAccount account){

        showProgress();

        Call<Arquiteto> requestArquiteto = getRetrofitInterface().
                buscaArquitetoPorID(account.getIdToken(),account.getId());

        requestArquiteto.enqueue(new Callback<Arquiteto>() {
            @Override
            public void onResponse(Call<Arquiteto> call, Response<Arquiteto> response) {
                switch(response.code()){
                    case OK:
                        Log.i(TAG,"Sucesso");
                        if(response.body()!=null){
                            SharedPreferences sharedPreferences = getSharedPreferences("APP", MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putBoolean("isLogin", true);
                            editor.putString("name", account.getDisplayName());
                            editor.putString("e_mail",account.getEmail());
                            editor.putString("ID", account.getId());
                            editor.apply();

                            final Arquiteto arquiteto =  response.body();
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    if(salvarLocal(arquiteto)){
                                        Log.i(TAG,"Arquiteto salvo no BD local");
                                    }else{
                                        Log.i(TAG,"Arquiteto não foi salvo no BD local");
                                    }
                                }
                            }).start();

                            Intent intent = new Intent(Login_Activity.this,Dashboard_Activity.class);
                            startActivity(intent);
                            finishProgress();
                            Login_Activity.this.finish();
                        }
                        break;
                    case NO_CONTENT:
                        Log.i(TAG,"Objeto arquiteo sem Conteúdo");
                        Arquiteto arquiteto = new Arquiteto();
                        arquiteto.setNome(account.getDisplayName());
                        arquiteto.setEmail(account.getEmail());
                        arquiteto.setId_google(account.getId());
                        arquiteto.setUri_foto(account.getPhotoUrl().toString());

                        Intent intent = new Intent(Login_Activity.this,FinalizarCadastro_Activity.class);
                        intent.putExtra("token",account.getIdToken());
                        intent.putExtra("arquiteto", arquiteto);
                        startActivity(intent);
                        finishProgress();
                        Login_Activity.this.finish();
                        break;
                    case FORBBIDEN:
                        Log.i(TAG,"Validação de token negada");
                        Toast.makeText(getApplicationContext(),"Validação de token negada",Toast.LENGTH_LONG).show();
                        break;
                }
            }

            @Override
            public void onFailure(Call<Arquiteto> call, Throwable t) {
                Log.i(TAG,"Falha na comuninação com o WebService");
                finishProgress();
            }
        });

        finishProgress();
    }

    public void initView(){
        edt_email = (EditText) findViewById(R.id.idt_email);
        edt_senha = (EditText) findViewById(R.id.edt_senha);
        bt_login = (Button) findViewById(R.id.bt_login);
        cadastrar = (TextView) findViewById(R.id.bt_cadastrar);
        signInButton = (SignInButton) findViewById(R.id.sign_in_button);
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.sign_in_button:
                signIn();
                break;
        }
    }

    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    public void showProgress(){
        if(!progress.isShowing()){
            progress.show();
        }
    }

    public void finishProgress(){
        if(progress.isShowing()){
            progress.dismiss();
        }
    }

}
