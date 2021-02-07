package com.example.epravara;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.epravara.Adapters.BpListAdapter;
import com.example.epravara.Adapters.HospitalStayAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class BpListActivity extends AppCompatActivity {

    private RecyclerView bpRecyclerList;
    private BpListAdapter adapter;
    private DatabaseReference reference;
    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bp_list);

        bpRecyclerList = findViewById(R.id.bpListRecyclerView);
        bpRecyclerList.setLayoutManager(new LinearLayoutManager(this));

        userID = FirebaseAuth.getInstance().getCurrentUser().getUid();

        reference = FirebaseDatabase.getInstance().getReference("BpWeightVital").child(userID).child("BpReading");

        FirebaseRecyclerOptions<BloodPressure> options =
                new FirebaseRecyclerOptions.Builder<BloodPressure>()
                        .setQuery(reference, BloodPressure.class)
                        .build();
        adapter = new BpListAdapter(options, this);
        bpRecyclerList.setAdapter(adapter);

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
        getMenuInflater().inflate(R.menu.deleteall_view, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.delete_all_item) {
            reference = FirebaseDatabase.getInstance().getReference("BpWeightVital").child(userID).child("BpReading");

            AlertDialog.Builder builder = new AlertDialog.Builder(BpListActivity.this);
            builder.setTitle("Delete");
            builder.setMessage("Do you really want to delete all these records?\nAll these will get removed at a time")
                    .setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            reference.removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            Toast.makeText(BpListActivity.this, "Removed Successfully!!", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });
            AlertDialog alert = builder.create();
            alert.show();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}