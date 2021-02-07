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

import com.example.epravara.Physician;
import com.example.epravara.R;
import com.example.epravara.Surrogate;
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

public class SurrogateAdapter extends FirebaseRecyclerAdapter<Surrogate, SurrogateAdapter.MyViewHolder> {

    private Context context;

    public SurrogateAdapter(@NonNull FirebaseRecyclerOptions<Surrogate> options, Context context) {
        super(options);
        this.context = context;
    }

    @Override
    protected void onBindViewHolder(@NonNull SurrogateAdapter.MyViewHolder myViewHolder, int i, @NonNull final Surrogate surrogate) {
        myViewHolder.name.setText(surrogate.getSurrogateName());
        myViewHolder.relation.setText(surrogate.getSurrogateRelation());
        myViewHolder.phone.setText(surrogate.getSurrogatePhone());
        myViewHolder.address.setText(surrogate.getSurrogateAddress());
        myViewHolder.city.setText(surrogate.getSurrogateCity());
        myViewHolder.state.setText(surrogate.getSurrogateState());
        myViewHolder.pincode.setText(surrogate.getSurrogatePincode());

        myViewHolder.sDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Delete");
                builder.setMessage("Do you really want to delete these surrogate?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                String phName = surrogate.getSurrogateName();
                                FirebaseDatabase.getInstance().getReference("Surrogates")
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

        myViewHolder.sUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final DialogPlus dialog = DialogPlus.newDialog(context)
                        .setGravity(Gravity.CENTER)
                        .setMargin(50,0,50,0)
                        .setContentHolder(new ViewHolder(R.layout.surrogate_update_view))
                        .setExpanded(false)
                        .create();

                View holderView = dialog.getHolderView();

                final EditText relation = holderView.findViewById(R.id.surrogateUpdateRelation);
                final EditText phone = holderView.findViewById(R.id.surrogateUpdatePhone);
                final EditText address = holderView.findViewById(R.id.surrogateUpdateAddress);
                final EditText city = holderView.findViewById(R.id.surrogateUpdateCity);
                final EditText state = holderView.findViewById(R.id.surrogateUpdateState);
                final EditText pincode = holderView.findViewById(R.id.surrogateUpdatePincode);

                Button update = holderView.findViewById(R.id.updateSurrogateOne);

                relation.setText(surrogate.getSurrogateRelation());
                phone.setText(surrogate.getSurrogatePhone());
                address.setText(surrogate.getSurrogateAddress());
                city.setText(surrogate.getSurrogateCity());
                state.setText(surrogate.getSurrogateState());
                pincode.setText(surrogate.getSurrogatePincode());

                update.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        String phName = surrogate.getSurrogateName();

                        Map<String, Object> map = new HashMap<>();
                        map.put("surrogateName", phName);
                        map.put("surrogateRelation", relation.getText().toString());
                        map.put("surrogatePhone", phone.getText().toString());
                        map.put("surrogateAddress", address.getText().toString());
                        map.put("surrogateCity", city.getText().toString());
                        map.put("surrogateState", state.getText().toString());
                        map.put("surrogatePincode", pincode.getText().toString());

                        FirebaseDatabase.getInstance().getReference("Surrogates")
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
    public SurrogateAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.surrogate_view, parent, false);

        return new SurrogateAdapter.MyViewHolder(view);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView name, relation, phone, address, city, state, pincode;
        ImageView sUpdate, sDelete;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.showSurrogateName);
            relation = itemView.findViewById(R.id.showSurrogateRelation);
            phone = itemView.findViewById(R.id.showSurrogatePhone);
            address = itemView.findViewById(R.id.showSurrogateAddress);
            city = itemView.findViewById(R.id.showSurrogateCity);
            state = itemView.findViewById(R.id.showSurrogateState);
            pincode = itemView.findViewById(R.id.showSurrogatePincode);

            sUpdate = itemView.findViewById(R.id.updateSurrogate);
            sDelete = itemView.findViewById(R.id.deleteSurrogate);
        }
    }
}
