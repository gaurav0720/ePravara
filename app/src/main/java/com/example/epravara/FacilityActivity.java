package com.example.epravara;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.epravara.Adapters.FacilityAdapter;
import com.example.epravara.Adapters.PharmacyAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FacilityActivity extends AppCompatActivity {

    private RecyclerView facilityRecyclerList;
    private FacilityAdapter adapter;
    private DatabaseReference reference;
    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_facility);

        facilityRecyclerList = findViewById(R.id.facilityRecyclerView);
        facilityRecyclerList.setLayoutManager(new LinearLayoutManager(this));

        userID = FirebaseAuth.getInstance().getCurrentUser().getUid();

        reference = FirebaseDatabase.getInstance().getReference("Facility").child(userID);

        FirebaseRecyclerOptions<Facility> options =
                new FirebaseRecyclerOptions.Builder<Facility>()
                        .setQuery(reference, Facility.class)
                        .build();
        adapter = new FacilityAdapter(options, this);
        facilityRecyclerList.setAdapter(adapter);

    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
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
            startActivity(new Intent(FacilityActivity.this, AddFacilityActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
