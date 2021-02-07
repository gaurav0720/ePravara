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
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.epravara.AddDiagnosisActivity;
import com.example.epravara.Diagnosis;
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

public class DiagnosisAdapter extends FirebaseRecyclerAdapter<Diagnosis, DiagnosisAdapter.MyViewHolder> {

    private Context context;

    public DiagnosisAdapter(@NonNull FirebaseRecyclerOptions<Diagnosis> options, Context context) {
        super(options);
        this.context = context;
    }

    @Override
    protected void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i, @NonNull final Diagnosis diagnosis) {
        myViewHolder.date.setText(diagnosis.getdDate());
        myViewHolder.physician.setText(diagnosis.getdPhysician());
        myViewHolder.detail.setText(diagnosis.getdDetails());
        myViewHolder.cause.setText(diagnosis.getdCause());
        myViewHolder.tplan.setText(diagnosis.getdTPlan());
        myViewHolder.notes.setText(diagnosis.getdNotes());

        myViewHolder.diagnosisClick.setOnClickListener(new View.OnClickListener() {
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

                                                String phName = diagnosis.getdDate();
                                                FirebaseDatabase.getInstance().getReference("DiagnosisDetails")
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
                                        .setContentHolder(new ViewHolder(R.layout.diagnosis_update_view))
                                        .setExpanded(false)
                                        .create();

                                View holderView = dialogOne.getHolderView();

                                final TextInputLayout details = holderView.findViewById(R.id.updateDiagnosisDetails);
                                final TextInputLayout cause = holderView.findViewById(R.id.updateDiagnosisCause);
                                final TextInputLayout tplan = holderView.findViewById(R.id.updateDiagnosisTreatmentPlan);
                                final TextInputLayout notes = holderView.findViewById(R.id.updateDiagnosisNotes);
                                final Spinner phySpinner = holderView.findViewById(R.id.updateDiagnosisPhysicianSpinner);

                                Button update = holderView.findViewById(R.id.updateDiagnosisDetailsOne);


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

                                details.getEditText().setText(diagnosis.getdDetails());
                                cause.getEditText().setText(diagnosis.getdCause());
                                tplan.getEditText().setText(diagnosis.getdTPlan());
                                notes.getEditText().setText(diagnosis.getdNotes());

                                update.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {

                                        String phName = diagnosis.getdDate();

                                        Map<String, Object> map = new HashMap<>();
                                        map.put("dDate", phName);
                                        map.put("dDetails", details.getEditText().getText().toString());
                                        map.put("dCause", cause.getEditText().getText().toString());
                                        map.put("dTPlan", tplan.getEditText().getText().toString());
                                        map.put("dNotes", notes.getEditText().getText().toString());
                                        map.put("dPhysician", phySpinner.getSelectedItem().toString());

                                        FirebaseDatabase.getInstance().getReference("DiagnosisDetails")
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
                .inflate(R.layout.diagnosis_view,parent, false);
        return new MyViewHolder(view);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView date, physician, detail, cause, tplan, notes;
        private LinearLayout diagnosisClick;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            date = itemView.findViewById(R.id.showDiagnosisDate);
            physician = itemView.findViewById(R.id.showDiagnosisPhysician);
            detail = itemView.findViewById(R.id.showDiagnosisDetails);
            cause = itemView.findViewById(R.id.showDiagnosisCause);
            tplan = itemView.findViewById(R.id.showDiagnosisTreatmentPlan);
            notes = itemView.findViewById(R.id.showDiagnosisNotes);

            diagnosisClick = itemView.findViewById(R.id.diagnosisLinearLayout);
        }
    }
}
