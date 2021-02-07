package com.example.epravara;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;

public class MedicalRecordsActivity extends AppCompatActivity {

    private CardView card_one, card_two, card_three, card_four;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medical_records);
        card_one = findViewById(R.id.card_diagnosis);
        card_two = findViewById(R.id.card_surgery);
        card_three = findViewById(R.id.card_procedures);
        card_four = findViewById(R.id.card_labresults);

        Animation animLTR = AnimationUtils.loadAnimation(MedicalRecordsActivity.this,R.anim.left_to_right);
        Animation animRTL = AnimationUtils.loadAnimation(MedicalRecordsActivity.this,R.anim.right_to_left);

        card_one.setAnimation(animLTR);
        card_three.setAnimation(animLTR);
        card_two.setAnimation(animRTL);
        card_four.setAnimation(animRTL);

        card_one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MedicalRecordsActivity.this, DiagnosisActivity.class));
            }
        });

        card_two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MedicalRecordsActivity.this, SurgeryActivity.class));
            }
        });

        card_three.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MedicalRecordsActivity.this, ProceduresActivity.class));
            }
        });

        card_four.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MedicalRecordsActivity.this, LabResultsActivity.class));
            }
        });

    }
}