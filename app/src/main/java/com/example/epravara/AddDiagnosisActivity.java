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

public class AddDiagnosisActivity extends AppCompatActivity {

    private TextView txtDiagnsisDate;
    private TextInputLayout edtDiagnosis, edtCause, edtTPlan, edtNotes;
    private Button btnAddDiagnosisDetails;
    private Spinner dPhysicianSpinner;
    private DatabaseReference reference, databaseReference;
    private FirebaseAuth firebaseAuth;
    private String userID;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_diagnosis);

        txtDiagnsisDate = findViewById(R.id.getDiagnosisDate);
        edtDiagnosis = findViewById(R.id.getDiagnosisDetails);
        edtCause = findViewById(R.id.getDiagnosisCause);
        edtTPlan = findViewById(R.id.getDiagnosisTreatmentPlan);
        edtNotes = findViewById(R.id.getDiagnosisNotes);
        dPhysicianSpinner = findViewById(R.id.diagnosisPhysicianSpinner);
        btnAddDiagnosisDetails = findViewById(R.id.addDiagnosisDetails);

        progressDialog = new ProgressDialog(this);

        firebaseAuth = FirebaseAuth.getInstance();
        userID = firebaseAuth.getCurrentUser().getUid();

        reference = FirebaseDatabase.getInstance().getReference("Physician").child(userID);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final List<String> titleList = new ArrayList<String>();
                for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren()){
                    String titlename = dataSnapshot1.child("pName").getValue(String.class);
                    titleList.add(titlename);
                }
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(AddDiagnosisActivity.this,
                        android.R.layout.simple_spinner_item, titleList);
                arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                dPhysicianSpinner.setAdapter(arrayAdapter);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(AddDiagnosisActivity.this,databaseError.getMessage(),Toast.LENGTH_LONG).show();
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
                txtDiagnsisDate.setText(dayOfMonth + "-" + month + "-" + year);
            }

        };

        txtDiagnsisDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(AddDiagnosisActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        databaseReference = FirebaseDatabase.getInstance().getReference("DiagnosisDetails").child(userID);

        btnAddDiagnosisDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String finalDate = txtDiagnsisDate.getText().toString();
                final String diagnosisDetail = edtDiagnosis.getEditText().getText().toString();
                final String diagnosisCause = edtCause.getEditText().getText().toString();
                final String diagnosisTPlan = edtTPlan.getEditText().getText().toString();
                final String diagnosisNotes = edtNotes.getEditText().getText().toString();
                final String dPhysician = dPhysicianSpinner.getSelectedItem().toString();

                if (TextUtils.isEmpty(finalDate)) {
                    Toast.makeText(AddDiagnosisActivity.this, "Diagnosis Date is required!!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(diagnosisDetail)) {
                    edtDiagnosis.setError("Diagnosis Details is required!!");
                    return;
                }
                if (TextUtils.isEmpty(diagnosisCause)) {
                    edtCause.setError("Diagnosis Cause is required!!");
                    return;
                }
                if (TextUtils.isEmpty(diagnosisTPlan)) {
                    edtTPlan.setError("Diagnosis Treatment Plan is required!!");
                    return;
                }
                if (TextUtils.isEmpty(diagnosisNotes)) {
                    edtNotes.setError("Diagnosis Notes is required!!");
                    return;
                }

                progressDialog.setTitle("Adding New Diagnosis Details");
                progressDialog.setMessage("Please wait while adding details..");
                progressDialog.show();

                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        Diagnosis diagnosis = new Diagnosis(
                                finalDate,
                                diagnosisDetail,
                                diagnosisCause,
                                diagnosisTPlan,
                                diagnosisNotes,
                                dPhysician
                        );
                        databaseReference.child(finalDate).setValue(diagnosis).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()){
                                    progressDialog.dismiss();
                                    Toast.makeText(AddDiagnosisActivity.this, "Diagnosis Details Added Successfully!!", Toast.LENGTH_SHORT).show();
                                    finish();
                                }else {
                                    progressDialog.dismiss();
                                    Toast.makeText(AddDiagnosisActivity.this, "Some Error Occurred!!", Toast.LENGTH_SHORT).show();
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