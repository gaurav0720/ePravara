package com.example.epravara;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        firebaseAuth = FirebaseAuth.getInstance();

        if (firebaseAuth.getCurrentUser() != null) {
            Toast.makeText(this, "Logged In Successfully", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(MainActivity.this, AfterLoginActivity.class));
            finish();
        } else {

            new Handler().postDelayed(new Runnable() {

// Using handler with postDelayed called runnable run method

                @Override

                public void run() {

                    Intent i = new Intent(MainActivity.this, LoginActivity.class);

                    startActivity(i);

                    // close this activity

                    finish();

                }

            }, 3 * 1000); // wait for 5 seconds
        }
    }
}
