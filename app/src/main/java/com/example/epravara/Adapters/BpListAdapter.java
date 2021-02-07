package com.example.epravara.Adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.epravara.BloodPressure;
import com.example.epravara.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class BpListAdapter extends FirebaseRecyclerAdapter<BloodPressure, BpListAdapter.MyViewHolder> {

    private Context context;

    public BpListAdapter(@NonNull FirebaseRecyclerOptions<BloodPressure> options, Context context) {
        super(options);
        this.context = context;
    }

    @Override
    protected void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i, @NonNull final BloodPressure bloodPressure) {
        myViewHolder.date.setText(bloodPressure.getBpDate());
        myViewHolder.sys.setText(bloodPressure.getBpSystolic());
        myViewHolder.dia.setText(bloodPressure.getBpDiastolic());
        myViewHolder.pulse.setText(bloodPressure.getBpPulse());
        myViewHolder.arm.setText(bloodPressure.getBpArm());

        myViewHolder.bp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Delete");
                builder.setMessage("Do you really want to delete these record?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                String phName = bloodPressure.getBpDate();
                                FirebaseDatabase.getInstance().getReference("BpWeightVital")
                                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                        .child("BpReading")
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
                .inflate(R.layout.bp_view, parent, false);

        return new MyViewHolder(view);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView date, sys, dia, pulse, arm;
        LinearLayout bp;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            date = itemView.findViewById(R.id.bpDateReadings);
            sys = itemView.findViewById(R.id.bpSysReadings);
            dia = itemView.findViewById(R.id.bpDiaReadings);
            pulse = itemView.findViewById(R.id.bpPulseReadings);
            arm = itemView.findViewById(R.id.bpArmReadings);

            bp = itemView.findViewById(R.id.bpListLinearLayout);

        }
    }
}
