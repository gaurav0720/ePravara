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

import com.example.epravara.MrProcedure;
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

public class MrProceduresAdapter extends FirebaseRecyclerAdapter<MrProcedure, MrProceduresAdapter.MyViewHolder> {

    private Context context;

    public MrProceduresAdapter(@NonNull FirebaseRecyclerOptions<MrProcedure> options, Context context) {
        super(options);
        this.context = context;
    }

    @Override
    protected void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i, @NonNull final MrProcedure mrProcedure) {

        myViewHolder.date.setText(mrProcedure.getpDate());
        myViewHolder.name.setText(mrProcedure.getpName());
        myViewHolder.reason.setText(mrProcedure.getpReason());

        myViewHolder.procedure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Update/Delete");
                builder.setMessage("Select one option to update or delete your record?")
                        .setCancelable(false)
                        .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                String illdate = mrProcedure.getpDate();
                                FirebaseDatabase.getInstance().getReference("MrProcedures")
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
                                        .setContentHolder(new ViewHolder(R.layout.mrprocedure_update_view))
                                        .setExpanded(false)
                                        .create();

                                View holderView = dialogOne.getHolderView();

                                final EditText name = holderView.findViewById(R.id.procedureUpdateName);
                                final EditText reason = holderView.findViewById(R.id.procedureUpdateReason);

                                Button update = holderView.findViewById(R.id.updateProcedureOne);

                                name.setText(mrProcedure.getpName());
                                reason.setText(mrProcedure.getpReason());

                                update.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {

                                        String phdate = mrProcedure.getpDate();

                                        Map<String, Object> map = new HashMap<>();
                                        map.put("pDate", phdate);
                                        map.put("pName", name.getText().toString());
                                        map.put("pReason", reason.getText().toString());

                                        FirebaseDatabase.getInstance().getReference("MrProcedures")
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
                .inflate(R.layout.mrprocedure_view, parent, false);

        return new MyViewHolder(view);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView date, name, reason;
        LinearLayout procedure;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            date = itemView.findViewById(R.id.showMrProcedureDate);
            name = itemView.findViewById(R.id.showMrProcedureName);
            reason = itemView.findViewById(R.id.showMrProcedureReason);

            procedure = itemView.findViewById(R.id.procedureLinearLayout);
        }
    }
}
