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

public class AddPhysicianActivity extends AppCompatActivity {

    private TextInputEditText phyName, phySpeciality, phyPhone, phyEmail, phyAddress, phyCity, phyState, phyPincode;
    private Button btnAddPhy;
    private ProgressDialog progressDialog;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference reference;
    private FirebaseAuth firebaseAuth;
    private String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_physician);

        phyName = findViewById(R.id.physicianName);
        phySpeciality = findViewById(R.id.physicianSpeciality);
        phyPhone = findViewById(R.id.physicianPhone);
        phyEmail = findViewById(R.id.physicianEmail);
        phyAddress = findViewById(R.id.physicianAddress);
        phyCity = findViewById(R.id.physicianCity);
        phyState = findViewById(R.id.physicianState);
        phyPincode = findViewById(R.id.physicianPincode);

        progressDialog = new ProgressDialog(this);
        
        firebaseDatabase = FirebaseDatabase.getInstance();
        reference = firebaseDatabase.getReference("Physician");
        
        firebaseAuth = FirebaseAuth.getInstance();
        userID = firebaseAuth.getCurrentUser().getUid();

        btnAddPhy = findViewById(R.id.addPhysician);
        btnAddPhy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String phName = phyName.getText().toString().trim();
                final String phSpeciality = phySpeciality.getText().toString().trim();
                final String phPhone = phyPhone.getText().toString().trim();
                final String phEmail = phyEmail.getText().toString().trim();
                final String phAddress = phyAddress.getText().toString().trim();
                final String phCity = phyCity.getText().toString().trim();
                final String phState = phyState.getText().toString().trim();
                final String phPincode = phyPincode.getText().toString().trim();

                if (TextUtils.isEmpty(phName)) {
                    Toast.makeText(AddPhysicianActivity.this, "Physician Name is required!!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(phSpeciality)) {
                    Toast.makeText(AddPhysicianActivity.this, "Physician Speciality is required!!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(phPhone) || phPhone.length() != 10) {
                    Toast.makeText(AddPhysicianActivity.this, "Enter a valid mobile number is required!!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(phEmail)) {
                    Toast.makeText(AddPhysicianActivity.this, "Physician Email ID is required!!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(phAddress)) {
                    Toast.makeText(AddPhysicianActivity.this, "Physician Address is required!!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(phCity)) {
                    Toast.makeText(AddPhysicianActivity.this, "Physician City is required!!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(phState)) {
                    Toast.makeText(AddPhysicianActivity.this, "Physician State is required!!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(phPincode) || phPincode.length() != 6) {
                    Toast.makeText(AddPhysicianActivity.this, "Enter a valid Pincode/Zipcode!!", Toast.LENGTH_SHORT).show();
                    return;
                }

                progressDialog.setTitle("Adding New Physician");
                progressDialog.setMessage("Please wait while adding a physician..");
                progressDialog.show();

                reference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        Physician physician = new Physician(
                                phName, 
                                phSpeciality,
                                phPhone,
                                phEmail,
                                phAddress,
                                phCity,
                                phState,
                                phPincode
                        );
                        reference.child(userID).child(phName).setValue(physician).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()){
                                    progressDialog.dismiss();
                                    Toast.makeText(AddPhysicianActivity.this, "Physician Added Successfully!!", Toast.LENGTH_SHORT).show();
                                    finish();
                                }else {
                                    progressDialog.dismiss();
                                    Toast.makeText(AddPhysicianActivity.this, "Some Error Occurred!!", Toast.LENGTH_SHORT).show();
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
