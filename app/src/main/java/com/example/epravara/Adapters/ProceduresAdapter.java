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

import com.example.epravara.Procedures;
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

public class ProceduresAdapter extends FirebaseRecyclerAdapter<Procedures, ProceduresAdapter.MyViewHolder> {

    private Context context;

    public ProceduresAdapter(@NonNull FirebaseRecyclerOptions<Procedures> options, Context context) {
        super(options);
        this.context = context;
    }

    @Override
    protected void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i, @NonNull final Procedures procedures) {
        myViewHolder.date.setText(procedures.getpDate());
        myViewHolder.name.setText(procedures.getpName());
        myViewHolder.op.setText(procedures.getpOrderingPhysician());
        myViewHolder.ps.setText(procedures.getpPerformingSurgeon());
        myViewHolder.pf.setText(procedures.getpProcedureFacility());
        myViewHolder.reason.setText(procedures.getpReason());
        myViewHolder.result.setText(procedures.getpResults());
        myViewHolder.notes.setText(procedures.getpNotes());

        myViewHolder.procedureClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Update/Delete");
                builder.setMessage("Select one option to update or delete these record?")
                        .setCancelable(false)
                        .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                String phName = procedures.getpDate();
                                FirebaseDatabase.getInstance().getReference("ProceduresDetails")
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
                                        .setContentHolder(new ViewHolder(R.layout.procedure_update_view))
                                        .setExpanded(false)
                                        .create();

                                View holderView = dialogOne.getHolderView();

                                final TextInputLayout name = holderView.findViewById(R.id.updateProcedureName);
                                final TextInputLayout reason = holderView.findViewById(R.id.updateProcedureReason);
                                final TextInputLayout results = holderView.findViewById(R.id.updateProcedureResults);
                                final TextInputLayout notes = holderView.findViewById(R.id.updateProcedureNotes);
                                final Spinner phySpinner = holderView.findViewById(R.id.updateProcedureOrderingPhysicianSpinner);
                                final Spinner ppSpinner = holderView.findViewById(R.id.updateProcedurePerformingPhysicianSpinner);
                                final Spinner pfSpinner = holderView.findViewById(R.id.updateProcedureProcedureFacilitySpinner);

                                Button update = holderView.findViewById(R.id.updateProcedure);


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

                                name.getEditText().setText(procedures.getpName());
                                reason.getEditText().setText(procedures.getpReason());
                                results.getEditText().setText(procedures.getpResults());
                                notes.getEditText().setText(procedures.getpNotes());

                                update.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {

                                        String phName = procedures.getpDate();

                                        Map<String, Object> map = new HashMap<>();
                                        map.put("pDate", phName);
                                        map.put("pName", name.getEditText().getText().toString());
                                        map.put("pReason", reason.getEditText().getText().toString());
                                        map.put("pResults", results.getEditText().getText().toString());
                                        map.put("pNotes", notes.getEditText().getText().toString());
                                        map.put("pOrderingPhysician",phySpinner.getSelectedItem().toString());
                                        map.put("pPerformingSurgeon", ppSpinner.getSelectedItem().toString());
                                        map.put("pProcedureFacility", pfSpinner.getSelectedItem().toString());

                                        FirebaseDatabase.getInstance().getReference("ProceduresDetails")
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
                .inflate(R.layout.procedures_view,parent, false);
        return new MyViewHolder(view);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView date, name, reason, result, notes, op, ps, pf;
        private LinearLayout procedureClick;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            date = itemView.findViewById(R.id.showProcedureDate);
            name = itemView.findViewById(R.id.showProcedureName);
            reason = itemView.findViewById(R.id.showProcedureReason);
            result = itemView.findViewById(R.id.showProcedureResults);
            notes = itemView.findViewById(R.id.showProcedureNotes);
            op = itemView.findViewById(R.id.showProcedureOP);
            ps = itemView.findViewById(R.id.showProcedurePS);
            pf = itemView.findViewById(R.id.showProcedureFacility);

            procedureClick = itemView.findViewById(R.id.proceduresLinearLayout);
        }
    }
}
