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

public class AddProceduresActivity extends AppCompatActivity {

    private TextView txtProcedureDate;
    private TextInputLayout edtName, edtReason, edtResults, edtNotes;
    private Button btnAddProcedureDetails;
    private Spinner pOrderingPhysicianSpinner, pPerformingSurgeonSpinner, pProcedureFacilitySpinner;
    private DatabaseReference referenceOne, referenceTwo, databaseReference;
    private FirebaseAuth firebaseAuth;
    private String userID;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_procedures);

        txtProcedureDate = findViewById(R.id.getProcedureDate);

        edtName = findViewById(R.id.getProcedureName);
        edtReason = findViewById(R.id.getProcedureReason);
        edtResults = findViewById(R.id.getProcedureResults);
        edtNotes = findViewById(R.id.getProcedureNotes);

        btnAddProcedureDetails = findViewById(R.id.addProcedureDetails);

        pOrderingPhysicianSpinner = findViewById(R.id.procedureOrderingPhysicianSpinner);
        pPerformingSurgeonSpinner = findViewById(R.id.procedurePerformingPhysicianSpinner);
        pProcedureFacilitySpinner = findViewById(R.id.procedureProcedureFacilitySpinner);

        progressDialog = new ProgressDialog(this);

        firebaseAuth = FirebaseAuth.getInstance();
        userID = firebaseAuth.getCurrentUser().getUid();

        referenceOne = FirebaseDatabase.getInstance().getReference("Physician").child(userID);

        referenceOne.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final List<String> titleList = new ArrayList<String>();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    String titlename = dataSnapshot1.child("pName").getValue(String.class);
                    titleList.add(titlename);
                }
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(AddProceduresActivity.this,
                        android.R.layout.simple_spinner_item, titleList);
                arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                pOrderingPhysicianSpinner.setAdapter(arrayAdapter);
                pPerformingSurgeonSpinner.setAdapter(arrayAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(AddProceduresActivity.this, databaseError.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

        referenceTwo = FirebaseDatabase.getInstance().getReference("Facility").child(userID);

        referenceTwo.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final List<String> titleList = new ArrayList<String>();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    String titlename = dataSnapshot1.child("facilityName").getValue(String.class);
                    titleList.add(titlename);
                }
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(AddProceduresActivity.this,
                        android.R.layout.simple_spinner_item, titleList);
                arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                pProcedureFacilitySpinner.setAdapter(arrayAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(AddProceduresActivity.this, databaseError.getMessage(), Toast.LENGTH_LONG).show();
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
                txtProcedureDate.setText(dayOfMonth + "-" + month + "-" + year);
            }

        };

        txtProcedureDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(AddProceduresActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });


        databaseReference = FirebaseDatabase.getInstance().getReference("ProceduresDetails").child(userID);

        btnAddProcedureDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String finalDate = txtProcedureDate.getText().toString();
                final String procedureName = edtName.getEditText().getText().toString();
                final String procedureReason = edtReason.getEditText().getText().toString();
                final String procedureResults = edtResults.getEditText().getText().toString();
                final String procedureNotes = edtNotes.getEditText().getText().toString();
                final String procedureOP = pOrderingPhysicianSpinner.getSelectedItem().toString();
                final String procedurePS = pPerformingSurgeonSpinner.getSelectedItem().toString();
                final String procedurePF = pProcedureFacilitySpinner.getSelectedItem().toString();

                if (TextUtils.isEmpty(finalDate)) {
                    Toast.makeText(AddProceduresActivity.this, "Procedure Date is required!!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(procedureName)) {
                    edtName.setError("Procedure Name is required!!");
                    return;
                }
                if (TextUtils.isEmpty(procedureReason)) {
                    edtReason.setError("Procedure Reason is required!!");
                    return;
                }
                if (TextUtils.isEmpty(procedureResults)) {
                    edtResults.setError("Procedure Results is required!!");
                    return;
                }
                if (TextUtils.isEmpty(procedureNotes)) {
                    edtNotes.setError("Procedure Aftercare is required!!");
                    return;
                }

                progressDialog.setTitle("Adding New Procedure Details");
                progressDialog.setMessage("Please wait while adding details..");
                progressDialog.show();

                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        Procedures procedures = new Procedures(
                                finalDate,
                                procedureName,
                                procedureReason,
                                procedureResults,
                                procedureNotes,
                                procedureOP,
                                procedurePS,
                                procedurePF
                        );
                        databaseReference.child(finalDate).setValue(procedures).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    progressDialog.dismiss();
                                    Toast.makeText(AddProceduresActivity.this, "Procedures Details Added Successfully!!", Toast.LENGTH_SHORT).show();
                                    finish();
                                } else {
                                    progressDialog.dismiss();
                                    Toast.makeText(AddProceduresActivity.this, "Some Error Occurred!!", Toast.LENGTH_SHORT).show();
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