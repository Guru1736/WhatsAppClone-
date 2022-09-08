package com.guruprasad.whatsappclone;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.guruprasad.whatsappclone.Models.users;
import com.guruprasad.whatsappclone.databinding.ActivitySignUpBinding;

import java.util.Objects;

public class sign_up extends AppCompatActivity {
      EditText  fullname  , email , password ;
      TextView textView ;
      Button sign_up , google, facebook ;

      ActivitySignUpBinding binding ;
      FirebaseAuth firebaseAuth;
      FirebaseDatabase firebaseDatabase ;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding =ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Objects.requireNonNull(getSupportActionBar()).hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);


        fullname = findViewById(R.id.fullname);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        textView = findViewById(R.id.account);
        sign_up = findViewById(R.id.signUp_btn);
        google = findViewById(R.id.google);
        facebook = findViewById(R.id.facebook);
        firebaseAuth = FirebaseAuth.getInstance() ;
        firebaseDatabase = FirebaseDatabase.getInstance();


        binding.signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ProgressDialog pd = new ProgressDialog(view.getContext());
                pd.setTitle("Sign_Up");
                pd.setMessage("Creating Your Account.... ");
                pd.setCanceledOnTouchOutside(false);
                pd.setCancelable(false);



                String Email = binding.email.getText().toString();
                String Password = binding.password.getText().toString();

                if (TextUtils.isEmpty(Email))
                {
                    email.setError("Email Please !!");
                    return;
                }

                if (TextUtils.isEmpty(Password))
                {
                    password.setError("Password Please ");
                    return;
                }
                pd.show();


                firebaseAuth.createUserWithEmailAndPassword(Email, Password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        users users = new users(binding.fullname.getText().toString() , binding.email.getText().toString(),binding.password.getText().toString());
                        String id  = authResult.getUser().getUid();
                        firebaseDatabase.getReference().child("users").child(id).setValue(users);
                        Toast.makeText(sign_up.this, "Account Has Been Created ", Toast.LENGTH_SHORT).show();
                        pd.dismiss();
                        fullname.setText("");
                        email.setText("");
                        password.setText("");

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(sign_up.this, "Error : "+ e.getMessage(), Toast.LENGTH_SHORT).show();
                        pd.dismiss();
                    }
                });
            }
        });

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),sign_in.class));
                finish();
            }
        });





    }
}