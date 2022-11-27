package com.example.gamebox;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivity extends AppCompatActivity {

    public static final int CAMERA_PERMISSION_CODE = 101;
    public static final int STORAGE_PERMISSION_CODE = 102;
    ActivityResultLauncher<Intent> activityCaptureImageLauncher;
    ActivityResultLauncher<Intent> activityGalleryLauncher;
    String currentPhotoPath;
    ProgressDialog pd;
    private CircleImageView profilePicture;
    private EditText edit_username;
    private EditText edit_email;
    private FirebaseAuth auth;
    private DatabaseReference dataRef;
    private StorageReference storageRef;
    private Uri imageUri;
    private String myUri = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        auth = FirebaseAuth.getInstance();
        dataRef = FirebaseDatabase.getInstance().getReference().child("users");
        storageRef = FirebaseStorage.getInstance().getReference().child("UserProfilePic");
        pd = new ProgressDialog(this);

        activityCaptureImageLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == RESULT_OK) {
                            File imgFile = new File(currentPhotoPath);
                            if (imgFile.exists()) {
                                imageUri = Uri.fromFile(imgFile);
                                profilePicture.setImageURI(imageUri);
                            }
                        }
                    }
                });

        activityGalleryLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == RESULT_OK) {
                            imageUri = result.getData().getData();
                            profilePicture.setImageURI(imageUri);
                        }
                    }
                });

        profilePicture = (CircleImageView) findViewById(R.id.profile);
        edit_username = (EditText) findViewById(R.id.username);
        edit_email = (EditText) findViewById(R.id.email);
        setUser(auth.getCurrentUser().getUid());
        imageUri = Uri.parse(myUri);
        TextView changeProfile = (TextView) findViewById(R.id.change);
        changeProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImage();
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

    private void setUser(String userUID) {
        dataRef.child(userUID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Userdetails obj = snapshot.getValue(Userdetails.class);
                assert obj != null;
                myUri = obj.getProfilePic();
                Picasso.get().load(myUri).placeholder(R.drawable.profile_24).into(profilePicture);
                String username= obj.getUsername();
                edit_username.setText(username);
                String email = obj.getEmail();
                edit_email.setText(email);
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
            map.put("email", edit_email.getText().toString());
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
                        map.put("email", edit_email.getText().toString());
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

    void selectImage() {
        final CharSequence[] options = {"Capture Image", "Choose from Gallery", "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(ProfileActivity.this);
        builder.setTitle("Select Option");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (options[i].equals("Capture Image")) {
                    askCameraPermission();
                    Toast.makeText(ProfileActivity.this, "Camera option selected", Toast.LENGTH_SHORT).show();
                } else if (options[i].equals("Choose from Gallery")) {
                    askStoragePermission();
                    Toast.makeText(ProfileActivity.this, "Gallery option selected", Toast.LENGTH_SHORT).show();
                } else if (options[i].equals("Cancel")) {
                    dialogInterface.dismiss();
                }
            }
        });
        builder.show();
    }

    void askCameraPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION_CODE);
        } else {
            openCamera();
        }
    }

    void askStoragePermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
        } else {
            openGallery();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CAMERA_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openCamera();
            } else {
                Toast.makeText(ProfileActivity.this, "Camera Permission Required!!!", Toast.LENGTH_SHORT).show();
            }
        } else if (requestCode == STORAGE_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openGallery();
            } else {
                Toast.makeText(ProfileActivity.this, "Storage Permission Required!!!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    void openCamera() {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File pictureFile = null;
        try {
            pictureFile = createImageFile();
        } catch (IOException ex) {
            Toast.makeText(this, "Photo file can't be created, please try again", Toast.LENGTH_SHORT).show();
            return;
        }
        if (pictureFile != null) {
            Uri photoURI = FileProvider.getUriForFile(this, "com.example.android.fileprovider", pictureFile);
            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
            activityCaptureImageLauncher.launch(cameraIntent);
        }
    }

    private File createImageFile() throws IOException {
        @SuppressLint("SimpleDateFormat")
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp;
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(imageFileName, ".jpg", storageDir);
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }

    void openGallery() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        activityGalleryLauncher.launch(galleryIntent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            default: finish();
        }
        return super.onOptionsItemSelected(item);
    }

}