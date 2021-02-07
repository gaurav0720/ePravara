package com.example.epravara.Adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.epravara.LabResults;
import com.example.epravara.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LabResultsAdapter extends FirebaseRecyclerAdapter<LabResults, LabResultsAdapter.MyViewHolder> {

    private Context context;

    public LabResultsAdapter(@NonNull FirebaseRecyclerOptions<LabResults> options, Context context) {
        super(options);
        this.context = context;
    }

    @Override
    protected void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i, @NonNull final LabResults labResults) {
        myViewHolder.date.setText(labResults.getLrDate());
        myViewHolder.physician.setText(labResults.getLrPhysician());
        myViewHolder.name.setText(labResults.getLrName());
        myViewHolder.value.setText(labResults.getLrValue());
        myViewHolder.unit.setText(labResults.getLrUnit());

        myViewHolder.labresultsClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Update/Delete");
                builder.setMessage("Select one option to update or delete these record?")
                        .setCancelable(false)
                        .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                                builder.setTitle("Delete");
                                builder.setMessage("Do you really want to delete these record?")
                                        .setCancelable(false)
                                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {

                                                String phName = labResults.getLrName();
                                                FirebaseDatabase.getInstance().getReference("LabResultsDetails")
                                                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
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
                        })
                        .setNegativeButton("Update", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                final DialogPlus dialogOne = DialogPlus.newDialog(context)
                                        .setGravity(Gravity.CENTER)
                                        .setMargin(50,0,50,0)
                                        .setContentHolder(new ViewHolder(R.layout.labresults_update_view))
                                        .setExpanded(false)
                                        .create();

                                View holderView = dialogOne.getHolderView();

                                final TextInputLayout date = holderView.findViewById(R.id.updateLabResultsDate);
                                final TextInputLayout value = holderView.findViewById(R.id.updateLabResultsValue);
                                final TextInputLayout unit = holderView.findViewById(R.id.updateLabResultsUnit);
                                final Spinner phySpinner = holderView.findViewById(R.id.updateLabResultsPhysicianSpinner);

                                Button update = holderView.findViewById(R.id.updateLabResultsDetails);


                                FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
                                final String userID = firebaseAuth.getCurrentUser().getUid();

                                DatabaseReference reference = FirebaseDatabase.getInstance()
                                        .getReference("Physician").child(userID);

                                reference.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        final List<String> titleList = new ArrayList<String>();
                                        for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren()){
                                            String titlename = dataSnapshot1.child("pName").getValue(String.class);
                                            titleList.add(titlename);
                                        }
                                        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(context,
                                                android.R.layout.simple_spinner_item, titleList);
                                        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                        phySpinner.setAdapter(arrayAdapter);
                                    }
                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {
                                        Toast.makeText(context,databaseError.getMessage(),Toast.LENGTH_LONG).show();
                                    }
                                });

                                date.getEditText().setText(labResults.getLrDate());
                                value.getEditText().setText(labResults.getLrValue());
                                unit.getEditText().setText(labResults.getLrUnit());

                                update.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {

                                        String phName = labResults.getLrName();

                                        Map<String, Object> map = new HashMap<>();
                                        map.put("lrDate", date.getEditText().getText().toString());
                                        map.put("lrName", phName);
                                        map.put("lrValue", value.getEditText().getText().toString());
                                        map.put("lrUnit", unit.getEditText().getText().toString());
                                        map.put("lrPhysician", phySpinner.getSelectedItem().toString());

                                        FirebaseDatabase.getInstance().getReference("LabResultsDetails")
                                                .child(userID)
                                                .child(phName)
                                                .updateChildren(map)
                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        Toast.makeText(context, "Updated Successfully!!!", Toast.LENGTH_SHORT).show();
                                                        dialogOne.dismiss();
                                                    }
                                                });
                                    }
                                });

                                dialogOne.show();
                            }
                        })
                        .setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
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
                .inflate(R.layout.labresults_view,parent, false);
        return new MyViewHolder(view);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView date, physician, name, value, unit;
        private LinearLayout labresultsClick;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            date = itemView.findViewById(R.id.showLabResultsDate);
            physician = itemView.findViewById(R.id.showLabResultsPhysician);
            name = itemView.findViewById(R.id.showLabResultsName);
            value = itemView.findViewById(R.id.showLabResultsValue);
            unit = itemView.findViewById(R.id.showLabResultsUnit);

            labresultsClick = itemView.findViewById(R.id.labresultsLinearLayout);
        }
    }
}
