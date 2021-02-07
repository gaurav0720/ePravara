package com.example.epravara.Adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.opengl.Visibility;
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

import com.example.epravara.Contacts;
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

public class ContactAdapter extends FirebaseRecyclerAdapter<Contacts, ContactAdapter.MyViewHolder> {

   private Context context;

    public ContactAdapter(@NonNull FirebaseRecyclerOptions<Contacts> options, Context context) {
        super(options);
        this.context = context;
    }

    @Override
    protected void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i, @NonNull final Contacts contacts) {

            myViewHolder.name.setText(contacts.getContactName());
            myViewHolder.relation.setText(contacts.getContactRelation());
            myViewHolder.phone.setText(contacts.getContactNumber());

            myViewHolder.eDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle("Delete");
                    builder.setMessage("Do you really want to delete these contact?")
                            .setCancelable(false)
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    String phName = contacts.getContactName();
                                    FirebaseDatabase.getInstance().getReference("EmergencyContacts")
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

            myViewHolder.eUpdate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final DialogPlus dialog = DialogPlus.newDialog(context)
                            .setGravity(Gravity.CENTER)
                            .setMargin(50,0,50,0)
                            .setContentHolder(new ViewHolder(R.layout.emergency_update_view))
                            .setExpanded(false)
                            .create();

                    View holderView = dialog.getHolderView();

                    final EditText relation = holderView.findViewById(R.id.emergencyUpdateRelation);
                    final EditText phone = holderView.findViewById(R.id.emergencyUpdatePhone);

                    Button update = holderView.findViewById(R.id.updateEmergencyContact);

                    relation.setText(contacts.getContactRelation());
                    phone.setText(contacts.getContactNumber());

                    update.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            String phName = contacts.getContactName();

                            Map<String, Object> map = new HashMap<>();
                            map.put("contactName", phName);
                            map.put("contactRelation", relation.getText().toString());
                            map.put("contactNumber", phone.getText().toString());

                            FirebaseDatabase.getInstance().getReference("EmergencyContacts")
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
                .inflate(R.layout.emergency_view, parent, false);

        return new ContactAdapter.MyViewHolder(view);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView name, relation, phone;
        ImageView eUpdate, eDelete;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.showEmergencyName);
            relation = itemView.findViewById(R.id.showEmergencyRelation);
            phone = itemView.findViewById(R.id.showEmergencyPhone);

            eUpdate = itemView.findViewById(R.id.updateEmergency);
            eDelete = itemView.findViewById(R.id.deleteEmergency);

        }
    }
}
