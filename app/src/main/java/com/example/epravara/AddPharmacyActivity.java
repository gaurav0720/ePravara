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

public class AddPharmacyActivity extends AppCompatActivity {

    private TextInputEditText phyName, phyPhone, phyEmail, phyAddress, phyCity, phyState, phyPhName;
    private Button btnAddPharmacy;
    private ProgressDialog progressDialog;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference reference;
    private FirebaseAuth firebaseAuth;
    private String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_pharmacy);

        phyName = findViewById(R.id.pharmacyName);
        phyPhone = findViewById(R.id.pharmacyPhone);
        phyEmail = findViewById(R.id.pharmacyEmail);
        phyAddress = findViewById(R.id.pharmacyAddress);
        phyCity = findViewById(R.id.pharmacyCity);
        phyState = findViewById(R.id.pharmacyState);
        phyPhName = findViewById(R.id.pharmacyPharmacistName);

        progressDialog = new ProgressDialog(this);

        firebaseDatabase = FirebaseDatabase.getInstance();
        reference = firebaseDatabase.getReference("Pharmacy");

        firebaseAuth = FirebaseAuth.getInstance();
        userID = firebaseAuth.getCurrentUser().getUid();

        btnAddPharmacy = findViewById(R.id.addPharmacy);
        btnAddPharmacy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String phName = phyName.getText().toString().trim();
                final String phPhone = phyPhone.getText().toString().trim();
                final String phEmail = phyEmail.getText().toString().trim();
                final String phAddress = phyAddress.getText().toString().trim();
                final String phCity = phyCity.getText().toString().trim();
                final String phState = phyState.getText().toString().trim();
                final String phPhName = phyPhName.getText().toString().trim();

                if (TextUtils.isEmpty(phName)) {
                    Toast.makeText(AddPharmacyActivity.this, "Pharmacy Name is required!!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(phPhone) || phPhone.length() != 10) {
                    Toast.makeText(AddPharmacyActivity.this, "Enter a valid mobile number is required!!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(phEmail)) {
                    Toast.makeText(AddPharmacyActivity.this, "Pharmacy Email ID is required!!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(phAddress)) {
                    Toast.makeText(AddPharmacyActivity.this, "Pharmacy Address is required!!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(phCity)) {
                    Toast.makeText(AddPharmacyActivity.this, "Pharmacy City is required!!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(phState)) {
                    Toast.makeText(AddPharmacyActivity.this, "Pharmacy State is required!!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(phPhName)) {
                    Toast.makeText(AddPharmacyActivity.this, "Pharmacist Name is required!!", Toast.LENGTH_SHORT).show();
                    return;
                }

                progressDialog.setTitle("Adding New Pharmacy");
                progressDialog.setMessage("Please wait while adding a pharmacy..");
                progressDialog.show();

                reference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        Pharmacy pharmacy = new Pharmacy(
                                phName,
                                phPhone,
                                phEmail,
                                phAddress,
                                phCity,
                                phState,
                                phPhName
                        );
                        reference.child(userID).child(phName).setValue(pharmacy).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()){
                                    progressDialog.dismiss();
                                    Toast.makeText(AddPharmacyActivity.this, "Pharmacy Added Successfully!!", Toast.LENGTH_SHORT).show();
                                    finish();
                                }else {
                                    progressDialog.dismiss();
                                    Toast.makeText(AddPharmacyActivity.this, "Some Error Occurred!!", Toast.LENGTH_SHORT).show();
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
