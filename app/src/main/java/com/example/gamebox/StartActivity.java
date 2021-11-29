package com.example.gamebox;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.internal.Objects;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class StartActivity extends AppCompatActivity {
    private EditText mEmail,mPass;
    private Button mSignBtn;
    private TextView mfgtpass,mcreateAcc;
    private FirebaseAuth auth;
    private ProgressDialog progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        auth=FirebaseAuth.getInstance();

        mEmail=findViewById(R.id.edtEtrEmail);
        mPass=findViewById(R.id.edtEtrPass);

        mSignBtn=findViewById(R.id.signinBtn2);

        mfgtpass=findViewById(R.id.fgtPass);
        mcreateAcc=findViewById(R.id.createAcc);

        if(auth.getCurrentUser() !=null){
            startActivity(new Intent(StartActivity.this,MainActivity.class));
            finish();
        }


        mSignBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txt_email,txt_pass;
                txt_email=mEmail.getText().toString();
                txt_pass=mPass.getText().toString();
                if(!txt_email.isEmpty()&&!txt_pass.isEmpty()){
                signin(txt_email,txt_pass);
                progress=new ProgressDialog(StartActivity.this);
                progress.setMessage(getString(R.string.signing_in));
                progress.show();
                progress.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                progress.setContentView(R.layout.progress_layout);
                }
                else {
                    Toast.makeText(StartActivity.this,"enter email and password",Toast.LENGTH_SHORT).show();
                }
            }
        });

        mcreateAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StartActivity.this,SignupActivity.class));
            }
        });

        mfgtpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

    private void signin(String txt_email, String txt_pass) {
        auth.signInWithEmailAndPassword(txt_email,txt_pass).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                Toast.makeText(StartActivity.this, getString(R.string.signed_in), Toast.LENGTH_SHORT).show();
                startActivity(new Intent(StartActivity.this,MainActivity.class));
                finish();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progress.dismiss();
                Toast.makeText(StartActivity.this,getString(R.string.pleaseEtr),Toast.LENGTH_SHORT).show();
            }
        });

    }
}