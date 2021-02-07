package com.example.epravara;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.common.api.internal.TaskUtil;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProfileActivity extends AppCompatActivity {

    private TextView txtProfileName, txtBirthDate, txtFullName, txtDateofBirth, txtGender, txtMNumber, txtANumber, txtEmail, changeProfilePic;
    private Button updateProfile;
    private ImageView profileImage;
    private DatabaseReference reference;
    private FirebaseAuth firebaseAuth;
    private String userID;
    private StorageReference storageReference;
    private static final int IMAGE_REQUEST = 1;
    private Uri imageUri;
    private StorageTask uploadTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        txtProfileName = findViewById(R.id.showProfileName);
        txtBirthDate = findViewById(R.id.showProfileBirthDate);

        txtFullName = findViewById(R.id.showFullName);
        txtDateofBirth = findViewById(R.id.showDateofBirth);
        txtGender = findViewById(R.id.showGender);
        txtMNumber = findViewById(R.id.showMobileNumber);
        txtANumber = findViewById(R.id.showAadharNumber);
        txtEmail = findViewById(R.id.showEmail);

        changeProfilePic = findViewById(R.id.profileChangeProfilePicture);
        profileImage = findViewById(R.id.profileImageOne);

        firebaseAuth = FirebaseAuth.getInstance();
        userID = firebaseAuth.getCurrentUser().getUid();

        reference = FirebaseDatabase.getInstance().getReference("Users").child(userID);

        updateProfile = findViewById(R.id.updateProfileInfo);
        updateProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final DialogPlus dialogOne = DialogPlus.newDialog(ProfileActivity.this)
                        .setGravity(Gravity.CENTER)
                        .setMargin(50, 0, 50, 0)
                        .setContentHolder(new ViewHolder(R.layout.profile_update_view))
                        .setExpanded(false)
                        .create();

                View holderView = dialogOne.getHolderView();

                final TextInputLayout name = holderView.findViewById(R.id.updateProfileName);
                final TextInputLayout dob = holderView.findViewById(R.id.updateProfileBirthDate);
                final TextInputLayout gender = holderView.findViewById(R.id.updateProfileGender);
                final TextInputLayout mnumber = holderView.findViewById(R.id.updateProfileMobileNumber);
                final TextInputLayout anumber = holderView.findViewById(R.id.updateProfileAadharNumber);
                final TextInputLayout email = holderView.findViewById(R.id.updateProfileEmail);

                Button update = holderView.findViewById(R.id.updateProfile);

                reference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        User user = snapshot.getValue(User.class);
                        name.getEditText().setText(user.getFullname());
                        dob.getEditText().setText(user.getBirthDate());
                        gender.getEditText().setText(user.getGender());
                        mnumber.getEditText().setText(user.getMobile());
                        anumber.getEditText().setText(user.getAadhar());
                        email.getEditText().setText(user.getEmail());
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

                update.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Map<String, Object> map = new HashMap<>();
                        map.put("fullname", name.getEditText().getText().toString());
                        map.put("birthDate", dob.getEditText().getText().toString());
                        map.put("gender", gender.getEditText().getText().toString());
                        map.put("mobile", mnumber.getEditText().getText().toString());
                        map.put("aadhar", anumber.getEditText().getText().toString());
                        map.put("email", email.getEditText().getText().toString());

                        FirebaseDatabase.getInstance().getReference("Users")
                                .child(userID)
                                .updateChildren(map)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        Toast.makeText(ProfileActivity.this, "Updated Successfully!!!", Toast.LENGTH_SHORT).show();
                                        dialogOne.dismiss();
                                    }
                                });
                    }
                });

                dialogOne.show();
            }
        });

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);
                txtProfileName.setText(user.getFullname());
                txtBirthDate.setText(user.getBirthDate());

                txtFullName.setText(user.getFullname());
                txtDateofBirth.setText(user.getBirthDate());
                txtGender.setText(user.getGender());
                txtMNumber.setText(user.getMobile());
                txtANumber.setText(user.getAadhar());
                txtEmail.setText(user.getEmail());

                if (user.getImageURL().equals("default")){
                    profileImage.setImageResource(R.drawable.profile);
                }else {
                    Glide.with(ProfileActivity.this).load(user.getImageURL()).into(profileImage);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        storageReference = FirebaseStorage.getInstance().getReference("uploads").child(userID);

        changeProfilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openImage();
            }
        });

    }

    private void openImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, IMAGE_REQUEST);
    }

    private String getFileExtension(Uri uri){
        ContentResolver contentResolver = this.getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    private void uploadImage(){
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Uploading your Image");
        progressDialog.show();

        if (imageUri != null){
            final StorageReference fileReference = storageReference.child(System.currentTimeMillis()+"."+getFileExtension(imageUri));

            uploadTask = fileReference.putFile(imageUri);
            uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                    if (! task.isSuccessful()){
                        throw task.getException();
                    }
                    return fileReference.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful()){
                        Uri downloadUri = task.getResult();
                        String mUri = downloadUri.toString();

                        HashMap<String, Object> map = new HashMap<>();
                        map.put("imageURL", mUri);
                        reference.updateChildren(map);
                        progressDialog.dismiss();
                    }else {
                        progressDialog.dismiss();
                        Toast.makeText(ProfileActivity.this, "Failed to upload image..!", Toast.LENGTH_SHORT).show();
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    progressDialog.dismiss();
                    Toast.makeText(ProfileActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }else {
            Toast.makeText(this, "No image selected", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null){
            imageUri = data.getData();
            if (uploadTask != null && uploadTask.isInProgress()){
                Toast.makeText(this, "Upload in progress", Toast.LENGTH_SHORT).show();
            }else {
                uploadImage();
            }
        }
    }
}