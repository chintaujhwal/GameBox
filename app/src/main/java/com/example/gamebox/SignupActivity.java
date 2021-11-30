package com.example.gamebox;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class SignupActivity extends AppCompatActivity {

    private EditText mEmail,mUsername,mPass,mConfirmPass;
    private Button mSignUpBtn;
    private TextView mHaveAcc;
    private CheckBox mshowpass;
    private ImageView mProfilepic;
    private FloatingActionButton mImagepicker;
    private FirebaseAuth auth;
    private FirebaseDatabase db;
    private DatabaseReference ref;
    private StorageReference mstorageRef;
    private ProgressDialog progress;
    private Uri imageURI;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        auth=FirebaseAuth.getInstance();
        db=FirebaseDatabase.getInstance();
       ref= db.getReference("users");
       mstorageRef= FirebaseStorage.getInstance().getReference("UserProfilePic");
        mEmail=findViewById(R.id.signup_email);
        mUsername=findViewById(R.id.signup_username);
        mPass=findViewById(R.id.signup_pass);
        mConfirmPass=findViewById(R.id.signup_conPass);
        mSignUpBtn =findViewById(R.id.signupBtn);
        mshowpass=findViewById(R.id.Showpass_signup);
        mHaveAcc=findViewById(R.id.haveacc);
        mProfilepic=findViewById(R.id.profilePic);

        mImagepicker=findViewById(R.id.imagePickerBtn);

        mImagepicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImagePicker.with(SignupActivity.this)
                        .crop(1f,1f)
                        .compress(1024)
                        .start();

            }
        });


        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        mSignUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txt_email,txt_username,txt_pass,txt_confirmpass;
                txt_email=mEmail.getText().toString();
                txt_username=mUsername.getText().toString();
                txt_pass=mPass.getText().toString();
                txt_confirmpass=mConfirmPass.getText().toString();
Log.v("email",String.valueOf(!txt_email.matches(emailPattern)));
                if(!txt_email.matches(emailPattern) || txt_email.isEmpty()){
                    mEmail.setError("invalid email");
                    return;
                }
                if(txt_username.isEmpty()){
                    mUsername.setError("username is required");
                    return;
                }
                Log.v("length", String.valueOf(txt_pass.length()));
                if(txt_pass.length()<7 || txt_pass.isEmpty()){
                    mPass.setError("minimum password length is 8 characters");
                    return;
                }
                if(!txt_pass.equals(txt_confirmpass)){
                    mConfirmPass.setError("password doesn't match");
                    return;
                }

                else {
                    progress=new ProgressDialog(SignupActivity.this);
                    progress.setMessage(getString(R.string.signing_in));
                    progress.show();
                    progress.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                    progress.setContentView(R.layout.progress_layout);
                    createuser(txt_email,txt_pass,txt_username, imageURI);

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


        mshowpass.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    mPass.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    mConfirmPass.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
                else{
                   mPass.setTransformationMethod(PasswordTransformationMethod.getInstance());
                   mConfirmPass.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });

    }


    private void createuser(String txt_email, String txt_pass,String txt_username,Uri profilepicURI) {
        auth.createUserWithEmailAndPassword(txt_email,txt_pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(!task.isSuccessful()){

                    Toast.makeText(SignupActivity.this, "Email already exists", Toast.LENGTH_SHORT).show();
                    progress.dismiss();

                }
                else {
                    if(profilepicURI!=null){
                        uploaduserdata(txt_username,profilepicURI);
                    }
                    else {
                        uploaduserdata(txt_username);
                    }
                    Toast.makeText(SignupActivity.this,"signed up",Toast.LENGTH_SHORT);
                    startActivity(new Intent(SignupActivity.this,MainActivity.class));
                    finish();
                }
            }
        });

    }

    private void uploaduserdata(String txt_username) {
        String userUID= auth.getCurrentUser().getUid();
        ref.child(userUID).child("username").setValue(txt_username);
    }

    private void uploaduserdata(String txt_username, Uri profilepicURI) {
        String userUID= auth.getCurrentUser().getUid();
        ref.child(userUID).child("username").setValue(txt_username);
        StorageReference imageRef =mstorageRef.child(userUID+".jpg");
        imageRef.putFile(profilepicURI).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                imageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        String downloadUri=uri.toString();
                        ref.child(userUID).child("profilePic").setValue(downloadUri);

                    }
                });
            }
        });


    }

    private String getFileExtension(Uri profilepicURI) {
        ContentResolver cr = getContentResolver();
        MimeTypeMap mime= MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cr.getType(profilepicURI));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        imageURI =data.getData();
        mProfilepic.setImageURI(imageURI);
    }
}