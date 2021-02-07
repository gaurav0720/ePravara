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

public class AddFacilityActivity extends AppCompatActivity {

    private TextInputEditText fName, fType, fPhone, fEmail, fAddress, fCity, fState, fContactPerson, fNotes;
    private Button btnAddFacility;
    private ProgressDialog progressDialog;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference reference;
    private FirebaseAuth firebaseAuth;
    private String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_facility);

        fName = findViewById(R.id.facilityName);
        fType = findViewById(R.id.facilityType);
        fPhone = findViewById(R.id.facilityPhone);
        fEmail = findViewById(R.id.facilityEmail);
        fAddress = findViewById(R.id.facilityAddress);
        fCity = findViewById(R.id.facilityCity);
        fState = findViewById(R.id.facilityState);
        fContactPerson = findViewById(R.id.facilityContactPerson);
        fNotes = findViewById(R.id.facilityNotes);

        progressDialog = new ProgressDialog(this);

        firebaseDatabase = FirebaseDatabase.getInstance();
        reference = firebaseDatabase.getReference("Facility");

        firebaseAuth = FirebaseAuth.getInstance();
        userID = firebaseAuth.getCurrentUser().getUid();

        btnAddFacility = findViewById(R.id.addFacility);
        btnAddFacility.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String phName = fName.getText().toString().trim();
                final String phType = fType.getText().toString().trim();
                final String phPhone = fPhone.getText().toString().trim();
                final String phEmail = fEmail.getText().toString().trim();
                final String phAddress = fAddress.getText().toString().trim();
                final String phCity = fCity.getText().toString().trim();
                final String phState = fState.getText().toString().trim();
                final String phContactPerson = fContactPerson.getText().toString().trim();
                final String phNotes = fNotes.getText().toString().trim();

                if (TextUtils.isEmpty(phName)) {
                    fName.setError("Facility Name is required!!");
                    Toast.makeText(AddFacilityActivity.this, "Facility Name is required!!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(phType)) {
                    fName.setError("Facility Type is required!!");
                    Toast.makeText(AddFacilityActivity.this, "Facility Type is required!!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(phPhone) || phPhone.length() != 10) {
                    Toast.makeText(AddFacilityActivity.this, "Enter a valid mobile number is required!!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(phEmail)) {
                    Toast.makeText(AddFacilityActivity.this, "Facility Email ID is required!!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(phAddress)) {
                    Toast.makeText(AddFacilityActivity.this, "Facility Address is required!!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(phCity)) {
                    Toast.makeText(AddFacilityActivity.this, "Facility City is required!!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(phState)) {
                    Toast.makeText(AddFacilityActivity.this, "Facility State is required!!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(phContactPerson)) {
                    Toast.makeText(AddFacilityActivity.this, "Facility Contact Person Name is required!!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(phNotes)) {
                    Toast.makeText(AddFacilityActivity.this, "Facility Notes is required!!", Toast.LENGTH_SHORT).show();
                    return;
                }

                progressDialog.setTitle("Adding New Facility");
                progressDialog.setMessage("Please wait while adding a facility..");
                progressDialog.show();

                reference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        Facility facility = new Facility(
                                phName,
                                phType,
                                phPhone,
                                phEmail,
                                phAddress,
                                phCity,
                                phState,
                                phContactPerson,
                                phNotes
                        );
                        reference.child(userID).child(phName).setValue(facility).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()){
                                    progressDialog.dismiss();
                                    Toast.makeText(AddFacilityActivity.this, "Facility Added Successfully!!", Toast.LENGTH_SHORT).show();
                                    finish();
                                }else {
                                    progressDialog.dismiss();
                                    Toast.makeText(AddFacilityActivity.this, "Some Error Occurred!!", Toast.LENGTH_SHORT).show();
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
