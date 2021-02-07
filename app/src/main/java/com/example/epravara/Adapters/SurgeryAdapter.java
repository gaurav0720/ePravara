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

import com.example.epravara.R;
import com.example.epravara.Surgery;
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

public class SurgeryAdapter extends FirebaseRecyclerAdapter<Surgery, SurgeryAdapter.MyViewHolder> {

    private Context context;

    public SurgeryAdapter(@NonNull FirebaseRecyclerOptions<Surgery> options, Context context) {
        super(options);
        this.context = context;
    }

    @Override
    protected void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i, @NonNull final Surgery surgery) {
        myViewHolder.date.setText(surgery.getsDate());
        myViewHolder.name.setText(surgery.getsName());
        myViewHolder.op.setText(surgery.getsOrderingPhysician());
        myViewHolder.ps.setText(surgery.getsPerformingSurgeon());
        myViewHolder.anesthes.setText(surgery.getsAnesthesiologist());
        myViewHolder.pf.setText(surgery.getsProcedureFacility());
        myViewHolder.reason.setText(surgery.getsReason());
        myViewHolder.result.setText(surgery.getsResults());
        myViewHolder.aftercare.setText(surgery.getsAftercare());
        myViewHolder.notes.setText(surgery.getsNotes());

        myViewHolder.surgeryClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Update/Delete");
                builder.setMessage("Select one option to update or delete these record?")
                        .setCancelable(false)
                        .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                String phName = surgery.getsDate();
                                FirebaseDatabase.getInstance().getReference("SurgeryDetails")
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
                        .setNegativeButton("Update", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                final DialogPlus dialogOne = DialogPlus.newDialog(context)
                                        .setGravity(Gravity.CENTER)
                                        .setMargin(50,0,50,0)
                                        .setContentHolder(new ViewHolder(R.layout.surgery_update_view))
                                        .setExpanded(false)
                                        .create();

                                View holderView = dialogOne.getHolderView();

                                final TextInputLayout name = holderView.findViewById(R.id.updateSurgeryName);
                                final TextInputLayout reason = holderView.findViewById(R.id.updateSurgeryReason);
                                final TextInputLayout results = holderView.findViewById(R.id.updateSurgeryResults);
                                final TextInputLayout aftercare = holderView.findViewById(R.id.updateSurgeryAftercare);
                                final TextInputLayout notes = holderView.findViewById(R.id.updateSurgeryNotes);
                                final Spinner phySpinner = holderView.findViewById(R.id.updateSurgeryOrderingPhysicianSpinner);
                                final Spinner ppSpinner = holderView.findViewById(R.id.updateSurgeryPerformingSurgeonSpinner);
                                final Spinner anesSpinner = holderView.findViewById(R.id.updateSurgeryAnesthesiologistSpinner);
                                final Spinner pfSpinner = holderView.findViewById(R.id.updateSurgeryProcedureFacilitySpinner);

                                Button update = holderView.findViewById(R.id.updateSurgery);


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
                                        ppSpinner.setAdapter(arrayAdapter);
                                        anesSpinner.setAdapter(arrayAdapter);
                                    }
                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {
                                        Toast.makeText(context,databaseError.getMessage(),Toast.LENGTH_LONG).show();
                                    }
                                });

                                DatabaseReference referenceone = FirebaseDatabase.getInstance()
                                        .getReference("Facility").child(userID);

                                referenceone.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        final List<String> titleList = new ArrayList<String>();
                                        for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren()){
                                            String titlename = dataSnapshot1.child("facilityName").getValue(String.class);
                                            titleList.add(titlename);
                                        }
                                        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(context,
                                                android.R.layout.simple_spinner_item, titleList);
                                        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                        pfSpinner.setAdapter(arrayAdapter);
                                    }
                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {
                                        Toast.makeText(context,databaseError.getMessage(),Toast.LENGTH_LONG).show();
                                    }
                                });

                                name.getEditText().setText(surgery.getsName());
                                reason.getEditText().setText(surgery.getsReason());
                                results.getEditText().setText(surgery.getsReason());
                                aftercare.getEditText().setText(surgery.getsAftercare());
                                notes.getEditText().setText(surgery.getsNotes());

                                update.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {

                                        String phName = surgery.getsDate();

                                        Map<String, Object> map = new HashMap<>();
                                        map.put("sDate", phName);
                                        map.put("sName", name.getEditText().getText().toString());
                                        map.put("sReason", reason.getEditText().getText().toString());
                                        map.put("sResults", results.getEditText().getText().toString());
                                        map.put("sAftercare", aftercare.getEditText().getText().toString());
                                        map.put("sNotes", notes.getEditText().getText().toString());
                                        map.put("sOrderingPhysician",phySpinner.getSelectedItem().toString());
                                        map.put("sPerformingSurgeon", ppSpinner.getSelectedItem().toString());
                                        map.put("sAnesthesiologist",  anesSpinner.getSelectedItem().toString());
                                        map.put("sProcedureFacility", pfSpinner.getSelectedItem().toString());

                                        FirebaseDatabase.getInstance().getReference("SurgeryDetails")
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
                .inflate(R.layout.surgery_view,parent, false);
        return new MyViewHolder(view);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView date, name, reason, result, aftercare, notes, op, ps, anesthes, pf;
        private LinearLayout surgeryClick;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            date = itemView.findViewById(R.id.showSurgeryDate);
            name = itemView.findViewById(R.id.showSurgeryName);
            reason = itemView.findViewById(R.id.showSurgeryReason);
            result = itemView.findViewById(R.id.showSurgeryResults);
            aftercare = itemView.findViewById(R.id.showSurgeryAftercare);
            notes = itemView.findViewById(R.id.showSurgeryNotes);
            op = itemView.findViewById(R.id.showSurgeryOP);
            ps = itemView.findViewById(R.id.showSurgeryPS);
            anesthes = itemView.findViewById(R.id.showSurgeryA);
            pf = itemView.findViewById(R.id.showSurgeryFacility);

            surgeryClick = itemView.findViewById(R.id.surgeryLinearLayout);
        }
    }
}
