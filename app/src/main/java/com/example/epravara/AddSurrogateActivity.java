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

public class AddSurrogateActivity extends AppCompatActivity {

    private TextInputEditText sName, sRelation, sPhone, sAddress, sCity, sState, sPincode;
    private Button btnAddSurrogate;
    private ProgressDialog progressDialog;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference reference;
    private FirebaseAuth firebaseAuth;
    private String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_surrogate);

        sName = findViewById(R.id.surrogateName);
        sRelation = findViewById(R.id.surrogateRelation);
        sPhone = findViewById(R.id.surrogatePhone);
        sAddress = findViewById(R.id.surrogateAddress);
        sCity = findViewById(R.id.surrogateCity);
        sState = findViewById(R.id.surrogateState);
        sPincode = findViewById(R.id.surrogatePincode);

        progressDialog = new ProgressDialog(this);

        firebaseDatabase = FirebaseDatabase.getInstance();
        reference = firebaseDatabase.getReference("Surrogates");

        firebaseAuth = FirebaseAuth.getInstance();
        userID = firebaseAuth.getCurrentUser().getUid();

        btnAddSurrogate = findViewById(R.id.addSurrogate);
        btnAddSurrogate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String phName = sName.getText().toString().trim();
                final String phRelation = sRelation.getText().toString().trim();
                final String phPhone = sPhone.getText().toString().trim();
                final String phAddress = sAddress.getText().toString().trim();
                final String phCity = sCity.getText().toString().trim();
                final String phState = sState.getText().toString().trim();
                final String phPincode = sPincode.getText().toString().trim();

                if (TextUtils.isEmpty(phName)) {
                    Toast.makeText(AddSurrogateActivity.this, "Surrogate Name is required!!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(phRelation)) {
                    Toast.makeText(AddSurrogateActivity.this, "Surrogate Relation is required!!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(phPhone) || phPhone.length() != 10) {
                    Toast.makeText(AddSurrogateActivity.this, "Enter a valid mobile number is required!!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(phAddress)) {
                    Toast.makeText(AddSurrogateActivity.this, "Surrogate Address is required!!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(phCity)) {
                    Toast.makeText(AddSurrogateActivity.this, "Surrogate City is required!!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(phState)) {
                    Toast.makeText(AddSurrogateActivity.this, "Surrogate State is required!!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(phPincode)) {
                    Toast.makeText(AddSurrogateActivity.this, "Surrogate Pincode/Zipcode is required!!", Toast.LENGTH_SHORT).show();
                    return;
                }

                progressDialog.setTitle("Adding New Surrogate");
                progressDialog.setMessage("Please wait while adding a surrogate..");
                progressDialog.show();

                reference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        Surrogate surrogate = new Surrogate(
                                phName,
                                phRelation,
                                phPhone,
                                phAddress,
                                phCity,
                                phState,
                                phPincode
                        );
                        reference.child(userID).child(phName).setValue(surrogate).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()){
                                    progressDialog.dismiss();
                                    Toast.makeText(AddSurrogateActivity.this, "Health Surrogate Added Successfully!!", Toast.LENGTH_SHORT).show();
                                    finish();
                                }else {
                                    progressDialog.dismiss();
                                    Toast.makeText(AddSurrogateActivity.this, "Some Error Occurred!!", Toast.LENGTH_SHORT).show();
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
