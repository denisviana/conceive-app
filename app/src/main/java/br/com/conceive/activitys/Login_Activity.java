package br.com.conceive.activitys;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
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

import com.google.android.gms.auth.GoogleAuthException;
import com.google.android.gms.auth.GoogleAuthUtil;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import java.io.IOException;

import br.com.conceive.R;

/**
 * Created by denis on 04/12/2016.
 */
public class Login_Activity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener, View.OnClickListener {

    private final static int RC_SIGN_IN = 777;

    private EditText edt_email;
    private EditText edt_senha;
    private Button bt_login;
    private TextView cadastrar;
    private GoogleApiClient mGoogleApiClient;
    private SignInButton signInButton;
    private FirebaseAuth mAuth;
    private boolean signout = false;
    private FirebaseAuth.AuthStateListener mAuthListener;

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

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("283117934284-2uqnuj7g64j8b47sduev5g3sk3hdgj7a.apps.googleusercontent.com")
                .requestProfile()
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();


        signInButton.setSize(SignInButton.SIZE_STANDARD);
        signInButton.setScopes(gso.getScopeArray());
        signInButton.setOnClickListener(this);


        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if(user != null){
                    Log.d("TAG", "onAuthStateChanged:signed_in: "+user.getUid());
                } else {
                    Log.d("TAG", "onAuthStateChanged:signed_in");
                }
            }
        };





        bt_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!edt_email.getText().equals("teste") || !edt_senha.getText().equals("teste")){
                    Intent intent = new Intent(Login_Activity.this,Dashboard_Activity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(Login_Activity.this, "Usuário ou senha inválidos", Toast.LENGTH_LONG).show();
                }
            }
        });


        cadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login_Activity.this,Cadastrar_Activity.class);
                startActivity(intent);
                finish();
            }
        });



    }

    @Override
    protected void onStop() {
        super.onStop();
        if(mAuthListener != null){
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
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
            SharedPreferences sharedPreferences = getSharedPreferences("APP", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("isLogin", true);
            editor.putString("name", account.getDisplayName());
                    editor.putString("e_mail",account.getEmail());
                            editor.putString("ID", account.getId());
                                    editor.commit();
            Intent intent = new Intent(Login_Activity.this,Dashboard_Activity.class);
            startActivity(intent);
            finish();

        } else {
            SharedPreferences sharedPreferences = getSharedPreferences("APP", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("isLogin", false);
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
}
