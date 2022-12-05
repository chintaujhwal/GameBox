package com.example.gamebox;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivity extends AppCompatActivity {

    ProgressDialog pd;
    private CircleImageView profilePicture;
    private EditText edit_username;
    private FirebaseAuth auth;
    private DatabaseReference dataRef;
    private StorageReference storageRef;
    private Uri imageUri;
    private String myUri = "";
    private String email = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        auth = FirebaseAuth.getInstance();
        dataRef = FirebaseDatabase.getInstance().getReference().child("users");
        storageRef = FirebaseStorage.getInstance().getReference().child("UserProfilePic");
        pd = new ProgressDialog(this);

        profilePicture = (CircleImageView) findViewById(R.id.profile);
        edit_username = (EditText) findViewById(R.id.username);
        setUser(auth.getCurrentUser().getUid());
        TextView changeProfile = (TextView) findViewById(R.id.change);
        changeProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImagePicker.with(ProfileActivity.this)
                        .crop(1f,1f)
                        .compress(1024)
                        .start();

            }
        });
        Button updateProfile = (Button) findViewById(R.id.updateprofile);
        updateProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateProfileData();
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
        imageUri =data.getData();
        Picasso.get().load(imageUri).placeholder(R.drawable.profile_24).into(profilePicture);
    }

    private void setUser(String userUID) {
        dataRef.child(userUID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Userdetails obj = snapshot.getValue(Userdetails.class);
                assert obj != null;
                myUri = obj.getProfilePic();
                imageUri = Uri.parse(myUri);
                Picasso.get().load(myUri).placeholder(R.drawable.profile_24).into(profilePicture);
                String username= obj.getUsername();
                edit_username.setText(username);
                email = obj.getEmail();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void updateProfileData() {
        pd.setTitle("Updating your Profile");
        pd.setMessage("Please wait!");
        pd.show();
        if (imageUri.toString().equals(myUri)) {
            HashMap<String, Object> map = new HashMap<>();
            map.put("profilePic", myUri);
            map.put("username", edit_username.getText().toString());
            map.put("email", email);
            dataRef.child(auth.getCurrentUser().getUid()).updateChildren(map);
            Toast.makeText(ProfileActivity.this, "Profile Updated!!!", Toast.LENGTH_SHORT).show();
            setUser(auth.getCurrentUser().getUid());
            pd.dismiss();
        } else {
            StorageReference fileRef = storageRef.child(auth.getCurrentUser().getUid() + ".jpg");
            UploadTask uploadTask = fileRef.putFile(imageUri);
            uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                    if (!task.isSuccessful()) {
                        throw task.getException();
                    }
                    return fileRef.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful()) {
                        Uri downloadUri = task.getResult();
                        myUri = downloadUri.toString();
                        HashMap<String, Object> map = new HashMap<>();
                        map.put("profilePic", myUri);
                        map.put("username", edit_username.getText().toString());
                        map.put("email", email);
                        dataRef.child(auth.getCurrentUser().getUid()).updateChildren(map);
                        Toast.makeText(ProfileActivity.this, "Profile Updated!!!", Toast.LENGTH_SHORT).show();
                        setUser(auth.getCurrentUser().getUid());
                        pd.dismiss();
                    } else {
                        setUser(auth.getCurrentUser().getUid());
                        pd.dismiss();
                        Toast.makeText(ProfileActivity.this, "Error Occurred!!!", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            default: finish();
        }
        return super.onOptionsItemSelected(item);
    }

}