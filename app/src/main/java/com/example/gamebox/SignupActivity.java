package com.example.gamebox;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignupActivity extends AppCompatActivity {

    private EditText mEmail,mUsername,mPass,mConfirmPass;
    private Button mSignUpBtn;
    private TextView mHaveAcc;
    private FirebaseAuth auth;
    private FirebaseDatabase db;
    private DatabaseReference ref;
    private ProgressDialog progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        auth=FirebaseAuth.getInstance();
        db=FirebaseDatabase.getInstance();


        mEmail=findViewById(R.id.signup_email);
        mUsername=findViewById(R.id.signup_username);
        mPass=findViewById(R.id.signup_pass);
        mConfirmPass=findViewById(R.id.signup_conPass);

        mSignUpBtn =findViewById(R.id.signupBtn);

        mHaveAcc=findViewById(R.id.haveacc);

        mSignUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txt_email,txt_username,txt_pass,txt_confirmpass;
                txt_email=mEmail.getText().toString();
                txt_username=mUsername.getText().toString();
                txt_pass=mPass.getText().toString();
                txt_confirmpass=mConfirmPass.getText().toString();

                if(txt_email.isEmpty()){
                    mEmail.setError("email is required");
                    return;
                }
                if(txt_username.isEmpty()){
                    mUsername.setError("username is required");
                    return;
                }
                if(txt_pass.isEmpty()){
                    mPass.setError("password is required");
                    return;
                }
                if(txt_pass.length()<=8){
                    mPass.setError("minimum password length is 8 characters");
                }
                if(txt_pass==txt_confirmpass){
                    mConfirmPass.setError("password doesn't match");
                    return;
                }

                else {
                    progress=new ProgressDialog(SignupActivity.this);
                    progress.setMessage(getString(R.string.signing_in));
                    progress.show();
                    progress.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                    progress.setContentView(R.layout.progress_layout);
                    createuser(txt_email,txt_pass,txt_username);
                }
            }
        });

        mHaveAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignupActivity.this,StartActivity.class));
                finish();
            }
        });



    }

    private void createuser(String txt_email, String txt_pass,String txt_username) {
        auth.createUserWithEmailAndPassword(txt_email,txt_pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(!task.isSuccessful()){

                    Toast.makeText(SignupActivity.this, "Email already exists", Toast.LENGTH_SHORT).show();
                    progress.dismiss();

                }
                else {
                    String userUID= auth.getCurrentUser().getUid();
                    db.getReference("users").child(userUID).child("username").setValue(txt_username);
                    Toast.makeText(SignupActivity.this,"signed up",Toast.LENGTH_SHORT);
                    startActivity(new Intent(SignupActivity.this,MainActivity.class));
                    finish();
                }
            }
        });

    }
}