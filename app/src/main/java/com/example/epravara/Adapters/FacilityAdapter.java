package com.example.epravara.Adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.epravara.Facility;
import com.example.epravara.Pharmacy;
import com.example.epravara.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import java.util.HashMap;
import java.util.Map;

public class FacilityAdapter extends FirebaseRecyclerAdapter<Facility, FacilityAdapter.MyViewHolder> {

    private Context context;

    public FacilityAdapter(@NonNull FirebaseRecyclerOptions<Facility> options, Context context) {
        super(options);
        this.context = context;
    }

    @Override
    protected void onBindViewHolder(@NonNull FacilityAdapter.MyViewHolder myViewHolder, int i, @NonNull final Facility facility) {
        myViewHolder.name.setText(facility.getFacilityName());
        myViewHolder.type.setText(facility.getFacilityType());
        myViewHolder.phone.setText(facility.getFacilityPhone());
        myViewHolder.email.setText(facility.getFacilityEmail());
        myViewHolder.address.setText(facility.getFacilityAddress());
        myViewHolder.city.setText(facility.getFacilityCity());
        myViewHolder.state.setText(facility.getFacilityState());
        myViewHolder.contactperson.setText(facility.getFacilityContactPerson());
        myViewHolder.notes.setText(facility.getFacilityNotes());

        myViewHolder.fDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Delete");
                builder.setMessage("Do you really want to delete these facility?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                String phName = facility.getFacilityName();
                                FirebaseDatabase.getInstance().getReference("Facility")
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
        });

        myViewHolder.fUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final DialogPlus dialog = DialogPlus.newDialog(context)
                        .setGravity(Gravity.CENTER)
                        .setMargin(50,0,50,0)
                        .setContentHolder(new ViewHolder(R.layout.facility_update_view))
                        .setExpanded(false)
                        .create();

                View holderView = dialog.getHolderView();

                final EditText type = holderView.findViewById(R.id.facilityUpdateType);
                final EditText phone = holderView.findViewById(R.id.facilityUpdatePhone);
                final EditText email = holderView.findViewById(R.id.facilityUpdateEmail);
                final EditText address = holderView.findViewById(R.id.facilityUpdateAddress);
                final EditText city = holderView.findViewById(R.id.facilityUpdateCity);
                final EditText state = holderView.findViewById(R.id.facilityUpdateState);
                final EditText contactperson = holderView.findViewById(R.id.facilityUpdateContactPerson);
                final EditText notes = holderView.findViewById(R.id.facilityUpdateNotes);

                Button update = holderView.findViewById(R.id.updateFacilityOne);

                type.setText(facility.getFacilityType());
                phone.setText(facility.getFacilityPhone());
                email.setText(facility.getFacilityEmail());
                address.setText(facility.getFacilityAddress());
                city.setText(facility.getFacilityCity());
                state.setText(facility.getFacilityState());
                contactperson.setText(facility.getFacilityContactPerson());
                notes.setText(facility.getFacilityNotes());

                update.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        String phName = facility.getFacilityName();

                        Map<String, Object> map = new HashMap<>();
                        map.put("facilityName", phName);
                        map.put("facilityType", type.getText().toString());
                        map.put("facilityPhone", phone.getText().toString());
                        map.put("facilityEmail", email.getText().toString());
                        map.put("facilityAddress", address.getText().toString());
                        map.put("facilityCity", city.getText().toString());
                        map.put("facilityState", state.getText().toString());
                        map.put("facilityContactPerson", contactperson.getText().toString());
                        map.put("facilityNotes", notes.getText().toString());

                        FirebaseDatabase.getInstance().getReference("Facility")
                                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                .child(phName)
                                .updateChildren(map)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        Toast.makeText(context, "Updated Successfully!!!", Toast.LENGTH_SHORT).show();
                                        dialog.dismiss();
                                    }
                                });
                    }
                });

                dialog.show();
            }
        });


    }

    @NonNull
    @Override
    public FacilityAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.facility_view, parent, false);

        return new FacilityAdapter.MyViewHolder(view);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView name, type, phone, email, address, city, state, contactperson, notes;
        ImageView fUpdate, fDelete;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.showFacilityName);
            type = itemView.findViewById(R.id.showFacilityType);
            phone = itemView.findViewById(R.id.showFacilityPhone);
            email = itemView.findViewById(R.id.showFacilityEmail);
            address = itemView.findViewById(R.id.showFacilityAddress);
            city = itemView.findViewById(R.id.showFacilityCity);
            state = itemView.findViewById(R.id.showFacilityState);
            contactperson = itemView.findViewById(R.id.showFacilityContactPerson);
            notes = itemView.findViewById(R.id.showFacilityNotes);

            fUpdate = itemView.findViewById(R.id.updateFacility);
            fDelete = itemView.findViewById(R.id.deleteFacility);
        }
    }
}

