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

public class AddLabResultsActivity extends AppCompatActivity {

    private TextView txtLabResultsDate;
    private TextInputLayout edtName, edtValue, edtUnit;
    private Button btnAddLabResultsDetails;
    private Spinner lrPhysicianSpinner;
    private DatabaseReference reference, databaseReference;
    private FirebaseAuth firebaseAuth;
    private String userID;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_lab_results);

        txtLabResultsDate = findViewById(R.id.getLabResultsDate);
        edtName = findViewById(R.id.getLabResultsDetails);
        edtValue = findViewById(R.id.getLabResultsValue);
        edtUnit = findViewById(R.id.getLabResultsUnits);
        btnAddLabResultsDetails = findViewById(R.id.addLabResultsDetails);
        lrPhysicianSpinner = findViewById(R.id.labresultsPhysicianSpinner);

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
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(AddLabResultsActivity.this,
                        android.R.layout.simple_spinner_item, titleList);
                arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                lrPhysicianSpinner.setAdapter(arrayAdapter);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(AddLabResultsActivity.this,databaseError.getMessage(),Toast.LENGTH_LONG).show();
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
                txtLabResultsDate.setText(dayOfMonth + "-" + month + "-" + year);
            }

        };

        txtLabResultsDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(AddLabResultsActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        databaseReference = FirebaseDatabase.getInstance().getReference("LabResultsDetails").child(userID);

        btnAddLabResultsDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String finalDate = txtLabResultsDate.getText().toString();
                final String lrName = edtName.getEditText().getText().toString();
                final String lrValue = edtValue.getEditText().getText().toString();
                final String lrUnit = edtUnit.getEditText().getText().toString();
                final String lrPhysician = lrPhysicianSpinner.getSelectedItem().toString();

                if (TextUtils.isEmpty(finalDate)) {
                    Toast.makeText(AddLabResultsActivity.this, "Lab Results Date is required!!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(lrName)) {
                    edtName.setError("Lab Results Name is required!!");
                    return;
                }
                if (TextUtils.isEmpty(lrValue)) {
                    edtValue.setError("Lab Results Value is required!!");
                    return;
                }
                if (TextUtils.isEmpty(lrUnit)) {
                    edtUnit.setError("Lab Results Unit is required!!");
                    return;
                }

                progressDialog.setTitle("Adding New Lab Results Details");
                progressDialog.setMessage("Please wait while adding details..");
                progressDialog.show();

                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        LabResults labResults = new LabResults(
                                finalDate,
                                lrName,
                                lrValue,
                                lrUnit,
                                lrPhysician
                        );
                        databaseReference.child(lrName).setValue(labResults).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()){
                                    progressDialog.dismiss();
                                    Toast.makeText(AddLabResultsActivity.this, "Lab Results Details Added Successfully!!", Toast.LENGTH_SHORT).show();
                                    finish();
                                }else {
                                    progressDialog.dismiss();
                                    Toast.makeText(AddLabResultsActivity.this, "Some Error Occurred!!", Toast.LENGTH_SHORT).show();
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