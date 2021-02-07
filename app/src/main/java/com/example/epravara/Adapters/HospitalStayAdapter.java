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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.epravara.HospitalStay;
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

public class HospitalStayAdapter extends FirebaseRecyclerAdapter<HospitalStay, HospitalStayAdapter.MyViewHolder> {

    private Context context;

    public HospitalStayAdapter(@NonNull FirebaseRecyclerOptions<HospitalStay> options, Context context) {
        super(options);
        this.context = context;
    }

    @Override
    protected void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i, @NonNull final HospitalStay hospitalStay) {

        myViewHolder.date.setText(hospitalStay.getHsDate());
        myViewHolder.name.setText(hospitalStay.getHsName());
        myViewHolder.reason.setText(hospitalStay.getHsReason());

        myViewHolder.hospitalstay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Update/Delete");
                builder.setMessage("Select one option to update or delete your record?")
                        .setCancelable(false)
                        .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                String illdate = hospitalStay.getHsDate();
                                FirebaseDatabase.getInstance().getReference("HospitalStays")
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
                        .setNegativeButton("Update", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                final DialogPlus dialogOne = DialogPlus.newDialog(context)
                                        .setGravity(Gravity.CENTER)
                                        .setMargin(50,0,50,0)
                                        .setContentHolder(new ViewHolder(R.layout.hospitalstay_update_view))
                                        .setExpanded(false)
                                        .create();

                                View holderView = dialogOne.getHolderView();

                                final EditText name = holderView.findViewById(R.id.hospitalstayUpdateName);
                                final EditText reason = holderView.findViewById(R.id.hospitalstayUpdateReason);

                                Button update = holderView.findViewById(R.id.updatehospitalstay);

                                name.setText(hospitalStay.getHsName());
                                reason.setText(hospitalStay.getHsReason());

                                update.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {

                                        String phdate = hospitalStay.getHsDate();

                                        Map<String, Object> map = new HashMap<>();
                                        map.put("hsDate", phdate);
                                        map.put("hsName", name.getText().toString());
                                        map.put("hsReason", reason.getText().toString());

                                        FirebaseDatabase.getInstance().getReference("HospitalStays")
                                                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                                .child(phdate)
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
                .inflate(R.layout.hospitalstay_view, parent, false);

        return new MyViewHolder(view);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView date, name, reason;
        LinearLayout hospitalstay;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            date = itemView.findViewById(R.id.showHospitalStayDate);
            name = itemView.findViewById(R.id.showHospitalStayName);
            reason = itemView.findViewById(R.id.showHospitalStayReason);

            hospitalstay = itemView.findViewById(R.id.hospitalstayLinearLayout);
        }
    }
}
