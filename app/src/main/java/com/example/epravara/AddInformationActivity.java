package com.example.epravara;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.Toast;

public class AddInformationActivity extends AppCompatActivity {

    private LinearLayout card_eight, card_seven, card_six, card_five, card_four, card_one, card_three,card_two;
    private LinearLayout main;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_information);

        card_eight = findViewById(R.id.card_eight);
        card_seven = findViewById(R.id.card_seven);
        card_six = findViewById(R.id.card_six);
        card_five = findViewById(R.id.card_five);
        card_four = findViewById(R.id.card_four);
        card_three = findViewById(R.id.card_three);
        card_two = findViewById(R.id.card_two);
        card_one = findViewById(R.id.card_one);

        main = findViewById(R.id.mainLayout);


        Animation animTtB = AnimationUtils.loadAnimation(AddInformationActivity.this,R.anim.bottom_to_top);
        Animation animFadein = AnimationUtils.loadAnimation(AddInformationActivity.this,R.anim.fadein);

        card_eight.setAnimation(animFadein);
        card_seven.setAnimation(animFadein);
        card_six.setAnimation(animFadein);
        card_five.setAnimation(animFadein);
        card_four.setAnimation(animFadein);
        card_three.setAnimation(animFadein);
        card_two.setAnimation(animFadein);
        card_one.setAnimation(animFadein);

        main.setAnimation(animTtB);

        card_eight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(AddInformationActivity.this, "Please wait while loading data..", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(AddInformationActivity.this, PhysiciansActivity.class));
            }
        });

        card_seven.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(AddInformationActivity.this, "Please wait while loading data..", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(AddInformationActivity.this, PharmacyActivity.class));
            }
        });

        card_six.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(AddInformationActivity.this, "Please wait while loading data..", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(AddInformationActivity.this, SurrogateActivity.class));
            }
        });

        card_five.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(AddInformationActivity.this, "Please wait while loading data..", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(AddInformationActivity.this, EmergencyActivity.class));
            }
        });

        card_four.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(AddInformationActivity.this, "Please wait while loading data..", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(AddInformationActivity.this, FacilityActivity.class));
            }
        });

        card_one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AddInformationActivity.this, MedicalRecordsActivity.class));
            }
        });

        card_three.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AddInformationActivity.this, MedicalHistoryActivity.class));
            }
        });

        card_two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AddInformationActivity.this, BpWeightActivity.class));
            }
        });

    }
}