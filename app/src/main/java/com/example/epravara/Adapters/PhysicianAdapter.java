package com.example.epravara.Adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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

import com.example.epravara.LoginActivity;
import com.example.epravara.Physician;
import com.example.epravara.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.OnItemClickListener;
import com.orhanobut.dialogplus.ViewHolder;

import java.util.HashMap;
import java.util.Map;

public class PhysicianAdapter extends FirebaseRecyclerAdapter<Physician, PhysicianAdapter.MyViewHolder> {

    private Context context;

    public PhysicianAdapter(@NonNull FirebaseRecyclerOptions<Physician> options, Context context) {
        super(options);
        this.context = context;
    }

    @Override
    protected void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i, @NonNull final Physician physician) {
        myViewHolder.name.setText(physician.getpName());
        myViewHolder.speciality.setText(physician.getpSpeciality());
        myViewHolder.phone.setText(physician.getpPhone());
        myViewHolder.email.setText(physician.getpEmail());
        myViewHolder.address.setText(physician.getpAddress());
        myViewHolder.city.setText(physician.getpCity());
        myViewHolder.state.setText(physician.getpState());
        myViewHolder.pincode.setText(physician.getpPincode());

        myViewHolder.pDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Delete");
                builder.setMessage("Do you really want to delete these physician?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                String phName = physician.getpName();
                                FirebaseDatabase.getInstance().getReference("Physician")
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
                        .setContentHolder(new ViewHolder(R.layout.physician_update_view))
                        .setExpanded(false)
                        .create();

                View holderView = dialog.getHolderView();

                final EditText speciality = holderView.findViewById(R.id.updatePhysicianSpeciality);
                final EditText phone = holderView.findViewById(R.id.updatePhysicianPhone);
                final EditText email = holderView.findViewById(R.id.updatePhysicianEmail);
                final EditText address = holderView.findViewById(R.id.updatePhysicianAddress);
                final EditText city = holderView.findViewById(R.id.updatePhysicianCity);
                final EditText state = holderView.findViewById(R.id.updatePhysicianState);
                final EditText pincode = holderView.findViewById(R.id.updatePhysicianPincode);

                Button update = holderView.findViewById(R.id.updatePhysician);

                speciality.setText(physician.getpSpeciality());
                phone.setText(physician.getpPhone());
                email.setText(physician.getpEmail());
                address.setText(physician.getpAddress());
                city.setText(physician.getpCity());
                state.setText(physician.getpState());
                pincode.setText(physician.getpPincode());

                update.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Map<String, Object> map = new HashMap<>();
                        map.put("pSpeciality", speciality.getText().toString());
                        map.put("pPhone", phone.getText().toString());
                        map.put("pEmail", email.getText().toString());
                        map.put("pAddress", address.getText().toString());
                        map.put("pCity", city.getText().toString());
                        map.put("pState", state.getText().toString());
                        map.put("pPincode", pincode.getText().toString());

                        String phName = physician.getpName();

                        FirebaseDatabase.getInstance().getReference("Physician")
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
                .inflate(R.layout.physician_view, parent, false);

        return new MyViewHolder(view);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView name, speciality, phone, email, address, city, state, pincode;
        ImageView pUpdate, pDelete;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.showPName);
            speciality = itemView.findViewById(R.id.showPSpeciality);
            phone = itemView.findViewById(R.id.showPPhone);
            email = itemView.findViewById(R.id.showPEmail);
            address = itemView.findViewById(R.id.showPAddress);
            city = itemView.findViewById(R.id.showPCity);
            state = itemView.findViewById(R.id.showPState);
            pincode = itemView.findViewById(R.id.showPPincode);

            pUpdate = itemView.findViewById(R.id.updatePhysician);
            pDelete = itemView.findViewById(R.id.deletePhysician);
        }
    }
}
