package com.example.epravara;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

public class RegisterActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference reference;

    private TextInputEditText fullName, mobileNumber, emailID, aadharNumber, pass, confirmPass;
    private TextView txtDateofBirth;
    private Button btnRegister;
    String userID, genderValue;
    private RadioButton radioMale, radioFemale;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        reference = firebaseDatabase.getReference("Users");

        fullName = findViewById(R.id.userFullName);
        mobileNumber = findViewById(R.id.userMobileNumber);
        emailID = findViewById(R.id.userEmail);
        aadharNumber = findViewById(R.id.userAadharNumber);
        pass = findViewById(R.id.userPassword);
        confirmPass = findViewById(R.id.userConfirmPassword);
        radioMale = findViewById(R.id.male);
        radioFemale = findViewById(R.id.female);
        txtDateofBirth = findViewById(R.id.registerDateofBirth);

        final Calendar myCalendar = Calendar.getInstance();

        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {

                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                int month = monthOfYear + 1;
                txtDateofBirth.setText(dayOfMonth + "-" + month + "-" + year);
            }

        };

        txtDateofBirth.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(RegisterActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        progressDialog = new ProgressDialog(this);

        btnRegister = findViewById(R.id.btnRegister);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String fname = fullName.getText().toString().trim();
                final String bDate = txtDateofBirth.getText().toString().trim();
                final String mobile = mobileNumber.getText().toString().trim();
                final String email = emailID.getText().toString().trim();
                final String aadhar = aadharNumber.getText().toString().trim();
                final String password = pass.getText().toString().trim();
                final String cpass = confirmPass.getText().toString().trim();
                final String imageUrl = "default";

                if (radioMale.isChecked()) {
                    genderValue = "Male";
                }
                if (radioFemale.isChecked()) {
                    genderValue = "Female";
                }

                if (TextUtils.isEmpty(fname)) {
                    Toast.makeText(RegisterActivity.this, "User's Full Name is required!!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(bDate)) {
                    Toast.makeText(RegisterActivity.this, "User's Birth Date is required!!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(mobile) || mobile.length() != 10) {
                    Toast.makeText(RegisterActivity.this, "Enter a valid mobile number is required!!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(RegisterActivity.this, "User's Email ID is required!!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(aadhar) || aadhar.length() != 12) {
                    Toast.makeText(RegisterActivity.this, "Enter a valid aadhar number is required!!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(password) || password.length() < 6) {
                    Toast.makeText(RegisterActivity.this, "User's Password must be greater than 6 characters!!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(cpass) || cpass.length() < 6) {
                    Toast.makeText(RegisterActivity.this, "User's Confirm Password must be greater than 6 characters!!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!password.equals(cpass)) {
                    Toast.makeText(RegisterActivity.this, "User's Password and Confirm Password won't match!!", Toast.LENGTH_SHORT).show();
                    return;
                }

                progressDialog.setTitle("Registration");
                progressDialog.setMessage("Please wait while registration gets complete..");
                progressDialog.show();

                firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(
                        RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()){
                                    progressDialog.dismiss();
                                    userID = firebaseAuth.getCurrentUser().getUid();
                                    User user = new User(
                                            fname,
                                            bDate,
                                            genderValue,
                                            mobile,
                                            email,
                                            aadhar,
                                            password,
                                            cpass,
                                            userID,
                                            imageUrl
                                    );
                                    reference.child(userID).setValue(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Toast.makeText(RegisterActivity.this, "Registered Successfully!!", Toast.LENGTH_SHORT).show();
                                            startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                                            finish();
                                        }
                                    });
                                }
                                else {
                                    progressDialog.dismiss();
                                    Toast.makeText(RegisterActivity.this, "Error:"+task.getException(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }

        });
    }

}
