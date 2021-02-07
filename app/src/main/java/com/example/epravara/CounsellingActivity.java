package com.example.epravara;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class CounsellingActivity extends AppCompatActivity {

    private TextView makeCall, startChat, gotoLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_counselling);

        makeCall = findViewById(R.id.callCounselling);
        startChat = findViewById(R.id.chatCounselling);
        gotoLocation = findViewById(R.id.meetCounselling);

        makeCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makePhoneCall();
            }
        });

        startChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makeWhatsAppChat();
            }
        });

        gotoLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goingLocation();
            }
        });

    }

    private void goingLocation() {

        String location = "geo:19.5758018,74.4356973";
        Intent i = new Intent();
        i.setAction(Intent.ACTION_VIEW);
        i.setData(Uri.parse(location));
        startActivity(i);

    }

    private void makeWhatsAppChat() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Start a Chat");
        builder.setMessage("Do you really want to start a chat with a counsellor?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Uri uri = Uri.parse("smsto:" + "91" + "9960465068");
                        Intent intent = new Intent(Intent.ACTION_SENDTO, uri);
                        intent.setPackage("com.whatsapp");
                        startActivity(intent);
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

    public void makePhoneCall(){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Make a Call");
        builder.setMessage("Do you really want to make a call to counsellor?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        Intent callIntent = new Intent(Intent.ACTION_DIAL);
                        callIntent.setData(Uri.parse("tel:9960465068"));

                        if (ActivityCompat.checkSelfPermission(CounsellingActivity.this,
                                Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                            ActivityCompat.requestPermissions(CounsellingActivity.this,
                                    new String[]{Manifest.permission.CALL_PHONE}, 1);
                            return;
                        }
                        startActivity(callIntent);

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

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 1){
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                makePhoneCall();
            }else {
                Toast.makeText(this, "Permission DENIED", Toast.LENGTH_SHORT).show();
            }
        }
    }
}