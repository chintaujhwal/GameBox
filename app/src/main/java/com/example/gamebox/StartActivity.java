package com.example.gamebox;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class StartActivity extends AppCompatActivity {
    private EditText mEmail,mPass;
    private Button mSignBtn;
    private TextView mfgtpass,mcreateAcc;
    private CheckBox mPassvisible;
    private FirebaseAuth auth;
    private ProgressDialog progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        getSupportActionBar().hide();

        auth=FirebaseAuth.getInstance();

        mEmail=findViewById(R.id.edtEtrEmail);
        mPass=findViewById(R.id.edtEtrPass);

        mSignBtn=findViewById(R.id.signinBtn2);

        mfgtpass=findViewById(R.id.fgtPass);
        mcreateAcc=findViewById(R.id.createAcc);

        mPassvisible=findViewById(R.id.Passvisiblity);



        if(auth.getCurrentUser() !=null){
            startActivity(new Intent(StartActivity.this,MainActivity.class));
            finish();
        }
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        mSignBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txt_email,txt_pass;
                txt_email=mEmail.getText().toString();
                txt_pass=mPass.getText().toString();
                if(txt_email.isEmpty()&&txt_pass.isEmpty()){
                    Toast.makeText(StartActivity.this,"Enter Email and Password",Toast.LENGTH_SHORT).show();
                }else if (txt_pass.isEmpty()){
                    mPass.setError("password is required");
                    return;
                }
                else if (!txt_email.matches(emailPattern)){
                    mEmail.setError("invalid email");
                    return;
                }

                else {

                    signin(txt_email,txt_pass);
                    progress=new ProgressDialog(StartActivity.this);
                    progress.setMessage(getString(R.string.signing_in));
                    progress.show();
                    progress.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                    progress.setContentView(R.layout.progress_layout);
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
                startActivity(new Intent(StartActivity.this,ForgotPasswordActivity.class));
            }
        });

        mPassvisible.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    mPass.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
                else{
                    mPass.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
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