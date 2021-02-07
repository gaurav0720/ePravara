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

public class PharmacyAdapter extends FirebaseRecyclerAdapter<Pharmacy, PharmacyAdapter.MyViewHolder> {

    private Context context;

    public PharmacyAdapter(@NonNull FirebaseRecyclerOptions<Pharmacy> options, Context context) {
        super(options);
        this.context = context;
    }

    @Override
    protected void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i, @NonNull final Pharmacy pharmacy) {
        myViewHolder.name.setText(pharmacy.getPharmacyName());
        myViewHolder.phone.setText(pharmacy.getPharmacyPhone());
        myViewHolder.email.setText(pharmacy.getPharmacyEmail());
        myViewHolder.address.setText(pharmacy.getPharmacyAddress());
        myViewHolder.city.setText(pharmacy.getPharmacyCity());
        myViewHolder.state.setText(pharmacy.getPharmacyState());
        myViewHolder.pharmName.setText(pharmacy.getPharmacistName());

        myViewHolder.pDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Delete");
                builder.setMessage("Do you really want to delete these pharmacy?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                String phName = pharmacy.getPharmacyName();
                                FirebaseDatabase.getInstance().getReference("Pharmacy")
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

        myViewHolder.pUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final DialogPlus dialog = DialogPlus.newDialog(context)
                        .setGravity(Gravity.CENTER)
                        .setMargin(50,0,50,0)
                        .setContentHolder(new ViewHolder(R.layout.pharmacy_update_view))
                        .setExpanded(false)
                        .create();

                View holderView = dialog.getHolderView();

                final EditText phone = holderView.findViewById(R.id.pharmacyUpdatePhone);
                final EditText email = holderView.findViewById(R.id.pharmacyUpdateEmail);
                final EditText address = holderView.findViewById(R.id.pharmacyUpdateAddress);
                final EditText city = holderView.findViewById(R.id.pharmacyUpdateCity);
                final EditText state = holderView.findViewById(R.id.pharmacyUpdateState);
                final EditText pharmacistName = holderView.findViewById(R.id.pharmacyUpdatePharmacistName);

                Button update = holderView.findViewById(R.id.updatePharmacyOne);

                phone.setText(pharmacy.getPharmacyPhone());
                email.setText(pharmacy.getPharmacyEmail());
                address.setText(pharmacy.getPharmacyAddress());
                city.setText(pharmacy.getPharmacyCity());
                state.setText(pharmacy.getPharmacyState());
                pharmacistName.setText(pharmacy.getPharmacistName());

                update.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        String phName = pharmacy.getPharmacyName();

                        Map<String, Object> map = new HashMap<>();
                        map.put("pharmacyName", phName);
                        map.put("pharmacyPhone", phone.getText().toString());
                        map.put("pharmacyEmail", email.getText().toString());
                        map.put("pharmacyAddress", address.getText().toString());
                        map.put("pharmacyCity", city.getText().toString());
                        map.put("pharmacyState", state.getText().toString());
                        map.put("pharmacistName", pharmacistName.getText().toString());

                        FirebaseDatabase.getInstance().getReference("Pharmacy")
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
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.pharmacy_view, parent, false);

        return new PharmacyAdapter.MyViewHolder(view);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView name, phone, email, address, city, state, pharmName;
        ImageView pUpdate, pDelete;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.showPharmacyName);
            phone = itemView.findViewById(R.id.showPharmacyPhone);
            email = itemView.findViewById(R.id.showPharmacyEmail);
            address = itemView.findViewById(R.id.showPharmacyAddress);
            city = itemView.findViewById(R.id.showPharmacyCity);
            state = itemView.findViewById(R.id.showPharmacyState);
            pharmName = itemView.findViewById(R.id.showPharmacistName);

            pUpdate = itemView.findViewById(R.id.updatePharmacy);
            pDelete = itemView.findViewById(R.id.deletePharmacy);
        }
    }
}
