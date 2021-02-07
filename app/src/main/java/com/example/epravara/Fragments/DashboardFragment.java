package com.example.epravara.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.epravara.AddInformationActivity;
import com.example.epravara.CounsellingActivity;
import com.example.epravara.ProfileActivity;
import com.example.epravara.R;
import com.example.epravara.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DashboardFragment extends Fragment {

    private TextView btn_fillinfo, btn_getview, btn_showprofile, txtShowFullName;
    private ImageView imageViewOne, imageViewTwo, imageViewThree;
    private LinearLayout linearLayoutOne, linearLayoutTwo, linearLayoutThree;
    private DatabaseReference reference;
    private FirebaseAuth firebaseAuth;
    private String userID;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);

        btn_fillinfo = view.findViewById(R.id.btn_fill_info);
        btn_getview = view.findViewById(R.id.btn_get_graphic);
        btn_showprofile = view.findViewById(R.id.btn_show_profile);
        txtShowFullName = view.findViewById(R.id.txtShowWelcomeText);

        imageViewOne = view.findViewById(R.id.imageOne);
        imageViewTwo = view.findViewById(R.id.imageTwo);
        imageViewThree = view.findViewById(R.id.imageThree);

        linearLayoutOne = view.findViewById(R.id.linearOne);
        linearLayoutTwo = view.findViewById(R.id.linearTwo);
        linearLayoutThree = view.findViewById(R.id.linearThree);

        Animation animFade = AnimationUtils.loadAnimation(getContext(),R.anim.fadein);
        Animation animLtR = AnimationUtils.loadAnimation(getContext(),R.anim.right_to_left);
        Animation animRtL = AnimationUtils.loadAnimation(getContext(),R.anim.left_to_right);
        Animation animTtB = AnimationUtils.loadAnimation(getContext(),R.anim.bottom_to_top);

        btn_fillinfo.setAnimation(animFade);
        btn_getview.setAnimation(animFade);
        btn_showprofile.setAnimation(animFade);

        linearLayoutOne.setAnimation(animTtB);
        linearLayoutTwo.setAnimation(animTtB);
        linearLayoutThree.setAnimation(animTtB);

        imageViewOne.setAnimation(animRtL);
        imageViewTwo.setAnimation(animLtR);
        imageViewThree.setAnimation(animRtL);

        firebaseAuth = FirebaseAuth.getInstance();
        userID = firebaseAuth.getCurrentUser().getUid();

        reference = FirebaseDatabase.getInstance().getReference("Users").child(userID);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);
                String name = user.getFullname();
                txtShowFullName.setText(name);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        btn_fillinfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), AddInformationActivity.class));
            }
        });

        btn_showprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), ProfileActivity.class));
            }
        });

        btn_getview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), CounsellingActivity.class));
            }
        });

        return view;
    }
}
