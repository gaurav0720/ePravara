package com.example.epravara;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;

public class AddBpActivity extends AppCompatActivity {

    private TextView readingsDate;
    private EditText edtSystolic, edtDiastolic, edtPulse, edtWeight, edtTemp, edtOxygen, edtRRate;
    private RadioGroup armGroup, unitGroup;
    private Button btnAddReadings;
    private ProgressDialog progressDialog;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference reference;
    private FirebaseAuth firebaseAuth;
    private String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_bp);

        readingsDate = findViewById(R.id.bpWeightDate);
        edtSystolic = findViewById(R.id.systolicReadings);
        edtDiastolic = findViewById(R.id.diastolicReadings);
        edtPulse = findViewById(R.id.pulseReadings);
        edtWeight = findViewById(R.id.weightReadings);
        edtTemp = findViewById(R.id.tempReadings);
        edtOxygen = findViewById(R.id.oxygenReadings);
        edtRRate = findViewById(R.id.rrReadings);
        btnAddReadings = findViewById(R.id.addReadings);

        armGroup = findViewById(R.id.armReadings);
        unitGroup = findViewById(R.id.weightUnits);

        final Calendar myCalendar = Calendar.getInstance();

        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @SuppressLint("SetTextI18n")
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                int month = monthOfYear + 1;
                readingsDate.setText(dayOfMonth + "-" + month + "-" + year);
            }

        };

        readingsDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                new DatePickerDialog(AddBpActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        progressDialog = new ProgressDialog(this);

        firebaseAuth = FirebaseAuth.getInstance();
        userID = firebaseAuth.getCurrentUser().getUid();

        firebaseDatabase = FirebaseDatabase.getInstance();
        reference = firebaseDatabase.getReference("BpWeightVital").child(userID);

        btnAddReadings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String rDate = readingsDate.getText().toString().trim();
                final String sReading = edtSystolic.getText().toString().trim();
                final String dReading = edtDiastolic.getText().toString().trim();
                final String pReading = edtPulse.getText().toString().trim();
                final String wReading = edtWeight.getText().toString().trim();
                final String tReading = edtTemp.getText().toString().trim();
                final String oReading = edtOxygen.getText().toString().trim();
                final String rrReading = edtRRate.getText().toString().trim();

                int selectedRadioButtonID1 = armGroup.getCheckedRadioButtonId();
                RadioButton selectedArmGroup = (RadioButton) findViewById(selectedRadioButtonID1);
                final String selectedArm = selectedArmGroup.getText().toString();

                int selectedRadioButtonID2 = unitGroup.getCheckedRadioButtonId();
                RadioButton selectedUnitGroup = (RadioButton) findViewById(selectedRadioButtonID2);
                final String selectedUnit = selectedUnitGroup.getText().toString();

                if (TextUtils.isEmpty(rDate)) {
                    Toast.makeText(AddBpActivity.this, "Reading Date is required!!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(sReading)) {
                    Toast.makeText(AddBpActivity.this, "Systolic Reading is required!!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(dReading)) {
                    Toast.makeText(AddBpActivity.this, "Diastolic Reading is required!!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(pReading)) {
                    Toast.makeText(AddBpActivity.this, "Pulse Reading is required!!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(wReading)) {
                    Toast.makeText(AddBpActivity.this, "Weight Reading is required!!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(tReading)) {
                    Toast.makeText(AddBpActivity.this, "Temperature Reading is required!!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(oReading)) {
                    Toast.makeText(AddBpActivity.this, "Oxygen Reading is required!!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(rrReading)) {
                    Toast.makeText(AddBpActivity.this, "Respiration Rate Reading is required!!", Toast.LENGTH_SHORT).show();
                    return;
                }

                progressDialog.setTitle("Adding New Readings");
                progressDialog.setMessage("Please wait while adding readings..");
                progressDialog.show();

                reference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        BloodPressure bloodPressure = new BloodPressure(
                                rDate,
                                sReading,
                                dReading,
                                pReading,
                                selectedArm
                        );
                        reference.child("BpReading").child(rDate).setValue(bloodPressure);

                        Weight weight = new Weight(
                                rDate,
                                wReading,
                                selectedUnit
                        );
                        reference.child("WeightReading").child(rDate).setValue(weight);

                        Vitals vitals = new Vitals(
                                rDate,
                                oReading,
                                tReading,
                                rrReading
                        );
                        reference.child("VitalsReading").child(rDate).setValue(vitals);

                        progressDialog.dismiss();
                        Toast.makeText(AddBpActivity.this, "Readings Added Successfully!!", Toast.LENGTH_SHORT).show();
                        finish();

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        });

    }
}