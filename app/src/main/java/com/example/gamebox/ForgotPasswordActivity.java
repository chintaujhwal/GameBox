package com.example.gamebox;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPasswordActivity extends AppCompatActivity {

    private EditText mEmail;
    private Button mSendmail;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        mEmail=findViewById(R.id.fgtEmail);
        mSendmail=findViewById(R.id.sendEmail);

        auth=FirebaseAuth.getInstance();

        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        mSendmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txt_email=mEmail.getText().toString();
                if(txt_email.isEmpty()||!txt_email.matches(emailPattern)){
                    mEmail.setError("invalid email");
                }
                else{
                    auth.sendPasswordResetEmail(txt_email).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                Toast.makeText(ForgotPasswordActivity.this, "Check your email to reset your password", Toast.LENGTH_LONG).show();
                                startActivity(new Intent(ForgotPasswordActivity.this,StartActivity.class));
                                finish();
                            }
                            else {
                                Toast.makeText(ForgotPasswordActivity.this,"Email is not registered",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }
}