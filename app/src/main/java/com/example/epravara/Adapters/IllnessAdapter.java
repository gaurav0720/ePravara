package com.example.epravara.Adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.epravara.Illness;
import com.example.epravara.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class IllnessAdapter extends FirebaseRecyclerAdapter<Illness, IllnessAdapter.MyViewHolder> {

    private Context context;

    public IllnessAdapter(@NonNull FirebaseRecyclerOptions<Illness> options, Context context) {
        super(options);
        this.context = context;
    }

    @Override
    protected void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i, @NonNull final Illness illness) {

        String firstData = illness.getIllnessType();
        String secondData = illness.getIllnessName();
        if (firstData.length() != 0 && secondData.length() !=0){
            myViewHolder.date.setText(illness.getIllnessDate());
            myViewHolder.type.setText(illness.getIllnessType());
            myViewHolder.name.setText(illness.getIllnessName());
            myViewHolder.othertype.setVisibility(View.GONE);
            myViewHolder.othername.setVisibility(View.GONE);
            myViewHolder.happened.setText(illness.getIllnessHappened());
        }
        else if (firstData.length() != 0 && secondData.length() == 0){
            myViewHolder.date.setText(illness.getIllnessDate());
            myViewHolder.type.setText(illness.getIllnessType());
            myViewHolder.name.setVisibility(View.GONE);
            myViewHolder.othertype.setVisibility(View.GONE);
            myViewHolder.othername.setText(illness.getOtherName());
            myViewHolder.happened.setText(illness.getIllnessHappened());
        }
        else {
            myViewHolder.date.setText(illness.getIllnessDate());
            myViewHolder.type.setVisibility(View.GONE);
            myViewHolder.name.setVisibility(View.GONE);
            myViewHolder.othertype.setText(illness.getOtherType());
            myViewHolder.othername.setText(illness.getOtherName());
            myViewHolder.happened.setText(illness.getIllnessHappened());
        }

        myViewHolder.illness.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Delete");
                builder.setMessage("Do you really want to delete these contact?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                String illdate = illness.getIllnessDate();
                                FirebaseDatabase.getInstance().getReference("ConditionIllness")
                                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                        .child(illdate)
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
                .inflate(R.layout.illness_view, parent, false);

        return new IllnessAdapter.MyViewHolder(view);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView date, type, name, othertype, othername, happened;
        LinearLayout illness;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            date = itemView.findViewById(R.id.showIllnessDate);
            type = itemView.findViewById(R.id.showIllnessType);
            name = itemView.findViewById(R.id.showIllnessName);
            othertype = itemView.findViewById(R.id.showIllnessOtherType);
            othername = itemView.findViewById(R.id.showIllnessOtherName);
            happened = itemView.findViewById(R.id.showIllnessHappened);

            illness = itemView.findViewById(R.id.illnessLinearLayout);
        }
    }
}
