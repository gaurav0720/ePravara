package com.example.epravara;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AddEmergencyActivity extends AppCompatActivity {

    private TextInputEditText emgName, emgRelation, emgPhone;
    private Button btnAddEmergency;
    private ProgressDialog progressDialog;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference reference;
    private FirebaseAuth firebaseAuth;
    private String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_emergency);

        emgName = findViewById(R.id.emergencyName);
        emgRelation = findViewById(R.id.emergencyRelation);
        emgPhone = findViewById(R.id.emergencyNumber);

        progressDialog = new ProgressDialog(this);

        firebaseDatabase = FirebaseDatabase.getInstance();
        reference = firebaseDatabase.getReference("EmergencyContacts");

        firebaseAuth = FirebaseAuth.getInstance();
        userID = firebaseAuth.getCurrentUser().getUid();

        btnAddEmergency = findViewById(R.id.addEmergencyContact);
        btnAddEmergency.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String eName = emgName.getText().toString().trim();
                final String eRelation = emgRelation.getText().toString().trim();
                final String ePhone = emgPhone.getText().toString().trim();

                if (TextUtils.isEmpty(eName)) {
                    Toast.makeText(AddEmergencyActivity.this, "Contact Name is required!!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(eRelation)) {
                    Toast.makeText(AddEmergencyActivity.this, "Contact Relation is required!!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(ePhone) || ePhone.length() != 10) {
                    Toast.makeText(AddEmergencyActivity.this, "Enter a valid mobile number is required!!", Toast.LENGTH_SHORT).show();
                    return;
                }

                progressDialog.setTitle("Adding New Contact");
                progressDialog.setMessage("Please wait while adding a contact..");
                progressDialog.show();

                reference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        Contacts contacts = new Contacts(
                                eName,
                                eRelation,
                                ePhone
                        );
                        reference.child(userID).child(eName).setValue(contacts).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()){
                                    progressDialog.dismiss();
                                    Toast.makeText(AddEmergencyActivity.this, "Contact Added Successfully!!", Toast.LENGTH_SHORT).show();
                                    finish();
                                }else {
                                    progressDialog.dismiss();
                                    Toast.makeText(AddEmergencyActivity.this, "Some Error Occurred!!", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        });

    }
}
