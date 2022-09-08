package com.guruprasad.whatsappclone;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.identity.BeginSignInRequest;
import com.google.android.gms.auth.api.identity.SignInCredential;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.FirebaseDatabase;
import com.guruprasad.whatsappclone.Models.users;

import java.security.PublicKey;
import java.util.Objects;

public class sign_in extends AppCompatActivity {

    EditText email , password ;
    Button signin , google ,facebook ;
    TextView signup ;
    ProgressDialog pd ;
    FirebaseAuth firebaseAuth ;
    GoogleSignInClient googleSignInClient;
    FirebaseDatabase firebaseDatabase ;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        Objects.requireNonNull(getSupportActionBar()).hide();

        email = findViewById(R.id.email_signin);
        password = findViewById(R.id.password_signin);
        signin = findViewById(R.id.signin_btn);
        google = findViewById(R.id.google_signin);
        facebook = findViewById(R.id.facebook_signin);
        signup = findViewById(R.id.signup_txt);
        pd = new ProgressDialog(sign_in.this);
        pd.setTitle("Login");
        pd.setMessage("Login To your Account...");
        pd.setCanceledOnTouchOutside(false);
        pd.setCancelable(false);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();



        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                        .requestEmail()
                                .build();
        googleSignInClient = GoogleSignIn.getClient(this,gso);



        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String Email = email.getText().toString();
                String Password  = password.getText().toString();

                if (TextUtils.isEmpty(Email))
                {
                    email.setError("Email Please....");
                    return;
                }

                if (TextUtils.isEmpty(Password))
                {
                    password.setError("Password Please....");
                    return;
                }

                pd.show();
                firebaseAuth.signInWithEmailAndPassword(Email,Password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        Toast.makeText(sign_in.this, "Login Successful...", Toast.LENGTH_LONG).show();
                        pd.dismiss();
                        startActivity(new Intent(getApplicationContext(),Home.class));
                        finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(sign_in.this, "Login Failed : "+e.getMessage(), Toast.LENGTH_SHORT).show();
                        pd.dismiss();
                    }
                });

            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),sign_up.class));
                finish();
            }
        });

        if (firebaseAuth.getCurrentUser()!=null)
        {
            startActivity(new Intent(getApplicationContext(),Home.class));
            finish();
        }

        google.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn();
            }
        });





    }

    int RC_SIGN_IN = 65;
    private void signIn()
    {
        Intent signInIntent = googleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent,RC_SIGN_IN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode==RC_SIGN_IN)
        {
            Task<GoogleSignInAccount>task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                Log.d("TAG","firebaseAuthWithGoogle:"+account.getId());
                firebaseAuthWithGoogle(account.getIdToken());
            }
            catch (ApiException e)
            {
                Log.w("TAG","Google Sign in failed",e);
            }
        }
    }

    private  void firebaseAuthWithGoogle(String idToken)
    {
        AuthCredential credential= GoogleAuthProvider.getCredential(idToken,null);
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful())
                        {
                            Log.d("TAG", "SignINWithCredential : success");
                           // FirebaseUser user = firebaseAuth.getCurrentUser();

                            users users = new users();
//                            users.setUserID(users.getUserID());
//                            users.setFullname(users.getFullname());
//                            users.setProfile_pic(users.getProfile_pic());

                            String id = firebaseAuth.getUid();
                            firebaseDatabase.getReference().child("users").child(id).setValue(users);

                            if (firebaseAuth.getCurrentUser()!=null)
                            {
                                startActivity(new Intent(getApplicationContext(),Home.class));
                                finish();
                            }

                        }
                        else
                        {
                            Log.w("TAG","SignINWithCredential : Failure",task.getException());
                            Toast.makeText(sign_in.this, "Authentication Failed ", Toast.LENGTH_SHORT).show();

                        }
                    }
                });

    }

}