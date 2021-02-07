package com.example.epravara;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class AddSurgeryActivity extends AppCompatActivity {

    private TextView txtSurgeryDate;
    private TextInputLayout edtName, edtReason, edtResults, edtAftercare, edtNotes;
    private Button btnAddSurgeryDetails;
    private Spinner sOrderingPhysicianSpinner, sPerformingSurgeonSpinner, sAnesthesiologistSpinner, sProcedureFacilitySpinner;
    private DatabaseReference referenceOne, referenceTwo, databaseReference;
    private FirebaseAuth firebaseAuth;
    private String userID;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_surgery);

        txtSurgeryDate = findViewById(R.id.getSurgeryDate);

        edtName = findViewById(R.id.getSurgeryName);
        edtReason = findViewById(R.id.getSurgeryReason);
        edtReason = findViewById(R.id.getSurgeryReason);
        edtResults = findViewById(R.id.getSurgeryResults);
        edtAftercare = findViewById(R.id.getSurgeryAftercare);
        edtNotes = findViewById(R.id.getSurgeryNotes);

        btnAddSurgeryDetails = findViewById(R.id.addSurgeryDetails);

        sOrderingPhysicianSpinner = findViewById(R.id.surgeryOrderingPhysicianSpinner);
        sPerformingSurgeonSpinner = findViewById(R.id.surgeryPerformingSurgeonSpinner);
        sAnesthesiologistSpinner = findViewById(R.id.surgeryAnesthesiologistSpinner);
        sProcedureFacilitySpinner = findViewById(R.id.surgeryProcedureFacilitySpinner);

        progressDialog = new ProgressDialog(this);

        firebaseAuth = FirebaseAuth.getInstance();
        userID = firebaseAuth.getCurrentUser().getUid();

        referenceOne = FirebaseDatabase.getInstance().getReference("Physician").child(userID);

        referenceOne.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final List<String> titleList = new ArrayList<String>();
                for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren()){
                    String titlename = dataSnapshot1.child("pName").getValue(String.class);
                    titleList.add(titlename);
                }
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(AddSurgeryActivity.this,
                        android.R.layout.simple_spinner_item, titleList);
                arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                sOrderingPhysicianSpinner.setAdapter(arrayAdapter);
                sPerformingSurgeonSpinner.setAdapter(arrayAdapter);
                sAnesthesiologistSpinner.setAdapter(arrayAdapter);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(AddSurgeryActivity.this,databaseError.getMessage(),Toast.LENGTH_LONG).show();
            }
        });

        referenceTwo = FirebaseDatabase.getInstance().getReference("Facility").child(userID);

        referenceTwo.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final List<String> titleList = new ArrayList<String>();
                for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren()){
                    String titlename = dataSnapshot1.child("facilityName").getValue(String.class);
                    titleList.add(titlename);
                }
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(AddSurgeryActivity.this,
                        android.R.layout.simple_spinner_item, titleList);
                arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                sProcedureFacilitySpinner.setAdapter(arrayAdapter);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(AddSurgeryActivity.this,databaseError.getMessage(),Toast.LENGTH_LONG).show();
            }
        });

        final Calendar myCalendar = Calendar.getInstance();

        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @SuppressLint("SetTextI18n")
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                int month = monthOfYear + 1;
                txtSurgeryDate.setText(dayOfMonth + "-" + month + "-" + year);
            }

        };

        txtSurgeryDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(AddSurgeryActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });


        databaseReference = FirebaseDatabase.getInstance().getReference("SurgeryDetails").child(userID);

        btnAddSurgeryDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String finalDate = txtSurgeryDate.getText().toString();
                final String surgeryName = edtName.getEditText().getText().toString();
                final String surgeryReason = edtReason.getEditText().getText().toString();
                final String surgeryResults = edtResults.getEditText().getText().toString();
                final String surgeryAftercare = edtAftercare.getEditText().getText().toString();
                final String surgeryNotes = edtNotes.getEditText().getText().toString();
                final String surgeryOP = sOrderingPhysicianSpinner.getSelectedItem().toString();
                final String surgeryPS = sPerformingSurgeonSpinner.getSelectedItem().toString();
                final String surgeryA = sAnesthesiologistSpinner.getSelectedItem().toString();
                final String surgeryPF = sProcedureFacilitySpinner.getSelectedItem().toString();

                if (TextUtils.isEmpty(finalDate)) {
                    Toast.makeText(AddSurgeryActivity.this, "Surgery Date is required!!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(surgeryName)) {
                    edtName.setError("Surgery Name is required!!");
                    return;
                }
                if (TextUtils.isEmpty(surgeryReason)) {
                    edtReason.setError("Surgery Reason is required!!");
                    return;
                }
                if (TextUtils.isEmpty(surgeryResults)) {
                    edtResults.setError("Surgery Results is required!!");
                    return;
                }
                if (TextUtils.isEmpty(surgeryAftercare)) {
                    edtAftercare.setError("Surgery Aftercare is required!!");
                    return;
                }
                if (TextUtils.isEmpty(surgeryNotes)) {
                    edtNotes.setError("Surgery Notes is required!!");
                    return;
                }

                progressDialog.setTitle("Adding New Surgery Details");
                progressDialog.setMessage("Please wait while adding details..");
                progressDialog.show();

                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        Surgery surgery = new Surgery(
                                finalDate,
                                surgeryName,
                                surgeryReason,
                                surgeryResults,
                                surgeryAftercare,
                                surgeryNotes,
                                surgeryOP,
                                surgeryPS,
                                surgeryA,
                                surgeryPF
                        );
                        databaseReference.child(finalDate).setValue(surgery).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()){
                                    progressDialog.dismiss();
                                    Toast.makeText(AddSurgeryActivity.this, "Surgery Details Added Successfully!!", Toast.LENGTH_SHORT).show();
                                    finish();
                                }else {
                                    progressDialog.dismiss();
                                    Toast.makeText(AddSurgeryActivity.this, "Some Error Occurred!!", Toast.LENGTH_SHORT).show();
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