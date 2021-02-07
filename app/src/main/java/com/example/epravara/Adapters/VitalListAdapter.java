package com.example.epravara.Adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.epravara.R;
import com.example.epravara.Vitals;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class VitalListAdapter extends FirebaseRecyclerAdapter<Vitals, VitalListAdapter.MyViewHolder> {

    private Context context;

    public VitalListAdapter(@NonNull FirebaseRecyclerOptions<Vitals> options, Context context) {
        super(options);
        this.context = context;
    }

    @Override
    protected void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i, @NonNull final Vitals vitals) {
        myViewHolder.date.setText(vitals.getvDate());
        myViewHolder.oxygen.setText(vitals.getvOxygen());
        myViewHolder.temp.setText(vitals.getvTemp());
        myViewHolder.rrate.setText(vitals.getvRRate());

        myViewHolder.vitals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Delete");
                builder.setMessage("Do you really want to delete these record?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                String phName = vitals.getvDate();
                                FirebaseDatabase.getInstance().getReference("BpWeightVital")
                                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                        .child("VitalsReading")
                                        .child(phName)
                                        .removeValue()
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                Toast.makeText(context, "Removed Successfully!!", Toast.LENGTH_SHORT).show();
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
            }
        });

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.vitallist_view, parent, false);

        return new MyViewHolder(view);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView date, oxygen, temp, rrate;
        LinearLayout vitals;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            date = itemView.findViewById(R.id.vitalDateReadings);
            oxygen = itemView.findViewById(R.id.vitalOxygenReadings);
            temp = itemView.findViewById(R.id.vitalTempReadings);
            rrate = itemView.findViewById(R.id.vitalRRateReadings);

            vitals = itemView.findViewById(R.id.vitalListLinearLayout);
        }
    }
}
