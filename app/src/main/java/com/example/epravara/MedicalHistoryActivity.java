package com.example.epravara;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

public class MedicalHistoryActivity extends AppCompatActivity {

    private CardView card_one, card_two, card_three;
    private AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medical_history);

        MobileAds.initialize(this, "ca-app-pub-3394154946391621~5471187138");

        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        mAdView.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                // Code to be executed when an ad finishes loading.
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                // Code to be executed when an ad request fails.
            }

            @Override
            public void onAdOpened() {
                // Code to be executed when an ad opens an overlay that
                // covers the screen.
            }

            @Override
            public void onAdClicked() {
                // Code to be executed when the user clicks on an ad.
            }

            @Override
            public void onAdLeftApplication() {
                // Code to be executed when the user has left the app.
            }

            @Override
            public void onAdClosed() {
                // Code to be executed when the user is about to return
                // to the app after tapping on an ad.
            }
        });

        card_one = findViewById(R.id.card_illness);
        card_two = findViewById(R.id.card_hospitalstays);
        card_three = findViewById(R.id.card_MHprocedures);

        Animation animLTR = AnimationUtils.loadAnimation(MedicalHistoryActivity.this,R.anim.left_to_right);
        Animation animRTL = AnimationUtils.loadAnimation(MedicalHistoryActivity.this,R.anim.right_to_left);

        card_one.setAnimation(animLTR);
        card_two.setAnimation(animRTL);
        card_three.setAnimation(animLTR);

        card_one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MedicalHistoryActivity.this, "Please while loading data..", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(MedicalHistoryActivity.this, IllnessActivity.class));
            }
        });

        card_two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MedicalHistoryActivity.this, "Please while loading data..", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(MedicalHistoryActivity.this, HospitalStaysActivity.class));
            }
        });
//
        card_three.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MedicalHistoryActivity.this, "Please while loading data..", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(MedicalHistoryActivity.this, MrProcesdureActivity.class));
            }
        });

    }
}