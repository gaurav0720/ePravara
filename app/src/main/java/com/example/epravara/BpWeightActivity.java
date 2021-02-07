package com.example.epravara;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class BpWeightActivity extends AppCompatActivity {

    private Button bp, weight, vitals;
    private TextView txtSystolic, txtDiastolic, txtPulse, txtWeight, txtTemp, txtOxygen, txtRRate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bp_weight);

        bp = findViewById(R.id.bpList);
        weight = findViewById(R.id.weightList);
        vitals = findViewById(R.id.vitalsList);

        bp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(BpWeightActivity.this, "Please wait while loading data", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(BpWeightActivity.this, BpListActivity.class));
            }
        });

        weight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(BpWeightActivity.this, "Please wait while loading data", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(BpWeightActivity.this, WeightListActivity.class));
            }
        });

        vitals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(BpWeightActivity.this, "Please wait while loading data", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(BpWeightActivity.this, VitalListActivity.class));
            }
        });

        txtSystolic = findViewById(R.id.viewSystolicReadings);
        txtDiastolic = findViewById(R.id.viewDiastolicReadings);
        txtPulse = findViewById(R.id.viewPulseReadings);
        txtWeight = findViewById(R.id.viewWeightReadings);
        txtTemp = findViewById(R.id.viewTempReadings);
        txtOxygen = findViewById(R.id.viewOxygenReadings);
        txtRRate = findViewById(R.id.viewRRReadings);

        showData();

    }

    public void showData()
    {

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        String userID = firebaseAuth.getCurrentUser().getUid();

        final DecimalFormat df2 = new DecimalFormat("#.##");

        DatabaseReference reference1 = FirebaseDatabase.getInstance().getReference("BpWeightVital")
                .child(userID).child("BpReading");
        reference1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<BloodPressure> list1 = new ArrayList<>();
                double total1 = 0, total2 = 0, total3 = 0;
                int count1 = 0, count2 = 0, count3 = 0;
                double average1 = 0, average2 = 0, average3 = 0;
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    BloodPressure bloodPressure = postSnapshot.getValue(BloodPressure.class);
                    list1.add(bloodPressure);
                }
                for (int i=0;i<list1.size();i++)
                {
                    total1 = total1 + Double.parseDouble(list1.get(i).getBpSystolic());
                    total2 = total2 + Double.parseDouble(list1.get(i).getBpDiastolic());
                    total3 = total3 + Double.parseDouble(list1.get(i).getBpPulse());
                    count1 = count1 + 1;
                    average1 = total1 / count1;
                    average2 = total2 / count1;
                    average3 = total3 / count1;
                    txtSystolic.setText(df2.format(average1));
                    txtDiastolic.setText(df2.format(average2));
                    txtPulse.setText(df2.format(average3));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        DatabaseReference reference2 = FirebaseDatabase.getInstance().getReference("BpWeightVital")
                .child(userID).child("WeightReading");
        reference2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<Weight> list = new ArrayList<>();
                double total = 0;
                int count = 0;
                double average = 0;
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    Weight weight = postSnapshot.getValue(Weight.class);
                    list.add(weight);
                }
                for (int i=0;i<list.size();i++)
                {
                    total = total + Double.parseDouble(list.get(i).getwWeight());
                    count = count + 1;
                    average = total / count;
                    txtWeight.setText(df2.format(average));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        DatabaseReference reference3 = FirebaseDatabase.getInstance().getReference("BpWeightVital")
                .child(userID).child("VitalsReading");
        reference3.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<Vitals> list = new ArrayList<>();
                double total1 = 0, total2 = 0, total3 = 0;
                int count = 0;
                double average1 = 0, average2 = 0, average3 = 0;
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    Vitals vitals = postSnapshot.getValue(Vitals.class);
                    list.add(vitals);
                }
                for (int i=0;i<list.size();i++)
                {
                    total1 = total1 + Double.parseDouble(list.get(i).getvTemp());
                    total2 = total2 + Double.parseDouble(list.get(i).getvOxygen());
                    total3 = total3 + Double.parseDouble(list.get(i).getvRRate());
                    count = count + 1;
                    average1 = total1 / count;
                    average2 = total2 / count;
                    average3 = total2 / count;
                    txtTemp.setText(df2.format(average1));
                    txtOxygen.setText(df2.format(average2));
                    txtRRate.setText(df2.format(average3));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        super.onStart();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.add_new_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.add_new_item) {
            startActivity(new Intent(BpWeightActivity.this, AddBpActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}