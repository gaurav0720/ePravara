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

import java.util.Calendar;

public class AddMrProcedureActivity extends AppCompatActivity {

    private TextView conditionDate;
    private Button btnAddProcedures;

    private ProgressDialog progressDialog;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference reference;
    private FirebaseAuth firebaseAuth;
    private String userID;
    private TextInputLayout edtName, edtReason;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_mr_procedure);

        conditionDate = findViewById(R.id.mrProceduresDate);
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
                conditionDate.setText(dayOfMonth + "-" + month + "-" + year);
            }

        };

        conditionDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(AddMrProcedureActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        edtName = findViewById(R.id.mrProceduresName);
        edtReason = findViewById(R.id.mrProceduresReason);
        btnAddProcedures = findViewById(R.id.addMrProcedures);

        progressDialog = new ProgressDialog(this);

        firebaseDatabase = FirebaseDatabase.getInstance();
        reference = firebaseDatabase.getReference("MrProcedures");

        firebaseAuth = FirebaseAuth.getInstance();
        userID = firebaseAuth.getCurrentUser().getUid();

        btnAddProcedures.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String finalDate = conditionDate.getText().toString();

                final String hospitalName = edtName.getEditText().getText().toString();
                final String hospitalReason = edtReason.getEditText().getText().toString();

                if (TextUtils.isEmpty(finalDate)) {
                    Toast.makeText(AddMrProcedureActivity.this, "Procedure Date is required!!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(hospitalName)) {
                    edtName.setError("Procedure Name is required!!");
                    return;
                }
                if (TextUtils.isEmpty(hospitalReason)) {
                    edtReason.setError("Procedure Reason is required!!");
                    return;
                }

                progressDialog.setTitle("Adding New Procedure");
                progressDialog.setMessage("Please wait while adding a procedure..");
                progressDialog.show();

                reference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        MrProcedure mrProcedure = new MrProcedure(
                                finalDate,
                                hospitalName,
                                hospitalReason
                        );
                        reference.child(userID).child(finalDate).setValue(mrProcedure).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    progressDialog.dismiss();
                                    Toast.makeText(AddMrProcedureActivity.this, "Procedure Added Successfully!!", Toast.LENGTH_SHORT).show();
                                    finish();
                                } else {
                                    progressDialog.dismiss();
                                    Toast.makeText(AddMrProcedureActivity.this, "Some Error Occurred!!", Toast.LENGTH_SHORT).show();
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